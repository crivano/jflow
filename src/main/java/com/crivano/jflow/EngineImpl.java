package com.crivano.jflow;

import java.util.List;
import java.util.Map;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.ProcessInstanceStatus;
import com.crivano.jflow.model.enm.TaskResultKind;

public class EngineImpl implements Engine {
	private Dao dao;

	private Handler handler;

	public EngineImpl(Dao dao, Handler handler) {
		this.setDao(dao);
		this.setHandler(handler);
	}

	@Override
	public void start(ProcessInstance pi, ProcessDefinition pd, Map<String, Object> variable) throws Exception {
		pi.setProcessDefinition(pd);
		pi.setVariable(variable);
		pi.start();
		TaskResult r = new TaskResult(TaskResultKind.DONE, null, null, null, null);
		resume(pi, r);
	}

	@Override
	public int resume(String event, Integer detourIndex, Map<String, Object> param) throws Exception {
		List<ProcessInstance> l = getDao().listByEvent(event);
		if (l == null || l.size() == 0)
			return 0;
		int i = 0;
		for (ProcessInstance pi : l) {
			if (pi.getStatus() != ProcessInstanceStatus.PAUSED || !event.equals(pi.getEvent()))
				continue;
			TaskDefinition td = pi.getCurrentTaskDefinition();
			Class<? extends Task> clazz = td.getKind().getClazz();
			if (td == null || !(PausableTask.class.isAssignableFrom(clazz)))
				continue;
			PausableTask ti = (PausableTask) clazz.newInstance();
			TaskResult r = ti.resume(td, pi, detourIndex, param, this);
			if (r != null) {
				resume(pi, r);
				i++;
			}
		}
		return i;
	}

	public TaskResult resume(ProcessInstance pi, TaskResult result) throws Exception {
		switch (result.getKind()) {
		case ERROR: {
			TaskDefinition td = pi.getCurrentTaskDefinition();
			// Should we retry after sometime?
			if (result.getError() != null)
				throw new Exception("error processing task " + td.toString(), result.getError());
			else
				throw new Exception("error processing task " + td.toString());
		}
		case PAUSE: {
			TaskDefinition td = pi.getCurrentTaskDefinition();
			if (result.getEvent() == null)
				throw new Exception("error processing task " + td.toString()
						+ ", when 'PAUSE' is returned, an 'event' must be specified.");
			pi.pause(result.getEvent(), result.getResponsible());
			if (getDao() != null)
				getDao().persist(pi);
			if (getHandler() != null)
				getHandler().afterPause(pi, result);
			break;
		}
		case DONE:
			pi.resume();

			int prox = pi.getCurrentIndex() + 1;
			if (result.getDetour() != null && result.getDetour().length() > 0)
				prox = pi.getIndexById(result.getDetour());
			else if (pi.getCurrentTaskDefinition() != null && pi.getCurrentTaskDefinition().getAfter() != null)
				prox = pi.getIndexById(pi.getCurrentTaskDefinition().getAfter());
			if (prox >= pi.getProcessDefinition().getTaskDefinition().size()) {
				pi.end();
				if (getDao() != null)
					getDao().persist(pi);
				break;
			}

			pi.setCurrentIndex(prox);
			TaskDefinition td = pi.getCurrentTaskDefinition();
			if (getDao() != null)
				getDao().persist(pi);

			Task ti = td.getKind().getClazz().newInstance();
			TaskDefinition proxtd = pi.getTaskDefinitionByIndex(prox);
			TaskResult proxr = ti.execute(proxtd, pi, this);
			resume(pi, proxr);
		}
		return result;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

}

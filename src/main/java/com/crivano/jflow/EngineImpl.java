package com.crivano.jflow;

import java.util.List;
import java.util.Map;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.ResponsibleKind;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.model.TaskDefinitionVariable;
import com.crivano.jflow.model.TaskKind;
import com.crivano.jflow.model.enm.ProcessInstanceStatus;
import com.crivano.jflow.model.enm.TaskResultKind;

public class EngineImpl<PD extends ProcessDefinition<TD>, TD extends TaskDefinition<TK, RK, DV, DD>, R extends Responsible, TK extends TaskKind, RK extends ResponsibleKind, DV extends TaskDefinitionVariable, DD extends TaskDefinitionDetour, PI extends ProcessInstance<PD, TD, R>, H extends Handler<PI, R>, D extends Dao<PI>>
		implements Engine<PI, R, H> {
	private D dao;

	private H handler;

	public EngineImpl(D dao, H handler) {
		this.setDao(dao);
		this.setHandler(handler);
	}

	@Override
	public void start(PI pi) throws Exception {
		pi.start();
		TaskResult r = new TaskResult(TaskResultKind.DONE, null, null, null, null);
		resume(pi, r);
	}

	// Resume from a user event
	@Override
	public int resume(String event, Integer detourIndex, Map<String, Object> param) throws Exception {
		List<PI> l = getDao().listByEvent(event);
		if (l == null || l.size() == 0)
			return 0;
		int i = 0;
		for (PI pi : l) {
			if (pi.getStatus() != ProcessInstanceStatus.PAUSED
					|| !(pi.getIdEvent() != null && pi.getIdEvent().startsWith(event)))
				continue;
			TD td = pi.getCurrentTaskDefinition();
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

	// Process other tasks after the current task has finished
	public TaskResult resume(PI pi, TaskResult result) throws Exception {
		switch (result.getKind()) {
		case ERROR: {
			TD td = pi.getCurrentTaskDefinition();
			if (result.getError() != null)
				throw new Exception("error processing task " + td.toString(), result.getError());
			else
				throw new Exception("error processing task " + td.toString());
		}
		case PAUSE: {
			TD td = pi.getCurrentTaskDefinition();
			if (result.getEvent() == null)
				throw new Exception("error processing task " + td.toString()
						+ ", when 'PAUSE' is returned, an 'event' must be specified.");
			pi.pause(result.getEvent(), (R) result.getResponsible());
			if (getDao() != null)
				getDao().persist(pi);
			if (getHandler() != null)
				getHandler().afterPause(pi, result);
			break;
		}
		case DONE:
			pi.resume();

			Integer from = pi.getCurrentIndex() >= 0 ? pi.getCurrentIndex() : null;

			int to = pi.getCurrentIndex() + 1;
			if (result.getDetour() != null && result.getDetour().length() > 0)
				to = pi.getIndexById(result.getDetour());
			else if (pi.getCurrentTaskDefinition() != null && pi.getCurrentTaskDefinition().getAfter() != null)
				to = pi.getIndexById(pi.getCurrentTaskDefinition().getAfter());
			if (to >= pi.getProcessDefinition().getTaskDefinition().size()) {
				pi.end();
				if (getDao() != null)
					getDao().persist(pi);
				if (getHandler() != null)
					getHandler().afterTransition(pi, from, pi.getCurrentIndex());
				break;
			}
			execute(pi, from, to);
		}
		return result;
	}

	// Retry current task, typically in case of a previous error
	@Override
	public TaskResult resume(PI pi) throws Exception {
		if (pi.getStatus() != ProcessInstanceStatus.RESUMING)
			return null;
		TD td = pi.getCurrentTaskDefinition();
		Class<? extends Task> clazz = td.getKind().getClazz();
		Task ti = (Task) clazz.newInstance();
		TaskResult r = ti.execute(td, pi, this);
		return resume(pi, r);
	}

	// Force a jump from one task to another
	@Override
	public TaskResult execute(PI pi, Integer from, int to) throws Exception {
		pi.resume();

		pi.setCurrentIndex(to);
		TaskDefinition td = pi.getCurrentTaskDefinition();
		if (getDao() != null)
			getDao().persist(pi);
		if (getHandler() != null)
			getHandler().afterTransition(pi, from, pi.getCurrentIndex());

		Task ti = td.getKind().getClazz().newInstance();
		TaskDefinition tdTo = pi.getTaskDefinitionByIndex(to);
		TaskResult trTo = ti.execute(tdTo, pi, this);
		return resume(pi, trTo);
	}

	public H getHandler() {
		return handler;
	}

	public void setHandler(H handler) {
		this.handler = handler;
	}

	public D getDao() {
		return dao;
	}

	public void setDao(D dao) {
		this.dao = dao;
	}

}

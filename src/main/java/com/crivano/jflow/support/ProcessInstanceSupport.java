package com.crivano.jflow.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.ProcessInstanceStatus;

public abstract class ProcessInstanceSupport
		implements ProcessInstance<ProcessDefinitionSupport, TaskDefinitionSupport, ResponsibleSupport> {
	private String codigo;

	private ProcessDefinitionSupport definicao;

	private Integer indiceCorrente = null;

	private Map<String, Object> variavel = new TreeMap<>();

	private List<ProcessInstanceVariableSupport> listaDeVariaveis = new ArrayList<>();

	private ProcessInstanceStatus status = ProcessInstanceStatus.INACTIVE;

	private Date dtEvento;

	private String evento;

	ResponsibleSupport responsavel;

	public ProcessInstanceSupport(ProcessDefinitionSupport definicao, Map<String, Object> variavel) {
		this.definicao = definicao;
		if (variavel != null)
			this.variavel.putAll(variavel);
	}

	@Override
	public void start() {
		indiceCorrente = -1;
		status = ProcessInstanceStatus.STARTED;
	}

	@Override
	public void pause(String evento, ResponsibleSupport responsavel) {
		this.evento = evento;
		this.responsavel = responsavel;
		this.dtEvento = new Date();
		status = ProcessInstanceStatus.PAUSED;
	}

	@Override
	public void resume() {
		this.evento = null;
		this.responsavel = null;
		this.dtEvento = null;
		status = ProcessInstanceStatus.RESUMING;
	}

	@Override
	public void end() {
		indiceCorrente = null;
		status = ProcessInstanceStatus.FINISHED;
	}

	@Override
	public TaskDefinitionSupport getCurrentTaskDefinition() {
		return getTaskDefinitionByIndex(indiceCorrente);
	}

	@Override
	public TaskDefinitionSupport getTaskDefinitionByIndex(int i) {
		if (i < 0 || i >= definicao.getTaskDefinition().size())
			return null;
		return definicao.getTaskDefinition().get(i);
	}

	// Se for "fim" retorna length + 1
	@Override
	public int getIndexById(String id) {
		int i = 0;
		for (TaskDefinition td : definicao.getTaskDefinition()) {
			if (td.getIdentifier().equals(id))
				return i;
			i++;
		}
		return i;
	}

	public void onLoad() {
		variavel.clear();
		for (ProcessInstanceVariableSupport v : listaDeVariaveis) {
			if (v.number != null)
				variavel.put(v.id, v.number);
			else if (v.bool != null)
				variavel.put(v.id, v.bool);
			else if (v.date != null)
				variavel.put(v.id, v.date);
			else
				variavel.put(v.id, v.string);
		}
	}

	public void onSave() throws Exception {
		listaDeVariaveis.clear();
		for (String k : variavel.keySet()) {
			ProcessInstanceVariableSupport v = new ProcessInstanceVariableSupport();
			v.id = k;
			Object o = variavel.get(k);
			if (o instanceof Boolean)
				v.bool = (Boolean) o;
			else if (o instanceof Double)
				v.number = (Double) o;
			else if (o instanceof Date)
				v.date = (Date) o;
			else
				v.string = o.toString();
			listaDeVariaveis.add(v);
		}
	}

	@Override
	public abstract ResponsibleSupport calcResponsible(TaskDefinitionSupport tarefa);

//	@Override
//	public String getCode() {
//		return codigo;
//	}

	@Override
	public ProcessDefinitionSupport getProcessDefinition() {
		return definicao;
	}

	@Override
	public Integer getCurrentIndex() {
		return indiceCorrente;
	}

	@Override
	public Map<String, Object> getVariable() {
		return variavel;
	}

	@Override
	public ProcessInstanceStatus getStatus() {
		return status;
	}

	@Override
	public Date getDtEvent() {
		return dtEvento;
	}

	@Override
	public String getIdEvent() {
		return evento;
	}

	@Override
	public ResponsibleSupport getResponsible() {
		return responsavel;
	}

	@Override
	public void setCurrentIndex(int indiceCorrente) {
		this.indiceCorrente = indiceCorrente;
	}

	@Override
	public void setProcessDefinition(ProcessDefinitionSupport definicao) {
		this.definicao = definicao;
	}

	@Override
	public void setVariable(Map<String, Object> variavel) {
		this.variavel = variavel;
	}

}

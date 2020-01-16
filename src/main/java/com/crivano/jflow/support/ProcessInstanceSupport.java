package com.crivano.jflow.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.crivano.jflow.model.Principal;
import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.enm.ProcessInstanceStatus;

public abstract class ProcessInstanceSupport implements ProcessInstance {
	private String codigo;

	private ProcessDefinition definicao;

	private Principal principal;

	private Integer indiceCorrente = null;

	private Map<String, Object> variavel = new TreeMap<>();

	private List<ProcessInstanceVariableSupport> listaDeVariaveis = new ArrayList<>();

	private ProcessInstanceStatus status = ProcessInstanceStatus.INACTIVE;

	private Date dtEvento;

	private String evento;

	Responsible responsavel;

	public ProcessInstanceSupport(ProcessDefinition definicao, Map<String, Object> variavel, Principal principal) {
		this.definicao = definicao;
		if (variavel != null)
			this.variavel.putAll(variavel);
		if (principal != null) {
			this.principal = principal;
			this.variavel.put("_keyPrincipal", this.principal.toString());
			this.variavel.put("_codPrincipal", this.principal.getCode());
		}
	}

	@Override
	public void start() {
		indiceCorrente = -1;
		status = ProcessInstanceStatus.STARTED;
	}

	@Override
	public void pause(String evento, Responsible responsavel) {
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
	public TaskDefinition getCurrentTaskDefinition() {
		return getTaskDefinitionByIndex(indiceCorrente);
	}

	@Override
	public TaskDefinition getTaskDefinitionByIndex(int i) {
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
	public abstract Responsible calcResponsible(TaskDefinition tarefa);

	@Override
	public String getCode() {
		return codigo;
	}

	@Override
	public ProcessDefinition getProcessDefinition() {
		return definicao;
	}

	public Principal getPrincipal() {
		return principal;
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
	public String getEvent() {
		return evento;
	}

	@Override
	public Responsible getResponsible() {
		return responsavel;
	}

	@Override
	public void setCurrentIndex(int indiceCorrente) {
		this.indiceCorrente = indiceCorrente;
	}

	@Override
	public void setProcessDefinition(ProcessDefinition definicao) {
		this.definicao = definicao;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	@Override
	public void setVariable(Map<String, Object> variavel) {
		this.variavel = variavel;
	}

}

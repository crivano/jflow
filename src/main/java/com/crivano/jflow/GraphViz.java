package com.crivano.jflow;

import java.util.ArrayList;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.support.DetourSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;

public class GraphViz {
	private static String graphElement(String shape, String color, TaskDefinition n, TaskDefinition nextn,
			String resp) {
		String s = "\"" + n.getId() + "\"[shape=\"" + shape + "\"][color=\"" + color + "\"][fontcolor=\"" + color
				+ "\"][label=<" + n.getTitle()
				+ (resp != null ? "<br/><font point-size=\"10pt\">" + resp + "</font>" : "") + ">];";
		if (n.getKind() != null || n.getId().equals("start")) {
			if (n.getDetour() != null && n.getDetour().size() > 0) {
				for (TaskDefinitionDetour tr : n.getDetour()) {
					if (tr.getTaskId() != null && tr.getTaskId().length() > 0)
						s += "\"" + n.getId() + "\"->\"" + tr.getTaskId() + "\"";
					else if (nextn != null)
						s += "\"" + n.getId() + "\"->\"" + nextn.getId() + "\"";
					else
						s += "\"" + n.getId() + "\"->\"finish\"";
					if (tr.getTitle() != null && tr.getTitle().trim().length() != 0)
						s += " [label=\"" + tr.getTitle() + "\"]";
					s += ";";
				}
			} else if (n.getAfter() != null) {
				s += "\"" + n.getId() + "\"->\"" + n.getAfter() + "\";";
			} else if (nextn != null) {
				s += "\"" + n.getId() + "\"->\"" + nextn.getId() + "\";";
			} else {
				s += "\"" + n.getId() + "\"->\"finish\";";
			}
		}
		return s;
	}

	public static String getDot(ProcessInstance pi, String labelStart, String labelFinish) {
		ProcessDefinition wf = pi.getProcessDefinition();
		String s = ""; // "digraph G { graph[size=\"3,3\"];";
		if (wf.getTaskDefinition() != null && wf.getTaskDefinition().size() > 0) {
			ArrayList<TaskDefinitionDetour> desvios = new ArrayList<>();
			desvios.add(new DetourSupport(null, wf.getTaskDefinition().get(0).getId(), null));
			s += graphElement("oval", "black",
					new TaskDefinitionSupport("start", null, null, labelStart, null, null, null, desvios, null, null),
					null, null);
			s += graphElement("oval", "black",
					new TaskDefinitionSupport("finish", null, null, labelFinish, null, null, null, null, null, null),
					null, null);

			for (int i = 0; i < wf.getTaskDefinition().size(); i++) {
				TaskDefinition n = wf.getTaskDefinition().get(i);
				Responsible responsible = pi.calcResponsible(n);
				String resp = responsible != null ? responsible.getInitials() : null;
				s += graphElement(n.getKind().getGraphKind(), pi.getCurrentIndex() == i ? "blue" : "black", n,
						i < wf.getTaskDefinition().size() - 1 ? wf.getTaskDefinition().get(i + 1) : null, resp);
			}
		}
		// s += "}";
		// System.out.println(s);
		return s;
	}

}

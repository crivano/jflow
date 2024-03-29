package com.crivano.jflow;

import java.util.ArrayList;
import java.util.List;

import com.crivano.jflow.model.ProcessDefinition;
import com.crivano.jflow.model.ProcessInstance;
import com.crivano.jflow.model.Responsible;
import com.crivano.jflow.model.TaskDefinition;
import com.crivano.jflow.model.TaskDefinitionDetour;
import com.crivano.jflow.support.DetourSupport;
import com.crivano.jflow.support.TaskDefinitionSupport;

public class GraphViz {
	private static String graphElement(String shape, String color, TaskDefinition n, String resp, String tooltip) {
		String s = "\"" + n.getIdentifier() + "\"" + (tooltip != null ? "[tooltip=\"" + tooltip + "\"]" : "" ) 
				+ "[shape=\"" + shape + "\"][color=\"" + color + "\"][fontcolor=\""
				+ color + "\"][label=<"
				+ (n != null && n.getKind() != null && n.getKind().getGraphTitle() != null
						? "<font point-size=\"10pt\">" + n.getKind().getGraphTitle() + "</font><br/>"
						: "")
				+ n.getTitle() + (resp != null ? "<br/><font point-size=\"10pt\">" + resp + "</font>" : "") + ">];";
		if (n != null && n.getKind() != null && n.getKind().getGraphTitle() != null && "rectangle".equals(shape)) {
			shape = "plaintext";
			s = "\"" + n.getIdentifier() + "\"" + (tooltip != null ? "[tooltip=\"" + tooltip + "\"]" : "" ) 
					+ "[shape=\"" + shape + "\"][color=\"" + color + "\"][fontcolor=\"" + color
					+ "\"][label=<<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">"
					+ (n != null && n.getKind() != null && n.getKind().getGraphTitle() != null
							? "<TR><TD><font point-size=\"10pt\">" + n.getKind().getGraphTitle() + "</font></TD></TR>"
							: "")
					+ "<TR><TD CELLSPACING=\"0\">" + n.getTitle() + ""
					+ (resp != null ? "<br/><font point-size=\"10pt\">" + resp + "</font>" : "")
					+ "</TD></TR></TABLE>>];";
		}
		if (n.getKind() != null || n.getIdentifier().equals("start")) {
			if (n.getDetour() != null && n.getDetour().size() > 0) {
				for (TaskDefinitionDetour dd : (List<TaskDefinitionDetour>) n.getDetour()) {
					if (dd.getTaskIdentifier() != null && dd.getTaskIdentifier().length() > 0)
						s += "\"" + n.getIdentifier() + "\"->\"" + dd.getTaskIdentifier() + "\"";
					else if (n.getAfter() != null)
						s += "\"" + n.getIdentifier() + "\"->\"" + n.getAfter() + "\"";
					else
						s += "\"" + n.getIdentifier() + "\"->\"finish\"";
					if (dd.getTitle() != null && dd.getTitle().trim().length() != 0)
						s += " [label=\"" + dd.getTitle() + "\"]";
					s += ";";
				}
			} else if (n.getAfter() != null) {
				s += "\"" + n.getIdentifier() + "\"->\"" + n.getAfter() + "\";";
			} else {
				s += "\"" + n.getIdentifier() + "\"->\"finish\";";
			}
		}
		return s;
	}

	public static String getDot(ProcessInstance pi, String labelStart, String labelFinish) {
		ProcessDefinition wf = pi.getProcessDefinition();
		String s = ""; // "digraph G { graph[size=\"3,3\"];";
		if (wf.getTaskDefinition() != null && wf.getTaskDefinition().size() > 0) {
			List<DetourSupport> desvios = new ArrayList<>();
			desvios.add(
					new DetourSupport(null, ((TaskDefinition) wf.getTaskDefinition().get(0)).getIdentifier(), null));
			s += graphElement("oval", "black",
					new TaskDefinitionSupport("start", null, labelStart, null, null, null, desvios, null, null) {

					}, null, null);
			s += graphElement("oval", pi.getCurrentIndex() == null ? "blue" : "black",
					new TaskDefinitionSupport("finish", null, labelFinish, null, null, null, null, null, null), null, null);

			for (int i = 0; i < wf.getTaskDefinition().size(); i++) {
				TaskDefinition n = (TaskDefinition) wf.getTaskDefinition().get(i);
				Responsible responsible = pi.calcResponsible(n);
				String resp = responsible != null ? responsible.getInitials() : null;
				String tooltip = responsible != null ? responsible.getTooltip() : null;
				s += graphElement(n.getKind().getGraphKind(),
						(pi.getCurrentIndex() != null && pi.getCurrentIndex() == i) ? "blue" : "black", n, resp, tooltip);
			}
		}
		// s += "}";
		// System.out.println(s);
		return s;
	}

	public static String getDot(ProcessDefinition wf, String labelStart, String labelFinish) {
		String s = ""; // "digraph G { graph[size=\"3,3\"];";
		if (wf.getTaskDefinition() != null && wf.getTaskDefinition().size() > 0) {
			List<DetourSupport> desvios = new ArrayList<>();
			desvios.add(
					new DetourSupport(null, ((TaskDefinition) wf.getTaskDefinition().get(0)).getIdentifier(), null));
			s += graphElement("oval", "black",
					new TaskDefinitionSupport("start", null, labelStart, null, null, null, desvios, null, null) {

					}, null, null);
			s += graphElement("oval", "black",
					new TaskDefinitionSupport("finish", null, labelFinish, null, null, null, null, null, null), null, null);

			for (int i = 0; i < wf.getTaskDefinition().size(); i++) {
				TaskDefinition n = (TaskDefinition) wf.getTaskDefinition().get(i);
				s += graphElement(n.getKind().getGraphKind(), "black", n, n.getResponsibleDescription(), n.getTooltip());
			}
		}
		// s += "}";
		// System.out.println(s);
		return s;
	}

}

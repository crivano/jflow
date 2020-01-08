package com.crivano.jflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.crivano.jflow.model.IDefinicaoDeProcedimento;
import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IDesvio;
import com.crivano.jflow.model.IParte;
import com.crivano.jflow.model.IProcedimento;
import com.crivano.jflow.support.DefinicaoDeTarefa;
import com.crivano.jflow.support.Desvio;
import com.crivano.jflow.support.TipoDeTarefaEnum;

public class GraficoDeProcedimento {
	private static String graphElement(String shape, String color, IDefinicaoDeTarefa n, IDefinicaoDeTarefa nextn,
			String resp) {
		String s = "\"" + n.getId() + "\"[shape=\"" + shape + "\"][color=\"" + color + "\"][fontcolor=\"" + color
				+ "\"][label=<" + n.getTitulo()
				+ (resp != null ? "<br/><font point-size=\"10pt\">" + resp + "</font>" : "") + ">];";
		if (n.getTipo() != null || n.getId().equals("ini")) {
			if (n.getDesvio() != null && n.getDesvio().size() > 0) {
				for (IDesvio tr : n.getDesvio()) {
					if (tr.getTarefa() != null && tr.getTarefa().length() > 0)
						s += "\"" + n.getId() + "\"->\"" + tr.getTarefa() + "\"";
					else if (nextn != null)
						s += "\"" + n.getId() + "\"->\"" + nextn.getId() + "\"";
					else
						s += "\"" + n.getId() + "\"->\"fim\"";
					if (tr.getTitulo() != null && tr.getTitulo().trim().length() != 0)
						s += " [label=\"" + tr.getTitulo() + "\"]";
					s += ";";
				}
			} else if (n.getDepois() != null) {
				s += "\"" + n.getId() + "\"->\"" + n.getDepois() + "\";";
			} else if (nextn != null) {
				s += "\"" + n.getId() + "\"->\"" + nextn.getId() + "\";";
			} else {
				s += "\"" + n.getId() + "\"->\"fim\";";
			}
		}
		return s;
	}

	public static String getDot(IProcedimento procedimento) {
		IDefinicaoDeProcedimento wf = procedimento.getDefinicao();
		Map<TipoDeTarefaEnum, String> tipos = new HashMap<TipoDeTarefaEnum, String>() {
			{
				put(TipoDeTarefaEnum.INCLUIR_DOCUMENTO, "note");
				put(TipoDeTarefaEnum.FORMULARIO, "rectangle");
				put(TipoDeTarefaEnum.DECISAO, "diamond");
				put(TipoDeTarefaEnum.EMAIL, "folder");
			}
		};
		String s = ""; // "digraph G { graph[size=\"3,3\"];";
		if (wf.getTarefa() != null && wf.getTarefa().size() > 0) {
			ArrayList<Desvio> desvios = new ArrayList<Desvio>();
			desvios.add(new Desvio(null, wf.getTarefa().get(0).getId(), null));
			s += graphElement("oval", "black", new DefinicaoDeTarefa("ini", null, null, "Início", null, null, null,
					null, null, null, null, desvios, null, null), null, null);
			s += graphElement("oval", "black", new DefinicaoDeTarefa("fim", null, null, "Fim", null, null, null, null,
					null, null, null, null, null, null), null, null);

			for (int i = 0; i < wf.getTarefa().size(); i++) {
				IDefinicaoDeTarefa n = wf.getTarefa().get(i);
				IParte responsavel = procedimento.calcularResponsavel(n);
				String resp = responsavel != null ? responsavel.nomeCompacto() : null;
				s += graphElement(tipos.get(n.getTipo()), procedimento.getIndiceCorrente() == i ? "blue" : "black", n,
						i < wf.getTarefa().size() - 1 ? wf.getTarefa().get(i + 1) : null, resp);
			}
		}
		// s += "}";
		return s;
	}

}

package com.crivano.jflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crivano.jflow.model.IDao;
import com.crivano.jflow.model.IDefinicaoDeProcedimento;
import com.crivano.jflow.model.IDefinicaoDeTarefa;
import com.crivano.jflow.model.IHandler;
import com.crivano.jflow.model.IMotorDeProcedimento;
import com.crivano.jflow.model.IPrincipal;
import com.crivano.jflow.model.IProcedimento;
import com.crivano.jflow.model.IRef;
import com.crivano.jflow.model.enm.StatusDeProcedimentoEnum;
import com.crivano.jflow.support.Procedimento;
import com.crivano.jflow.support.TipoDeTarefaEnum;

public class MotorDeProcedimento implements IMotorDeProcedimento {

	private static Map<String, Class<? extends ITarefa>> classes = null;

	private static synchronized Class<? extends ITarefa> getClasse(String classe) throws Exception {
		if (classes == null) {
			classes = new HashMap<>();
			for (TipoDeTarefaEnum t : TipoDeTarefaEnum.values())
				classes.put(t.getClazz(),
						(Class<? extends ITarefa>) MotorDeProcedimento.class.getClassLoader().loadClass(t.getClazz()));
		}
		return classes.get(classe);
	}

	private IDao dao;

	private IHandler handler;

	public MotorDeProcedimento(IDao dao, IHandler handler) {
		this.dao = dao;
		this.handler = handler;
	}

	@Override
	public void iniciar(IProcedimento pi, IDefinicaoDeProcedimento definicao, IRef<IPrincipal> principal,
			Map<String, Object> variavel) throws Exception {
		pi.setDefinicao(definicao);
		pi.setRefPrincipal(principal);
		pi.setVariavel(variavel);
		pi.iniciar();
		ResultadoDeTarefa r = new ResultadoDeTarefa(TipoDeResultadoDeTarefaEnum.CONCLUIDO, null, null, null, null);
		prosseguir(pi, r);
	}

	@Override
	public int sinalizar(String namespace, String evento, Integer indiceDesvio, Map<String, Object> parametro)
			throws Exception {
		List<Procedimento> l = dao.loadAllByConditionByAncestor(Procedimento.class, "evento", evento, namespace);
		if (l == null || l.size() == 0)
			return 0;
		int i = 0;
		for (IProcedimento pi : l) {
			if (pi.getStatus() != StatusDeProcedimentoEnum.AGUARDANDO || !evento.equals(pi.getEvento()))
				continue;
			IDefinicaoDeTarefa td = pi.getTarefaCorrente();
			Class<? extends ITarefa> clazz = getClasse(td.getTipo().getClazz());
			if (td == null || !(ITarefaContinuavel.class.isAssignableFrom(clazz)))
				continue;
			ITarefaContinuavel ti = (ITarefaContinuavel) clazz.newInstance();
			ResultadoDeTarefa r = ti.continuar(td, pi, indiceDesvio, parametro);
			if (r != null) {
				prosseguir(pi, r);
				i++;
			}
		}
		return i;
	}

	public ResultadoDeTarefa prosseguir(IProcedimento pi, ResultadoDeTarefa resultado) throws Exception {
		switch (resultado.getTipo()) {
		case ERRO: {
			IDefinicaoDeTarefa td = pi.getTarefaCorrente();
			// Será que eu teria de agendar um retry depois de algum tempo?
			if (resultado.getErro() != null)
				throw new Exception("Erro executando tarefa " + td.toString(), resultado.getErro());
			else
				throw new Exception("Erro executando tarefa " + td.toString());
		}
		case AGUARDAR: {
			IDefinicaoDeTarefa td = pi.getTarefaCorrente();
			if (resultado.getEvento() == null)
				throw new Exception("Erro executando tarefa " + td.toString()
						+ ", retorno do tipo 'AGUARDAR' requer que seja informado o evento.");
			pi.aguardar(resultado.getEvento(), resultado.getResponsavel());
			dao.persist(pi);

			if (handler != null)
				handler.afterPause(pi, resultado);

			// Tramitar se for necessário
//			if (pi.getRefPrincipal() != null && resultado.getResponsavel() != null) {
//				IPrincipal principal = pi.getRefPrincipal().get();
//				if (principal instanceof Documento) {
//					Documento doc = (Documento) principal;
//					if (!doc.isEnviado())
//						DocumentoBL.enviar(doc, resultado.getResponsavel());
//				}
//			}
			break;
		}
		case CONCLUIDO:
			pi.prosseguir();

			int prox = pi.getIndiceCorrente() + 1;
			if (resultado.getDesvio() != null && resultado.getDesvio().length() > 0)
				prox = pi.getIndicePorId(resultado.getDesvio());
			else if (pi.getTarefaCorrente() != null && pi.getTarefaCorrente().getDepois() != null)
				prox = pi.getIndicePorId(pi.getTarefaCorrente().getDepois());
			if (prox >= pi.getDefinicao().getTarefa().size()) {
				pi.finalizar();
				dao.persist(pi);
				break;
			}

			pi.setIndiceCorrente(prox);
			IDefinicaoDeTarefa td = pi.getTarefaCorrente();
			dao.persist(pi);

			ITarefa ti = getClasse(td.getTipo().getClazz()).newInstance();
			IDefinicaoDeTarefa proxtd = pi.getTarefaPorIndice(prox);
			ResultadoDeTarefa proxr = ti.executar(proxtd, pi);
			prosseguir(pi, proxr);
		}
		return resultado;
	}

}

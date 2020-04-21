package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

	private List<Processo> processos = new ArrayList<>();
	private String mensagem = "";

	private int idCount = 0;
	private int timeCount = 0;
	private int idCoordenador = 0;

	private int TEMPO_EXECUCAO = 600;

	private Timer timer = new Timer();

	public Controller() {
		// Auto-generated constructor stub
	}

	public void start() {
		criaProcesso(30000); // 30s
		inativarCoordenador(100000); // 100s
		inativarProcessoAleatorio(80000); // 80s
		enviaRequisicao(25000); // 25s
		timerDoConsole(1000); // 1s
	}

	private void timerDoConsole(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println(timeCount++ + "s " + mensagem);
				mensagem = "";

				if (timeCount > TEMPO_EXECUCAO) {
					timer.cancel();
				}
			}
		}, 1, interval);
	}

	private void enviaRequisicao(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				mensagem += "\n ENVIAR REQUISIÇÃO: " + enviaRequisicacao();
			}

			private String enviaRequisicacao() {
				if (processos.isEmpty()) {
					return "Nenhum processo ativo";
				}

				if (idCoordenador == 0) {
					elegerCoordenador();
				}

				for (Processo processo : processos) {
					if (processo.isAtivo() && !processo.isCoordernador()) {
						return "Processo " + processo.getId() + " enviando requisição para o Coordenador "
								+ idCoordenador;
					}
				}

				return "Nenhum processo não coordenador ativo.";
			}
		}, interval, interval);
	}

	private void criaProcesso(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				idCount++;
				processos.add(new Processo(idCount, false, true, buscarProcessoAnterior()));
				mensagem += "\n CRIAR PROCESSO: Criado novo processo " + idCount;
			}
		}, interval, interval);
	}

	private void inativarProcessoAleatorio(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				mensagem += "\n INATIVAR PROCESSO: " + inativarProcesso(processos);
			}

			private String inativarProcesso(List<Processo> list) {
				if (list.isEmpty()) {
					return "Nenhum processo encontrado.";
				}

				for (Processo processo : list) {
					if (processo.isAtivo() && !processo.isCoordernador()) {
						processo.setAtivo(false);
						return "Processo " + processo.getId() + " foi inativado.";
					}
				}

				return "Nenhum processo ativo encontrado.";
			}
		}, interval, interval);
	}

	private void inativarCoordenador(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				mensagem += "\n INATIVAR COORDENADOR: " + inativarCoordenador(processos);
				idCoordenador = 0;
			}

			private String inativarCoordenador(List<Processo> list) {
				if (list.isEmpty()) {
					return "Nenhum processo encontrado.";
				}

				for (Processo processo : list) {
					if (processo.isAtivo() && processo.isCoordernador()) {
						processo.setAtivo(false);
						return "Processo Coordenador " + processo.getId() + " foi inativado.";
					}
				}

				return "Nenhum processo coordenador ativo encontrado.";
			}
		}, interval, interval);
	}

	private void elegerCoordenador() {
		Processo processoIdMaior = null;
		Processo processoAtual = processos.get(processos.size() - 1);

		while (processoAtual.getProxProcesso() != null) {
			if (processoAtual.isAtivo()
					&& (processoIdMaior == null || processoAtual.getId() > processoIdMaior.getId())) {
				processoIdMaior = processoAtual;
			}
			processoAtual = processoAtual.getProxProcesso();
		}

		if (processoIdMaior == null) {
			processoIdMaior = processoAtual;
		}

		processoIdMaior.setCoordernador(true);
		idCoordenador = processoIdMaior.getId();
	}

	private Processo buscarProcessoAnterior() {
		if (processos.isEmpty()) {
			return null;
		}

		return processos.get(idCount - 2);
	}

}

package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

	private List<Processo> processos = new ArrayList<Processo>();
	private List<Processo> fila = new ArrayList<Processo>();
	private Recurso recurso = new Recurso();

	private Processo coordenador = new Processo(1, true);
	private int indexCoordenador = 0;

	private int TEMPO_EXECUCAO_TOTAL = 600;
	private int TEMPO_EXECUTANDO = 0;
	private String mensagem = "";

	private Timer timer = new Timer();
	private NumeroAleatorio numeroAleatorio = new NumeroAleatorio();

	public Controller() {
		// Auto-generated constructor stub
	}

	public void start() {
		processos.add(coordenador);

		criaProcesso(40000); // 40s
		matarCoordenador(60000); // 60s
		processaRecurso(numeroAleatorio.buscaNumeroAleatorio(10000, 25000)); // 10s a 25s
		timerDoConsole(1000); // 1s
	}

	private void timerDoConsole(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println(TEMPO_EXECUTANDO++ + "s " + mensagem);
				mensagem = "";

				if (TEMPO_EXECUTANDO > TEMPO_EXECUCAO_TOTAL) {
					timer.cancel();
				}
			}
		}, 1, interval);
	}

	private void processaRecurso(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				while (recurso.isEmUso()) {
					// somente continuará quando não estiver em uso
				}

				if (!fila.isEmpty()) {
					Processo processo = fila.get(0);
					fila.remove(0);
					recurso.setEmUso(true);
					mensagem += "\n CONSUMIR RECURSO: Início do processo de consumir recurso, processo "
							+ processo.getId();

					int delayConsumo = numeroAleatorio.buscaNumeroAleatorio(5000, 15000);
					consumirRecurso(delayConsumo, processo.getId());
				}
			}
		}, interval, interval);
	}

	private void consumirRecurso(int delay, int id) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				recurso.setEmUso(false);
				mensagem += "\n CONSUMIR RECURSO: Fim do processo de consumir recurso, processo " + id;
			}
		}, delay, 1);
	}

	private void criaProcesso(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				int id = numeroAleatorio.buscaIdAleatoriaNaoRepetida();
				fila.add(new Processo(id, false));
				mensagem += "\n CRIAR PROCESSO: Criado novo processo " + id;
			}
		}, interval, interval);
	}

	private void matarCoordenador(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				mensagem += "\n INATIVAR COORDENADOR: coordenador " + processos.get(indexCoordenador).getId()
						+ " inativado";
				processos.get(indexCoordenador).setAtivo(false);
				fila.clear();

				elegerCoordenador();
			}
		}, interval, interval);
	}

	private void elegerCoordenador() {
		// Não especificado qual o tipo de eleição no problema.
		// Buscamos o maior ID para ser o coordenador.
		for (int i = 0; i < processos.size() - 1; i++) {
			Processo processo = processos.get(i);
			if (processo.getId() > coordenador.getId()) {
				coordenador = processo;
				indexCoordenador = i;
			}
		}

	}

}

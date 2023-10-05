package centralizado;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bully.Processo;

public class Controller {

	private List<Process> processos = new ArrayList<Process>();
	private List<Process> fila = new ArrayList<Process>();
	private Recurso recurso = new Recurso();

	private Process coordenador = new Process(1, true);
	private int indexCoordenador = 0;

	private int TEMPO_EXECUCAO_TOTAL = 600;
	private int TEMPO_EXECUTANDO = 0;
	private String mensagem = "";

	private Timer timer = new Timer();
	private RandomNumber numeroAleatorio = new RandomNumber();

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
					// somente continuar� quando n�o estiver em uso
				}

				if (!fila.isEmpty()) {
					Process processo = fila.get(0);
					fila.remove(0);
					recurso.setEmUso(true);
					mensagem += "\n CONSUMIR RECURSO: In�cio do processo de consumir recurso, processo "
							+ processo.getId();

					new Thread(() -> {
						Integer delayConsumo = numeroAleatorio.buscaNumeroAleatorio(5000, 15000);

						try {
							Thread.sleep(delayConsumo);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (recurso.isEmUso()) {
							// Caso o coordenador seja desligado, deve ser retirado o processo operante.
							mensagem += "\n CONSUMIR RECURSO: Fim do processo de consumir recurso, processo "
									+ processo.getId() + ", em " //
									+ (delayConsumo.toString().length() == 4 ? delayConsumo.toString().substring(0, 1)
											: delayConsumo.toString().substring(0, 2))
									+ " segundos";
							recurso.setEmUso(false);
						}
					}).run();
				}
			}
		}, interval, interval);
	}

	private void criaProcesso(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				int id = numeroAleatorio.buscaIdAleatoriaNaoRepetida();
				Process novoProcesso = new Process(id, false);
				fila.add(novoProcesso);
				processos.add(novoProcesso);
				mensagem += "\n CRIAR PROCESSO: Criado novo processo " + id;
				mensagem += " : {";

				for (Process process : processos) {
					mensagem += process.getId() + ", END";
				}

				mensagem += "}";
			}
		}, interval, interval);
	}

	private void matarCoordenador(int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				mensagem += "\n INATIVAR COORDENADOR: coordenador " + processos.get(indexCoordenador).getId()
						+ " inativado, fila limpa";
				processos.get(indexCoordenador).setAtivo(false);
				fila.clear();

				if (recurso.isEmUso()) {
					mensagem += "\n CONSUMIR RECURSO: Consumo de recurso cancelado";
					recurso.setEmUso(false);
				}

				elegerCoordenador();
			}
		}, interval, interval);
	}

	private void elegerCoordenador() {
		// N�o especificado qual o tipo de elei��o no problema.
		// Buscamos o maior ID para ser o coordenador.
		for (int i = 0; i < processos.size(); i++) {
			Process processo = processos.get(i);
			if (processo.isAtivo() && (!coordenador.isAtivo() || processo.getId() > coordenador.getId())) {
				coordenador = processo;
				indexCoordenador = i;
			}
		}

		mensagem += "\n ELEGER COORDENADOR: Novo coordenador elegido " + coordenador.getId();
	}

}
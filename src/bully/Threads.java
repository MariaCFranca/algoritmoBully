package bully;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Threads {

	static Bully bully = new Bully();
	static boolean emEleicao;
	
	public static Runnable vinteCincoSegundos = new Runnable() {
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(25);
					
					if (!emEleicao) {
						System.out.println("\n===25 segundos chamou eleição====");
						Processo pAleatorio = processoAleatorio();
						
						if (pAleatorio != null) {
							if (pAleatorio.isbCoordenador()) {
								System.out.println("Processo de eleição não iniciado, pois o processo " + pAleatorio.getiID() + " é o próprio coordenador.");
							} else if (pAleatorio.getCoordenador() != null && pAleatorio.getCoordenador().isAtivo()) {
								System.out.println("Processo de eleição não iniciado, pois o coordenador " + pAleatorio.getCoordenador().getiID() + " está ativo.");
							} else {
								System.out.println("Processo aleatório que chamou a eleição " + pAleatorio.getiID());
								eleicao(pAleatorio);
							}
						}
					}
				} catch (InterruptedException ex) {
				}
			}
		}
	};
	
	public static Runnable trintaSegundos = new Runnable() {
		public void run() {
			int id = 0;
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(30);
					
					Processo processoNovo = new Processo(id++);
					System.out.println("\n====30 segundos criado novo processo===");
					
					if (bully.coordenador != null) {
						processoNovo.setCoordenador(bully.coordenador);
					}
					
					bully.listaProcessos.add(processoNovo);
					StringBuilder string = new StringBuilder();
					
					for (Processo p : bully.listaProcessos) {
						if (!"".equalsIgnoreCase(string.toString())) {
							string.append(", ").append(p.getiID());
						} else {
							string.append(p.getiID());
						}
					}
					
					System.out.println("Nova lista de processos [" + string.toString() + "]");
					
				} catch (InterruptedException ex) {
					
				}
			}
		}
	};
	
	public static Runnable oitentaSegundos = new Runnable() {
		public void run() {
			while(true) {
				try {
					TimeUnit.SECONDS.sleep(80);
					Processo p = processoAleatorio();
					
					if (p != null) {
						p.setAtivo(false);
						System.out.println("\n===80 segundos processo aleatório desativado===");
						System.out.println("Processo " + p.getiID() + " desativado");
					}
				} catch (InterruptedException ex) {
					
				}
			}
		}
	};
	
	public static Runnable cemSegundos = new Runnable() {
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(100);
					bully.coordenador.setAtivo(false);
					System.out.println("\n===100 segundos coordenador desativado===");
					System.out.println("Coordenador " + bully.coordenador.getiID());
					
				} catch (InterruptedException ex) {
					
				}
			}
		}
	};
	
	private static void eleicao(Processo processo) {
		try {
			emEleicao = true;
			int controlador = 0;
			Processo possivelCoordenador = processo;
			
			for (Processo p : bully.listaProcessos) {
				if (p.isAtivo() && p.getiID() > possivelCoordenador.getiID()) {
					controlador++;
					possivelCoordenador = p;
					break;
				}
			}
			
			if (controlador > 0) {
				System.out.println("Processo " + possivelCoordenador.getiID() + " chamou novamente o trabalho de eleição.");
				eleicao(possivelCoordenador);
			} else {
				for (Processo p : bully.listaProcessos) {
					p.setCoordenador(possivelCoordenador);
					System.out.println("O novo coordenador " + possivelCoordenador.getiID() + " avisou o processo " + p.getiID() + " que ele é o novo coordenador.");
				}
				
				if (bully.coordenador != null) {
					bully.coordenador.setbCoordenador(false);
				}
				
				bully.coordenador = possivelCoordenador;
				possivelCoordenador.setbCoordenador(true);
				emEleicao = false;
			}
			
		} catch (Exception e) {}
	}
	
	public static Processo processoAleatorio() {
		while (true) {
			if (bully.listaProcessos.size() > 0) {
				Processo p = bully.listaProcessos.get(new Random().nextInt(bully.listaProcessos.size()));
				
				if (p.isAtivo()) {
					return p;
				}
			} else {
				return null;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}

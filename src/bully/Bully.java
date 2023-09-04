package bully;

import java.util.ArrayList;
import java.util.List;

public class Bully {

	public List<Processo> listaProcessos = new ArrayList<>();
	public Processo coordenador;
	
	public static void main(String[] args) throws InterruptedException {
		Thread a = new Thread(Threads.vinteCincoSegundos);
		Thread b = new Thread(Threads.trintaSegundos);
		Thread c = new Thread(Threads.oitentaSegundos);
		Thread d = new Thread(Threads.cemSegundos);
		
		a.setName("vinteCincoSegundos");
		b.setName("trintaSegundos");
		c.setName("oitentaSegundos");
		d.setName("cemSegundos");
		
		a.start();
		b.start();
		c.start();
		d.start();		
	}
}

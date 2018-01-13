package ae;
import java.util.Date;

import ae.PSOVizinhanca;

public class Main {
	
	public void executar() {
		
		PSOVizinhanca ae = new PSOVizinhanca(2);
		
		ae.executar();
		
	}
	
	public static void main(String args[]) {
		long inicio = new Date().getTime();
		new Main().executar();
		long fim = new Date().getTime();
		System.out.println("\n\nTempo de execução em milisegundos: "+(fim -inicio));
	}
}

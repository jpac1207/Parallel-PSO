package ae;
import java.util.Date;

import ae.PSOVizinhancaMultiIndividuos;

public class Main {
	
	public void executar() {
		
		PSOVizinhancaMultiIndividuos ae = new PSOVizinhancaMultiIndividuos(2, 60);
		
		ae.executar();
		
	}
	
	public static void main(String args[]) {
		
		long inicio = new Date().getTime();
		new Main().executar();
		long fim = new Date().getTime();
		System.out.println("\n\nTempo de execução em milisegundos: "+ (fim-inicio));
	}
}

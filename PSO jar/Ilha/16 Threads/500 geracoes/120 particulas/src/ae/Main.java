package ae;
import java.util.Date;

import ae.PSOIlha;

import ae.migration.TopologiaStrategy;
import ae.migration.AnelBidirecional;

public class Main {
	
	public void executar() {
		
		TopologiaStrategy topologia = new AnelBidirecional();
		
		PSOIlha ae = new PSOIlha(16, 120, 1, 5, topologia);
		
		ae.executar();
		
	}
	
	public static void main(String args[]) {
		
		long inicio = new Date().getTime();		
			new Main().executar();				
		long fim = new Date().getTime();
		System.out.println("\n\nTempo de execução em milisegundos: "+(fim-inicio));
	}
}

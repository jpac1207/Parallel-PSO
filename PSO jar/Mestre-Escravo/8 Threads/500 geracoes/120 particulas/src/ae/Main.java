package ae;
import java.util.Date;

import ae.PSOMestreEscravo;

import ae.Populacao;
import ae.evaluate.Avaliadores;
import ae.synchronization.SincronizadorAvaliacao;

import ae.util.CorretorValor;

import ae.util.RandomGenerator;
import ae.termination.CriterioParadaStrategy;
import ae.termination.MaximoGeracoes;

public class Main {
	
	public void executar() {
		
		double limiteInferior = -600.0;
		
		double limiteSuperior = 600.0;
		
		RandomGenerator randomGenerator = new RandomGenerator(limiteInferior, limiteSuperior);
		
		int tamanhoPopulacao = 120;
		
		int tamanhoIndividuo = 30;
		
		int qtdGeracoes = 500;
		
		int qtdEscravos = 8;
		
		Populacao populacao = new Populacao(tamanhoIndividuo, tamanhoPopulacao);
		
		CriterioParadaStrategy criterio = new MaximoGeracoes(qtdGeracoes);
		
		SincronizadorAvaliacao monitor = new SincronizadorAvaliacao(qtdEscravos);
		
		Avaliadores avaliadores = new Avaliadores(qtdEscravos, monitor);
		PSOMestreEscravo ae = new PSOMestreEscravo(qtdGeracoes, populacao, randomGenerator, avaliadores, criterio);
		
		ae.executar();
		
	}
	
	public static void main(String args[]) {
		long inicio = new Date().getTime();
		new Main().executar();
		long fim = new Date().getTime();
		System.out.println("\n\nTempo de execução em milisegundos: "+ (fim - inicio));
	}
}

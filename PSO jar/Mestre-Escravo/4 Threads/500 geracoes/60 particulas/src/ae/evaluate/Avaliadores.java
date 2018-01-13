package ae.evaluate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ae.Individuo;
import ae.Populacao;
import ae.synchronization.SincronizadorAvaliacao;

public class Avaliadores{
	
	private List<Avaliador> avaliadores;
	
	private int qtdEscravos;
	
	private SincronizadorAvaliacao sincronizadorAvaliacao;
	
	public Avaliadores(int qtdEscravos, SincronizadorAvaliacao sincronizadorAvaliacao) {
		
		this.qtdEscravos = qtdEscravos;
		
		this.sincronizadorAvaliacao = sincronizadorAvaliacao;
		
		instanciarAvaliadores();
	}
	
	private ExecutorService threadPool;
	
	public void instanciarAvaliadores() {
		
		avaliadores = new ArrayList<Avaliador>();
		
		for (int i = 0; i < qtdEscravos; i++) {
			
			Avaliador avaliador = new Avaliador(i+1, sincronizadorAvaliacao);
			
			avaliadores.add(avaliador);
		}
		
		threadPool = Executors.newFixedThreadPool(qtdEscravos);
		
		for (Avaliador a : avaliadores) {
			threadPool.submit(a);
		}
		
	}
	
	public void finalizar() {
		threadPool.shutdown();
		threadPool.shutdownNow();
		
		for (Avaliador a: avaliadores) {
			a.finalizar();
		}
		
	}
	
	public void avaliarPopulacao(Populacao populacao) {
		avaliarIndividuos(populacao.getIndividuos());
	}
	
	
	
	public void avaliarIndividuos(List<Individuo> individuos) {
		
		for (int i = 0; i < qtdEscravos; i++) {
			
			Avaliador avaliador = avaliadores.get(i); 
			
			int inicioAvaliados = (individuos.size()/qtdEscravos)*i;
			
			int fimAvaliados = (individuos.size()/qtdEscravos)*i+(individuos.size()/qtdEscravos - 1); 
			
			if (i+1 == qtdEscravos) {
				fimAvaliados = fimAvaliados + (individuos.size()%qtdEscravos);
			}
			
			avaliador.setPopulacao(individuos.subList(inicioAvaliados, fimAvaliados+1));
			
		}
		
		sincronizadorAvaliacao.esperar();
	}
	
	public int getQtdEscravos() {
		return qtdEscravos;
	}
	
	public void setQtdEscravos(int qtdEscravos) {
		this.qtdEscravos = qtdEscravos;
	}
	
}

package ae.evaluate;

import ae.Individuo;
import java.util.List;

import ae.synchronization.SincronizadorAvaliacao;
import ae.observer.Observable;

public class Avaliador extends Observable implements Runnable{
	
	int numeroAvaliador;
	
	private List<Individuo> populacao;
	
	public Avaliador(int numeroAvaliador, SincronizadorAvaliacao sincronizador) {
		
		this.numeroAvaliador = numeroAvaliador;
		
		this.addObserver(sincronizador);
	}
	
public Avaliador(){};
	
	public List<Individuo> getPopulacao() {
		return populacao;
	}
	
	public void setPopulacao(List<Individuo> populacao) {
		this.populacao = populacao;
	}
	
	@Override
	public void run() {
		
		for (;;) {
			
			if (this.populacao != null && this.populacao.size() > 0) {
				
				avaliarPopulacao();
				
				this.populacao = null;
				
				notifyObservers();
				
			} 
			
			if (finalizar) {
				break;
			}
			
			Thread.interrupted();
		}
	}
	
	private boolean finalizar = false;
	
	public void finalizar() {
		this.finalizar = true;
	}
	
	public void avaliarPopulacao() {
		
		for (Individuo individuo : populacao) {
			individuo.setAptidao(avaliarIndividuo(individuo));
		}
	}
	
	public double avaliarIndividuo(Individuo individuo) {
		double somatorio = 0;
		double produtorio = 1;
		int count =0;
		for(int i=0;i<individuo.getGenes().length;i++)
			somatorio += Math.pow(individuo.getGenes()[i],2);
		
				
		for(int i=1;i<individuo.getGenes().length-1;i++){
			produtorio = produtorio*(Math.cos(individuo.getGenes()[count]/Math.sqrt(i)));
			count++;
		}
		
		double grieWank = somatorio/4000 - produtorio + 1 ;		
		 
		
		 if(grieWank > individuo.getBestAptidao())
			 individuo.setHistoricoGenes(individuo.getGenes());
		
		 return grieWank;
	}
	
	public void avaliarIndividuos(List<Individuo> individuos) {
		for (Individuo individuo : individuos) {
			individuo.setAptidao(avaliarIndividuo(individuo));
		}
	}
}

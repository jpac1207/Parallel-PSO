package ae.evaluate;

import ae.Individuo;
import java.util.List;

import ae.Populacao;

public class Avaliador{
	
	public void avaliarPopulacao(Populacao populacao) {
		for (Individuo individuo : populacao.getIndividuos()) {
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

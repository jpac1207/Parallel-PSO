package ae;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import ae.Populacao;
import ae.Individuo;
import ae.evaluate.Avaliador;
import ae.synchronization.SincronizadorGeracao;
import ae.observer.Observable;
import ae.termination.CriterioParadaStrategy;
import ae.util.RandomGenerator;
import ae.util.CorretorValor;
import ae.neighborhood.VizinhancaStrategy;
import java.util.Random;
import java.util.Collections;

public class Celula extends Observable implements Callable<Individuo>{
	
	
	private String numeroCelula;
	
	private Avaliador avaliador;
	
	private Populacao populacao;
	
	private RandomGenerator random;
	
	private CriterioParadaStrategy criteriosParada;
	
	private PSOVizinhancaMultiIndividuos psoVizinhanca;
	
	private int x;
	
	private int y;
	
	private VizinhancaStrategy vizinhanca;
	
	private SincronizadorGeracao sincronizador;
	private CorretorValor corretor;
	public Celula(VizinhancaStrategy vizinhanca, SincronizadorGeracao m, int x, int y, PSOVizinhancaMultiIndividuos psoVizinhanca, String numeroCelula, int qtdGeracoes,Populacao populacao, RandomGenerator random,  
	 Avaliador avaliador, CriterioParadaStrategy criteriosParada) {
		
		
		this.vizinhanca = vizinhanca;
		
		this.sincronizador = m;
		
		this.x = x;
		
		this.y = y;
		
		this.numeroCelula = numeroCelula;
		
		this.random = random;
		
		this.avaliador = avaliador;
		
		this.populacao = populacao;
		
		this.criteriosParada = criteriosParada;
		
		this.psoVizinhanca = psoVizinhanca;
		
		this.corretor = new CorretorValor(this.random.getLimiteInferior(), this.random.getLimiteSuperior());
		this.addObserver(sincronizador);
		
	}
	
	
	
	
	
	public Individuo executar() {
		
		double gBest=0;
		double particleBest[];
		int tamanhoFixoPopulacao=populacao.getTamanhoPopulacao();
		populacao.iniciarPopulacao(random);
		notifyObservers();
		
		for (int geracao = 0; !criteriosParada.done(populacao, geracao); geracao++) {
			
			
			
			List<Individuo> individuosVizinhanca = vizinhanca.getBairro(psoVizinhanca.getCelulas(), x, y);
			
			individuosVizinhanca.addAll(this.getIndividuos());
			
			Collections.sort(individuosVizinhanca);
			
			populacao = new Populacao(individuosVizinhanca.get(0).getGenes().length, tamanhoFixoPopulacao);
			
			populacao.setIndividuos(individuosVizinhanca.subList(0, tamanhoFixoPopulacao));
			
			avaliador.avaliarPopulacao(populacao);
			
			gBest=populacao.getMelhorIndividuo().getAptidao();
			
			particleBest= populacao.getMelhorIndividuo().getGenes();
			
			double c1=2,c2=2;
			double w =0.9;
			for(int i=0;i<populacao.getTamanhoPopulacao();i++){
				
				for(int j=0;j<populacao.getIndividuo(i).getVelocidadeSize();j++){
					double rand1 = new Random().nextDouble();
					double rand2 = new Random().nextDouble();
					
					double v =populacao.getIndividuo(i).getVelocidade()[j];
					double equationA = w * (v) + ((c1) *(rand1))*((populacao.getIndividuo(i).getHistoricoGenes()[j])-(populacao.getIndividuo(i).getGenes()[j])) +((c2) *(rand2)) *((particleBest[j])- (populacao.getIndividuo(i).getGenes()[j]));
					populacao.getIndividuo(i).getVelocidade()[j] = equationA;
					double equationB = populacao.getIndividuo(i).getGenes()[j] + equationA;
					if(equationB> this.random.getLimiteSuperior() || equationB < this.random.getLimiteInferior()){
						equationB = corretor.manterValorNoLimite(equationB);
						populacao.getIndividuo(i).getVelocidade()[j] = (populacao.getIndividuo(i).getVelocidade()[j] * -0.5);
					}
					populacao.getIndividuo(i).getGenes()[j] = equationB;
					
				}
				
				
			}
			
			
			
			notifyObservers();
			
		}
		avaliador.avaliarPopulacao(populacao);
		
		return populacao.getMelhorIndividuo();
	}
	
	
	
	
	
	@Override
	public String toString() {
		return String.valueOf("CELULA: " + this.numeroCelula);
	}
	
	@Override
	public Individuo call() throws Exception {
		return executar();
	}
	
	public String getNumeroCelula() {
		return numeroCelula;
	}
	
	public void setNumeroCelula(String numeroCelula) {
		this.numeroCelula = numeroCelula;
	}
	
	public List<Individuo> getIndividuos() {
		
		ArrayList<Individuo> lista = new ArrayList<>();
		for(Individuo i : populacao.getIndividuos()){
			lista.add(i.clone());
		}
		return lista;
		
	}
}


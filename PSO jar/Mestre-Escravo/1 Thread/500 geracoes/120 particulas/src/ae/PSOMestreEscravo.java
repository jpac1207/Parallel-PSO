package ae;
import ae.Individuo;
import ae.Populacao;
import java.util.List;
import ae.evaluate.Avaliadores;
import ae.termination.CriterioParadaStrategy;
import ae.util.Printer;
import ae.util.RandomGenerator;
import java.util.ArrayList;
import ae.util.CorretorValor;
import java.util.Random;
public class PSOMestreEscravo {
	
	
	
	
	private RandomGenerator random;
	
	private Avaliadores avaliador;
	
	private Populacao populacao;
	
	private CriterioParadaStrategy criterioParada;
	
	private Printer printer;
	
	private CorretorValor corretor;
	
	public PSOMestreEscravo(int qtdGeracoes, Populacao populacao, RandomGenerator random, 
	 Avaliadores avaliadores, CriterioParadaStrategy criterioParada) {
		
		this.printer = new Printer();
		
		this.random = random;
		
		this.avaliador = avaliadores;
		
		this.populacao = populacao;
		this.corretor = new CorretorValor(this.random.getLimiteInferior(), this.random.getLimiteSuperior());
		this.criterioParada = criterioParada;
	}
	
	
	 double pBest=0;
	 double particleBest[];
	
	public void executar() {
		
		populacao.iniciarPopulacao(random);
		avaliador.avaliarPopulacao(populacao);
		for (int geracao = 0; !criterioParada.done(populacao, geracao); geracao++) {
			
			particleBest= populacao.getMelhorIndividuo().getGenes();
			double c1=2,c2=2;
			double w =0.9;
			for(int i=0;i<populacao.getTamanhoPopulacao();i++){
				
				for(int j=0;j<populacao.getIndividuo(i).getVelocidadeSize();j++){
					
					double rand1 = new Random().nextDouble();
					double rand2 = new Random().nextDouble();
					double v =populacao.getIndividuo(i).getVelocidade()[j];
					double equationA = w * (v) + ((c1) *(rand1))*((populacao.getIndividuo(i).getHistoricoGenes()[j])-(populacao.getIndividuo(i).getGenes()[j])) +((c2) *(rand2)) *((particleBest[j])- (populacao.getIndividuo(i).getGenes()[j]));
					populacao.getIndividuo(i).getVelocidade()[j]=equationA;
					double equationB= populacao.getIndividuo(i).getGenes()[j] + equationA;
					if(equationB> this.random.getLimiteSuperior() || equationB < this.random.getLimiteInferior()){
						equationB = corretor.manterValorNoLimite(equationB);
						populacao.getIndividuo(i).getVelocidade()[j] = (populacao.getIndividuo(i).getVelocidade()[j] * -0.5);
					}
					populacao.getIndividuo(i).getGenes()[j]=equationB;
					
				}
				
			}
			
			avaliador.avaliarPopulacao(populacao);
			
		}
		
		printer.imprimirIndividuo(populacao.getMelhorIndividuo());
		
		avaliador.finalizar();
		
	}
	
	
	
	
}


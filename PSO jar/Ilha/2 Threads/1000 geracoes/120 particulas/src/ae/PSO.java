package ae;
import ae.Populacao;
import ae.migration.SelecaoMigrantesStrategy;
import ae.Individuo;
import ae.synchronization.SincronizadorMigracao;
import ae.termination.CriterioParadaStrategy;
import ae.graph.No;
import java.util.List;
import java.util.ArrayList;
import ae.evaluate.Avaliador;
import ae.util.RandomGenerator;
import java.util.Random;
import ae.util.CorretorValor;
public class PSO extends No {
	
	
	private int numeroIlha;
	
	private int taxaMigracao;
	
	private int frequenciaMigracao;
	
	private Avaliador avaliador;
	
	private Populacao populacao;
	
	private RandomGenerator random;
	
	private SelecaoMigrantesStrategy migrador;
	
	private CriterioParadaStrategy criterioParada;
	
	private CorretorValor corretor;
	private List<Individuo> migrantesRecebidos;
	
	
	public PSO(int numeroIlha, int qtdGeracoes, int taxaMigracao, int frequenciaMigracao,SincronizadorMigracao sincronizador, 
	Populacao populacao, RandomGenerator random, Avaliador avaliador, CriterioParadaStrategy criterioParada, SelecaoMigrantesStrategy migrador) {
		
		this.random = random;
		
		this.migrador = migrador; 
		
		this.avaliador = avaliador;
		
		this.populacao = populacao;
		
		this.frequenciaMigracao = frequenciaMigracao;
		
		this.numeroIlha = numeroIlha;
		
		this.taxaMigracao = taxaMigracao;
		
		this.criterioParada = criterioParada;
		
		this.migrantesRecebidos = new ArrayList<Individuo>();
		this.corretor = new CorretorValor(this.random.getLimiteInferior(), this.random.getLimiteSuperior());
		this.addObserver(sincronizador);
	}
	
	public void enviarMigrantes() {
		
		if (getVizinhos() != null) {
			
			List<Individuo> migrantes = migrador.getMigrantes(populacao, taxaMigracao);
			
			for (No vizinho : getVizinhos()) {
				
				((PSO)vizinho).adicionarMigrantes(migrantes);
				
			}
		}
	}
	
	public void receberMigrantes() {
		
		populacao.removerPioresIndividuos(migrantesRecebidos.size());
		
		populacao.adicionarIndividuos(migrantesRecebidos);
		
		migrantesRecebidos = new ArrayList<Individuo>();
	}
	
	public Individuo clocarIndividuo(Individuo i){
		Individuo clone = new Individuo(i.getGenes().length);
		clone.setGenes(i.getGenes());
		clone.setAptidao(i.getAptidao());
		clone.setBestAptidao(i.getBestAptidao());
		clone.setHistoricoGenes(i.getHistoricoGenes());
		clone.setVelocidade(i.getVelocidade());
		
		return clone;
	}
	
	public synchronized void adicionarMigrantes(List<Individuo> migrantes) {
		for (Individuo i : migrantes) {
			migrantesRecebidos.add(i);
		}
	}
	
	public void migracao() {
		
		enviarMigrantes();
		
		notifyObservers();
		
		receberMigrantes();
		
		notifyObservers();
	}
	
	public Individuo executar() {
		
		 double particleBest[];
		 try {
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
			
			if (geracao % frequenciaMigracao == 0) {
				
				migracao();
				
			}
			
		}
		
		return populacao.getMelhorIndividuo();
		 }catch(Exception e){
			 e.printStackTrace();
			 System.out.println(e.getMessage());
		 }
		 return null;
	}
	
	
	@Override
	public String toString() {
		return String.valueOf("ILHA: " + this.numeroIlha);
	}
	
	@Override
	public Individuo call() throws Exception {
		return executar();
	}
	
}


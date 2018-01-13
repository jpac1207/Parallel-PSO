package ae;
import ae.graph.Grafo;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import ae.migration.MigracaoMelhores;
import ae.PSO;
import ae.Individuo;
import ae.migration.SelecaoMigrantesStrategy;
import ae.Populacao;
import ae.migration.TopologiaStrategy;
import ae.evaluate.Avaliador;
import ae.synchronization.SincronizadorMigracao;
import ae.util.Printer;
import ae.util.RandomGenerator;
import ae.termination.CriterioParadaStrategy;
import ae.termination.MaximoGeracoes;



public class PSOIlha {
	
	private List<Individuo> melhoresIndividuos;
	
	private Grafo ilhas;
	
	private TopologiaStrategy topologia;
	
	private ExecutorService threadPool;
	
	private int qtdIlhas;
	
	private Printer printer;
	
	
	public PSOIlha(int qtdIlhas, int tamanhoPopulacao, int taxaMigracao, int frequenciaMigracao, TopologiaStrategy topologia) {
		
		printer = new Printer();
		
		this.qtdIlhas = qtdIlhas;
		
		this.topologia = topologia;
		
		this.melhoresIndividuos = new ArrayList<Individuo>();
		
		SincronizadorMigracao sincronizador = new SincronizadorMigracao(qtdIlhas);
		
		ilhas = new Grafo();
		
		int individuosRestantes = tamanhoPopulacao % qtdIlhas;
		
		for (int i = 0; i < qtdIlhas; i++) {
			
			double limiteInferior = -600.0;
			
			double limiteSuperior = 600.0;
			
			RandomGenerator randomGenerator = new RandomGenerator(limiteInferior, limiteSuperior);
			
			int tamanhoPopulacaoIlha = tamanhoPopulacao / qtdIlhas;
			
			if (i == qtdIlhas - 1) {
				tamanhoPopulacaoIlha = tamanhoPopulacaoIlha + individuosRestantes;
			}
			
			int tamanhoIndividuo = 30;
			
			int qtdGeracoes = 1000;
			
			Avaliador avaliador = new Avaliador();
			
			Populacao populacao = new Populacao(tamanhoIndividuo, tamanhoPopulacaoIlha);
			
			CriterioParadaStrategy criterio = new MaximoGeracoes(qtdGeracoes);
			
			SelecaoMigrantesStrategy migrador = new MigracaoMelhores();
			
			PSO ilha = new PSO(i+1, qtdGeracoes, taxaMigracao, frequenciaMigracao, sincronizador, populacao, randomGenerator, avaliador, criterio, migrador);
			
			ilhas.adicionarNo(ilha);
			
		}
		
		this.topologia.criarTopologia(ilhas);
	}
	
	
	public void executar() {
		
		threadPool = Executors.newFixedThreadPool(qtdIlhas);
		
		try {
			
			List<Future<Individuo>> resultados = threadPool.invokeAll(ilhas.getNos());
			
			for (Future<Individuo> resultado : resultados) {
				
				addMelhorIndividuo(resultado.get());
			}
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
		
		Thread.interrupted();
	}
	
	public synchronized void addMelhorIndividuo(Individuo i) {
		
		melhoresIndividuos.add(i);
		
		if (melhoresIndividuos.size() == qtdIlhas) {
			
			Collections.sort(melhoresIndividuos);
			
			printer.imprimirIndividuo(melhoresIndividuos.get(0));
			
			threadPool.shutdownNow();
		}
	}
	
	public void imprimirMelhorIndividuo(Individuo i) {
		
		NumberFormat format = NumberFormat.getNumberInstance();
		
		format.setMaximumFractionDigits(200);
		
		System.out.println("----------------------- MELHOR INDIVIDUO-----------------------" +"\n" + "[" + i.toString() + "]" + " (" + format.format(i.getAptidao()) + ")" + "\n" + "---------------------------------------------------------------------------------------------------");
	}
	
	//GETs e SETs
	
	public Grafo getIlhas() {
		
		return ilhas;
	}
	
	public void setIlhas(Grafo ilhas) {
		
		this.ilhas = ilhas;
	}
	
}

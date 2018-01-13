package ae;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ae.evaluate.Avaliador;
import ae.synchronization.SincronizadorGeracao;

import ae.util.CorretorValor;

import ae.neighborhood.Cruz;
import ae.neighborhood.VizinhancaStrategy;

import ae.termination.CriterioParadaStrategy;
import ae.termination.MaximoGeracoes;
import ae.util.Printer;
import ae.util.RandomGenerator;

public class PSOVizinhancaMultiIndividuos {
	
	private Celula celulas[][];
	
	private ExecutorService threadPool;
	
	private int qtdCelulas;
	
	private int largura;
	
	private int altura;
	
	private Printer printer; 
	
	public PSOVizinhancaMultiIndividuos(int qtdCelulas, int tamanhoPopulacao) {
		
		printer = new Printer();
		
		this.qtdCelulas = qtdCelulas;
		
		definirLarguraAltura();
		
		celulas = new Celula[largura][altura];
		
		SincronizadorGeracao sincronizador = new SincronizadorGeracao(qtdCelulas);
		
		preencherCelulas(sincronizador, tamanhoPopulacao);
	}
	
	private void preencherCelulas(SincronizadorGeracao sincronizador, int tamanhoPopulacao) {
		
		int preenchidas = 0;
		
		int individuosRestantes = tamanhoPopulacao % qtdCelulas;
		
		for (int i = 0; i < largura && preenchidas < qtdCelulas; i++) {
			
			for (int j = 0; j < altura && preenchidas < qtdCelulas; j++) {
				
				double limiteInferior = -600.0;
				
				double limiteSuperior = 600.0;
				
				RandomGenerator randomGenerator = new RandomGenerator(limiteInferior, limiteSuperior);
				
				int tamanhoPopulacaoCelula = tamanhoPopulacao / qtdCelulas;
				
				if (preenchidas == qtdCelulas - 1) {
					tamanhoPopulacaoCelula = tamanhoPopulacaoCelula + individuosRestantes;
				}
				
				int tamanhoIndividuo = 30;
				
				int qtdGeracoes = 500;
				
				Avaliador avaliador = new Avaliador();
				
				
				Populacao populacao = new Populacao(tamanhoIndividuo, tamanhoPopulacaoCelula);
				
				CriterioParadaStrategy criterio = new MaximoGeracoes(qtdGeracoes);
				
				VizinhancaStrategy vizinhanca = new Cruz(4);
				
				Celula celula = new Celula(vizinhanca, sincronizador, i,j,this, (i)+ " - " +j ,qtdGeracoes, populacao, randomGenerator, avaliador, criterio);
				
				celulas[i][j] = celula;
				
				preenchidas++;
				
			}
		}
		
	}
	
	private void definirLarguraAltura() { //Define uma largura e altura qeu permita alocar todas as celulas em um formato o mais proximo possivel de um quadrado
		
		int dimensao = (int)Math.sqrt(this.qtdCelulas);
		
		this.largura = dimensao;
		
		this.altura = dimensao;
		
		for (;;) {
			if (largura * altura < this.qtdCelulas) {
				altura++;
				continue;
			}
			break;
		}
		
	}
	
	public void executar() {
		
		threadPool = Executors.newFixedThreadPool(qtdCelulas);
		
		try {
			
			List<Celula> cells = new ArrayList<Celula>();
			
			for (int i = 0; i < largura; i++) {
				
				for (int j = 0; j < altura; j++) {
					
					Celula c = celulas[i][j];
					
					if (c != null) {
						cells.add(c);
					}
				}
				
			}
			
			List<Future<Individuo>> resultados = threadPool.invokeAll(cells);
			
			for (Future<Individuo> resultado : resultados) {
				addMelhorIndividuo(resultado.get());
			}
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private List<Individuo> melhoresIndividuos = new ArrayList<Individuo>();
	
	public synchronized void addMelhorIndividuo(Individuo i) {
		
		melhoresIndividuos.add(i);
		
		if (melhoresIndividuos.size() == qtdCelulas) {
			
			Collections.sort(melhoresIndividuos);
			
			printer.imprimirIndividuo(melhoresIndividuos.get(0));
			
			threadPool.shutdownNow();
			
		}
	}
	
	public Celula[][] getCelulas() {
		return celulas;
	}
	
	public void setCelulas(Celula[][] celulas) {
		this.celulas = celulas;
	}
	
}

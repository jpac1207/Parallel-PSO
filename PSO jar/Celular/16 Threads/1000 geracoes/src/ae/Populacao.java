package ae;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ae.Individuo;
import ae.util.RandomGenerator;

public class Populacao {
	
	private int tamanhoPopulacao;
	
	private int tamanhoIndividuo;
	
	private List<Individuo> individuos;
	
	public Populacao(int tamanhoIndividuo, int tamanhoPopulacao) {
		
		this.tamanhoIndividuo = tamanhoIndividuo;
		
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	public void iniciarPopulacao(RandomGenerator random) {
		
		individuos = new ArrayList<Individuo>();
		
		for (int i = 0; i < tamanhoPopulacao; i++) {
			
			Individuo individuo = new Individuo(tamanhoIndividuo);
			
			iniciarIndividuo(individuo, random);
			
			individuos.add(individuo);
		}
	}
	
	private void iniciarIndividuo(Individuo individuo, RandomGenerator random) {
		
		individuo.setGenes(new double[tamanhoIndividuo]);
		
		for (int i = 0; i < tamanhoIndividuo; i++) {
			
			double valor = random.getNumeroAleatorioNoDominio();
			
			individuo.getGenes()[i] = valor;
			
			individuo.getHistoricoGenes()[i]=valor;
			individuo.getVelocidade()[i]=1;
			
		}
	}
	
	public void removerIndividuos(List<Individuo> remover) {
		
		for (Individuo i : remover) {
			
			individuos.remove(i);
		}
	}
	
	public void removerPioresIndividuos(int qtd) {
		
		List<Individuo> individuosRemover = new ArrayList<Individuo>();
		
		for (int i = 0; i < qtd; i++) {
			
			individuosRemover.add(getIndividuo(tamanhoPopulacao - 1 - i));
		}
		
		removerIndividuos(individuosRemover);
	}
	
	public List<Individuo> getMelhoresIndividuos(int qtd) {
		
		Collections.sort(individuos);
		
		List<Individuo> melhores = new ArrayList<Individuo>();
		
		for (int i = 0; i < qtd; i++){
			
			melhores.add(getIndividuo(i));
		}
		
		return melhores;
	}
	
	public Individuo getMelhorIndividuo(){
		
		Collections.sort(individuos);
		
		return individuos.get(0);
	}
	
	public void adicionarIndividuos(List<Individuo> adicionar){
		
		for (Individuo j : adicionar) {
			
			individuos.add(j);
		}
	}
	
	public void ordenar() {
		
		Collections.sort(individuos);
	}
	
	public Individuo getIndividuo(int index) {
		
		return individuos.get(index);
	}
	
	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		
		this.tamanhoPopulacao = tamanhoPopulacao;
	}
	
	public int getTamanhoPopulacao(){
		return this.tamanhoPopulacao;
	}
	
	public int getTamanhoIndividuo() {
		
		return tamanhoIndividuo;
	}
	
	public void setTamanhoIndividuo(int tamanhoIndividuo) {
		
		this.tamanhoIndividuo = tamanhoIndividuo;
	}
	
	public List<Individuo> getIndividuos() {
		
		return individuos;
	}
	
	public void setIndividuos(List<Individuo> individuos) {
		this.individuos = individuos;
		this.setTamanhoPopulacao(this.individuos.size());
	}
	
	public void imprimirMelhorIndividuo() {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(200);
		
		Individuo i = getMelhorIndividuo();
		
		System.out.println("----------------------- MELHOR INDIVIDUO-----------------------" +"\n" + "[" + i.toString() + "]" + " (" + format.format(i.getAptidao()) + ")" + "\n" + "---------------------------------------------------------------------------------------------------");
	}
	
	public void setIndividuo(Individuo individuo) {
		this.individuos.set(0, individuo);
	}
	
}

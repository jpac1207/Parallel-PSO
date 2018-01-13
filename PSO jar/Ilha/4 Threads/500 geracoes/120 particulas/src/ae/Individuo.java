package ae;

import java.text.NumberFormat;

public class Individuo implements Comparable<Individuo>{
	
	private double genes[];
	
	private double velocidade[];
	private double historicoGenes[];
	
	private double aptidao;
	private double bestAptidao;
	public double[] getGenes() {
		return genes;
	}
	public void setGenes(double[] genes) {
		this.genes = genes;
	}
	public double getAptidao() {
		return aptidao;
	}
	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;
		if(aptidao>bestAptidao)
		bestAptidao=aptidao;
	}
	
	public Individuo(int qtdGenes){
		this.genes = new double[qtdGenes];
		this.velocidade = new double[qtdGenes];
		this.bestAptidao=0;
		this.historicoGenes = new double[qtdGenes];
	}
	
	public void setBestAptidao(double value){
		this.bestAptidao=value;
	}
	public double[] getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(double[] velocidade) {
		this.velocidade = velocidade;
	}
	public int getVelocidadeSize(){
		return this.velocidade.length;
	}
	public double getVelocidadeByIndex(int index){
		return this.velocidade[index];
	}
	public void setVelocidadeByIndex(double value, int index){
		this.velocidade[index]=value;
	}
	public double getGenesByIndex(int index){
		return this.genes[index];
	}
	public void setGenesByIndex(double value, int index){
		this.genes[index]=value;
	}
	public double[] getHistoricoGenes() {
		return this.historicoGenes;
	}
	public void setHistoricoGenes(double[] genes) {
		this.historicoGenes = genes;
	}
	public double getHistoricoGenesByIndex(int index) {
		return this.historicoGenes[index];
	}
	public double getBestAptidao() {
		return this.bestAptidao;
	}
	
	@Override
	public String toString() {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(200);
		
		String strGenes = "";
		
		for (Double gene : this.genes) {
			strGenes = strGenes + format.format(gene) + " ";
		}
		
		strGenes = strGenes.substring(0, strGenes.length()-1);
		
		return strGenes;
	}
	
	public Individuo clone() {
		Individuo clone = new Individuo(this.genes.length);
		clone.setGenes(this.genes);
		clone.setAptidao(this.aptidao);		
		clone.velocidade = this.velocidade;
		clone.historicoGenes = this.historicoGenes;
		clone.bestAptidao = this.aptidao;
		
		return clone;
	}
	
	@Override
	public int compareTo(Individuo o) {
		int retorno = 0;
		
		if (this.aptidao < o.aptidao) {
			retorno = -1;
		}
		
		if (this.aptidao > o.aptidao) {
			retorno = 1;
		}
		
		return retorno;
	}
	
}

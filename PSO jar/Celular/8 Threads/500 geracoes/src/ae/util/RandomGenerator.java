package ae.util;

import java.util.Random;

public class RandomGenerator {
	private double limiteInferior;
	private double limiteSuperior;
	private Random random = new Random();
	
	public RandomGenerator(double limiteInferior, double limiteSuperior) {
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
	}
	
	public double getNumeroAleatorioNoDominio() {
		double intervalo = Math.abs(limiteInferior - limiteSuperior);
		double deslocamento = random.nextDouble() * intervalo;
		
		return limiteInferior + deslocamento;
	}
	
	public double getLimiteInferior() {
		return limiteInferior;
	}
	
	public void setLimiteInferior(double limiteInferior) {
		this.limiteInferior = limiteInferior;
	}
	
	public double getLimiteSuperior() {
		return limiteSuperior;
	}
	
	public void setLimiteSuperior(double limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}
}

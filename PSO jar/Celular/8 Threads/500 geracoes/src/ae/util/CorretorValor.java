package ae.util;

public class CorretorValor {
	
	private double limiteInferior;
	
	private double limiteSuperior;
	
	public CorretorValor(double limiteInferior, double limiteSuperior) {
		
		this.limiteInferior = limiteInferior;
		
		this.limiteSuperior = limiteSuperior;
	}
	
	public double manterValorNoLimite(double valorGene) {
		
		if (valorGene > limiteSuperior) {
			
			valorGene = limiteSuperior;
			
		}
		
		if (valorGene < limiteInferior) {
			
			valorGene = limiteInferior;
			
		}
		
		return valorGene;
	}
}

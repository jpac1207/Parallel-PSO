package ae.termination;

import ae.Populacao;

public class MaximoGeracoes implements CriterioParadaStrategy{
	
	private int geracoes;
	
	public MaximoGeracoes (int geracoes) {
		this.geracoes = geracoes;
	}
	
	@Override
	public boolean done(Populacao populacao, int geracao) {
		
		if (geracao < this.geracoes) {
			return false;
		} else {
			return true;
		}
		
	}
	
}

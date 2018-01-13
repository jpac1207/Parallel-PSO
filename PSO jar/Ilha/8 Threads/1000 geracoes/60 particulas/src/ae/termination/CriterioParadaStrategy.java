package ae.termination;

import ae.Populacao;

public interface CriterioParadaStrategy {
	public boolean done(Populacao populacao, int geracao);
}

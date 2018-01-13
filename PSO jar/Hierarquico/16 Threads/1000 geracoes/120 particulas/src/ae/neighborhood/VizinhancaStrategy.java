package ae.neighborhood;

import java.util.List;

import ae.Celula;
import ae.Individuo;

public interface VizinhancaStrategy {
	public List<Individuo> getBairro(Celula[][] celulas, int x, int y);
}

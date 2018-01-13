package ae.migration;

import java.util.List;
import ae.Individuo;
import ae.Populacao;

public interface SelecaoMigrantesStrategy {
	public List<Individuo> getMigrantes(Populacao p, int txMigracao);
}

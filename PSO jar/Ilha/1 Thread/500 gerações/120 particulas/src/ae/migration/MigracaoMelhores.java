package ae.migration;

import java.util.List;
import ae.Individuo;
import ae.Populacao;

public class MigracaoMelhores implements SelecaoMigrantesStrategy{
	
	@Override
	public List<Individuo> getMigrantes(Populacao p , int txMigracao) {
		return p.getMelhoresIndividuos(txMigracao);
	}
	
}

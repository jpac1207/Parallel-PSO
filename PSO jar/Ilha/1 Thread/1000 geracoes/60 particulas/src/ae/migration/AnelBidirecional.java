package ae.migration;

import ae.graph.Grafo;

public class AnelBidirecional implements TopologiaStrategy{
	
	@Override
	public void criarTopologia(Grafo ilhas) {
		
		ilhas.setDirecionado(false);
		
		for (int i = 0; i < ilhas.getNos().size(); i++) {
			ilhas.adicionarArestaNoGrafo(ilhas.getNos().get(i), (i == 0 ? ilhas.getNos().get(ilhas.getNos().size()-1) : ilhas.getNos().get(i-1)), 1);
		}
		
	}
	
}

package ae.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import ae.Individuo;
import ae.observer.Observable;

public abstract class No extends Observable implements Comparable<Object>, Callable<Individuo> {
	
	private List<Aresta> arestas = new ArrayList<Aresta>();
	
	public List<Aresta> getArestas() {
		return arestas;
	}
	
	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}
	
	@SuppressWarnings("rawtypes")
	public int getPeso(No destino) {
		Iterator i = this.getArestas().iterator();
		
		while (i.hasNext()) {
			Aresta aresta = (Aresta)i.next();
			
			if ((aresta.getOrigem().compareTo(this) == 0 && aresta.getDestino().compareTo(destino) == 0) ||
			(aresta.getOrigem().compareTo(destino) == 0 && aresta.getDestino().compareTo(this) == 0))
			return aresta.getPeso();
		}
		
		return Integer.MAX_VALUE;
	}
	
	public void adicionarAresta(Aresta aresta) {
		if (aresta == null) {
			throw new IllegalArgumentException("Parametro invalido - aresta nula");
		}
		
		if (this.getArestas().indexOf(aresta) == -1) {
			this.getArestas().add(aresta);
		}
	}
	
	public boolean removerAresta(Aresta aresta) {
		return this.getArestas().remove(aresta);
	}
	
	public void removerNo() {
		for (int i = 0; i < this.getArestas().size(); i++) {
			Aresta a = (Aresta)this.getArestas().get(i);
			
			if (a.removerAresta() > 0) {
				i--;
			}
		}
	}
	
	public List<No> getVizinhos() {
		
		List<No> nos = new ArrayList<No>();
		
		for (Aresta a : this.getArestas()) {
			if (a.getOrigem().compareTo(this) == 0) {
				nos.add(a.getDestino());
			} else if(!a.isDirecionada()) {
				nos.add(a.getOrigem());
			}
		}
		
		return nos;
	}
	
	@Override
	public int compareTo(Object o) {
		if (this.equals(o)) {
			return 0;
		} else {
			return 1;
		}
		
	}
	
}

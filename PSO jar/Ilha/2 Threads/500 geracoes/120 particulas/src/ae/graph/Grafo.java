package ae.graph;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
	
	private List<Aresta> arestas = new ArrayList<Aresta>();
	
	private List<No> nos = new ArrayList<No>();
	
	private boolean direcionado = false;
	
	public List<Aresta> getArestas() {
		return arestas;
	}
	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}
	public List<No> getNos() {
		return nos;
	}
	public void setNos(List<No> nos) {
		this.nos = nos;
	}
	
	public boolean adicionarNo(No obj) {
		if (!existe(nos, obj)) {
			nos.add(obj);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean adicionarArestaNoGrafo(No origem, No destino, int peso) {
		Aresta a = new Aresta(origem, destino, direcionado ,peso);
		
		if (!existe(arestas, a)) {
			arestas.add(a);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean existe(List<?> collection, Object anItem) {
		return collection.contains(anItem);
	}
	public boolean isDirecionado() {
		return direcionado;
	}
	public void setDirecionado(boolean direcionado) {
		this.direcionado = direcionado;
	}
	
}

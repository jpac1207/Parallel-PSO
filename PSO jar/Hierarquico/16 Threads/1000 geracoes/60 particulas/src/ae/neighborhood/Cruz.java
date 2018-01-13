package ae.neighborhood;

import java.util.ArrayList;
import java.util.List;

import ae.Celula;
import ae.Individuo;

public class Cruz implements VizinhancaStrategy{
	
	private int tamanhoVizinhanca;
	
	public Cruz (int tamanhoVizinhanca) {
		this.tamanhoVizinhanca = tamanhoVizinhanca;
	}
	
	@Override
	public List<Individuo> getBairro(Celula[][] celulas, int i, int j) {
		List<Individuo> vizinhos = new ArrayList<Individuo>();
		
		for (int x = 1; x < tamanhoVizinhanca+1; x++) {
			
			try {
				vizinhos.addAll(celulas[i][j-x].getIndividuos()); //Vizinho de cima
			} catch (ArrayIndexOutOfBoundsException e) {} catch(NullPointerException e){
				
			}
			
			try{
				vizinhos.addAll(celulas[i][j+x].getIndividuos()); //Vizinho de baixo
			} catch (ArrayIndexOutOfBoundsException e) {} catch(NullPointerException e){
				
			}
			
			try {
				vizinhos.addAll(celulas[i-x][j].getIndividuos()); //Vizinho da esquerda
			}  catch (ArrayIndexOutOfBoundsException e) {} catch(NullPointerException e){
				
			}
			
			try{
				vizinhos.addAll(celulas[i+x][j].getIndividuos()); //Vizinho da direita
			} catch (ArrayIndexOutOfBoundsException e) {} catch(NullPointerException e){
				
			}
			
		}
		
		return vizinhos;
	}
	
}

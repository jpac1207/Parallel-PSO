package ae.synchronization;

import ae.observer.Observer;

public class SincronizadorGeracao implements Observer{
	
	private int qtdCptGeracao;
	private int qtdCelulas;
	
	public SincronizadorGeracao(int qtdCelulas) {
		this.qtdCelulas = qtdCelulas;
		this.qtdCptGeracao = 0;
	}
	
	public synchronized void sincronizarGeracao() {
		
		qtdCptGeracao++;
		
		if (qtdCptGeracao == qtdCelulas) {
			
			qtdCptGeracao = 0;
			
			notifyAll();
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Object obj) {
		sincronizarGeracao();
	}
	
}

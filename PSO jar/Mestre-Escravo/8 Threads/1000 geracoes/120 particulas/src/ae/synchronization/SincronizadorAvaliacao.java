package ae.synchronization;

import ae.observer.Observer;

public class SincronizadorAvaliacao implements Observer{
	
	private int qtdAvaliaram;
	private int qtdEscravos;
	
	public SincronizadorAvaliacao(int qtdEscravos) {
		this.qtdEscravos = qtdEscravos;
		this.qtdAvaliaram = 0;
	}
	
	public synchronized void sincronizarAvaliacao() {
		qtdAvaliaram++;
		
		if (qtdAvaliaram == qtdEscravos) {
			qtdAvaliaram = 0;
			notifyAll();
		}
	}
	
	public synchronized void esperar() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Object obj) {
		sincronizarAvaliacao();
	}
	
}

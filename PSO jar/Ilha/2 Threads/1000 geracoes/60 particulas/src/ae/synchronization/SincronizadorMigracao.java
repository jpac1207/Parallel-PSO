package ae.synchronization;

import ae.observer.Observer;

public class SincronizadorMigracao implements Observer{
	
	private int qtdMigraram;
	private int qtdIlhas;
	
	public SincronizadorMigracao(int qtdIlhas) {
		this.qtdIlhas = qtdIlhas;
		this.qtdMigraram = 0;
	}
	
	public synchronized void sincronizarMigracao() {
		qtdMigraram++;
		
		if (qtdMigraram == qtdIlhas) {
			qtdMigraram = 0;
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
		sincronizarMigracao();
	}
	
}

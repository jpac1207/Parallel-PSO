package ae.util;

import java.text.NumberFormat;

import ae.Individuo;

public class Printer {
	
	private NumberFormat format = NumberFormat.getNumberInstance();
	
	public void imprimirIndividuo(Individuo i) {
		
		format.setMaximumFractionDigits(2000);
		
		System.out.print("----MELHOR INDIV�DUO----\n" + i.toString() + "\n\n" + "APTID�O: " + format.format(i.getAptidao()));
	}
	
}

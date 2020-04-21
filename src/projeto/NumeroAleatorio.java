package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumeroAleatorio {

	private List<Integer> idsUsadas = new ArrayList<>();

	public int buscaIdAleatoriaNaoRepetida() {
		int novaId = buscaNumeroAleatorio(0, 100);

		while (idsUsadas.contains(novaId)) {
			novaId = buscaNumeroAleatorio(0, 100);
		}

		idsUsadas.add(novaId);
		return novaId;
	}

	public int buscaNumeroAleatorio(int min, int max) {
		Random rand = new Random();
		int numero = rand.nextInt((max - min) + 1) + min;
		return numero;
	}

}

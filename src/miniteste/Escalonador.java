package miniteste;
import java.io.PrintWriter;
import java.util.List;

public class Escalonador extends Thread {
	
	private GeradorNumeros geradorNumeros;
	private List<Integer> fila1numerosAleatorios;
	private List<Integer> fila2numerosAleatorios;
	private int tempo;
	private int fila1Chegada;
	private int fila2Chegada;
	private int fila1Saida;
	private int fila2Saida;
	private int filaChegada1;
	private int filaChegada2;
	private int a;
	private int c;
	private int fila1m;
	private int fila2m;
	private int fila1Indice;
	private int fila2Indice;
	private boolean servico;
	private PrintWriter escritor;

	/**
	 * Construtor
	 * @param tempo de execucao
	 */
	
	public Escalonador(int tempo) {
		this.tempo = tempo;
		this.fila1Chegada = 0;
		this.fila2Chegada = 0;
		this.fila1Saida = 0;
		this.fila2Saida = 0;
		this.filaChegada1 = 0;
		this.filaChegada2 = 0;
		this.servico = false;
		this.geradorNumeros = new GeradorNumeros(7);
		this.a = 1;
		this.c = 3;
		this.fila1m = 10;
		this.fila2m = 5;
		this.fila1numerosAleatorios = geradorNumeros.metodoMisto(a, c, fila1m);
		this.fila2numerosAleatorios = geradorNumeros.metodoMisto(a, c, fila2m);
		this.fila1Indice = 0;
		this.fila2Indice = 0;
		
		this.fila1EscalonaChegada(0);
		this.fila2EscalonaChegada(0);

		try {
			escritor = new PrintWriter("saida/escalonador", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		escritor.println("-*-");
		escritor.println("Tempo de escalonamento" + this.tempo + "'");
		escritor.println(" Chegada de fregues na fila 1 em " + this.fila1Chegada + "'");
		escritor.println(" Chegada de fregues na fila 2 em " + this.fila2Chegada + "'");
		escritor.println("-*-");
		escritor.println();
	}
	
	@Override
	public void run() {
		int segundos = 0;
		while (segundos < this.tempo) {
			try {
				this.sleep(1);
				segundos++;
				//fila1ChecaEventosCriados(segundos);
				//fila2ChegaEventosCriados(segundos);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		escritor.close();
	}
	
	

	public void fila1ChecaEventosCriados(int segundos) {
		boolean houveAlteracao = false;
		if (segundos == fila1Chegada) {
			houveAlteracao = true;
			escritor.println("Evento 1 > Chegada de fregues na fila 1 aos " + segundos + "'");
			if (!this.servico) {
				fila1EscalonaSaida(segundos);
				this.servico = true;
				escritor.println("Evento 2 > atendimento na fila 1 iniciado em "	+ segundos + "'");
				escritor.println("Atendimento na fila 1 encerrado em " + (this.fila1Saida - segundos)	+ "'");
			} else {
				this.filaChegada1++;
				escritor.println("Evento 4 > fila 1 esperando atendimento");
				escritor.println("Tamanho da fila 1 em " + segundos + "'" + this.filaChegada1);
			}
			fila1EscalonaChegada(segundos);
			escritor.println("Proximo fregues na fila 1 em " + (this.fila1Chegada - segundos) + "'");
		}

		if (segundos == this.fila1Saida) {
			houveAlteracao = true;
			escritor.println("Evento 3 > atendimento na fila 1 encerrado em " + segundos + "'");
			if (this.filaChegada1 != 0) {
				this.filaChegada1--;
				this.fila1EscalonaSaida(segundos);
				this.servico = true;
				escritor.println("Evento 2 > atendimento na fila 1 iniciado em "	+ segundos + "'");
				escritor.println("Atendimento na fila 1 encerrado em " + (this.fila1Saida - segundos)	+ "'");
			} else {
				this.servico = false;
			}
		}
		if (houveAlteracao) {
			if (this.servico) {
				escritor.println("Sistema ocupado com fila 1 em " + segundos + "'");
			} else {
				escritor.println("Sistema livre para fila 1 em " + segundos + "'");
			}
			escritor.println("Tamanho da fila 1 em " + segundos + "'" + this.filaChegada1);
			escritor.println("-*-");
		}
	}
	public void fila2ChecaEventosCriados(int segundos) {
		boolean houveAlteracao = false;
		if (segundos == fila2Chegada) {
			houveAlteracao = true;
			escritor.println("Evento 1 > Chegada de fregues na fila 2 aos " + segundos + "'");
			if (!this.servico) {
				fila2EscalonaSaida(segundos);
				this.servico = true;
				escritor.println("Evento 2 > Atendimento na fila 2 iniciado em "	+ segundos + "'");
				escritor.println("Atendimento na fila 2 encerrado em " + (this.fila2Saida - segundos)	+ "'");
			} else {
				this.filaChegada2++;
				escritor.println("Evento 4 > fila 2 esperando atendimento");
				escritor.println("Tamanho da fila 2 em " + segundos + "'" + this.filaChegada2);
			}
			fila2EscalonaChegada(segundos);
			escritor.println("Proximo fregues na fila 2 em " + (this.fila2Chegada - segundos) + "'");
		}

		if (segundos == this.fila2Saida) {
			houveAlteracao = true;
			escritor.println("Evento 3 > atendimento na fila 2 encerrado em " + segundos + "'");
			if (this.filaChegada2 != 0) {
				this.filaChegada2--;
				this.fila2EscalonaSaida(segundos);
				this.servico = true;
				escritor.println("Evento 2 > Atendimento na fila 2 iniciado em "	+ segundos + "'");
				escritor.println("Atendimento na fila 2 encerrado em " + (this.fila2Saida - segundos)	+ "'");
			} else {
				this.servico = false;
			}
		}
		if (houveAlteracao) {
			if (this.servico) {
				escritor.println("Sistema ocupado com fila 2 em " + segundos + "'");
			} else {
				escritor.println("Sistema livre para fila 2 em " + segundos + "'");
			}
			escritor.println("Tamanho da fila 2 em " + segundos + "'" + this.filaChegada2);
			escritor.println("-*-");
		}
	}
	

	public void fila1GeraNumerosAleatorios(){
		if (fila1Indice == (fila1numerosAleatorios.size()-1)){
			this.a++;
			this.fila1m++;
			this.fila1numerosAleatorios = geradorNumeros.metodoMisto(a, c, fila1m);
			this.fila1Indice = 0;
		}
	}
	
	public void fila2GeraNumerosAleatorios(){
		if (fila2Indice == (fila2numerosAleatorios.size()-1)){
			this.a++;
			this.fila2m++;
			this.fila1numerosAleatorios = geradorNumeros.metodoMisto(a, c, fila2m);
			this.fila2Indice = 0;
		}
	}
	
	public void fila1EscalonaChegada(int segundos) {
		this.fila1GeraNumerosAleatorios();
		this.fila1Chegada = segundos + fila1numerosAleatorios.get(fila1Indice);
		this.fila1Indice++;
	}
	
	public void fila2EscalonaChegada(int segundos) {
		this.fila2GeraNumerosAleatorios();
		this.fila2Chegada = segundos + fila2numerosAleatorios.get(fila2Indice);
		this.fila2Indice++;
	}
	
	public void fila1EscalonaSaida(int segundos) {
		this.fila1GeraNumerosAleatorios();
		this.fila1Saida = segundos + fila1numerosAleatorios.get(fila1Indice);
		this.fila1Indice++;
	}
	
	public void fila2EscalonaSaida(int segundos) {
		this.fila2GeraNumerosAleatorios();
		this.fila2Saida = segundos + fila2numerosAleatorios.get(fila2Indice);
		this.fila2Indice++;
	}

	
	public static void main(String[] args) {
		int tempoDeEscalonamento = 300; //em segundos
		Escalonador miniteste = new Escalonador(tempoDeEscalonamento);
		
		//inicia a thread
		miniteste.start();
	}
}


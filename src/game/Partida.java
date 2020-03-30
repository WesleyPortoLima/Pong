package game;

public class Partida {
	public static int pontosInimigo, pontos;
	public static boolean iniciada;
	
	@SuppressWarnings("static-access")
	public Partida(int pontosInimigo, int pontos, boolean iniciada) {
		this.pontosInimigo = pontosInimigo;
		this.pontos = pontos;
		this.iniciada = iniciada;
	}
}

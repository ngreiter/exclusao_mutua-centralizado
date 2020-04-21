package projeto;

public class Processo {

	private int id;
	private boolean isCoordernador;
	private boolean ativo;
	private Processo proxProcesso;

	public Processo(int id, boolean isCoordernador, boolean ativo, Processo proxProcesso) {
		this.id = id;
		this.isCoordernador = isCoordernador;
		this.ativo = ativo;
		this.proxProcesso = proxProcesso;
	}

	public Processo getProxProcesso() {
		return proxProcesso;
	}

	public void setProxProcesso(Processo proxProcesso) {
		this.proxProcesso = proxProcesso;
	}

	public Processo() {
		// Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCoordernador() {
		return isCoordernador;
	}

	public void setCoordernador(boolean isCoordernador) {
		this.isCoordernador = isCoordernador;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}

package centralizado;

public class Process {

	private int id;
	private boolean ativo;
	private boolean isCoordernador;

	public Process(int id, boolean isCoordernador) {
		this.id = id;
		this.ativo = true;
		this.isCoordernador = isCoordernador;
	}

	public Process() {
		// Auto-generated constructor stub
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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

}
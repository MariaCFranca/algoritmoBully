package bully;

public class Processo {
	
	private boolean isCoordenador;
	private int iID;
	private boolean ativo;
	private Processo coordenador;
	
	public Processo getCoordenador() {
		return coordenador;
	}
	
	public void setCoordenador(Processo coordenador) {
		this.coordenador = coordenador;
	}
	
	public Processo(int id) {
		this.isCoordenador = false;
		this.iID = id;
		this.ativo = true;
	}
	
	public boolean isbCoordenador() {
		return isCoordenador;
	}
	
	public void setbCoordenador(boolean bCoordenador) {
		this.isCoordenador = bCoordenador;
	}
	
	public int getiID() {
		return this.iID;
	}
	
	public void setiID(int iID) {
		this.iID = iID;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}

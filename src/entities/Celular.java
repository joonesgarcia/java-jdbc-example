package entities;
public final class Celular extends Produto {
	
	private String modelo, marca;
	private Integer memoria;
	private Boolean acessorios;
	
	// implementacao dos metodos herdados
	
	public String consultaValorRevenda() {
		return "[REVENDA] Celular #" + this.modelo + ": R$ " + this.valorRevenda;
	}
	
	public String consultacustoUnitario() {
		return "[CUSTO] Celular #" + this.modelo + ": " + this.custoUnitario;
	}
	
	//GETTERS e setters do encapsulamento
	
	public String getModelo() {
		return modelo;
	}
	public String getMarca() {
		return marca;
	}	
	public int getMemoria() {
		return memoria;
	}
	public boolean getAcessorios() {
		return acessorios;
	}
	
	//
	
	public void setModelo(String argModelo) {
		this.modelo = argModelo;
	}
	public void setMarca(String argMarca) {
		this.marca = argMarca;
	}
	public void setMemoria(int argMemoria) {
		this.memoria = argMemoria;
	}
	public void setAcessorios(boolean argAcessorios) {
		this.acessorios = argAcessorios;
	}
	
	// Construtor
	
	public Celular (String argSetor, String argStatus, float agrValorRevenda, float argCustoUnitario,  //argumentos Produto
			String argModelo, String argMarca, int argMemoria, boolean argAcessorios) { 							//argumentos Celular
		setor=argSetor;
		status=argStatus;
		valorRevenda=agrValorRevenda;
		custoUnitario=argCustoUnitario;
	
		this.modelo=argModelo;
		this.marca=argMarca;
		this.memoria=argMemoria;
		this.acessorios=argAcessorios;
	}
}
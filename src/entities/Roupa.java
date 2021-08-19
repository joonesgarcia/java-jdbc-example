package entities;
public final class Roupa extends Produto {
	
	// implementacao dos metodos herdados
	
	public String consultaValorRevenda() {
		return "[REVENDA] Roupa #" + this.tipo + ": R$ " + this.valorRevenda;
	}
	
	public String consultacustoUnitario() {
		return "[CUSTO] Roupa #" + this.tipo + ": " + this.custoUnitario;
	}

	// Atributos especificos
	
	private String tipo, cor;
	private Byte tamanho;
	
	//GETTERS e setters do encapsulamento
	
	public String getTipo() {
		return tipo;
	}
	public String getCor() {
		return cor;
	}
	public byte getTamanho() {
		return tamanho;
	}
	
	//
	
	public void setTipo(String argTipo) {
		this.tipo = argTipo;
	}

	public void setCor(String argCor) {
		this.cor = argCor;
	}

	public void setTamanho(byte argTamanho) {
		this.tamanho = argTamanho;
	}
	
	// Construtor
	
	public Roupa (String argSetor, String argStatus, float agrValorRevenda, float argCustoUnitario,  //argumentos Produto
			String argTipo, String argCor, byte argTamanho) {													   //argumentos Roupa	
		this.setor=argSetor;
		this.status=argStatus;
		this.valorRevenda=agrValorRevenda;
		this.custoUnitario=argCustoUnitario;
			
		this.tipo= argTipo;
		this.cor=argCor;
		this.tamanho=argTamanho;
	}
	
}
package entities;
// Nao usei INTERFACE nesta SUPERCLASSE uma vez que as suas SUBCLASSES foram obtidas por heranca simples.

abstract class Produto {		
	
	protected double id; 													//id preenchido no banco de dados	
	protected String setor, status;
	protected float valorRevenda;
	protected float custoUnitario;

	
	// metodos abstratos que devem implementados por subclasses
	
	public abstract String consultaValorRevenda();
	public abstract String consultacustoUnitario();
	
	
	// funcoes comuns a Produtos
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getValorRevenda() {
		return valorRevenda;
	}
	public void setValorRevenda(float valorRevenda) {
		this.valorRevenda = valorRevenda;
	}
	public float getCustoUnitario() {
		return custoUnitario;
	}
	public void setCustoUnitario(float custoUnitario) {
		this.custoUnitario = custoUnitario;
	}
	
	
}
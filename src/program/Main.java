package program;
import java.util.Scanner;

import entities.Celular;
import entities.Roupa;

import java.sql.*;

class Main {
		
	public static void main(String[] args) {	

		// Menu persistente
		
		while (true) {
			System.out.println("SISTEMA DE CONTROLE DE MERCADORIAS");
			System.out.println("----------------------------------");
			System.out.println("DIGITE :\n");
			System.out.println("[1]: Buscar produto");				
			System.out.println("[2]: Cadastrar produto");         
			System.out.println("[3]: Atualizar produto");		
			System.out.println("[4]: Remover produto");          
			System.out.println("----------------------------------");		
			System.out.println("[5]: Mostrar produtos em [ESTOQUE]");
			System.out.println("[6]: Mostrar produtos [VENDIDOS]");
			System.out.println("[7]: Mostrar todos produtos");
			
			System.out.println("\n------ Digite [0] para SAIR ------");
			
			Scanner scan = new Scanner(System.in);
			int opcao = scan.nextInt();
			scan.nextLine();
			if (opcao==0) {break;}

			// Parametros de conexao com o banco via JDBC
			
			String url = "jdbc:postgresql://localhost:5432/Mercadorias";
			String username = "postgres";
			String password = "admin";	
					
			switch(opcao) {
			case 1:
				System.out.println("Digite o setor: [TI] ou [VESTUARIO]");
				String setor = scan.nextLine();
				if (setor.equals("TI")) { 		
					System.out.println("[1] Ver todo setor  [2] Busca por Marca");
					int option = scan.nextInt();
					scan.nextLine();
					if(option==1) {
						try (Connection conn = DriverManager.getConnection(url, username, password)){
							String sql = "SELECT * FROM celulares";
							Statement statement = conn.createStatement();
							ResultSet result = statement.executeQuery(sql);
							while(result.next()) {	
								int id = result.getInt("id");
								String status = result.getString("status");
								float valorRevenda = result.getFloat("valor_revenda");
								
								String modelo = result.getString("modelo");
								String marca = result.getString("marca");
								int memoria = result.getInt("memoria");
								
								String msg = "ID # %d : [%s] %s %s | %d GB: R$ %f";
								System.out.println(String.format(msg, id, status, marca, modelo, memoria, valorRevenda));
							}
						}catch (SQLException e) {
							System.out.println("Erro na conexao com o servidor");
							e.printStackTrace();
						}
					}else if(option==2) {
						System.out.println("Digite a marca: Samsung, Motorola, Iphone");
						String opMarca = scan.nextLine();
						
						try (Connection conn = DriverManager.getConnection(url, username, password)){
							String sql = "SELECT * FROM celulares WHERE marca='" + opMarca + "'";
							Statement statement = conn.createStatement();
							ResultSet result = statement.executeQuery(sql);
							while(result.next()) {	
								int id = result.getInt("id");
								String status = result.getString("status");
								float valorRevenda = result.getFloat("valor_revenda");
								
								String modelo = result.getString("modelo");
								String dbmarca = result.getString("marca");
								int memoria = result.getInt("memoria");
								
								String msg = "ID # %d : [%s] %s %s | %d GB: R$ %f";
								System.out.println(String.format(msg, id, status, dbmarca, modelo, memoria, valorRevenda));
							}
						}catch (SQLException e) {
							System.out.println("Erro na conexao com o servidor");
							e.printStackTrace();
						}
					}
				}else if (setor.equals("VESTUARIO")) {
				
					try (Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "SELECT * FROM roupas";
						Statement statement = conn.createStatement();
						ResultSet result = statement.executeQuery(sql);
						while(result.next()) {		
							int id = result.getInt("id");
							String status = result.getString("status");
							float valorRevenda = result.getFloat("valor_revenda");
							
							String tipo = result.getString("tipo");
							String cor = result.getString("cor");
							int tamanho = result.getInt("tamanho");
							
							String msg = "ID # %d : [%s] %s %s | Tamanho %d: R$ %f";
							System.out.println(String.format(msg, id, status, tipo, cor, tamanho, valorRevenda));
						}
					}catch (SQLException e) {
						System.out.println("Erro na conexao com o servidor");
						e.printStackTrace();
					}					
				}
			break;		
			case 2:
				String status="ESTOQUE";										//STATUS=ESTOQUE p/ todo novo produto
				
				System.out.println("Digite o setor: [TI] ou [VESTUARIO]");
				setor = scan.nextLine();
				System.out.println("Digite o valor de custo:");
				float custoUnitario = scan.nextFloat();
				scan.nextLine();
				System.out.println("Digite o valor de revenda:");
				float valorRevenda = scan.nextFloat();
				scan.nextLine();
				
				if (setor.equals("TI")) {
					System.out.println("Digite a marca: Samsung, Motorola, Iphone");
					String marca = scan.nextLine();
					
					System.out.println("Digite o modelo:");
					String modelo = scan.nextLine();

					System.out.println("Digite o armazenamento: ");
					int memoria = scan.nextInt();
					scan.nextLine();
					
					System.out.println("Possui Acessorios? [1] Sim [2] Nao ");
					boolean acessorios;
					byte op = scan.nextByte();
					scan.nextLine();
						if (op==1) acessorios=true;
						else acessorios=false;
					
					// Instanciando objeto que sera convertido em query SQL 
					Celular c1 = new Celular(setor, status, valorRevenda, custoUnitario,
							modelo, marca, memoria, acessorios);		
						
					try (Connection conn = DriverManager.getConnection(url, username, password)){
						System.out.println("Conectado ao servidor");
						
						// enviando objeto Celular ao banco de dados
						
						String sql = "INSERT INTO celulares (setor, status, valor_revenda, custo_unitario,"
								+ "modelo, marca, memoria, acessorios) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement statement = conn.prepareStatement(sql);
						
						statement.setString(1, c1.getSetor());
						statement.setString(2, c1.getStatus());
						statement.setFloat(3, c1.getValorRevenda());
						statement.setFloat(4, c1.getCustoUnitario());
						
						statement.setString(5, c1.getModelo());
						statement.setString(6, c1.getMarca());
						statement.setInt(7, c1.getMemoria());
						statement.setBoolean(8, c1.getAcessorios());

						if (statement.executeUpdate()!=0) System.out.println("Inserido com sucesso.");
						conn.close();
						System.out.println("Conexao encerrada.");

					} catch (SQLException e) {
						System.out.println("Erro na conexao com o servidor");
						e.printStackTrace();
					}
					
					
					}else if (setor.equals("VESTUARIO")) {
					
						System.out.println("Digite o tipo: Calca, Blusa, ...");
						String tipo = scan.nextLine();	
						System.out.println("Digite a cor: ");
						String cor = scan.nextLine();	
						System.out.println("Digite o tamanho: 35, 36, ..");
						byte tamanho = scan.nextByte();
						scan.nextLine();
						
						// Instanciando o objeto com os dados fornecidos 
						Roupa r1 = new Roupa (setor, status, valorRevenda, custoUnitario, 
								tipo, cor, tamanho);
						
						// Iniciando conexao com o banco via JDBC						
						
						try(Connection conn = DriverManager.getConnection(url, username, password)) {
							System.out.println("Conectado ao servidor");
							
							// enviando objeto Roupa ao banco de dados
							String sql = "INSERT INTO roupas (setor, status, valor_revenda, custo_unitario,"
									+ " tipo, cor, tamanho) VALUES (?, ?, ?, ?, ?, ?, ?)";
							
							PreparedStatement statement = conn.prepareStatement(sql);
								statement.setString(1, r1.getSetor());
								statement.setString(2, r1.getStatus());
								statement.setFloat(3, r1.getValorRevenda());
								statement.setFloat(4, r1.getCustoUnitario());
								
								statement.setString(5, r1.getTipo());
								statement.setString(6, r1.getCor());
								statement.setByte(7, r1.getTamanho());
							
							if (statement.executeUpdate()!=0) System.out.println("Inserido com sucesso.");
							conn.close();
							System.out.println("Conexao encerrada.");					
						}catch (SQLException e) {
							System.out.println("Erro na conexao com o servidor");
							e.printStackTrace();
						}
					}
				break;
				case 3:
					
					System.out.println("Digite o setor: [TI] ou [VESTUARIO]");
					String alter_setor = scan.nextLine();
						if (alter_setor.equals("TI")) {
							System.out.println("Digite o [ID] do item a ser atualizado: ");
							int idAlter = scan.nextInt();
							System.out.println("Escolha o dado a ser atualizado: ");
							System.out.println(" 1:[VALOR DE CUSTO] | 2:[VALOR DE REVENDA] | 3:[ALTERAR PARA VENDIDO]");
							int opAlter = scan.nextInt();
 

							String sql = "UPDATE celulares SET ";
							if (opAlter==1) {
								System.out.println(" Digite o valor atualizado: ");
								float newValue = scan.nextInt();
									sql += "custo_unitario=" + newValue
											+ "WHERE id=" + idAlter;
								try(Connection conn = DriverManager.getConnection(url, username, password)) {
									PreparedStatement statement = conn.prepareStatement(sql);									
									if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
									conn.close();
									System.out.println("Conexao encerrada.");	
								}catch (SQLException e) {
									System.out.println("Erro na conexao com o servidor");
									e.printStackTrace();
								}								
							}else if (opAlter==2) {
								System.out.println(" Digite o valor atualizado: ");
								float newValue = scan.nextInt();
									sql += "valor_revenda=" + newValue
											+ "WHERE id=" + idAlter;
								try(Connection conn = DriverManager.getConnection(url, username, password)) {
									PreparedStatement statement = conn.prepareStatement(sql);									
									if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
									conn.close();
									System.out.println("Conexao encerrada.");	
								}catch (SQLException e) {
									System.out.println("Erro na conexao com o servidor");
									e.printStackTrace();
								}								
							}else if (opAlter==3) {

								sql += "status='VENDIDO' WHERE id=" + idAlter;
							try(Connection conn = DriverManager.getConnection(url, username, password)) {
								PreparedStatement statement = conn.prepareStatement(sql);									
								if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
								conn.close();
								System.out.println("Conexao encerrada.");	
							}catch (SQLException e) {
								System.out.println("Erro na conexao com o servidor");
								e.printStackTrace();
							}								
						}
						
						}else if(alter_setor.equals("VESTUARIO")) {
							System.out.println("Digite o [ID] do item a ser atualizado: ");
							int idAlter = scan.nextInt();
							System.out.println("Escolha o dado a ser atualizado: ");
							System.out.println(" 1:[VALOR DE CUSTO] | 2:[VALOR DE REVENDA] | 3:[ALTERAR PARA VENDIDO]");
							int opAlter = scan.nextInt();

							String sql = "UPDATE roupas SET ";
							System.out.println(sql);
							if (opAlter==1) {
								System.out.println(" Digite o valor atualizado: ");
								float newValue = scan.nextInt();
									sql += "custo_unitario=" + newValue
											+ "WHERE id=" + idAlter;
								try(Connection conn = DriverManager.getConnection(url, username, password)) {
									PreparedStatement statement = conn.prepareStatement(sql);									
									if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
									conn.close();
									System.out.println("Conexao encerrada.");	
								}catch (SQLException e) {
									System.out.println("Erro na conexao com o servidor");
									e.printStackTrace();
								}								
							}else if (opAlter==2) {
								System.out.println(" Digite o valor atualizado: ");
								float newValue = scan.nextInt();
									sql += "valor_revenda=" + newValue
											+ "WHERE id=" + idAlter;
								try(Connection conn = DriverManager.getConnection(url, username, password)) {
									PreparedStatement statement = conn.prepareStatement(sql);									
									if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
									conn.close();
									System.out.println("Conexao encerrada.");	
								}catch (SQLException e) {
									System.out.println("Erro na conexao com o servidor");
									e.printStackTrace();
								}								
							}else if (opAlter==3) {
								sql += "status='VENDIDO' WHERE id=" + idAlter;
							try(Connection conn = DriverManager.getConnection(url, username, password)) {
								PreparedStatement statement = conn.prepareStatement(sql);									
								if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
								conn.close();
								System.out.println("Conexao encerrada.");	
							}catch (SQLException e) {
								System.out.println("Erro na conexao com o servidor");
								e.printStackTrace();
							}								
						} 
						}
				break;
				case 4:	
				System.out.println("Digite o setor: [TI] ou [VESTUARIO]");
				String remove_setor = scan.nextLine();
					if (remove_setor.equals("TI")) {
						System.out.println("Digite o [ID] do item a ser excluido: ");
						int idRemove = scan.nextInt();
						String sql = "DELETE FROM celulares WHERE id=" + idRemove;
						try(Connection conn = DriverManager.getConnection(url, username, password)) {
							PreparedStatement statement = conn.prepareStatement(sql);									
							if (statement.executeUpdate()!=0) System.out.println("Atualizado com sucesso.");
							conn.close();
							System.out.println("Conexao encerrada.");	
						}catch (SQLException e) {
							System.out.println("Erro na conexao com o servidor");
							e.printStackTrace();
						}
					}else if(remove_setor.equals("VESTUARIO")) {
						System.out.println("Digite o [ID] do item a ser excluido: ");
						int idRemove = scan.nextInt();
						String sql = "DELETE FROM roupas WHERE id=" + idRemove;
						try(Connection conn = DriverManager.getConnection(url, username, password)) {
							PreparedStatement statement = conn.prepareStatement(sql);									
							if (statement.executeUpdate()!=0) System.out.println("Excluido com sucesso.");
							conn.close();
							System.out.println("Conexao encerrada.");	
						}catch (SQLException e) {
							System.out.println("Erro na conexao com o servidor");
							e.printStackTrace();
						}
					} 
				break;	
				case 5:
					try (Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "SELECT * FROM celulares where status='ESTOQUE'";
						Statement statement = conn.createStatement();
						ResultSet result = statement.executeQuery(sql);
						while(result.next()) {													// Celulares em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String modeloE = result.getString("modelo");
							String marcaE = result.getString("marca");
							int memoriaE = result.getInt("memoria");
							
							String msg = "ID # %d : [%s] %s %s | %d GB: R$ %f";
							System.out.println(String.format(msg, idE, statusE, marcaE, modeloE, memoriaE, valorRevendaE));
						}
						sql = "SELECT * FROM roupas where status='ESTOQUE'";
						result = statement.executeQuery(sql);
						while(result.next()) {													// Roupas em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String tipoE = result.getString("tipo");
							String corE = result.getString("cor");
							int tamanhoE = result.getInt("tamanho");
							
							String msg = "ID # %d : [%s] %s %s | Tamanho %d: R$ %f";
							System.out.println(String.format(msg, idE, statusE, tipoE, corE, tamanhoE, valorRevendaE));
						}
					}catch (SQLException e) {
						System.out.println("Erro na conexao com o servidor");
						e.printStackTrace();
					}	
					
				break;
				case 6:
					try (Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "SELECT * FROM celulares where status!='ESTOQUE'";
						Statement statement = conn.createStatement();
						ResultSet result = statement.executeQuery(sql);
						while(result.next()) {													// Celulares em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String modeloE = result.getString("modelo");
							String marcaE = result.getString("marca");
							int memoriaE = result.getInt("memoria");
							
							String msg = "ID # %d : [%s] %s %s | %d GB: R$ %f";
							System.out.println(String.format(msg, idE, statusE, marcaE, modeloE, memoriaE, valorRevendaE));
						}
						sql = "SELECT * FROM roupas where status!='ESTOQUE'";
						result = statement.executeQuery(sql);
						while(result.next()) {													// Roupas em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String tipoE = result.getString("tipo");
							String corE = result.getString("cor");
							int tamanhoE = result.getInt("tamanho");
							
							String msg = "ID # %d : [%s] %s %s | Tamanho %d: R$ %f";
							System.out.println(String.format(msg, idE, statusE, tipoE, corE, tamanhoE, valorRevendaE));
						}
					}catch (SQLException e) {
						System.out.println("Erro na conexao com o servidor");
						e.printStackTrace();
					}
				break;
				case 7:
					try (Connection conn = DriverManager.getConnection(url, username, password)){
						String sql = "SELECT * FROM celulares";
						Statement statement = conn.createStatement();
						ResultSet result = statement.executeQuery(sql);
						while(result.next()) {													// Celulares em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String modeloE = result.getString("modelo");
							String marcaE = result.getString("marca");
							int memoriaE = result.getInt("memoria");
							
							String msg = "ID # %d : [%s] %s %s | %d GB: R$ %f";
							System.out.println(String.format(msg, idE, statusE, marcaE, modeloE, memoriaE, valorRevendaE));
						}
						sql = "SELECT * FROM roupas";
						result = statement.executeQuery(sql);
						while(result.next()) {													// Roupas em estoque
							int idE = result.getInt("id");
							String statusE = result.getString("status");
							float valorRevendaE = result.getFloat("valor_revenda");
							
							String tipoE = result.getString("tipo");
							String corE = result.getString("cor");
							int tamanhoE = result.getInt("tamanho");
							
							String msg = "ID # %d : [%s] %s %s | Tamanho %d: R$ %f";
							System.out.println(String.format(msg, idE, statusE, tipoE, corE, tamanhoE, valorRevendaE));
						}
					}catch (SQLException e) {
						System.out.println("Erro na conexao com o servidor");
						e.printStackTrace();
					}
				break;
				}
			
			
		}
	
	}
}
	
	

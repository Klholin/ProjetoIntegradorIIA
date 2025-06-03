package com.universidade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoBanco {
    private String nomeBanco;
	private String senha;
	private String nomeUsuario;
	private Connection conexao;
	
	public ConexaoBanco(String nomeBanco, String senha, String nomeUsuario) {
		this.nomeBanco = nomeBanco;
		this.senha = senha;
		this.nomeUsuario = nomeUsuario;
	}
	
	public ConexaoBanco() {
		
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public void conectar () {
		this.conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.nomeBanco,this.nomeUsuario,this.senha);
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver do banco de dados não localizado.");
		} catch (SQLException e) {
			System.out.println("Erro ao tentar acessar o banco de dados: " + e.getMessage());
		}
	}
	
	public void desconectar () {
		if(this.conexao != null) {
		try {
			this.conexao.close();
		} catch (SQLException e) {System.out.println("Erro ao tentar fechar o banco de dados: " + e.getMessage());}
		this.conexao = null;
		}else{System.out.println("Não há conexão para ser encerrada");}
	}
	
	public int consultar(int codigo) {
		int codigoProd;
		String descricaoProd;
		double valor_Unitario;
		try {
			ResultSet rsProduto = conexao.createStatement().executeQuery ("SELECT * FROM tab_dados_produtos WHERE codigo = "+codigo);
			if(rsProduto.next()) {
			codigoProd  = rsProduto.getInt("codigo");
			descricaoProd  = rsProduto.getString("descricao");
			valor_Unitario  = rsProduto.getDouble("valor_unitario");	
			System.out.println("Codigo:"+codigoProd+"\nDescrição:"+descricaoProd+"\nValor:"+valor_Unitario);				
			}else{
				System.out.println("O produto informado não existe");
			}
			}catch(SQLException e){
			System.out.println("Erro ao tentar acessar o banco de dados: " + e.getMessage());}
		return 0;
		}
	
	public int inserir(int codigoProd,String descricaoProd,double valor_unitario) {	
		int resultadoOp = -1 ;
		String mensQuery = "INSERT INTO tab_dados_produtos (codigo,descricao,valor_unitario) VALUES (?, ?, ?);";
		try {
			PreparedStatement pst = conexao.prepareStatement(mensQuery);
			pst.setInt(1,codigoProd);
			pst.setString(2,descricaoProd);
			pst.setDouble(3,valor_unitario);
			resultadoOp = pst.executeUpdate();
			pst.close();
		}catch(SQLException e) {
			System.out.println("Erro ao realizar operação: "+ e.getMessage());
		}
		return resultadoOp;
	}
	
	public int update(int codigoProd,String descricaoProd,double valor_unitario) {	
		int resultadoOp = -1 ;
		String mensQuery = "UPDATE tab_dados_produtos SET descricao = ?, valor_unitario = ? WHERE codigo = ?";
		try {
			PreparedStatement pst = conexao.prepareStatement(mensQuery);
			pst.setString(1,descricaoProd);
			pst.setDouble(2,valor_unitario);
			pst.setInt(3,codigoProd); // parâmetro do where, o último ?
			resultadoOp = pst.executeUpdate();
			pst.close();
		}catch(SQLException e) {
			System.out.println("Erro ao realizar operação: "+ e.getMessage());
		}
		return resultadoOp;
	}
}

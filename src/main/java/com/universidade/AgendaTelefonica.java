package com.universidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.universidade.entity.Contato;

public class AgendaTelefonica {
    private ConexaoBanco conexaoBanco;

    public AgendaTelefonica(ConexaoBanco conexaoBanco) {
        this.conexaoBanco = conexaoBanco;
    }

    public boolean adicionarContato(Contato contato) {
        String sql = "INSERT INTO contato (nome, telefone, email) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = conexaoBanco.getConexao().prepareStatement(sql);
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getTelefone());
            pst.setString(3, contato.getEmail());
            int res = pst.executeUpdate();
            pst.close();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar contato: " + e.getMessage());
            return false;
        }
    }

    public boolean removerContato(String nome) {
        String sql = "DELETE FROM contato WHERE nome = ?";
        try {
            PreparedStatement pst = conexaoBanco.getConexao().prepareStatement(sql);
            pst.setString(1, nome);
            int res = pst.executeUpdate();
            pst.close();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover contato: " + e.getMessage());
            return false;
        }
    }

    public Contato buscarContato(String nome) {
        String sql = "SELECT * FROM contato WHERE nome = ?";
        try {
            PreparedStatement pst = conexaoBanco.getConexao().prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();
            Contato contato = null;
            if (rs.next()) {
                contato = new Contato();
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
            }
            rs.close();
            pst.close();
            return contato;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contato: " + e.getMessage());
            return null;
        }
    }

    public List<Contato> listarContatos() {
        List<Contato> lista = new ArrayList<>();
        String sql = "SELECT * FROM contato";
        try {
            Statement st = conexaoBanco.getConexao().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                lista.add(contato);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        }
        return lista;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinic
 */
public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, celular, email, endereco, dataCadastro) VALUES(?, ?, ?, ?, ?)";
        String sqlPF = "INSERT INTO pessoa_fisica(id_cliente, cpf, dataNascimento) VALUES((SELECT max(id) FROM cliente), ?, ?)";
        String sqlPJ = "INSERT INTO pessoa_juridica(id_cliente, cnpj, inscricaoEstadual, dataNascimento) VALUES((SELECT max(id) FROM cliente), ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());
            stmt.setDate(5, java.sql.Date.valueOf(cliente.getDataCadastro()));
            stmt.execute();
            if (cliente instanceof PessoaFisica) {
                stmt = connection.prepareStatement(sqlPF);
                stmt.setString(1, ((PessoaFisica)cliente).getCpf());
                stmt.setDate(2, java.sql.Date.valueOf(((PessoaFisica)cliente).getDataNascimento()));
                stmt.execute();
            } else {
                stmt = connection.prepareStatement(sqlPJ);
                stmt.setString(1, ((PessoaJuridica)cliente).getCnpj());
                stmt.setString(2, ((PessoaJuridica)cliente).getInscricaoEstadual());
                stmt.setDate(3, java.sql.Date.valueOf(((PessoaJuridica)cliente).getDataNascimento()));
                stmt.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, celular=?, email=?, endereco=?, dataCadastro=? WHERE id=?";
        String sqlPF = "UPDATE pessoa_fisica SET cpf=?, dataNascimento=? WHERE id_cliente = ?";
        String sqlPJ = "UPDATE pessoa_juridica SET cnpj=?, inscricaoEstadual=?, dataNascimento=? WHERE id_cliente = ?";  
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCelular());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());
            stmt.setDate(5, java.sql.Date.valueOf(cliente.getDataCadastro()));
            stmt.setInt(6, cliente.getId());
            stmt.execute();
            if (cliente instanceof PessoaFisica) {
                stmt = connection.prepareStatement(sqlPF);
                stmt.setString(1, ((PessoaFisica)cliente).getCpf());
                stmt.setDate(2, java.sql.Date.valueOf(((PessoaFisica)cliente).getDataNascimento()));
                stmt.setInt(3, cliente.getId());
                stmt.execute();
            } else {
                stmt = connection.prepareStatement(sqlPJ);
                stmt.setString(1, ((PessoaJuridica)cliente).getCnpj());
                stmt.setString(2, ((PessoaJuridica)cliente).getInscricaoEstadual());
                stmt.setDate(3, java.sql.Date.valueOf(((PessoaJuridica)cliente).getDataNascimento()));
                stmt.setInt(4, cliente.getId());
                stmt.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listar() {
        String sql = "SELECT * FROM cliente c "
                        + "LEFT JOIN pessoa_fisica pf on pf.id_cliente = c.id "
                        + "LEFT JOIN pessoa_juridica pj on pj.id_cliente = c.id;";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = populateVO(resultado);
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM cliente c "
                        + "LEFT JOIN pessoa_fisica pf on pf.id_cliente = c.id "
                        + "LEFT JOIN pessoa_juridica pj on pj.id_cliente = c.id WHERE id=?";
        Cliente retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Cliente populateVO(ResultSet rs) throws SQLException {
        Cliente cliente;      
        if (rs.getString("cnpj") == null || rs.getString("cnpj").length() <= 0) 
        {
            cliente =  new PessoaFisica();
            ((PessoaFisica)cliente).setCpf(rs.getString("cpf"));
            ((PessoaFisica)cliente).setDataNascimento(rs.getDate("pf.dataNascimento").toLocalDate());
        }
        else
        {
           cliente =  new PessoaJuridica();
           ((PessoaJuridica)cliente).setCnpj(rs.getString("cnpj"));
           ((PessoaJuridica)cliente).setInscricaoEstadual(rs.getString("inscricaoEstadual"));
           ((PessoaJuridica)cliente).setDataNascimento(rs.getDate("pj.dataNascimento").toLocalDate());
        }
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCelular(rs.getString("celular"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());
        return cliente;
    }
}
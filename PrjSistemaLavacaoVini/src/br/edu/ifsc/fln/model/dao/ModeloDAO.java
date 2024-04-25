package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.ECategoria;
import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModeloDAO{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int inserir(Modelo modelo) {
        String sql = "INSERT INTO modelo(descricao, id_marca, categoria) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().name());
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                int idAutoIncrementado = generatedKeys.getInt(1);
                return idAutoIncrementado;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        return 0;
    }

    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelo SET descricao=?, id_marca=?, categoria=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().name());
            stmt.setInt(4, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Modelo> listar() {
        String sql =  "SELECT mo.id as modelo_id, mo.descricao as modelo_descricao, mo.categoria as modelo_categoria, "
                + "ma.id as marca_id, ma.nome as marca_nome, "
                + "mt.potencia as motor_potencia, mt.tipoCombustivel as motor_tipoCombustivel "
                + "FROM modelo mo "
                + "INNER JOIN marca ma ON ma.id = mo.id_marca "
                + "INNER JOIN motor mt ON mo.id = mt.id_modelo;";
        
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Modelo> listarPorMarca(Marca marca) {
        String sql =  "SELECT mo.id as modelo_id, mo.descricao as modelo_descricao, mo.categoria as modelo_categoria, "
                + "ma.id as marca_id, ma.nome as marca_nome "
                + "FROM modelo mo INNER JOIN marca ma ON ma.id = mo.id_marca WHERE ma.id = ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Modelo buscar(Modelo modelo) {
        String sql =  "SELECT mo.id as modelo_id, mo.descricao as modelo_descricao, mo.categoria as modelo_categoria, "
                + "ma.id as marca_id, ma.nome as marca_nome "
                + "FROM modelo mo INNER JOIN marca ma ON ma.id = mo.id_marca WHERE mo.id = ?;";
        Modelo retorno = new Modelo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Modelo populateVO(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        Motor motor = new Motor();
        
        modelo.setMarca(marca);
        modelo.setMotor(motor);
        
        modelo.setId(rs.getInt("modelo_id"));
        modelo.setDescricao(rs.getString("modelo_descricao"));
        modelo.getMarca().setId(rs.getInt("marca_id"));
        modelo.getMarca().setNome(rs.getString("marca_nome"));
        modelo.setCategoria(ECategoria.valueOf(rs.getString("modelo_categoria")));
        motor.setCombustivel(ETipoCombustivel.valueOf(rs.getString("motor_tipoCombustivel")));
        motor.setPotencia(rs.getDouble("motor_potencia"));
        return modelo;
    }    
}

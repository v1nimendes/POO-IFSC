package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotorDAO {

    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(int modeloId, Motor motor) {
        String sql = "INSERT INTO motor(id_modelo, potencia, tipoCombustivel) VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modeloId);
            stmt.setDouble(2, motor.getPotencia());
            stmt.setString(3, motor.getCombustivel().name());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

   public boolean alterar(Motor motor, Modelo modelo) {
        String sql = "UPDATE motor SET potencia=?, tipoCombustivel=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, motor.getPotencia());
            stmt.setString(2, motor.getCombustivel().name());
            stmt.setInt(3, modelo.getId());
            stmt.setString(4, modelo.getMotor().getCombustivel().name());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }



    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM motor WHERE id_modelo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Motor> listar() {
        String sql = "SELECT * FROM motor";
        List<Motor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = new Modelo();
                Motor motor = new Motor();
                
                modelo.setId(resultado.getInt("id_modelo"));
                motor.setPotencia(resultado.getDouble("potencia"));

                String combustivelString = resultado.getString("combustivel");
                ETipoCombustivel combustivel = ETipoCombustivel.valueOf(combustivelString);
                motor.setCombustivel(combustivel);
                
                retorno.add(motor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Motor buscar(Motor motor, int modeloId) {
        String sql = "SELECT * FROM motor WHERE id_modelo=?";
        Motor retorno = new Motor();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modeloId);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                motor.setPotencia(resultado.getDouble("motor_potencia"));
                retorno = motor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}

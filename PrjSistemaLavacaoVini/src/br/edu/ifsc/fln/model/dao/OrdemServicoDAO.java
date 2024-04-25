package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.EStatus;
import br.edu.ifsc.fln.model.domain.ItemOS;
import br.edu.ifsc.fln.model.domain.OrdemServico;
import br.edu.ifsc.fln.model.domain.Servico;
import br.edu.ifsc.fln.model.domain.Veiculo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class OrdemServicoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico(total, agenda, desconto, id_veiculo, status) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            stmt.setBigDecimal(1, ordemServico.getTotal());
            stmt.setDate(2, Date.valueOf(ordemServico.getAgenda()));
            stmt.setDouble(3, ordemServico.getDesconto());
            stmt.setInt(4, ordemServico.getVeiculo().getId());
            if  (ordemServico.getStatus() != null) {
                stmt.setString(5, ordemServico.getStatus().name());
            } else {
                //TODO apresentar situação clara de inconsistência de dados
                //tratamento de exceções e a necessidade de uso de commit e rollback
                //stmt.setString(6, "teste");
                stmt.setString(5, EStatus.ABERTA.name());
            }
            stmt.execute();
            ItemOS_DAO itemOS_DAO = new ItemOS_DAO();
            itemOS_DAO.setConnection(connection);
            
            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.setConnection(connection);
            
            for (ItemOS itemOS: ordemServico.getItensOS()) {
                Servico servico = itemOS.getServico();
                itemOS.setOrdemServico(this.buscarUltimaOrdemServico());
                itemOS_DAO.inserir(itemOS);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            
        }
    }

    public boolean alterar(OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET total=?, agenda=?, desconto=?, id_veiculo=?, status=? WHERE numero=?";
        try {
            //antes de atualizar a nova ordem de serviço, a anterior terá seus itens de ordem de serviço removidos
            connection.setAutoCommit(false);
            ItemOS_DAO itemOS_DAO = new ItemOS_DAO();
            itemOS_DAO.setConnection(connection);
            
            ServicoDAO servicoDAO = new ServicoDAO();
            servicoDAO.setConnection(connection);
            
            OrdemServico ordemServicoAnterior = buscar(ordemServico);
            List<ItemOS> itensOS = itemOS_DAO.listarPorOrdemServico(ordemServicoAnterior);

            //atualiza os dados da venda
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBigDecimal(1, ordemServico.getTotal());
            stmt.setDate(2, Date.valueOf(ordemServico.getAgenda()));
            stmt.setDouble(3, ordemServico.getDesconto());
            stmt.setInt(4, ordemServico.getVeiculo().getId());
            if  (ordemServico.getStatus() != null) {
                stmt.setString(5, ordemServico.getStatus().name());
            } else {
                stmt.setString(5, EStatus.ABERTA.name());
            }
            stmt.setInt(6, ordemServico.getNumero());
            stmt.execute();
            connection.commit();
            return true;
        } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException exc1) {
                    Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, exc1);
                }
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(OrdemServico ordemServico) {
        String sql = "DELETE FROM ordem_servico WHERE numero=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            try {
                connection.setAutoCommit(false);
                ItemOS_DAO itemOS_DAO = new ItemOS_DAO();
                itemOS_DAO.setConnection(connection);
                
                ServicoDAO servicoDAO = new ServicoDAO();
                servicoDAO.setConnection(connection);
                
                for (ItemOS itemOS : ordemServico.getItensOS()) {
                    itemOS_DAO.remover(itemOS);
                }
                stmt.setInt(1, ordemServico.getNumero());
                stmt.execute();
                connection.commit();
            } catch (SQLException exc) {
                try {
                    connection.rollback();
                } catch (SQLException exc1) {
                    Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, exc1);
                }
                Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, exc);
            }            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<OrdemServico> listar() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                OrdemServico ordemServico = new OrdemServico();
                Veiculo veiculo = new Veiculo();
                List<ItemOS> itensOS = new ArrayList();

                ordemServico.setNumero(resultado.getInt("numero"));
                //ordemServico.setTotal(resultado.getBigDecimal("total"));
                ordemServico.setAgenda(resultado.getDate("agenda").toLocalDate());
                ordemServico.setDesconto(resultado.getDouble("desconto"));
                veiculo.setId(resultado.getInt("id_veiculo"));
                ordemServico.setStatus(Enum.valueOf(EStatus.class, resultado.getString("status")));
               
                //Obtendo os dados completos do Veículo associado à Ordem de Serviço
                VeiculoDAO veiculoDAO = new VeiculoDAO();
                veiculoDAO.setConnection(connection);
                veiculo = veiculoDAO.buscar(veiculo);

                //Obtendo os dados completos dos Itens de Venda associados à Venda
                ItemOS_DAO itemOS_DAO = new ItemOS_DAO();
                itemOS_DAO.setConnection(connection);
                itensOS = itemOS_DAO.listarPorOrdemServico(ordemServico);

                ordemServico.setVeiculo(veiculo);
                ordemServico.setItensOS(itensOS);
                retorno.add(ordemServico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public OrdemServico buscar(OrdemServico ordemServico) {
        String sql = "SELECT * FROM ordem_servico WHERE numero=?";
        OrdemServico ordemServicoRetorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, (int) ordemServico.getNumero());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Veiculo veiculo = new Veiculo();
                ordemServicoRetorno.setNumero(resultado.getInt("numero"));
                //ordemServicoRetorno.setTotal(resultado.getBigDecimal("total"));
                ordemServicoRetorno.setAgenda(resultado.getDate("agenda").toLocalDate());
                ordemServicoRetorno.setDesconto(resultado.getDouble("desconto"));
                veiculo.setId(resultado.getInt("id_veiculo"));
                ordemServicoRetorno.setStatus(Enum.valueOf(EStatus.class, resultado.getString("status")));
                ordemServicoRetorno.setVeiculo(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordemServicoRetorno;
    }
    
    public OrdemServico buscar(int id) {
        /*
            Método necessário para evitar que a instância de retorno seja 
            igual a instância a ser atualizada.
        */
        String sql = "SELECT * FROM ordem_servico WHERE numero=?";
        OrdemServico ordemServicoRetorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Veiculo veiculo = new Veiculo();
                ordemServicoRetorno.setNumero(resultado.getInt("numero"));
                //ordemServicoRetorno.setTotal(resultado.getBigDecimal("total"));
                ordemServicoRetorno.setAgenda(resultado.getDate("agenda").toLocalDate());
                ordemServicoRetorno.setDesconto(resultado.getDouble("desconto"));
                veiculo.setId(resultado.getInt("id_veiculo"));
                ordemServicoRetorno.setStatus(Enum.valueOf(EStatus.class, resultado.getString("status")));
                ordemServicoRetorno.setVeiculo(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordemServicoRetorno;
    }    
    public OrdemServico buscarUltimaOrdemServico() {
        String sql = "SELECT max(numero) as max FROM ordem_servico";
        
        OrdemServico retorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setNumero(resultado.getInt("max"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
//
//    public Map<Integer, ArrayList> listarQuantidadeVendasPorMes() {
//        String sql = "select count(cdVenda) as count, extract(year from data) as ano, extract(month from data) as mes from vendas group by ano, mes order by ano, mes";
//        Map<Integer, ArrayList> retorno = new HashMap();
//        
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            ResultSet resultado = stmt.executeQuery();
//
//            while (resultado.next()) {
//                ArrayList linha = new ArrayList();
//                if (!retorno.containsKey(resultado.getInt("ano")))
//                {
//                    linha.add(resultado.getInt("mes"));
//                    linha.add(resultado.getInt("count"));
//                    retorno.put(resultado.getInt("ano"), linha);
//                }else{
//                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
//                    linhaNova.add(resultado.getInt("mes"));
//                    linhaNova.add(resultado.getInt("count"));
//                }
//            }
//            if (retorno.size() > 0) {
//                retorno = ordenar(retorno);
//            }
//            return retorno;
//        } catch (SQLException ex) {
//            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            
//        }
//        return retorno;
//    }
//    
//    private Map<Integer, ArrayList> ordenar(Map<Integer, ArrayList> vendas) {
//        LinkedHashMap<Integer, ArrayList> orderedMap = vendas.entrySet() 
//            .stream() 
//            .sorted(Map.Entry.comparingByKey()) 
//                .collect(Collectors.toMap(Map.Entry::getKey, 
//                    Map.Entry::getValue, //
//                    (key, content) -> content, //
//                    LinkedHashMap::new));
//        return orderedMap;
//    }

}

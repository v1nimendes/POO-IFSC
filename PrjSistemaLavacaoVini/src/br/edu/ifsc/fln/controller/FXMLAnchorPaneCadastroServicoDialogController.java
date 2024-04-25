/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cor;
import br.edu.ifsc.fln.model.domain.ECategoria;
import br.edu.ifsc.fln.model.domain.Servico;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroServicoDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<ECategoria> cbCategoria;

    @FXML
    private Spinner<Integer> spnPontos;

    @FXML
    private Spinner<Double> spnValor;

    @FXML
    private TextField tfDescricao;
     
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Servico servico;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         servicoDAO.setConnection(connection);
                
         SpinnerValueFactory<Integer> spnFactoryInteger = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99999);
         
         spnFactoryInteger.setValue(0);
         
         SpinnerValueFactory<Double> spnFactoryDouble = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00,99999.00);
        
         spnFactoryDouble.setValue(0.00);
         
         spnValor.setValueFactory(spnFactoryDouble);
         spnPontos.setValueFactory(spnFactoryInteger);
         carregarComboBoxCategorias();
    }       

    public void carregarComboBoxCategorias() {
        cbCategoria.setItems(FXCollections.observableArrayList(ECategoria.values()));  
    }
    
    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }

    public void setBtConfirmarClicked(boolean btConfirmarClicked) {
        this.btConfirmarClicked = btConfirmarClicked;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        this.tfDescricao.setText(servico.getDescricao());
        
        SpinnerValueFactory<Double> spnFactoryDouble = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00,99999.00);
        spnFactoryDouble.setValue(servico.getValor());
        spnValor.setValueFactory(spnFactoryDouble);
        
        SpinnerValueFactory<Integer> spnFactoryInteger = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,99999);
        spnFactoryInteger.setValue(servico.getPontos());
        spnPontos.setValueFactory(spnFactoryInteger);
        
        cbCategoria.getSelectionModel().select(servico.getCategoria());
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            servico.setDescricao(tfDescricao.getText());
            servico.setValor(spnValor.getValue());
            servico.setPontos(spnPontos.getValue());
            servico.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (this.tfDescricao.getText() == null || this.tfDescricao.getText().length() == 0) {
            errorMessage += "Descrição inválida!\n";
        }
        
        if(spnValor.getValue() == 0.0)
        {
            errorMessage += "Valor do serviço inválido!\n";
        }
        
        if(spnPontos.getValue() == 0)
        {
            errorMessage += "Número de pontos do serviço inválido!\n";
        }
        
        if (cbCategoria.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma categoria!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}

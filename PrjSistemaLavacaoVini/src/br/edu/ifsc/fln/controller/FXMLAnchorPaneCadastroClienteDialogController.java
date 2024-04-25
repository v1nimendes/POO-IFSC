/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mpisc
 */
public class FXMLAnchorPaneCadastroClienteDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private DatePicker dpDataCadastro;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private Group gbTipo;

    @FXML
    private RadioButton rbPessoaFisica;

    @FXML
    private RadioButton rbPessoaJuridica;

    @FXML
    private TextField tfCPF_CNPJ;

    @FXML
    private TextField tfCelular;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfIncricaoEstadual;

    @FXML
    private TextField tfNome;

    @FXML
    private ToggleGroup tgTipo;

    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Cliente cliente;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        
        this.tfNome.setText(this.cliente.getNome());
        this.tfCelular.setText(this.cliente.getCelular());
        this.tfEmail.setText(this.cliente.getEmail());
        this.tfEndereco.setText(this.cliente.getEndereco());
        this.dpDataCadastro.setValue(this.cliente.getDataCadastro());
        this.gbTipo.setDisable(true);
        if (cliente instanceof PessoaFisica) {
            this.dpDataNascimento.setValue(((PessoaFisica) this.cliente).getDataNascimento());
            rbPessoaFisica.setSelected(true);
            tfCPF_CNPJ.setText(((PessoaFisica) this.cliente).getCpf());
            tfIncricaoEstadual.setText("");
            tfIncricaoEstadual.setDisable(true);
        } else {
            this.dpDataNascimento.setValue(((PessoaJuridica) this.cliente).getDataNascimento());
            rbPessoaJuridica.setSelected(true);
            tfCPF_CNPJ.setText(((PessoaJuridica) this.cliente).getCnpj());
            tfIncricaoEstadual.setText(((PessoaJuridica) this.cliente).getInscricaoEstadual());
            tfIncricaoEstadual.setDisable(false);
        }
        this.tfNome.requestFocus();
    }
    

    @FXML
    public void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            //condição para verificar se trata da atualização ou de um novo cliente. 
            //Sendo novo, verifica qual é o tipo de cliente para fazer a instanciação apropriada. 
            if (this.cliente == null) {
                if (rbPessoaFisica.isSelected()) {
                    this.cliente = new PessoaFisica();
                } else {
                    this.cliente = new PessoaJuridica();
                }
            }
            cliente.setNome(tfNome.getText());
            cliente.setCelular(tfCelular.getText());
            cliente.setEmail(tfEmail.getText());
            cliente.setEndereco(tfEndereco.getText());
            cliente.setDataCadastro(dpDataCadastro.getValue());
            if (rbPessoaFisica.isSelected()) {
                ((PessoaFisica) cliente).setCpf(tfCPF_CNPJ.getText());
                ((PessoaFisica) cliente).setDataNascimento(dpDataNascimento.getValue());
            } else {
                ((PessoaJuridica) cliente).setCnpj(tfCPF_CNPJ.getText());
                ((PessoaJuridica) cliente).setDataNascimento(dpDataNascimento.getValue());
                ((PessoaJuridica) cliente).setInscricaoEstadual(tfIncricaoEstadual.getText());
            }
            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleBtCancelar() {
        dialogStage.close();
    }
    
     @FXML
    public void handleRbPessoaFisica() {
        this.tfIncricaoEstadual.setText("");
        this.tfIncricaoEstadual.setDisable(true);
    }

    @FXML
    public void handleRbPessoaJuridica() {
        this.tfIncricaoEstadual.setText("");
        this.tfIncricaoEstadual.setDisable(false);
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() 
    {
        String errorMessage = "";
        if (this.tfNome.getText() == null || this.tfNome.getText().length() == 0) 
        {
            errorMessage += "Nome Inválido.\n";
        }
        
                
        if (this.tfCelular.getText() == null || this.tfCelular.getText().length() == 0) 
        {
            errorMessage += "Celular Inválido.\n";
        }
        
        if (this.tfEmail.getText() == null || this.tfEmail.getText().length() == 0) 
        {
            errorMessage += "Email Inválido.\n";
        }
        
        
        if (this.dpDataCadastro.getValue() == null || this.dpDataCadastro.getValue().toString().length() == 0) 
        {
            errorMessage += "Data de Cadastro Inválida.\n";
        }
        
        if (this.dpDataNascimento.getValue() == null || this.dpDataNascimento.getValue().toString().length() == 0)
        {
            errorMessage += "Data de Nascimento Inválida.\n";
        }
        
        if (rbPessoaFisica.isSelected()) 
        {
            if (this.tfCPF_CNPJ.getText() == null || this.tfCPF_CNPJ.getText().length() == 0)
            {
                errorMessage += "CPF Inválido.\n";
            }
        } 
        else 
        {
            if (this.tfCPF_CNPJ.getText() == null || this.tfCPF_CNPJ.getText().length() == 0) {
                errorMessage += "CNPJ inválido.\n";
            }
            if (this.tfIncricaoEstadual.getText() == null || this.tfIncricaoEstadual.getText().length() == 0) {
                errorMessage += "Informe a Incrição Estadual.\n";
            }
        }
        
        if (errorMessage.length() == 0) 
        {
            return true;
        } 
        else 
        {
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

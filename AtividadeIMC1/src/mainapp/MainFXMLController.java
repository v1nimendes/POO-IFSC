/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class MainFXMLController implements Initializable {

    @FXML
    private ChoiceBox<String> cbSexo;
    
    private String[] sexo = {"Masculino","Feminino"};
    
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfPeso;
    @FXML
    private Spinner<Integer> spIdade;
    @FXML
    private Button btnCalcular;
    @FXML
    private Button btnNovo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        spIdade.setValueFactory(valueFactory);
        spIdade.setEditable(true);
       
        cbSexo.getItems().addAll("Masculino", "Feminino");
      
    }
    
    public void getSexo(ActionEvent event){
        String mySexo = cbSexo.getValue();
    }

    @FXML
    private void handleBtnCalcular(ActionEvent event) {
        String nome = tfNome.getText();
        String altura = tfAltura.getText();
        String peso = tfPeso.getText();
        String mySexo = cbSexo.getValue();
        Integer idade = spIdade.getValue();
        
        
        float nAltura = Float.parseFloat(altura);
        float nPeso = Float.parseFloat(peso);
      
        float IMC = nPeso / (nAltura*nAltura);
        String classificacao = " ";
        
        if(IMC < 18.5) {
            classificacao = "Magreza";
        } else if (IMC < 24.9) {
            classificacao = "Normal";
        } else if (IMC < 29.9) {
            classificacao = "Sobrepeso";
        } else if (IMC < 39.0) {
            classificacao = "Obesidade";
        } else {
            classificacao = "Obesidade Grave";
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Calculo IMC\n"
                + "Nome:  " + nome + "\n"
                + "Idade: " + idade + "\n"
                + "Sexo: " + mySexo + "\n" 
                + "Altura:" + nAltura + "\n"
                + "Peso: " + nPeso + "\n" + "\n"
                + "IMC: " + IMC + "\n" 
                + "Classicação: " + classificacao);
        alert.show();
    }

    @FXML
    private void handleBtnNovo(ActionEvent event) {
        tfNome.setText(null);
         spIdade.getEditor().clear();
        cbSexo.setValue(null);
        tfPeso.setText(null);
        tfAltura.setText(null);
    }
    

}

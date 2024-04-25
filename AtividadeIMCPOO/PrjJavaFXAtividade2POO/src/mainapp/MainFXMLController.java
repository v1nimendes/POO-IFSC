package mainapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author J
 */
public class MainFXMLController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAltura;
    @FXML
    private Spinner<Integer> spIdade;
    @FXML
    private ChoiceBox<String> cbSexo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spIdade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        cbSexo.getItems().addAll("Masculino", "Feminino", "Não informar");
    }

    @FXML
    private void handleBtCalcular(ActionEvent event) {

        //recebe dados
        String nome = tfNome.getText();
        int idade = spIdade.getValue();
        String sexo = cbSexo.getValue();
        float altura = Float.parseFloat(tfAltura.getText());
        float peso = Float.parseFloat(tfPeso.getText());

        Imc imcObj = new Imc(nome, idade, sexo, altura, peso);

        //imprime resultados
        String resultado = "Cálculo de IMC \n\nNome: " + nome + "\nIdade: " + idade + "\nSexo: " + sexo
                + "\nAltura: " + altura + "\nPeso: " + peso + "\n\nIMC: " + imcObj.calcularImc() + "\nClassificação: " + imcObj.classificarImc();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado do IMC");
        alert.setHeaderText(null);
        alert.setContentText(resultado);
        alert.showAndWait();
    }

    @FXML
    private void handleBtLimpar(ActionEvent event) {
        tfNome.setText("");
        spIdade.getValueFactory().setValue(0);
        cbSexo.getSelectionModel().selectLast();
        tfPeso.setText("");
        tfAltura.setText("");
        tfNome.requestFocus();
    }
}

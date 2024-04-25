package mainapp;

/**
 *
 * @author J
 */

public class Imc {
    private float peso;
    private float altura;
    private String nome;
    private String sexo;
    private int idade;
    
    public Imc(String nome, int idade, String sexo, float altura, float peso) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
    }
    
    // Getters e Setters
    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float calcularImc() {
        return peso / (altura * altura);
    }
    
    public String classificarImc() {
        float imc = calcularImc();
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25.0) {
            return "Normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else if (imc < 40.0) {
            return "Obesidade";
        } else {
            return "Obesidade grave";
        }
    }
}

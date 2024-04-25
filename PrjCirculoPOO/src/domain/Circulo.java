/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Aluno
 */
public class Circulo {
    public float raio;
    
    public void definirRaio(float raio) {
        this.raio = raio;
    }
    
    public float obterRaio() {
        return raio;
    }
    
    public float calcularCircunferencia() {
        return 2 * (float)Math.PI * raio;
    }
    
    public float calcularDiametro() {
        return 2 * raio;
    }
   
    public float calcularArea() {
        return (float)(Math.PI * Math.pow(raio, 2));
    }
    
}

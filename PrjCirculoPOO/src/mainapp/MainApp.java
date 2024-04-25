/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mainapp;

import domain.Circulo;

/**
 *
 * @author Aluno
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("POO - Mundo dos Circulos");
        new Circulo(); 
        /*SOBRE A LINHA 20
         objeto criado mas está sem referência. 
         É um objeto que esta perdido na memória
         e não terá acesso ao seu conteúdo
         faz pouco sentido ter ma declaração como esta
        */
        
        Circulo c1 = new Circulo();
        /*SOBRE A LINHA 27
        A variável c1 é uma refeência para um objeto do
        tipo Circulo na memória.
        */
        
        c1.definirRaio(2);
        
        /* raio = 2;//raio é uma variável que pertence a classe 
        Circulo. Para ter acesso a esta variáel é necessário uma variável de referÊncia*/
        
        c1.raio = 2;//atributo raio sendo acessado por meio da variável de referência c1
        
        c1.definirRaio(2);
        
        System.out.println("\nCirculo 1 \nÁrea de circulo: " + c1.calcularArea());
        System.out.println("Diámetro do circulo: " + c1.calcularDiametro());
        System.out.println("Circunferência: " + c1.calcularCircunferencia());
        
        Circulo c2 = new Circulo();
        c2.raio = 3;
        
        c2.definirRaio(3);
        
        System.out.println("\nCirculo 2 \nÁrea de circulo: " + c2.calcularArea());
        System.out.println("Diámetro do circulo: " + c2.calcularDiametro());
        System.out.println("Circunferência: " + c2.calcularCircunferencia());
        
     
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atividade2;

import java.util.Scanner;


/**
 *
 * @author vinic
 */
public class Atividade2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Informe o raio: ");
        float raio = entrada.nextFloat();
        
        System.out.println("Diametro= " + (2 * raio) + "\n" + "Circunferencia = " + (2 * Math.PI * raio)
                + "\n" + "Area = " + Math.pow(raio, 2.0));
      
    }
    
    
    
    
}

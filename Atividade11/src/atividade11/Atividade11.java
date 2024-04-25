/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atividade11;

import java.util.Scanner;

/**
 *
 * @author vinic
 */
public class Atividade11 {

    public static Scanner entrada = new Scanner(System.in);
    
    public static void main(String[] args) {
       int menorCon = 0, maiorCon = 0;
       int totalConCom = 0, totalConInd = 0, totalConRes = 0, totalConsumido = 0;
       int qntConsumidor = 0;
       float mediaGeralConsumida = 0;
       String opcao = "N";
       
       do {
           System.out.print("Preço do KWh consumido: ");
           float precoKWh = Float.parseFloat(entrada.next());
           System.out.print("Numero do consumidor: ");
           int numeroConsumidor = Integer.parseInt(entrada.next());
           System.out.print("Quantidade de KWh consumido: " );
           int quantidadeKWhCon = Integer.parseInt(entrada.next());
           System.out.print("Codigo do tipo de consumidor: ");
           int codTipoCon = Integer.parseInt(entrada.next());
           
           float totalAPagar = precoKWh * quantidadeKWhCon;
           qntConsumidor++;
                   
           if(qntConsumidor == 1 ){
               maiorCon = menorCon = quantidadeKWhCon;   
           } else {
               if (quantidadeKWhCon > maiorCon) {
                   maiorCon = quantidadeKWhCon;
               } else if (quantidadeKWhCon < menorCon) {
                   menorCon = quantidadeKWhCon;
               }
           }
           switch (codTipoCon) {
               case 1 -> totalConRes  += quantidadeKWhCon;
               case 2 -> totalConCom  += quantidadeKWhCon;
               case 3 -> totalConInd  += quantidadeKWhCon;
               default -> totalConRes   += quantidadeKWhCon;
               
           }
           
           totalConsumido += quantidadeKWhCon;
           mediaGeralConsumida = (float)totalConsumido / (float)qntConsumidor;
           
           System.out.println("Total a pagar do consumidor " + numeroConsumidor + " sera de R$ " + totalAPagar + ""
                              + " para um consumo total de "+ quantidadeKWhCon + " KWh");
            System.out.println(" Maior Consumo: " + maiorCon);
            System.out.println("Menor Consumo: " + menorCon);
            System.out.println("Quantidade Consumida pelo tipo 1: " + totalConRes);
            System.out.println("Quantidade Consumida pelo tipo 2: " + totalConCom);
            System.out.println("Quantidade Consumida pelo tipo 3: " + totalConInd);
            System.out.println("Media Geral consumida em KWh: " + mediaGeralConsumida);
            System.out.println("Deseja Continuar(S/N)?");
            opcao = entrada.next();
       }while (opcao.equalsIgnoreCase("s"));
           
           
       
    }
    
}

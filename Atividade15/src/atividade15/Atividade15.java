/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atividade15;

import java.util.Scanner;

/**
 *
 * @author vinic
 */
public class Atividade15 {

    private static int[] assentos = new int[10];
    private static Scanner entrada = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcao = 0;
        do{
            System.out.println("1 - Primeira classe");
            System.out.println("2 - Classe executiva");
            System.out.println("3 - Listar situação dos assentos");
            System.out.println("4 - Inicializar o voo");
            System.out.println("0 - Sair");
            System.out.println("Opcao: ");
            
            opcao = entrada.nextInt();
            
            switch (opcao){
                case 1 -> venderPassagemPC();
                
                case 2 -> venderPassagemCE();
                   
                case 3 -> imprimir();
                    
                case 4 -> inicializarAssentos();
                    
                case 0 -> System.out.println("O sistema de controle de vendas de passagens sera finalizado");
                    
                default -> System.out.println("Opcao Invalida");
            }
        }while (opcao != 0);
    }
        
        private static void venderPassagemCE() {
            int assento = verificarDisponibilidadeCE();
            
            if (assento == -1) {
                System.out.println("A classe executiva está lotada.");
                int assentoDisponivelPC = verificarDisponibilidadePC();
                
                if (assentoDisponivelPC == -1) {
                    System.out.println("Voo está lotado, aguarde o próximo!!!");
                    
                }else {
                    System.out.println("Gostaria de ocupar um assento na primeira classe (S/N)?");
                    char opcao = entrada.next().charAt(0);
                    if (opcao == 's' || opcao == 'S') {
                        assentos[assentoDisponivelPC] = 1;
                        imprimirTicket(assentoDisponivelPC, "Primeira Classe");
                    }
                }
            } else {
                assentos[assento] = 1;
                imprimirTicket(assento, "Classe Executiva");
            }
    }
        private static int verificarDisponibilidadeCE(){
            for (int i = 5; i < 10; i++){
                if (assentos[i] == 0){
                    return i;
                }
            }
            return -1;
        }
        private static void venderPassagemPC() {
            int assento = verificarDisponibilidadePC();
            
            if (assento == -1) {
                System.out.println("A primeira classe esta lotada");
                int assentoDisponivelCE = verificarDisponibilidadeCE();
                if (assentoDisponivelCE == -1) {
                    System.out.println("Voo está lotado, aguarde o proximo!!!");
                    
                }else {
                    System.out.println("Gostaria de ocupar um assento na classe executiva (S/N)?");
                    char opcao = entrada.next().charAt(0);
                    if (opcao == 's' || opcao == 'S') {
                        assentos[assentoDisponivelCE] = 1;
                        imprimirTicket(assentoDisponivelCE, "Classe Executiva");
                    }
                }
            } else {
                assentos[assento] = 1;
                imprimirTicket(assento, "Primeira Classe");
            }
        }
       private static int verificarDisponibilidadePC(){
           for (int i = 0; i < 5; i++){
               if (assentos[i] == 0){
                   return i;
               }
           }
           return -1;
       }
       private static void imprimir() {
           for (int i = 0; i < assentos.length; i++){
               System.out.println("Assento" + (i + 1) + ": " + (assentos[i] == 0 ? "Disponivel" : "Ocupado"));
           }
       }
       
       private static void inicializarAssentos() {
           for (int i = 0; i < assentos.length; i++) {
               assentos[i] = 0;
           }
       }
       
       private static void imprimirTicket (int assento, String classe) {
           System.out.println("Voce reservou o assento " + (assento + 1) + " da " + classe);
       }
    }
   

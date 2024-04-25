 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atividade14;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author vinic
 */
public class Atividade14 {

    private static Scanner entrada = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        DecimalFormat df = new DecimalFormat("0.00");
        double valorParcela = 0.0;
        System.out.print("Valor da Compra: ");
        double valor = entrada.nextDouble();
        int opcao = menu();
        
        switch (opcao) {
            case 1:
                valorParcela = pagamentoAVista(valor);
                System.out.print("Pagamento a vista no valor de R$ " + df.format(valorParcela));
                break;
            
            case 2:
                valorParcela = pagamentoEm2x(valor);
                System.out.print("Pagamento em 2x, 0 valor da parcela é R$" + df.format(valorParcela));
                break;
                
            case 3: 
                int quantidadeParcelas = 0;
                do {
                    System.out.print("Numero de parcelas (entre 3 e 10): ");
                    quantidadeParcelas = entrada.nextInt();
                    
                    if (quantidadeParcelas < 3 || quantidadeParcelas > 10) {
                        System.out.print("Quantidade de Parcelas Invalida");
                    }
                }while (quantidadeParcelas < 3 || quantidadeParcelas > 10);
                
                valorParcela = pagamentoParcelado(valor, quantidadeParcelas);
                System.out.print("O valor da parcela sera de R$ " + df.format(valorParcela));
                break;
                
            default:
                System.out.print("Opcao invalida.");
        }
    }
    private static int menu() {
        System.out.println("Formas de pagamento: ");
        System.out.print("1 - A vista com 10% de desconto\n"
                       + "2 - Em duas vezes (preco da etiqueta)\n"
                       + "3 - De 3 a 10 vezes com 3% de juros ao mes\n"
                       + "Opcao: ");
        int opcao = entrada.nextInt();
        return opcao;
    }
    
    private static double pagamentoAVista(double valor) {
        return valor - (valor * 0.10);
    }
    
    private static double pagamentoEm2x(double valor){
        return valor / 2;
    }
    
    private static double pagamentoParcelado(double valor, int quantidadeParcelas){
        double montante = valor * Math.pow(1 + 0.03, quantidadeParcelas);
        return montante / quantidadeParcelas;
        
    }
    
}

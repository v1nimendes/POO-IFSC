/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package atividade5;

import javax.swing.JOptionPane;

/**
 *
 * @author vinic
 */
public class Atividade5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        byte cod = Byte.parseByte(JOptionPane.showInputDialog("Informe o código do produto"));
             switch (cod) {
            case 1 -> JOptionPane.showMessageDialog(null, "Alimento não perecível");
            case 2, 3, 4 -> JOptionPane.showMessageDialog(null, "Alimento perecível");
            case 5,6 -> JOptionPane.showMessageDialog(null, "Vestuário");
            case 7 -> JOptionPane.showMessageDialog(null, "Higiene pessoal");
            case 8, 9, 10, 11, 12, 13, 14, 15 -> JOptionPane.showMessageDialog(null, "Limpeza e utensílios domésticos.");
            default -> JOptionPane.showMessageDialog(null, "Código inválido."); 
           
        }
    }
    
}

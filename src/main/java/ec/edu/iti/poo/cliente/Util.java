/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.iti.poo.cliente;

public class Util {

    /**
     * Valida cédula ecuatoriana de persona natural (10 dígitos).
     * Reglas clave:
     * - 10 dígitos numéricos.
     * - Provincia: 01..24 (y 30 para exterior, opcional).
     * - Tercer dígito < 6.
     * - Dígito verificador por algoritmo Módulo 10.
     */
    public static boolean validarCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        int tercer = Character.getNumericValue(cedula.charAt(2));
        if (!((provincia >= 1 && provincia <= 24) || provincia == 30)) return false;
        if (tercer >= 6) return false;

        int[] coef = {2,1,2,1,2,1,2,1,2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int dig = Character.getNumericValue(cedula.charAt(i));
            int prod = dig * coef[i];
            if (prod >= 10) prod -= 9;
            suma += prod;
        }
        int verif = (10 - (suma % 10)) % 10;
        int ultimo = Character.getNumericValue(cedula.charAt(9));
        return verif == ultimo;
    }
}

package com.proyecto.irp.Utilitario;

public class CalculaDivisor {

    public int Calcular_divisor(String v_numero, int v_basemax) {
        int v_total, v_resto, k, v_numero_aux, v_digit;
        String v_numero_al = "";
        for (int i = 0; i < v_numero.length(); i++) {
            char c = v_numero.charAt(i);
            if (Character.isDigit(c)) {
                v_numero_al += c;
            } else {
                v_numero_al += (int) c;
            }
        }
        k = 2;
        v_total = 0;
        for (int i = v_numero_al.length() - 1; i >= 0; i--) {
            k = k > v_basemax ? 2 : k;
            v_numero_aux = v_numero_al.charAt(i) - 48;
            v_total += v_numero_aux * k++;
        }
        v_resto = v_total % 11;
        v_digit = v_resto > 1 ? 11 - v_resto : 0;
        return v_digit;
    }

}



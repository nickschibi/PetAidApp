package com.example.bonnie.petaid;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.InputMismatchException;

public class Utils {

    //Validação de Campos

    public static boolean isCampoVazio(String valor){
        //Verifica que se o campo está vazio, ou vazio após ter retirado todos os espaços

        boolean resultado = (TextUtils.isEmpty(valor)|| valor.trim().isEmpty());
        return resultado;
    }

    public static boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    public static boolean isPhone(String phone){
        boolean resultado = (!isCampoVazio(phone) && Patterns.PHONE.matcher(phone).matches());
        return resultado;
    }

    public static boolean isName(String name){
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(name);
        boolean resultado = ms.matches();
        return resultado;
    }

    public static boolean isPass(String senha){
        Pattern ps = Pattern.compile("^(?=.{8})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?._]).{8}$");
        Matcher ms = ps.matcher(senha);
        boolean resultado = ms.matches();
        return resultado;
    }
    public static boolean isTelefone(String numeroTelefone) {
        if(numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
                numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}")){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isCNPJ(String CNPJ){

        CNPJ = CNPJ.replaceAll("[^0-9]", "");

        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }

    }
    public static String imprimeCNPJ(String CNPJ) {
    // máscara do CNPJ: 99.999.999/9999-99
        return(CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
                CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" +
                CNPJ.substring(12, 14));
    }

    public static String covertData(Date date){
        //SimpleDateFormat originalFormato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss zzz");
        SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
        //ParsePosition pos = new ParsePosition(0);
        //Date dataFromString = originalFormato.parse(String.valueOf(date),pos);
        //String dateStringNovoFormato = novoFormato.format(dataFromString);
        String dateStringNovoFormato = novoFormato.format(date);

        return dateStringNovoFormato;
    }



    public static boolean isEstado(String uf){
        String estado = uf;
        String Estados[] = {"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR",
                "RS","SC","SE","SP","TO"};
        int status = 0;
        System.out.println("Entre com seu estado: ");

        for(String x : Estados)
        {
            if(estado .equals(x))
                status = 1;
        }
        if(status == 1)
            return  true;
        else
           return false;
    }


    public static boolean isCPF(String CPF) {
        CPF = CPF.replaceAll("[^0-9]", "");
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}





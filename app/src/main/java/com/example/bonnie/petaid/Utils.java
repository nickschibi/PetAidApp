package com.example.bonnie.petaid;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}



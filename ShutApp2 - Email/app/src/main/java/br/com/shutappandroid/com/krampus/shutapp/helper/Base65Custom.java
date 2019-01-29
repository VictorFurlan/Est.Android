package br.com.shutappandroid.com.krampus.shutapp.helper;

import android.util.Base64;

public class Base65Custom {

    public static String codificarBase64(String texto){

        return  Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r|\\t)", "");
    }

    public static String decodificarBase64(String textoCodificado){
        return new String( Base64.decode(textoCodificado, Base64.DEFAULT) );
    }

}

package br.com.shutappandroid.com.krampus.shutapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "ShutAppPreferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";

    public Preferencias(Context contextoPreferencias) {

        contexto = contextoPreferencias;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificadorUsuario) {

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }
}

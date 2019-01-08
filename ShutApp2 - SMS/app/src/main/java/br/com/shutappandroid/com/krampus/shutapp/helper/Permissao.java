package br.com.shutappandroid.com.krampus.shutapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if(Build.VERSION.SDK_INT >= 23){//verifica se a versão do android é maior q 23
            List<String> listaPermissoes = new ArrayList<String>();


            for( String permissao : permissoes){//percorre as parmissões necessarias setadas na LoginActivity(30-34)
                Boolean validaParmissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;//verifica permissao

                if( !validaParmissao ){//se o usuario não tiver permissao
                    listaPermissoes.add(permissao);//adiciona a permissão que o usuario não tem an lista
                }
            }
            if(listaPermissoes.isEmpty()) return true; //caso a lista de permissoes esteja vazia, não é necessário solicitar permissão

            //convertendo o List listaPermissoes para Array, o metodo requestPermissions solicita um Array
            String[] novasPermissoes = new String[ listaPermissoes.size() ];

            //Solicitando as permissões
            ActivityCompat.requestPermissions(activity, listaPermissoes.toArray(novasPermissoes), requestCode);
        }
        return true;
    }
}

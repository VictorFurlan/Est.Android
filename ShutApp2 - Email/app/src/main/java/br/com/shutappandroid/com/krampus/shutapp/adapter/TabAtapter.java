package br.com.shutappandroid.com.krampus.shutapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.shutappandroid.com.krampus.shutapp.fragment.ContatosFragment;
import br.com.shutappandroid.com.krampus.shutapp.fragment.ConversasFragment;

public class TabAtapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONVERSAS","CONTATOS"};

    public TabAtapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        switch (i){
            case 0:
                fragment = new ConversasFragment();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //retorna o titulo baseado no fragmento atual
        return tituloAbas[ position ];
    }
}

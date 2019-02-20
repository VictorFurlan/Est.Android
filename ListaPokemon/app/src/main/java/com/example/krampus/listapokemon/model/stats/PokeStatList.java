package com.example.krampus.listapokemon.model.stats;

import com.google.gson.annotations.SerializedName;

public class PokeStatList {


    private int baseStat;
    @SerializedName("stat")
    private PokeStat stat;

    public PokeStatList(int baseStat, PokeStat stat) {
        this.baseStat = baseStat;
        this.stat = stat;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public PokeStat getPokeStat() {
        return stat;
    }

    public void setPokeStat(PokeStat pokeStat) {
        this.stat = pokeStat;
    }
}

package com.example.krampus.listapokemon.model;

import com.example.krampus.listapokemon.model.stats.PokeStatList;
import com.example.krampus.listapokemon.model.types.PokeTypeList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon{

    private int id;
    private String name;
    private String url;
    @SerializedName("types")
    private List<PokeTypeList> types;
    @SerializedName("stats")
    private List<PokeStatList> stats;

    public Pokemon(int id, String name, String url, List<PokeTypeList> types, List<PokeStatList> stats) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.types = types;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public int getid() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length -1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<PokeTypeList> getPokeTypes() {
        return types;
    }

    public void setPokeTypes(List<PokeTypeList> pokeTypes) {
        this.types = pokeTypes;
    }

    public List<PokeStatList> getStats() {
        return stats;
    }

    public void setStats(List<PokeStatList> stats) {
        this.stats = stats;
    }

    public String pokeTypesToString() {
        String typePoke = "";
        for (int i = 0; i < types.size(); i++) {
            if (i > 0){
                typePoke += ", ";
            }
            typePoke += types.get(i).getTypes().getName();
        }
        return typePoke;
    }

    public String pokeStatsToString(String stat) {
        String typePoke = "";
        for (int i = 0; i < stats.size(); i++) {
            if (stats.get(i).getPokeStat().getName().equals(stat)){
                typePoke += stats.get(i).getPokeStat().getName() + ": ";
                typePoke += stats.get(i).getBaseStat();
            }
        }
        return typePoke;
    }
}

package com.example.krampus.listapokemon.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pokemon{

    private int id;
    private String name;
    private String url;
    private String type;

    @SerializedName("types")
    private List<PokeType> pokeTypes = new ArrayList<>();

    public Pokemon(int id, String name, String url, String type, ArrayList<PokeType> pokeTypes) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.pokeTypes = pokeTypes;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<PokeType> getPokeTypes() {
        return pokeTypes;
    }

    public void setPokeTypes(List<PokeType> pokeTypes) {
        this.pokeTypes = pokeTypes;
    }

    public String pokeTypesToString() {
        String types = "";
        for (int i = 0; i < pokeTypes.size(); i++) {
            if(i > 0)
                types += ", ";
            types += pokeTypes.get(i).getName();
        }
        return types;
    }
}
package com.example.krampus.listapokemon.model;

public class Pokemon {

    private int idPoke;
    private String name;
    private String url;

    public int getIdPoke() {
        return idPoke;
    }

    public int setIdPoke(int idPoke) {
        String[] urlPoke = url.split("/");
        return Integer.parseInt(urlPoke[urlPoke.length - 1]);
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

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.example.krampus.listapokemon.model;

public class PokeAbiliy {

    int id;
    String name;
    String resource_uri;
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        String[] urlPartes = resource_uri.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

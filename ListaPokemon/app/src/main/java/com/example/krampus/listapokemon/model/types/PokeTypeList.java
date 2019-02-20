package com.example.krampus.listapokemon.model.types;

import com.google.gson.annotations.SerializedName;

public class PokeTypeList {

    private int slot;
    @SerializedName("type")
    private PokeType type;

    public PokeTypeList(int slot, PokeType types) {
        this.slot = slot;
        this.type = types;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public PokeType getTypes() {
        return type;
    }

    public void setTypes(PokeType types) {
        this.type = types;
    }
}

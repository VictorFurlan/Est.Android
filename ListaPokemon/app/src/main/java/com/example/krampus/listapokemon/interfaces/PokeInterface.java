package com.example.krampus.listapokemon.interfaces;

import com.example.krampus.listapokemon.controler.PokeGetLista;
import com.example.krampus.listapokemon.model.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeInterface {

    @GET("pokemon/{id}")
    Call<Pokemon> obterPokemon(@Path("id")int idPokemon);
}

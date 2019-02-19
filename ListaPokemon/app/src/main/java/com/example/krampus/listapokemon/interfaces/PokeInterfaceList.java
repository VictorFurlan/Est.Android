package com.example.krampus.listapokemon.interfaces;

import com.example.krampus.listapokemon.controler.PokeGetLista;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeInterfaceList {

    @GET("pokemon")
    Call<PokeGetLista> obterListaPokemon(@Query("limit")int limit, @Query("offset")int offset);
}

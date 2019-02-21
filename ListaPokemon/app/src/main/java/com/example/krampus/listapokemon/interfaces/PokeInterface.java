package com.example.krampus.listapokemon.interfaces;

import com.example.krampus.listapokemon.model.PokeGet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeInterface {
    @GET("pokemon")
    Call<PokeGet> obterListaPokemon(@Query("limit")int limit, @Query("offset")int offset);
}

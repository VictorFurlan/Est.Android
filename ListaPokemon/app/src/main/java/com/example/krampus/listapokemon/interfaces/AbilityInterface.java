package com.example.krampus.listapokemon.interfaces;

import com.example.krampus.listapokemon.controler.AbilityGet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AbilityInterface {
        @GET("ability")
        Call<AbilityGet> obtenerListaAbilidades(@Query("limit")int limit, @Query("offset")int offset);

}

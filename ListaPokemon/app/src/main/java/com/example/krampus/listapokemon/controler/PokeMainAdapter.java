package com.example.krampus.listapokemon.controler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.krampus.listapokemon.R;
import com.example.krampus.listapokemon.model.Pokemon;
import com.example.krampus.listapokemon.view.DetalhesActivity;

import java.util.ArrayList;
import java.util.Locale;


public class PokeMainAdapter extends RecyclerView.Adapter<PokeMainAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;
    private Pokemon p;

    public PokeMainAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public PokeMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokeMainAdapter.ViewHolder holder, int position) {
        p = dataset.get(position);
        holder.nomeTextView.setText(p.getName());
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getid() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView fotoImageView;
        private TextView nomeTextView;
        private CardView cardItemPokemon;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nomeTextView = (TextView) itemView.findViewById(R.id.nomeTextView);
            cardItemPokemon = (CardView) itemView.findViewById(R.id.item_pokemon);
            cardItemPokemon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String pokemon = nomeTextView.getText().toString();
            Intent i = new Intent(v.getContext(),DetalhesActivity.class);
            Bundle bundle = new Bundle();

            for(Pokemon poke:dataset){

                if(poke.getName().equals(pokemon)){
                    int idPoke = poke.getid();
                    bundle.putInt("NumberPokemon", idPoke);
                }
            }
            i.putExtras(bundle);
            v.getContext().startActivity(i);
        }
    }
}


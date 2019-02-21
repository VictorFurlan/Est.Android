package com.example.krampus.listapokemon.controler;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import com.example.krampus.listapokemon.model.PokeGet;
import com.example.krampus.listapokemon.model.Pokemon;
import com.example.krampus.listapokemon.view.DetalhesActivity;


import java.util.ArrayList;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;
    private Pokemon p;

    public PokeAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public PokeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokeAdapter.ViewHolder holder, int position) {
        p = dataset.get(position);
        holder.tvNome.setText(p.getName());
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
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
        private TextView tvNome;
        private CardView itemPokemon;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            tvNome = (TextView) itemView.findViewById(R.id.tv_nome);
            itemPokemon = (CardView) itemView.findViewById(R.id.layout_item);

            itemPokemon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_item:
                    String pokemon =  p.getName();

                    Intent i = new Intent(v.getContext(),DetalhesActivity.class);
                    v.getContext().startActivity(i);
                    Snackbar.make(v, pokemon, Snackbar.LENGTH_LONG).show();
                    break;
            }

        }
    }
}
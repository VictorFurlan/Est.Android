package com.example.krampus.listapokemon.controler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krampus.listapokemon.R;
import com.example.krampus.listapokemon.model.PokeAbiliy;

import java.util.ArrayList;

public class AbilityAdapter extends RecyclerView.Adapter<AbilityAdapter.ViewHolder>{
    private ArrayList<PokeAbiliy> dataset;
    private Context context;
    private PokeAbiliy abiliy;

    public AbilityAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public AbilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalhes, parent, false);
        return new AbilityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbilityAdapter.ViewHolder holder, int position) {
        abiliy = dataset.get(position);
        holder.tvNameAbility.setText(abiliy.getName());
        holder.tvDescricao.setText(abiliy.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokeAbility(ArrayList<PokeAbiliy> listaPokemon) {
        dataset.addAll(listaPokemon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNameAbility;
        private TextView tvDescricao;

        public ViewHolder(View itemView) {
            super(itemView);

            //tvNameAbility = (TextView) itemView.findViewById(R.id.tv_nameAbility);
           // tvDescricao = (TextView) itemView.findViewById(R.id.tv_decricao);

        }

        @Override
        public void onClick(View v) {

        }
    }
}

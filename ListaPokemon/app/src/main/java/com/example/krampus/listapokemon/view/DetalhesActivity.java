package com.example.krampus.listapokemon.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.krampus.listapokemon.R;
import com.squareup.picasso.Picasso;

public class DetalhesActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int pokemon = bundle.getInt("NumberPokemon");
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +  pokemon + ".png";

        imageView = (ImageView) findViewById(R.id.image_detalhes);

        loadImage(url);
    }

    private void loadImage(String url){
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}

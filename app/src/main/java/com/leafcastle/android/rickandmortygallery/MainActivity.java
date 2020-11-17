package com.leafcastle.android.rickandmortygallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leafcastle.android.rickandmortygallery.model.RickMortyCharacter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recCharacters;
    CharacterCardAdaptor adaptor;
    RecyclerView.LayoutManager layoutMan;

    ArrayList<RickMortyCharacter> characters;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        recCharacters = findViewById(R.id.recCharacters);
        layoutMan = new LinearLayoutManager(this);
        recCharacters.setLayoutManager(layoutMan);
        recCharacters.setHasFixedSize(true);

        adaptor = new CharacterCardAdaptor(characters, this);
        recCharacters.setAdapter(adaptor);

    }

    private void init(){
        characters = new ArrayList<RickMortyCharacter>();
        loadData();
    }

    private void loadData(){
        String base = "https://rickandmortyapi.com/api/character/";
        for(int i=1; i<250; i++){
            sendRequest(base + i);
        }
    }

    private void sendRequest(String url){
        if(queue==null){
            queue = Volley.newRequestQueue(this);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "That did not work!", error);
                    }
                });

        queue.add(stringRequest);
    }

    private void parseJson(String json){
        Gson gson = new GsonBuilder().create();
        RickMortyCharacter character =  gson.fromJson(json, RickMortyCharacter.class);
        if(character!=null){
            characters.add(character);
            adaptor.notifyDataSetChanged();
        }
    }
}

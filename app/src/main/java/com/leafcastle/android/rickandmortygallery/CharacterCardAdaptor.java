package com.leafcastle.android.rickandmortygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leafcastle.android.rickandmortygallery.model.RickMortyCharacter;

import java.util.ArrayList;

public class CharacterCardAdaptor extends RecyclerView.Adapter<CharacterCardAdaptor.ViewHolder> {

    private ArrayList<RickMortyCharacter> characters;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtCharacterName;
        public TextView txtCharacterHome;
        public TextView txtCharacterRating;
        //public NetworkImageView imgCharacter;
        public ImageView imgCharacter;
        public CardView crdCard;

        //constructor gets reference to the view
        public ViewHolder(View v) {
            super(v);
            crdCard = v.findViewById(R.id.crdCard);
            imgCharacter = v.findViewById(R.id.imgCharacter);
            txtCharacterName = v.findViewById(R.id.txtCharacterName);
            txtCharacterHome = v.findViewById(R.id.txtCharacterHome);
            txtCharacterRating  = v.findViewById(R.id.txtCharacterRating);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                RickMortyCharacter characterClicked = characters.get(position);
                Toast.makeText(view.getContext(), "You clicked " + characterClicked.getName(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                RickMortyCharacter characterClicked = characters.get(position);
                Toast.makeText(view.getContext(), "You long clicked " + characterClicked.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }

    public CharacterCardAdaptor(ArrayList<RickMortyCharacter> characterList, Context c) {
        characters = characterList;
    }

    @Override
    public CharacterCardAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlist_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(holder.imgCharacter.getContext()).load(characters.get(position).getImage()).into(holder.imgCharacter);
        holder.txtCharacterName.setText(characters.get(position).getName());
        holder.txtCharacterHome.setText(characters.get(position).getLocation().getName());
        holder.txtCharacterRating.setText("0.0");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            holder.crdCard.setElevation(10);
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}
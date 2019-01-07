package com.memes.guilherme.sonsdesenhos.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.memes.guilherme.sonsdesenhos.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SonsAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> nomesAudios;

    public SonsAdapter (@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.nomesAudios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        //verifica se a view está criada
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montar a view a partir do xml
            view = inflater.inflate(R.layout.layout_sons, parent, false);
        }

        //verifica se há postagens
        if(nomesAudios.size() > 0)
        {
            ImageView imagemPostagem = (ImageView) view.findViewById(R.id.listview_image_audio);
            TextView txtNomeDesenho = (TextView)view.findViewById(R.id.listview_item_title_sons);
            ParseObject parseObject = nomesAudios.get(position);
            //parseObject.getParseFile("imagem");
            txtNomeDesenho.setText(parseObject.get("nome_audio").toString());
            Picasso.get().load(parseObject.getParseFile("imagem").getUrl()).fit().into(imagemPostagem);

        }

        return view;
    }



}

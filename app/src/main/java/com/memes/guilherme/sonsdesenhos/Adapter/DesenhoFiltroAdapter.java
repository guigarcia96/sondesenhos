package com.memes.guilherme.sonsdesenhos.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.memes.guilherme.sonsdesenhos.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DesenhoFiltroAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> nomesSons;





    public DesenhoFiltroAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.nomesSons = objects;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //montar a view a partir do xml
            view = inflater.inflate(R.layout.layout_desenho_filtro, parent, false);

        }
        if (nomesSons.size() > 1)
        {
            ImageView imagemPostagem = (ImageView) view.findViewById(R.id.listview_image1);
            TextView txtNomeDesenho = (TextView)view.findViewById(R.id.listview_item_title1);
            ParseObject parseObject = nomesSons.get(position);
            //parseObject.getParseFile("imagem");
            String nome = parseObject.get("nomeDesenho").toString();
            txtNomeDesenho.setText(nome);
            Picasso.get().load(parseObject.getParseFile("imagem").getUrl()).fit().into(imagemPostagem);

        }



        return view;
    }



}




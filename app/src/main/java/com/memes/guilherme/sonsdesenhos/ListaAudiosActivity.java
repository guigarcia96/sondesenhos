package com.memes.guilherme.sonsdesenhos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.memes.guilherme.sonsdesenhos.Adapter.DesenhosAdapter;
import com.memes.guilherme.sonsdesenhos.Adapter.SonsAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaAudiosActivity extends AppCompatActivity {

    private ListView lvlListaAudio;
    private TextView txtTitulo;
    private String titulo1;
    private ArrayList<ParseObject> nomesAudios;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_audios);


        lvlListaAudio = (ListView) findViewById(R.id.lvlListaAudios);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);


        Intent intent = getIntent();
        titulo1 = intent.getStringExtra("titulo");

        txtTitulo.setText(titulo1);

        nomesAudios = new ArrayList<>();
        adapter = new SonsAdapter(getApplicationContext(), nomesAudios);
        lvlListaAudio.setAdapter(adapter);

        getListaAudios();

        lvlListaAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getApplicationContext(), PlayerActivity.class);
                ParseObject titulo = nomesAudios.get(i);
                String nome= titulo.get("nome_audio").toString();
                intent1.putExtra("nomeAudio", nome );
                intent1.putExtra("tituloDesenho", titulo1);
                startActivity(intent1);
            }
        });






    }

    private void getListaAudios() {

        query = ParseQuery.getQuery("Sons");
        query.whereEqualTo("nome_desenho", titulo1);


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        nomesAudios.clear();
                        for (ParseObject parseObject : objects) {
                            nomesAudios.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();


                    }
                } else {
                    e.printStackTrace();
                }
            }
        });


    }
}

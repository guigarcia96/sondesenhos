package com.memes.guilherme.sonsdesenhos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.memes.guilherme.sonsdesenhos.Adapter.DesenhoFiltroAdapter;
import com.memes.guilherme.sonsdesenhos.Adapter.DesenhosAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvlDesenhos;
    private ArrayList<ParseObject> nomesSons;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> nomesSons1;
    private ArrayAdapter<ParseObject> adapter1;
    private ParseQuery<ParseObject> query;
    private Toolbar toolbar;
    private ParseQuery<ParseObject> query1;
    String teste, saida1, saida2, saida3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Codigo de configuração do App
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("zUq8BEMFKxt0SrjqGwBYQ2KscnBQZaCmtLE9yhbn")
                .clientKey("lpyRyRZvDZqtSdUv1E3zW4cjUQJBV2qRQ1Oe9oj3")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        nomesSons = new ArrayList<>();
        lvlDesenhos = (ListView) findViewById(R.id.lvlDesenhos);
        adapter = new DesenhosAdapter(MainActivity.this, nomesSons);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
       // toolbar.setTitleTextColor(android.R.color.white);
        toolbar.setTitle("SD Sons");
        setSupportActionBar(toolbar);


        getNomeDesenho();
        lvlDesenhos.setAdapter(adapter);




        lvlDesenhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListaAudiosActivity.class);
                ParseObject titulo = nomesSons.get(i);
                String nome= titulo.get("nomeDesenho").toString();
                intent.putExtra("titulo", nome );
                startActivity(intent);
            }
        });






    }

    private void getNomeDesenho(){


        query = ParseQuery.getQuery("Desenhos");
      //  query.whereEqualTo("nome_desenho", ParseUser.getCurrentUser().getUsername())

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        nomesSons.clear();
                        for (ParseObject parseObject : objects) {
                            nomesSons.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();


                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getApplicationContext());
        nomesSons1 = new ArrayList<>();

      sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String s) {


              return false;
          }

          @Override
          public boolean onQueryTextChange(String s) {

              if(s.length() >= 1) {
                //a String de Entrada
                teste = s ;
//pega a posição da primeira letra da String
                 saida1 = s.substring(0, 1).toUpperCase();
//pega a posição das outras letras após a primeira
                 saida2 = teste.substring(1);

                  saida3 = saida1.concat(saida2);

            }

            else {
                 saida3 = s.toUpperCase();
              }



              query1 = ParseQuery.getQuery("Desenhos");
              query1.whereContains("nomeDesenho", saida3);


              query1.findInBackground(new FindCallback<ParseObject>() {
                  @Override
                  public void done(List<ParseObject> objects, ParseException e) {
                      query1.findInBackground(new FindCallback<ParseObject>() {
                          @Override
                          public void done(List<ParseObject> objects, ParseException e) {

                              if (e == null) {
                                  if (objects.size() > 0) {

                                      nomesSons.clear();
                                      for (ParseObject parseObject : objects) {
                                          nomesSons.add(parseObject);
                                      }
                                      adapter.notifyDataSetChanged();




                                  } else {
                                      nomesSons.clear();
                                      adapter.notifyDataSetChanged();

                                  }
                              } else {

                                  e.printStackTrace();
                              }
                          }
                      });
                  }
              });




              return false;
          }
      });
          item.setActionView(sv);
          return super.onCreateOptionsMenu(menu);

    }


}

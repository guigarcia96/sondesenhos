package com.memes.guilherme.sonsdesenhos;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import android.os.Bundle;
import android.util.Log;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;


public class PlayerActivity extends AppCompatActivity{



    String titulo, nomeAudio;
    private ParseQuery<ParseObject> query;

    ImageView imageView;
    Button playBtn;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;
    Handler handler;
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        titulo = intent.getStringExtra("tituloDesenho");
        nomeAudio = intent.getStringExtra("nomeAudio");

        handler = new Handler();

        playSom();




        playBtn = (Button) findViewById(R.id.playBtn);
        imageView = (ImageView) findViewById(R.id.img);


        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );







        // Volume Bar
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );






        }



    public void playSom() {
        query = ParseQuery.getQuery("Sons");
        query.whereEqualTo("nome_audio", nomeAudio);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject parseObject : objects) {
                            Picasso.get().load(parseObject.getParseFile("imagem").getUrl()).fit().into(imageView);
                            if (mp == null) {
                                mp = new MediaPlayer();
                            }
                            String codigo = parseObject.getObjectId();
                            Log.d("codigoP", "mostra ai: " + codigo);
                            query.getInBackground(codigo, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    ParseFile audioFile = object.getParseFile("audio"); // audio is the column of the file audio
                                    String audioFileURL = audioFile.getUrl();
                                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mp.setDataSource(audioFileURL);
                                        mp.setVolume(0.5f, 0.5f);
                                        mp.prepare();
                                        positionBar.setMax(mp.getDuration());


                                    } catch (IllegalArgumentException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    } catch (SecurityException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    } catch (IllegalStateException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    } catch (IOException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }
                                }
                            });
                        }


                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void barra() {

        positionBar.setProgress(mp.getCurrentPosition());

        if(mp.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    barra();
                }
            };
            handler.postDelayed(runnable, 1000);
        }

    }




    public void playBtnClick(View view) {

        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            barra();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.pause();
    }


    }







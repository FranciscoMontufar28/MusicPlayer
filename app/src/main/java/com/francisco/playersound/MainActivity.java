package com.francisco.playersound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void PlayMusic(View view){
        mediaPlayer.start();
    }

    public void PauseMusic(View view){
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.besos);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //definicion del servio a usar

        int MaxVolumen = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //captura del valor
        int CurVolumen = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //valor current

        SeekBar volumControl = findViewById(R.id.seekBar_Music);
        final SeekBar MusicProgress = findViewById(R.id.seekBar_MusicPlayer);

        MusicProgress.setMax(mediaPlayer.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MusicProgress.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 100);

        volumControl.setMax(MaxVolumen); // set valor maximo
        volumControl.setProgress(CurVolumen); //set current valor de volumen

        MusicProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("progress value", ""+i);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0); //agregar valores a la barra y actualizarla
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

package com.example.javabtvn_4;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlbum;
    ImageButton btnBack, btnPlay, btnPause, btnNext;
    Spinner spinnerSongs;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchRepeat;
    ProgressBar progressLoading;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    int[] songRes = { R.raw.honlecuaem, R.raw.benayemcoairoi, R.raw.tasinh };
    int[] imgRes  = { R.drawable.images, R.drawable.images2, R.drawable.images3 };
    String[] songTitles = { "Hôn lễ của em", "Bên ấy em có ai rồi", "Tái sinh" };

    int currentIndex = 0;
    boolean isPrepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init views
        imgAlbum = findViewById(R.id.imgAlbum);
        btnBack = findViewById(R.id.btnBack);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnNext = findViewById(R.id.btnNext);
        spinnerSongs = findViewById(R.id.spinnerSongs);
        switchRepeat = findViewById(R.id.switchRepeat);
        progressLoading = findViewById(R.id.progressLoading);
        seekBar = findViewById(R.id.seekBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, songTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSongs.setAdapter(adapter);

        loadSong(currentIndex);

        // Buttons
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && isPrepared) {
                mediaPlayer.start();
                startSeekBarUpdater();
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        btnNext.setOnClickListener(v -> {
            int next = (currentIndex + 1) % songRes.length;
            changeTo(next);
        });

        btnBack.setOnClickListener(v -> {
            int prev = (currentIndex - 1 + songRes.length) % songRes.length;
            changeTo(prev);
        });

        spinnerSongs.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            boolean first = true;
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                if (first) { first = false; return; }
                changeTo(position);
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean userTouch = false;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null && isPrepared) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { userTouch = true; }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { userTouch = false; }
        });
    }

    private void loadSong(int index) {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(songRes[index]);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                progressLoading.setVisibility(ProgressBar.GONE);
                seekBar.setMax(mediaPlayer.getDuration());
                // auto play on load
                mediaPlayer.start();
                startSeekBarUpdater();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                if (switchRepeat.isChecked()) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                } else {
                    // go next automatically
                    int next = (currentIndex + 1) % songRes.length;
                    changeTo(next);
                }
            });
            mediaPlayer.prepareAsync(); // async prepare -> triggers OnPreparedListener
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cannot load song", Toast.LENGTH_SHORT).show();
            progressLoading.setVisibility(ProgressBar.GONE);
        }

        // update UI
        imgAlbum.setImageResource(imgRes[index]);
        spinnerSongs.setSelection(index, true);
    }

    private void changeTo(int newIndex) {
        if (newIndex < 0 || newIndex >= songRes.length) return;
        currentIndex = newIndex;
        loadSong(currentIndex);
    }

    private final Runnable updater = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && isPrepared && mediaPlayer.isPlaying()) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
            handler.postDelayed(this, 500);
        }
    };

    private void startSeekBarUpdater() {
        handler.removeCallbacks(updater);
        handler.post(updater);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updater);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

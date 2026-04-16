package com.example.javabtvn_7;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listViewSongs;
    private TextView tvCurrentSong;
    private ImageView imgAlbum;

    private String[] songNames;
    private String[] songArtists;
    private int[] songImages;
    private int[] songResources;

    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        listViewSongs = findViewById(R.id.listViewSongs);
        tvCurrentSong = findViewById(R.id.tvCurrentSong);
        imgAlbum = findViewById(R.id.imgAlbum);

        songNames = new String[]{
                "Bên ấy em có ai rồi",
                "Hôn lễ của em",
                "Tái Sinh",
        };

        songArtists = new String[]{
                "Châu Khải Phong",
                "Trọng Nhân",
                "Tùng Dương",
        };


        songImages = new int[]{
                R.drawable.images,
                R.drawable.images2,
                R.drawable.images3,
        };

        songResources = new int[]{
                R.raw.benayemcoairoi,
                R.raw.honlecuaem,
                R.raw.tasinh,
        };

        String[] displaySongs = new String[songNames.length];
        for (int i = 0; i < songNames.length; i++) {
            displaySongs[i] = songNames[i] + " - Nhạc sĩ: " + songArtists[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, displaySongs);
        listViewSongs.setAdapter(adapter);

        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                playSelectedSong();
            }
        });

        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && !isPlaying) {
                    mediaPlayer.start();
                    isPlaying = true;
                } else if (mediaPlayer == null) {
                    playSelectedSong();
                }
            }
        });

        findViewById(R.id.btnPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                }
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = songNames.length - 1;
                }
                playSelectedSong();
            }
        });

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition >= songNames.length) {
                    currentPosition = 0;
                }
                playSelectedSong();
            }
        });
    }

    private void playSelectedSong() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(this, songResources[currentPosition]);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;

            String songInfo = songNames[currentPosition] + " - " + songArtists[currentPosition];
            tvCurrentSong.setText(songInfo);
            imgAlbum.setImageResource(songImages[currentPosition]);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    currentPosition++;
                    if (currentPosition >= songNames.length) {
                        currentPosition = 0;
                    }
                    playSelectedSong();
                }
            });

            Toast.makeText(this, "Đang phát: " + songNames[currentPosition], Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Không thể phát bài hát này", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
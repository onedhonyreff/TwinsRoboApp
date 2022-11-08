package com.codepan.twinsrobo_apps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class FullScreenVideoMateriActivity extends AppCompatActivity {

    private YouTubePlayerView ytpMateriFullScreen;

    private float currentSecondVideo = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video_materi);

        Bundle bundle = getIntent().getExtras();
        currentSecondVideo = bundle.getFloat("current_second");

        if(savedInstanceState != null){
            currentSecondVideo = savedInstanceState.getFloat("current_second_video");
        }

        ytpMateriFullScreen = findViewById(R.id.ytpMateriFullScreen);

        ytpMateriFullScreen.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(bundle.getString("video_materi_full_screen"), currentSecondVideo);
            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float second) {
                currentSecondVideo = second;
                super.onCurrentSecond(youTubePlayer, second);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("current_second_video", currentSecondVideo);
    }
}
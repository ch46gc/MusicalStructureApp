package com.example.android.musicalstructureapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BtsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(android.media.MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song(" BTS", "\n Intro: Ringwanderung", R.drawable.bts_face_yourself, R.raw.ringwanderung));
        songs.add(new Song(" BTS", "\n Best Of Me \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.best_of_me));
        songs.add(new Song(" BTS(solo by Jungkook)", "\n  Blood Sweat & Tears \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.blood_sweat_tears));
        songs.add(new Song(" BTS(solo by Jimin)", "\n DNA \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.dna));
        songs.add(new Song(" BTS(solo by V)", "\n  Not Today \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.not_today));
        songs.add(new Song(" BTS(solo by Suga)", "\n MIC Drop \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.mic_drop));
        songs.add(new Song(" BTS(solo by RM)", "\n Don' t Leave Me", R.drawable.bts_face_yourself, R.raw.dont_leave_me));
        songs.add(new Song(" BTS(solo by J-Hope)", "\n Go Go \n (Japanese Version)", R.drawable.bts_face_yourself, R.raw.go_go));
        songs.add(new Song(" BTS(solo by Jin)", "\n Crystal Snow", R.drawable.bts_face_yourself, R.raw.crystal_snow));
        songs.add(new Song(" BTS", "\n Spring Day", R.drawable.bts_face_yourself, R.raw.spring_day));
        songs.add(new Song(" BTS", "\n Let Go", R.drawable.bts_face_yourself, R.raw.let_go));
        songs.add(new Song(" BTS", "\n Outro Crack", R.drawable.bts_face_yourself, R.raw.outro_crack));

        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songsListView = findViewById(R.id.list);
        songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(BtsActivity.this, song.getAudioResourceId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                mMediaPlayer.start();
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }



    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }

}




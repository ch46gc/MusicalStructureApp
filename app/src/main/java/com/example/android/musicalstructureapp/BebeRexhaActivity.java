package com.example.android.musicalstructureapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class BebeRexhaActivity extends AppCompatActivity {
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
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
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
        songs.add(new Song( " Beba Rexha ", "\n That's It", R.drawable.bebe_rexha, R.raw.thats_it));
        songs.add(new Song( " Bebe Rexha", "\n I Got Time\"", R.drawable.bebe_rexha, R.raw.i_got_time));
        songs.add(new Song( " BeBe Rexha\n (featuring Lil Wayne)", "\n The Way I Are \n (Dance with Somebody)", R.drawable.bebe_rexha, R.raw.the_way_i));
        songs.add(new Song( " BeBe Rexha", "\n (Not) The One", R.drawable.bebe_rexha, R.raw.not_the_one));
        songs.add(new Song( " BeBe Rexha\n (featuring Kranium)", "\n Comfortable", R.drawable.bebe_rexha, R.raw.comfortable));
        songs.add(new Song( " BeBe Rexha\n (featuring Florida Georgia Line)", "\n Meant to Be?", R.drawable.bebe_rexha, R.raw.meant_to_be));

        SongAdapter adapter = new SongAdapter(this, songs);

        ListView songsListView =  findViewById(R.id.list);



         songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
             Song song = songs.get(position);
             releaseMediaPlayer();
             mMediaPlayer = MediaPlayer.create(BebeRexhaActivity.this, song.getAudioResourceId());
             int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                     AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
             mMediaPlayer.start();
             if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                 mMediaPlayer.setOnCompletionListener(mCompletionListener);

                 mMediaPlayer.start();
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
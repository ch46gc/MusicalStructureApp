package com.example.android.musicalstructureapp;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class AjrActivity extends AppCompatActivity {
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
        songs.add(new Song(" AJR", "\n Overture", R.drawable.ajr, R.raw.over_ture));
        songs.add(new Song(" AJR", "\n The Good Part", R.drawable.ajr, R.raw.the_good_part));
        songs.add(new Song(" AJR", "\n Weak", R.drawable.ajr, R.raw.weak));
        songs.add(new Song(" AJR (featuring Rivers Cuomo)", "\n Sober Up", R.drawable.ajr, R.raw.sober_up));
        songs.add(new Song(" AJR", "\n Drama", R.drawable.ajr, R.raw.drama));
        songs.add(new Song(" AJR", "\n Turning Out?", R.drawable.ajr, R.raw.turning_out));
        songs.add(new Song(" AJR", "\n No Grass Today", R.drawable.ajr, R.raw.no_grass_today));
        songs.add(new Song(" AJR", "\n Three-Thirty?", R.drawable.ajr, R.raw.three_thirty));
        songs.add(new Song(" AJR", "\n Call My Dad", R.drawable.ajr, R.raw.call_my_dad));
        songs.add(new Song(" AJR", "\n I'm Not Famous", R.drawable.ajr, R.raw.not_famous));
        songs.add(new Song(" AJR", "\n Netflix Trip", R.drawable.ajr, R.raw.netflix_trip));
        songs.add(new Song(" AJR", "\n Bud Like You", R.drawable.ajr, R.raw.bud_like_you));
        songs.add(new Song(" AJR", "\n Come Hang Out", R.drawable.ajr, R.raw.come_hang_out));

        SongAdapter adapter = new SongAdapter(this, songs);

        ListView songsListView = findViewById(R.id.list);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        songsListView.setAdapter(adapter);
        // Set a click listener to play the audio when the list item is clicked on
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(AjrActivity.this, song.getAudioResourceId());
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
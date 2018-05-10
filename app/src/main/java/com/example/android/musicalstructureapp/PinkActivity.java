package com.example.android.musicalstructureapp;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
public class PinkActivity extends AppCompatActivity {
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
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song(" P!NK", "\n Revenge", R.drawable.pink_beautiful_trauma, R.raw.revenge));
        songs.add(new Song(" P!NK", "\n Beautiful Trauma", R.drawable.pink_beautiful_trauma, R.raw.beautiful_trauma));
        songs.add(new Song(" P!NK", "\n Whatever You Want", R.drawable.pink_beautiful_trauma, R.raw.whatever_you_want));
        songs.add(new Song(" P!NK", "\n What About Us", R.drawable.pink_beautiful_trauma, R.raw.what_about_us));
        songs.add(new Song(" P!NK", "\n But We Lost It", R.drawable.pink_beautiful_trauma, R.raw.but_we_lost_it));
        songs.add(new Song(" P!NK", "\n Barbies?", R.drawable.pink_beautiful_trauma, R.raw.barbies));
        songs.add(new Song(" P!NK", "\n Where We Go", R.drawable.pink_beautiful_trauma, R.raw.where_we_go));
        songs.add(new Song(" P!NK", "\n For Now", R.drawable.pink_beautiful_trauma, R.raw.for_now));
        songs.add(new Song(" P!NK", "\n Secrets", R.drawable.pink_beautiful_trauma, R.raw.secrets));
        songs.add(new Song(" P!NK", "\n Better Life", R.drawable.pink_beautiful_trauma, R.raw.better_life));
        songs.add(new Song(" P!NK", "\n I Am Here", R.drawable.pink_beautiful_trauma, R.raw.i_am_here));
        songs.add(new Song(" P!NK", "\n Wild Hearts Can't Be Broken", R.drawable.pink_beautiful_trauma, R.raw.wild_hearts_cant_be_broken));
        songs.add(new Song(" P!NK", "\n You Get My Love", R.drawable.pink_beautiful_trauma, R.raw.you_get_my_love));
        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songsListView = findViewById(R.id.list);
        songsListView.setAdapter(adapter);
        songsListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
                Song song = songs.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(PinkActivity.this, song.getAudioResourceId());
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
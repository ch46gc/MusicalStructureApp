package com.example.android.musicalstructureapp;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Activity context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.song_items_list, parent, false);
        }
        Song currentSong = getItem(position);
        TextView songTextView = listItemView.findViewById(R.id.song_name);
        songTextView.setText(currentSong.getSongTitle());
        TextView artistTextView = listItemView.findViewById(R.id.artist_name);
        artistTextView.setText(currentSong.getArtistName());
        ImageView imageView = listItemView.findViewById(R.id.image);
        if (currentSong.hasImage()) {
            imageView.setImageResource(currentSong.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        return listItemView;
    }
}


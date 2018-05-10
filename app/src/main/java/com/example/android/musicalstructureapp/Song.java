package com.example.android.musicalstructureapp;
public class Song {

    private String mArtist;
    private String mSong;
    private int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    @Override
    public String toString() {
        return "Song{" +
                "mArtist='" + mArtist + '\'' +
                ", mSong='" + mSong + '\'' +
                ", mAudioResourceId=" + mAudioResourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }

    public Song(String artist, String song, int audioResourceId) {
        mArtist = artist;
        mSong = song;
        mAudioResourceId = audioResourceId;
    }

    public Song(String artist, String song, int imageResourceId, int audioResourceId) {
        mArtist = artist;
        mSong = song;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getArtistName() {
        return mArtist;
    }

    public String getSongTitle() {
        return mSong;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
      public int getAudioResourceId() { return mAudioResourceId;}


}


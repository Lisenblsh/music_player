package com.lis.player_java.player;

import android.content.Context;
import android.media.MediaPlayer;

import com.lis.player_java.R;

public class Music {
    private final int mUri;
    private final Context mContext;

    private MediaPlayer mPlayer;
    private MediaPlayer nextPlayer;

    public Music (Context context){
            mUri = R.raw.music;
            mContext = context;
            create();
    }

    public void create(){
        mPlayer = MediaPlayer.create(mContext, mUri);
        nextPlayer = MediaPlayer.create(mContext, R.raw.music2);
        nextPlayer.setOnCompletionListener(MediaPlayer::stop);
    }

    public void start(){
        mPlayer.start();
    }

    public void pause(){
        mPlayer.pause();
    }

    public double getPosition(){
        return mPlayer.getCurrentPosition();
    }

    public double getDuration() {
        return mPlayer.getDuration();
    }

    public void seekTo(int progress) {
        mPlayer.seekTo(progress);
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }
}

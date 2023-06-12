package com.demo.bywindow.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.VideoView

class MyVideoView(context: Context, attrs: AttributeSet) : VideoView(context, attrs) {

//    init {
//        setOnPreparedListener {
//            it.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
//        }
//    }

//    inner class MyOnPreparedListener() : MediaPlayer.OnPreparedListener {
//        override fun onPrepared(mp: MediaPlayer) {
//            mp.player
//            playerInfo.playerStatus = PREPARED
//            int width = mp.getVideoWidth()
//            int height = mp.getVideoHeight()
//            playerInfo.videoSize = new Size(width,height)
//            callbackInfo(playerInfo)
//        }
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getDefaultSize(0, widthMeasureSpec)
        val height = getDefaultSize(0, heightMeasureSpec)
        Log.d("VideoView", "$width, $height")
        setMeasuredDimension(width, height)
//        setOnPreparedListener {
//            it.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
//        }
    }

}
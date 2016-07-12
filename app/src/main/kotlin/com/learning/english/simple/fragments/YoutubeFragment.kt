package com.learning.english.simple.fragments


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.learning.english.simple.R
import com.learning.english.simple.utils.Utils

class YoutubeFragment() : android.support.v4.app.Fragment(), YouTubePlayer.OnInitializedListener {
    companion object {
        const val VIDEO_ID_KEY = "VIDEO_ID_KEY"
    }

    private var VIDEO_ID = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater!!.inflate(R.layout.fragment_youtube, container, false)
        activity.title = resources.getString(R.string.drawer_menu_video)
        val youtubePlayerFragment = YouTubePlayerFragment()
        VIDEO_ID = arguments[VIDEO_ID_KEY] as String
        youtubePlayerFragment.initialize(Utils.GOOGLE_API_KEY, this)
        activity.fragmentManager.beginTransaction()
                .replace(R.id.fragment_youtube_player, youtubePlayerFragment)
                .commit()
        return fragmentView
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if (player == null) return
        
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID)
        }
        player.setOnFullscreenListener(object : YouTubePlayer.OnFullscreenListener {
            override fun onFullscreen(full: Boolean) {
                if (!full) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            }
        })
        player.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
            override fun onAdStarted() {
            }

            override fun onError(arg0: YouTubePlayer.ErrorReason) {
            }

            override fun onLoaded(arg0: String) {
            }

            override fun onLoading() {
            }

            override fun onVideoEnded() {
            }

            override fun onVideoStarted() {
            }
        })


        player.setPlaybackEventListener(object : YouTubePlayer.PlaybackEventListener {
            override fun onBuffering(arg0: Boolean) {
            }

            override fun onPaused() {
            }

            override fun onPlaying() {
            }

            override fun onSeekTo(arg0: Int) {
            }

            override fun onStopped() {
            }
        })
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        Utils.showToast(activity, resources.getString(R.string.youtube_connection_error))
    }

}

package com.furkanaskin.app.podpocket.ui.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.furkanaskin.app.podpocket.R
import com.furkanaskin.app.podpocket.core.BaseFragment
import com.furkanaskin.app.podpocket.core.Constants
import com.furkanaskin.app.podpocket.databinding.FragmentPlayerQueueBinding
import com.furkanaskin.app.podpocket.db.entities.EpisodeEntity
import com.furkanaskin.app.podpocket.db.entities.PlayerEntity
import com.furkanaskin.app.podpocket.ui.player.queue.QueueAdapter

/**
 * Created by Furkan on 6.05.2019
 */

class PlayerQueueFragment : BaseFragment<PlayerQueueViewModel, FragmentPlayerQueueBinding>(PlayerQueueViewModel::class.java) {

    var player: PlayerEntity? = null

    override fun getLayoutRes(): Int = R.layout.fragment_player_queue

    override fun initViewModel() {
        mBinding.viewModel = viewModel
    }

    companion object {
        fun newInstance(podcastId: String, currentEpisode: Int): Fragment {
            val playerQueueFragment = PlayerQueueFragment()
            val bundle = Bundle()
            bundle.putString(Constants.BundleArguments.PODCAST_ID, podcastId)
            bundle.putInt(Constants.BundleArguments.CURRENT_EPISODE, currentEpisode)
            playerQueueFragment.arguments = bundle
            return playerQueueFragment
        }
    }

    override fun init() {


        mBinding.progressBar.visibility = View.VISIBLE

        val adapter = QueueAdapter { item, position, _ ->

            (activity as PlayerActivity).getEpisodeDetail(item.id)
            (activity as PlayerActivity).currentPosition = position
            mBinding.recyclerViewQueueEpisodes.smoothScrollToPosition(position)
        }

        // Add episodes to queue.
        viewModel.db.episodesDao().getEpisodes().observe(this@PlayerQueueFragment, object : Observer<List<EpisodeEntity>> {
            override fun onChanged(t: List<EpisodeEntity>?) {
                mBinding.recyclerViewQueueEpisodes.adapter = adapter
                (mBinding.recyclerViewQueueEpisodes.adapter as QueueAdapter).submitList(t)

            }
        })


        mBinding.progressBar.visibility = View.GONE
    }

}
package app.lingoknight.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.lingoknight.R
import app.lingoknight.databinding.FragmentLeaderboardBinding


class LeaderboardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLeaderboardBinding>(
                inflater, R.layout.fragment_leaderboard, container, false)

        return binding.root
    }
}
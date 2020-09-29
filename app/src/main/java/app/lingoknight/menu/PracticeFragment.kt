package app.lingoknight.menu

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.lingoknight.R
import app.lingoknight.databinding.FragmentPracticeBinding


class PracticeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentPracticeBinding>(
                inflater, R.layout.fragment_practice, container, false)

        return binding.root
    }
}

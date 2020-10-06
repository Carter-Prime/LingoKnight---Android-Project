//Michael Carter
// 1910059

package app.lingoknight.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import app.lingoknight.R
import app.lingoknight.databinding.FragmentChangeLanguageBinding


class ChangeLanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChangeLanguageBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.englishButton.setOnClickListener {
            val bundle = bundleOf("language" to "English")
            Log.d("testing", "$bundle")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment, bundle)
        }
        binding.germanButton.setOnClickListener {
            val bundle = bundleOf("language" to "German")
            Log.d("testing", "$bundle")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment, bundle)
        }
        binding.finnishButton.setOnClickListener {
            val bundle = bundleOf("language" to "Finnish")
            Log.d("testing", "$bundle")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment, bundle)
        }


        return binding.root
    }

}
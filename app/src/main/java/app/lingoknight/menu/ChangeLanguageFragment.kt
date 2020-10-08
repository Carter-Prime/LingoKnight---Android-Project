//Michael Carter
// 1910059

package app.lingoknight.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("Language", "English")
            this?.apply()
        }

        binding.englishButton.setOnClickListener {
            setLanguage("English")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment)
        }
        binding.germanButton.setOnClickListener {
            setLanguage("German")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment)
        }
        binding.finnishButton.setOnClickListener {
            setLanguage("Finnish")
            view?.findNavController()
                ?.navigate(R.id.action_changeLanguageFragment_to_titleFragment)
        }


        return binding.root
    }

    private fun setLanguage(language: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("Language", language)
            this?.apply()
        }
    }

}
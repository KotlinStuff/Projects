package es.javiercarrasco.mynavigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.mynavigator.databinding.FragmentDosBinding

class DosFragment : Fragment() {
    private lateinit var binding: FragmentDosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDosBinding.inflate(layoutInflater, container, false)

        binding.textView.text = DosFragmentArgs.fromBundle(requireArguments()).numFragment

        binding.button.setOnClickListener {
            findNavController().navigate(
                DosFragmentDirections.actionToTresFragment(
                    getString(
                        R.string.txtFragment,
                        "TRES"
                    )
                )
            )
        }

        return binding.root
    }
}
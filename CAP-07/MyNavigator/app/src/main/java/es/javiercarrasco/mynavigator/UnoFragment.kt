package es.javiercarrasco.mynavigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.javiercarrasco.mynavigator.databinding.FragmentUnoBinding

class UnoFragment : Fragment() {
    private lateinit var binding: FragmentUnoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUnoBinding.inflate(layoutInflater, container, false)

        binding.button.setOnClickListener {
            findNavController().navigate(UnoFragmentDirections.actionToDosFragment())
        }

        return binding.root
    }
}
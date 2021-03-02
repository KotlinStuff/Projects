package es.javiercarrasco.mynavigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mynavigator.databinding.FragmentDosBinding

class DosFragment : Fragment() {
    private lateinit var binding: FragmentDosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
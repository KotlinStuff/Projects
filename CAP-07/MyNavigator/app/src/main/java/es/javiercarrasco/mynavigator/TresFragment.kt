package es.javiercarrasco.mynavigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mynavigator.databinding.FragmentTresBinding

class TresFragment : Fragment() {
    private lateinit var binding: FragmentTresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTresBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
package es.javiercarrasco.mytabsv3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.mytabsv3.databinding.FragmentPageBinding

class PageFragment : Fragment() {

    private lateinit var binding: FragmentPageBinding
    private var nombre: String? = null
    private var texto: String? = null

    companion object {
        @JvmStatic
        fun newInstance(name: String, texto: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("texto", texto)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nombre = it.getString("name")
            texto = it.getString("texto")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView.text = nombre
        binding.textView2.text = texto
    }
}
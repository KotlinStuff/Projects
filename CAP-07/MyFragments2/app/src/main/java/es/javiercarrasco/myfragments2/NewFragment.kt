package es.javiercarrasco.myfragments2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.myfragments2.databinding.FragmentNewBinding

class NewFragment : Fragment() {

    private lateinit var binding: FragmentNewBinding

    private var numFrag: Int? = null
    private var colorBack: Int? = null

    private val TAG = "FragmentNew - ${numFrag}"

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        // Si existen argumentos pasados en el Bundle desde la llamada,
        // se asignan a las propiedades.
        // ARG_NUMFRAG y ARG_COLORBACK están declaradas en MainActivity.
        arguments?.let {
            numFrag = it.getInt(ARG_NUMFRAG)
            colorBack = it.getInt(ARG_COLORBACK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        binding = FragmentNewBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param nFrag Número de fragment.
         * @param cBack Color de fondo.
         * @return A new instance of fragment NewFragment.
         */
        @JvmStatic
        fun newInstance(nFrag: Int, cBack: Int) =
            NewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NUMFRAG, nFrag)
                    putInt(ARG_COLORBACK, cBack)
                }
            }
    }

    // Se modifican las propiedades en este método para asegurar que la activity
    // está creada y se evitan así posibles fallos.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        binding.frameRoot.setBackgroundColor(colorBack!!)
        binding.tvFragment.text = "Fragment\n${numFrag}"
        super.onViewCreated(view, savedInstanceState)
    }

    /*
    Los siguientes métodos sobrecargados son exactamente igual que los
    utilizados en el ejemplo MyFragments.
     */

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }
}
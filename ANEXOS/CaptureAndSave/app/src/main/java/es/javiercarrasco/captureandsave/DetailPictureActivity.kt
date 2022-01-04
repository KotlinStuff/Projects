package es.javiercarrasco.captureandsave

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.captureandsave.databinding.ActivityDetailPictureBinding
import java.io.File

class DetailPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se obtiene el objeto File que contiene la imagen pasada.
        val image = intent.getSerializableExtra("FILE") as File

        binding.imgviewDetail.setImageBitmap(BitmapFactory.decodeFile(image.absolutePath))
        binding.tvFileDetail.text = getString(R.string.label_name, image.name)
    }
}
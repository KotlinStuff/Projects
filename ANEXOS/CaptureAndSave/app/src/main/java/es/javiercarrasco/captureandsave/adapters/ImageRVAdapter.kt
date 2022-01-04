package es.javiercarrasco.captureandsave.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.captureandsave.DetailPictureActivity
import es.javiercarrasco.captureandsave.R
import es.javiercarrasco.captureandsave.databinding.ImageItemBinding
import java.io.File

class ImageRVAdapter : RecyclerView.Adapter<ImageRVAdapter.ViewHolder>() {

    var images: MutableList<File> = ArrayList()
    lateinit var context: Context

    private lateinit var mLongClickListener: ItemLongClickListener

    // Interface para capturar eventos.
    interface ItemLongClickListener {
        // Método encargado de pasar al parent la vista y la posición.
        fun onItemLongClick(view: View?, position: Int)
    }

    // Método encargado de establecer el listener en el parent.
    fun setLongClickListener(itemLongClickListener: ItemLongClickListener?) {
        mLongClickListener = itemLongClickListener!!
    }

    fun ImageRVAdapter(imagesList: MutableList<File>, contxt: Context) {
        this.images = imagesList
        this.context = contxt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ImageItemBinding.inflate(
                layoutInflater,
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = ImageItemBinding.bind(view)

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(image: File) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.root.setBackgroundColor(context.getColor(R.color.bg_item))
            }else{
                binding.root.setBackgroundColor(R.color.bg_item)
            }

            binding.imageView.setImageBitmap(BitmapFactory.decodeFile(image.absolutePath))
            binding.tvNameFile.text = "${image.name.substring(0, 14)}..."

            itemView.setOnClickListener {
                val intent = Intent(context, DetailPictureActivity::class.java).apply {
                    putExtra("FILE", image)
                }
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                mLongClickListener.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }
}
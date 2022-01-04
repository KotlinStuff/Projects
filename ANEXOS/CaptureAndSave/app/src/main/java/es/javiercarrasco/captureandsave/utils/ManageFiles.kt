package es.javiercarrasco.captureandsave.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ManageFiles {
    /**
     * Método encargado de crear el contenedor de la imagen.
     */
    @SuppressLint("SimpleDateFormat")
    fun createImageFile(context: Context): File {
        // Se crea un timeStamp para el nombre del fichero.
        val timeStamp = SimpleDateFormat("yyyyMMdd").format(Date())

        // Se obtiene el directorio según el path creado utilizando el provider.
        val directoryStorage = context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile("IMG_$timeStamp", ".jpg", directoryStorage)

        // Ubicación: /storage/self/primary/Android/data/es.javiercarrasco.captureandsave/files/Pictures
    }

    /**
     * Método encargado de recorrer el directorio donde se almacenan las imágnes y
     * devuelve un ArrayList con los ficheros.
     */
    fun listFiles(context: Context): MutableList<File> {
        val tmp: MutableList<File> = ArrayList()

        val files = context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        )!!.absoluteFile.listFiles()

        if (files != null) {
            for (file in files) {
                if (file != null) {
                    Log.d("DIRECTORY", file.absolutePath)
                    tmp.add(file)
                }
            }
        }

        return tmp
    }
}
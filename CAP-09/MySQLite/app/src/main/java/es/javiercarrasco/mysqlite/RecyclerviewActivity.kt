package es.javiercarrasco.mysqlite

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.javiercarrasco.mysqlite.databinding.ActivityRecyclerviewBinding

class RecyclerviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerviewBinding
    private val amigosDBHelper = MyDBOpenHelper(this, null)
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(R.string.bt_ver_recyclerview)

        // Se instancia la BD en modo lectura y se crea la SELECT.
        db = amigosDBHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${MyDBOpenHelper.TABLA_AMIGOS};",
            null
        )

        // Se crea el adaptador con el resultado del cursor.
        val myRecyclerViewAdapter = MyRecyclerViewAdapter()
        myRecyclerViewAdapter.MyRecyclerViewAdapter(this, cursor)

        // Montamos el RecyclerView.
        binding.myRecyclerview.setHasFixedSize(true)
        binding.myRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.myRecyclerview.adapter = myRecyclerViewAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cerramos la conexión al terminar la activity.
        Log.d("onDestroy", "Cerramos la conexión")
        db.close()
    }
}
package es.javiercarrasco.mysqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    val TAG = "SQLite"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "personas.db"
        val TABLA_AMIGOS = "amigos"
        val COLUMNA_ID = "_id"
        val COLUMNA_NOMBRE = "nombre"
        val COLUMNA_APELLIDOS = "apellidos"
    }

    /**
     * Este método es llamado cuando se crea la base por primera vez. Debe
     * producirse la creación de todas las tablas que formen la base de datos.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val crearTablaAmigos = "CREATE TABLE $TABLA_AMIGOS " +
                    "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMNA_NOMBRE TEXT, " +
                    "$COLUMNA_APELLIDOS TEXT)"
            db!!.execSQL(crearTablaAmigos)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }

    /**
     * Este método se invocará cuando la base de datos necesite ser actualizada.
     * Se utiliza para hacer DROPs, añadir tablas o cualquier acción que
     * actualice el esquema de la BD.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropTablaAmigos = "DROP TABLE IF EXISTS $TABLA_AMIGOS"
            db!!.execSQL(dropTablaAmigos)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    /**
     * Método opcional. Se llamará a este método después de abrir la base de
     * datos, antes de ello, comprobará si está en modo lectura. Se llama justo
     * después de establecer la conexión y crear el esquema.
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d("$TAG (onOpen)", "¡¡Base de datos abierta!!")
    }

    /**
     * Método para añadir un amigo a la tabla amigos.
     */
    fun addAmigo(name: String, surname: String) {
        // Se crea un ArrayMap<>() haciendo uso de ContentValues().
        val data = ContentValues()
        data.put(COLUMNA_NOMBRE, name)
        data.put(COLUMNA_APELLIDOS, surname)

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        db.insert(TABLA_AMIGOS, null, data)
        db.close()
    }

    /**
     * Método para eliminar un amigo de la tabla por el identificador.
     */
    fun delAmigo(identifier: Int): Int {
        val args = arrayOf(identifier.toString())

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase

        // Se puede elegir un sistema u otro.
        val result = db.delete(TABLA_AMIGOS, "$COLUMNA_ID = ?", args)
        // db.execSQL("DELETE FROM $TABLA_AMIGOS WHERE $COLUMNA_ID = ?", args)

        db.close()
        return result
    }

    /**
     * Método para actualizar el nombre de un amigo de la tabla por el id.
     */
    fun updateAmigo(identifier: Int, newName: String) {
        val args = arrayOf(identifier.toString())

        // Se crea un ArrayMap<>() con los datos nuevos.
        val data = ContentValues()
        data.put(COLUMNA_NOMBRE, newName)

        val db = this.writableDatabase
        db.update(TABLA_AMIGOS, data, "$COLUMNA_ID = ?", args)
        db.close()
    }
}
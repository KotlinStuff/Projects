package es.javiercarrasco.mypreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preferences(context: Context) {
    val PREFS_NAME = "es.javiercarrasco.mypreferences"
    val SHARED_NAME = "shared_name"
    val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, MODE_PRIVATE
    )

    // Se crea la propiedad name que será persistente, además se modifica
    // su getter y setter para que almacene en SharedPreferences.
    var name: String
        get() = prefs.getString(SHARED_NAME, "").toString()
        set(value) = prefs.edit().putString(SHARED_NAME, value).apply()

    // Se eliminan las preferencias.
    fun deletePrefs() {
        prefs.edit().apply {
            remove(SHARED_NAME)
            apply()
        }
    }
}
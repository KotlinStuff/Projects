package es.javiercarrasco.mygridview2

// Clase encargada de almacenar la información de un item.
data class MyItems(val name: String, val image: Int) {
    private var n: String? = null
    private var img: Int? = null

    init {
        this.n = name
        this.img = img
    }
}
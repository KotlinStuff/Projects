package es.javiercarrasco.myrecyclerview

data class MyAnimals(val name: String, val latin: String, val image: Int) {
    var animalName: String? = null
    var latinName: String? = null
    var imageAnimal: Int? = null

    init {
        this.animalName = name
        this.latinName = latin
        this.imageAnimal = image
    }
}
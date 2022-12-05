package project.st991377867.marcin.data.model

class Diet {
    var id: String
        get() = field
    var name: String
        get() = field
    var description: String
        get() = field
    var image: String
        get() = field
    var type: String
        get() = field
    var calories: String
        get() = field
    var protein: String
        get() = field
    var fat: String
        get() = field
    var carbs: String
        get() = field
    var ingredients: String
        get() = field
    var instructions: String
        get() = field
    var tips: String
        get() = field
    var duration: String
        get() = field

    constructor() {
        id = ""
        name = ""
        description = ""
        image = ""
        type = ""
        calories = ""
        protein = ""
        fat = ""
        carbs = ""
        ingredients = ""
        instructions = ""
        tips = ""
        duration = ""
    }

    constructor(
        id: String,
        name: String,
        description: String,
        type: String,
        calories: String,
        ingredients: String,
        instructions: String,
        duration: String
    ) {
        this.id = id
        this.name = name
        this.description = description
        image = ""
        this.type = type
        this.calories = calories
        protein = ""
        fat = ""
        carbs = ""
        this.ingredients = ingredients
        this.instructions = instructions
        tips = ""
        this.duration = duration
    }

    constructor(
        id: String,
        name: String,
        description: String,
        image: String,
        type: String,
        calories: String,
        protein: String,
        fat: String,
        carbs: String,
        ingredients: String,
        preparation: String,
        tips: String,
        duration: String
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.image = image
        this.type = type
        this.calories = calories
        this.protein = protein
        this.fat = fat
        this.carbs = carbs
        this.ingredients = ingredients
        this.instructions = preparation
        this.tips = tips
        this.duration = duration
    }

    override fun toString(): String {
        return "Diet(id='$id', name='$name', description='$description', image='$image', type='$type', calories='$calories', protein='$protein', fat='$fat', carbs='$carbs', ingredients='$ingredients', instructions='$instructions', tips='$tips', duration='$duration')"
    }
    
}
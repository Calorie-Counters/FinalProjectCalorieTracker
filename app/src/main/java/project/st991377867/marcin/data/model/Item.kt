package project.st991377867.marcin.data.model


class Item{

    var id: String
    var uid: String
    var item_name: String
    var item_weight: String
    var item_quantity: String
    var item_calorie: String
    var item_description: String


    constructor() {
        id = ""
        uid = ""
        item_name = ""
        item_weight = ""
        item_quantity = ""
        item_calorie = ""
        item_description = ""
    }

    constructor(id: String, uid: String, name: String, weight: String, quantity: String, calorie: String, description: String) {
        this.id = id
        this.uid = uid
        this.item_name = name
        this.item_weight = weight
        this.item_quantity = quantity
        this.item_calorie = calorie
        this.item_description = description
    }

    override fun toString(): String {
        return "Item(id='$id', uid='$uid', item_name='$item_name', item_weight='$item_weight', item_quantity='$item_quantity', item_calorie='$item_calorie', item_description='$item_description')"
    }


}

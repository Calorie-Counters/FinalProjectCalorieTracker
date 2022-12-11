package project.st991377867.marcin.data.model

class Reminder {

    var id: String
        get() = field
    var uid: String
        get() = field
    var title: String
        get() = field
    var date: String
        get() = field
    var time: String
        get() = field
    var description: String
        get() = field

    constructor() {
        id = ""
        uid = ""
        title = ""
        date = ""
        time = ""
        description = ""
    }

    constructor(id: String, userId: String, title: String, date: String, time: String, description: String) {
        this.id = id
        this.uid = userId
        this.title = title
        this.date = date
        this.time = time
        this.description = description
    }

    override fun toString(): String {
        return "Reminder(id='$id', userId='$uid', title='$title', date='$date', time='$time', description='$description')"
    }

}
package project.st991377867.marcin.ui.item

import android.app.Application
import project.st991377867.marcin.data.ItemRoomDatabase

class ItemApplication : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}
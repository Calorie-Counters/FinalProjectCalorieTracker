package project.st991377867.marcin.ui.help

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HelpViewModel : ViewModel() {
    val fireStoreDatabase = FirebaseFirestore.getInstance()

    private val _helpText: MutableLiveData<String> = MutableLiveData<String>()

    val helpText: LiveData<String>
        get() = _helpText

    suspend fun run() {
        viewModelScope.launch {
            fireStoreDatabase.collection("helptext")
                .document("o9sXLLCEPfPglFsyTyGc")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        try {
                            var text = it.result.data!!.getValue("help").toString()
                            text = text.replace("\\n", "\n")
                            _helpText.value = text
                            //Log.d("JORDANDEBUG", _helpText.value.toString())
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
                .await()
        }
    }

    suspend fun saveHelp(text: String){
        fireStoreDatabase.collection("helptext")
            .document("o9sXLLCEPfPglFsyTyGc")
            .update("help", text)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Log.d("HelpViewModel", "Help Text updated")
                } else {
                    Log.d("HelpViewModel", it.isSuccessful.toString())
                }
            }
    }

    suspend fun getHelpTxt() :String {
        var text: String = ""
        fireStoreDatabase.collection("helptext")
            .document("o9sXLLCEPfPglFsyTyGc")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    try {
                        text = it.result.data!!.getValue("help").toString()
                        text = text.replace("\\n", "\n")
                        //_helpText.value = text
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
            .await()


        return text
    }
}
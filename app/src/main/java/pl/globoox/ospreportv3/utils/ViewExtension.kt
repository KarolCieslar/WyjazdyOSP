package pl.globoox.ospreportv3.utils

import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import pl.globoox.ospreportv3.MainActivity

fun EditText.checkIsNullAndSetError(errorText: String) : Boolean {
    if (this.text.isNullOrBlank()) {
        this.error = errorText
        return true
    }
    return false
}

fun Fragment.showSnackBar(text: String) {
    (activity as MainActivity?)!!.showSnackBar(text)
}


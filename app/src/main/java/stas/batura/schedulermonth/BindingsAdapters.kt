package stas.batura.schedulermonth

import android.widget.EditText
import androidx.databinding.BindingAdapter
import stas.batura.schedulermonth.ui.utils.IntegerTextWatcher
import stas.batura.schedulermonth.ui.utils.StringTextWatcher
import java.lang.Exception

/*
    добавляем слушателя дял получения текста из EditText
 */
@BindingAdapter("addEditTextWatcher")
fun bindEditText(editText: EditText, stringTextWatcher: StringTextWatcher) {
    val string = editText.text.toString()
    stringTextWatcher.setString(editText.text.toString())
    editText.addTextChangedListener(stringTextWatcher)
}

/*
    добавляем слушателя дял получения текста из EditText
 */
@BindingAdapter("addIntEditTextWatcher")
fun bindIntEditText(editText: EditText, stringTextWatcher: IntegerTextWatcher) {
    val string = editText.text.toString()
    try {
        stringTextWatcher.setNum(string.toInt())
    } catch (exc : Exception) {
        print("Error in int number +$exc")
    }

    editText.addTextChangedListener(stringTextWatcher)
}
package stas.batura.schedulermonth

import android.widget.EditText
import androidx.databinding.BindingAdapter
import stas.batura.schedulermonth.ui.utils.EditTextWatcher

/*
    добавляем слушателя дял получения текста из EditText
 */
@BindingAdapter("addEditTextWatcher")
fun bindEditText(editText: EditText, editTextWatcher: EditTextWatcher) {
    editText.addTextChangedListener(editTextWatcher)
}
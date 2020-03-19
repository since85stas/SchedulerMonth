package stas.batura.schedulermonth.ui.utils

import android.text.Editable
import android.text.TextWatcher

/*
    класс для получения текста из EditText простейший вариант
    без особых наваротов, для нормального использования надо доработать
 */
class EditTextWatcher : TextWatcher {

    var _string:String = ""

    fun setString( string: String) {
        _string = string
    }

    val string:String
        get() = _string

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _string = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _string = s.toString()
    }



}
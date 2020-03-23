package stas.batura.schedulermonth.ui.utils

import android.text.Editable
import android.text.TextWatcher
import java.lang.Exception

/*
    класс для получения целого числа из EditText простейший вариант
    без особых наваротов, для нормального использования надо доработать
 */
class IntegerTextWatcher : TextWatcher {

    private var _number:Int = 4

    fun setNum(value : Int) {
        _number = value
    }

    val number:Int
        get() = _number

    override fun afterTextChanged(s: Editable?) {
        try {
            _number = s!!.toString().toInt()
        } catch (exc : Exception) {
            print("Error in int number +$exc")
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        _number = s.toString().toInt()
    }



}
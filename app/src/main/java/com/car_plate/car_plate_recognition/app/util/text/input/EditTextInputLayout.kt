package com.car_plate.car_plate_recognition.app.util.text.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.car_plate.car_plate_recognition.R
import kotlinx.android.synthetic.main.layout_text_input.view.*
import java.util.*

class EditTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var hint: String = ""
    private var subHint: String = ""
    private var text: String? = null
    private var maxLines = 1
    private var error: String? = null
    private var inputType = EditorInfo.TYPE_TEXT_VARIATION_NORMAL
    private var imeOptions = 0

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_text_input, this, true)
        initTypedArray(context, attrs)
        displayData()
    }

    private fun initTypedArray(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextInputLayout)
        hint = typedArray.getString(R.styleable.EditTextInputLayout_hint) ?: hint
        subHint = typedArray.getString(R.styleable.EditTextInputLayout_subHint) ?: subHint
        inputType = typedArray.getInt(R.styleable.EditTextInputLayout_android_inputType, inputType)
        imeOptions =
            typedArray.getInt(R.styleable.EditTextInputLayout_android_imeOptions, imeOptions)
        text = typedArray.getString(R.styleable.EditTextInputLayout_android_text) ?: text?.toUpperCase(
            Locale.ROOT
        )
        maxLines = typedArray.getInt(R.styleable.EditTextInputLayout_android_maxLines, maxLines)
        error = typedArray.getString(R.styleable.EditTextInputLayout_error) ?: error
        textInputLayout.error = error

        typedArray.recycle()
    }

    private fun displayData() {
        setHint(hint)
        setSubHint(subHint)
        setInputType(inputType)
        setImeOptions(imeOptions)
        setText(text?.toUpperCase(Locale.ROOT))
        setMaxLines(maxLines)
    }

    fun setHint(hint: String) {
        this.hint = hint
        textInputHint.text = hint
    }

    fun setSubHint(hint: String) {
        this.subHint = hint
        textInput.hint = hint
    }

    fun setInputType(inputType: Int) {
        this.inputType = inputType
        textInput.inputType = inputType
    }

    fun setImeOptions(imeOption: Int) {
        this.imeOptions = imeOption
        textInput.imeOptions = imeOption
    }

    fun setText(text: String?) {
        this.text = text
        textInput.setText(text?.toUpperCase(Locale.ROOT))
    }

    fun setMaxLines(maxLines: Int) {
        this.maxLines = maxLines
        textInput.maxLines = maxLines
    }

    fun getText() = text

    fun addTextChangedListener(afterTextChanged: () -> Unit = {}) {
        textInput.addTextChangedListener {
            text = it.toString()
            afterTextChanged.invoke()
        }
    }

    fun setError(error: String?) {
        this.error = error
        textInputLayout.error = error
    }

    fun getError() = error

}

@BindingAdapter("android:text")
fun setText(view: EditTextInputLayout, text: String?) {
    if (view.getText() != text) {
        view.setText(text?.toUpperCase(Locale.ROOT))
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: EditTextInputLayout): String? {
    return view.getText()?.toUpperCase(Locale.ROOT)
}

@BindingAdapter(value = ["android:textAttrChanged"])
fun setListener(editText: EditTextInputLayout, listener: InverseBindingListener?) {
    if (listener != null) {
        editText.addTextChangedListener {
            listener.onChange()
        }
    }
}

@BindingAdapter("app:error")
fun setError(view: EditTextInputLayout, error: String?) {
    if (view.getError() != error) {
        view.setError(error)
    }
}
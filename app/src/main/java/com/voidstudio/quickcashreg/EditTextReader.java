package com.voidstudio.quickcashreg;public class EditTextReader{	public EditTextReader()	{	}@androidx.annotation.NonNull
    private java.lang.String getTextFromEditText(android.widget.EditText textBox) {
        return textBox.getText().toString().trim();
    }}
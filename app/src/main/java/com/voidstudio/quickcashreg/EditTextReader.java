package com.voidstudio.quickcashreg;

import android.widget.EditText;

import androidx.annotation.NonNull;

public class EditTextReader {
  public EditTextReader() {
  }

  @NonNull
  protected String getTextFromEditText(EditText textBox) {
    return textBox.getText().toString().trim();
  }
}
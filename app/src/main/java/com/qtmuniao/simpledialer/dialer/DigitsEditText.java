/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qtmuniao.simpledialer.dialer;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;


/** EditText which suppresses IME show up. */
public class DigitsEditText extends AppCompatEditText {
  private OnTextChangeListener mOnTextChangeListener;

  public DigitsEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    setShowSoftInputOnFocus(false);
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    final InputMethodManager imm =
        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
    if (imm != null && imm.isActive(this)) {
      imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    final boolean ret = super.onTouchEvent(event);
    // Must be done after super.onTouchEvent()
    final InputMethodManager imm =
        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
    if (imm != null && imm.isActive(this)) {
      imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
    }
    return ret;
  }

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    if (isCursorVisible()) {
      setSelection(getText().length());
    }

    if (mOnTextChangeListener != null) {
      mOnTextChangeListener.onTextChange(this.getText().toString());
    }
  }

  public interface OnTextChangeListener {
    void onTextChange(String phoneNumber);
  }

  public void setOnTextChangeListener(OnTextChangeListener listener) {
    this.mOnTextChangeListener = listener;
  }
}

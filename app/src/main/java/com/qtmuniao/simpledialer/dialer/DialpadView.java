package com.qtmuniao.simpledialer.dialer;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtmuniao.simpledialer.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by songpengwei233 on 2017/12/6.
 */

public class DialpadView extends LinearLayout {
    /** {@code True} if the dialpad is in landscape orientation. */
    private final boolean mIsLandscape;

    private final int[] mButtonIds =
            new int[] {
                    R.id.zero,
                    R.id.one,
                    R.id.two,
                    R.id.three,
                    R.id.four,
                    R.id.five,
                    R.id.six,
                    R.id.seven,
                    R.id.eight,
                    R.id.nine,
                    R.id.star,
                    R.id.pound
            };
    private DigitsEditText mDigits;
    private ImageButton mDelete;

    public DialpadView(Context context) {
        this(context, null);
    }

    public DialpadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialpadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mIsLandscape =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupKeypad();
        mDigits = (DigitsEditText) findViewById(R.id.digits);
        mDelete = (ImageButton) findViewById(R.id.deleteButton);

        AccessibilityManager accessibilityManager =
                (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager.isEnabled()) {
            // The text view must be selected to send accessibility events.
            mDigits.setSelected(true);
        }
    }

    private void setupKeypad() {
        final int[] letterIds =
                new int[] {
                        R.string.dialpad_0_letters,
                        R.string.dialpad_1_letters,
                        R.string.dialpad_2_letters,
                        R.string.dialpad_3_letters,
                        R.string.dialpad_4_letters,
                        R.string.dialpad_5_letters,
                        R.string.dialpad_6_letters,
                        R.string.dialpad_7_letters,
                        R.string.dialpad_8_letters,
                        R.string.dialpad_9_letters,
                        R.string.dialpad_star_letters,
                        R.string.dialpad_pound_letters
                };

        final Resources resources = getContext().getResources();
        final NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);

        for (int i = 0; i < mButtonIds.length; i++) {
            FrameLayout dialpadKey = (FrameLayout) findViewById(mButtonIds[i]);
            TextView numberView = (TextView) dialpadKey.findViewById(R.id.dialpad_key_number);
            TextView lettersView = (TextView) dialpadKey.findViewById(R.id.dialpad_key_letters);

            final String numberString;
            if (mButtonIds[i] == R.id.pound) {
                numberString = resources.getString(R.string.dialpad_pound_number);
            } else if (mButtonIds[i] == R.id.star) {
                numberString = resources.getString(R.string.dialpad_star_number);
            } else {
                numberString = nf.format(i);
            }

            numberView.setText(numberString);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                numberView.setElegantTextHeight(false);
            }
            if (lettersView != null) {
                lettersView.setText(resources.getString(letterIds[i]));
            }
        }
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return true;
    }

    public DigitsEditText getDigits() {
        return mDigits;
    }

    public ImageButton getDeleteButton() {
        return mDelete;
    }


}

package com.qtmuniao.simpledialer.dialer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtmuniao.simpledialer.R;


public class DialpadFragment extends Fragment {
    public final static String EXTRA_CURSOR_VISIBLE = "EXTRA_CURSOR_VISIBLE";

    private DigitsEditText digits;
    private String input = "";
    private Callback callback;
    private OnTextChange onTextChange;
    private boolean cursorVisible = false;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        // get saved arguments
        Bundle arguments = savedInstanceState;
        if (arguments == null) {
            arguments = getArguments();
        }
        if (arguments != null) {
            cursorVisible = arguments.getBoolean(EXTRA_CURSOR_VISIBLE, cursorVisible);
        }

        // inflate a view
        View view = inflater.inflate(R.layout.dialpad_fragment, container, false);
        DialpadView dialpadView = (DialpadView)view.findViewById(R.id.dialpad_view);

        digits =  dialpadView.getDigits();
        digits.setCursorVisible(cursorVisible);
        dialpadView.findViewById(R.id.zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('0');
            }
        });
        dialpadView.findViewById(R.id.zero).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                append('+');
                return true;
            }
        });
        dialpadView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('1');
            }
        });
        dialpadView.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('2');
            }
        });
        dialpadView.findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('3');
            }
        });
        dialpadView.findViewById(R.id.four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('4');
            }
        });
        dialpadView.findViewById(R.id.five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('5');
            }
        });
        dialpadView.findViewById(R.id.six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('6');
            }
        });
        dialpadView.findViewById(R.id.seven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('7');
            }
        });
        dialpadView.findViewById(R.id.eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('8');
            }
        });
        dialpadView.findViewById(R.id.nine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('9');
            }
        });

        dialpadView.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('*');
            }
        });

        dialpadView.findViewById(R.id.pound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append('#');
            }
        });


        dialpadView.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poll();
            }
        });
        dialpadView.getDeleteButton().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clear();
                return true;
            }
        });

        // call button
        View callButton = view.findViewById(R.id.fab_ok);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.ok(digits.getText().toString(), input);
                }
            }
        });

        digits.setOnTextChangeListener(new DigitsEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String phoneNumber) {
                if (onTextChange != null) {
                    onTextChange.change(phoneNumber);
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_CURSOR_VISIBLE, cursorVisible);
    }

    private void poll() {
        if (!input.isEmpty()) {
            input = input.substring(0, input.length() - 1);
            digits.setText(input);
        }
    }

    public void clear() {
        digits.setText("");
        input = "";
    }

    private void append(char c) {
        input += c;
        digits.setText(input);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    public void addOntextChange(OnTextChange callback) {
        onTextChange = callback;
    }

    public interface Callback {
        void ok(String formatted, String raw);
    }

    public interface OnTextChange {
        void change(String text);
    }
}

package com.qtmuniao.simpledialer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qtmuniao.simpledialer.dialer.DialpadFragment;

public class MainActivity extends AppCompatActivity implements DialpadFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDialFragment();
    }

    private DialpadFragment dialFragment = null;
    public DialpadFragment getDialFragment() {return dialFragment;}
    public DialpadFragment addDialFragment() {
        if (dialFragment == null) {
            dialFragment = new DialpadFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.pad, dialFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(dialFragment)
                    .commit();

            View dialPadView = findViewById(R.id.pad);
            dialPadView.setVisibility(View.VISIBLE);
        }

        dialFragment.onAttach((Context) this);

        return dialFragment;
    }

    public void delDialFragment() {
        if (dialFragment == null) return;

        getSupportFragmentManager()
                .beginTransaction()
                .detach(dialFragment)
                .commit();

        View dialPadView = findViewById(R.id.pad);
        dialPadView.setVisibility(View.GONE);
    }


    // item click
    @Override
    public void ok(String formatted, String raw) {
        if (dialFragment != null) {
            dialFragment.clear();
        }

        // to do what you want when dial the phone icon
    }
}

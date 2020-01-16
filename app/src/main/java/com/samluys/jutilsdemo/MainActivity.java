package com.samluys.jutilsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.samluys.jutils.ScreenUtils;
import com.samluys.jutils.ToastUtils;
import com.samluys.statusbar.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.showLong("屏幕宽度 " + ScreenUtils.getScreenWidth(this));

        StatusBarUtils.transparencyBar(this);
    }
}

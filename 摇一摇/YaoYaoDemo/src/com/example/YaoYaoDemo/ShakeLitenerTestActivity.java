package com.example.YaoYaoDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-17
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
public class ShakeLitenerTestActivity extends Activity
{
    private ShakeListener mShaker;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                new AlertDialog.Builder(ShakeLitenerTestActivity.this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage("Shooken!")
                        .show();
            }
        });
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }
}
package com.example.YaoYaoDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MyYaoYaoActivity extends Activity {


        SensorManager sm;
        SensorL listener;

        private boolean isRefresh=false;

        Dialog d;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);

            sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

            listener = new SensorL();
            // 对加速计进行监听
            sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST);
            // sm.registerListener(listener,
            // sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_FASTEST);
            // sm.registerListener(listener,sm.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_FASTEST);

            AlertDialog.Builder b = new AlertDialog.Builder(this);
            // 用来显示的对话框
            d = b.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    isRefresh = false;
                    dialog.cancel();
                }
            }).setMessage("摇到了.................").create();

        }

        private class SensorL implements SensorEventListener {

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    // 判断是否在刷新
                    if (isRefresh)
                        return;
                    float newX = Math.abs(event.values[SensorManager.DATA_X]);
                    float newY = Math.abs(event.values[SensorManager.DATA_Y]);
                    float newZ = Math.abs(event.values[SensorManager.DATA_Z]);
                    // 这里是关键，判断某个方向上的加速度值是否达到自己想要的值
                    // X
                    if (newX >= 18) {
                        Toast.makeText(MyYaoYaoActivity.this, "newX" + newX, 0).show();
                        isRefresh = true;
                        d.show();
                        return;
                    }
                    // Y
                    if (newY >= 20) {
                        Toast.makeText(MyYaoYaoActivity.this, "newY" + newY, 0).show();
                        isRefresh = true;
                        d.show();
                        return;
                    }
                    // Z
                    if (newZ >= 20) {
                        Toast.makeText(MyYaoYaoActivity.this, "newZ" + newZ, 0).show();
                        isRefresh = true;
                        d.show();
                        return;
                    }

                }
                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    // Log.e("TYPE_MAGNETIC_FIELD", ""+event.sensor.toString());
                }
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    // Log.e("TYPE_PRESSURE", ""+event.sensor.toString());
                }
            }

        }

        @Override
        protected void onPause() {
            // ACTIVITY消失时取消监听
            sm.unregisterListener(listener);
            super.onPause();
        }


    @Override
    protected void onResume() {
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }
}
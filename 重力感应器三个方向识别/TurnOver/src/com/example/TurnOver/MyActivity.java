package com.example.TurnOver;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;

public class MyActivity extends Activity {

    TextView myTextView;
    SensorManager sensorManager;
    int ringerMode;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTextView= (TextView) findViewById(R.id.myTextView);
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        GetAudioManagerMode();

    }
    //得到现在的模式
    private void GetAudioManagerMode() {
       AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(audioManager!=null){
            ringerMode=audioManager.getRingerMode();
        }
    }
    //更换为静音模式
    private void changeToSilentMode(){
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(audioManager!=null){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            ringerMode=audioManager.getRingerMode();
        }
    }
    //更换为振动模式
    private void changeToVibrateMode(){
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(audioManager!=null){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            ringerMode=audioManager.getRingerMode();
        }
    }
    //更换为正常模式
    private void changeToNormalMode(){
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(audioManager!=null){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            ringerMode=audioManager.getRingerMode();
        }
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }

    private final SensorEventListener  sensorListener=new SensorEventListener() {

        private float x,y,z;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
          switch(sensorEvent.sensor.getType()){
              case Sensor.TYPE_ACCELEROMETER:
                  x=sensorEvent.values[0];
                  y=sensorEvent.values[1];
                  z=sensorEvent.values[2];
                  if (z>9){
                      changeToNormalMode();
                      switch (ringerMode){
                          case AudioManager.RINGER_MODE_NORMAL:
                              myTextView.setText("正常模式");
                              break;
                          case AudioManager.RINGER_MODE_SILENT:
                              myTextView.setText("静音模式");
                              break;
                          case AudioManager.RINGER_MODE_VIBRATE:
                              myTextView.setText("振动模式");
                              break;
                      }
                  }else  if (z<-9){
                      changeToSilentMode();
                      changeToVibrateMode();
                      switch (ringerMode){
                          case AudioManager.RINGER_MODE_NORMAL:
                              myTextView.setText("正常模式");
                              break;
                          case AudioManager.RINGER_MODE_SILENT:
                              myTextView.setText("静音模式");
                              break;
                          case AudioManager.RINGER_MODE_VIBRATE:
                              myTextView.setText("振动模式");
                              break;
                      }
                  }else if (x>9){
                      myTextView.setText("正面向左");
                  }else if (x<-9){
                      myTextView.setText("正面向右");
                  }else if (y>9){
                      myTextView.setText("手掌正翻向自己直立");
                  }else if (y<-9){
                      myTextView.setText("手掌反翻反向直立");
                  }else{
                      myTextView.setText("无法判断");
                  }
                  break;
          }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}

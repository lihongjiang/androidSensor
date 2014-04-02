package com.qing;

import com.qing.MySlipSwitch.OnSwitchListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	private Button switch_Btn;
	private MySlipSwitch slipswitch_MSL;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        slipswitch_MSL = (MySlipSwitch)findViewById(R.id.main_myslipswitch);
        slipswitch_MSL.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
        slipswitch_MSL.setSwitchState(true);
        slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {
			
			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if(isSwitchOn) {
					Toast.makeText(MainActivity.this, "开关已经开启", 300).show();
				} else {
					Toast.makeText(MainActivity.this, "开关已经关闭", 300).show();
				}
			}
		});
        
        switch_Btn = (Button)findViewById(R.id.main_button_switch);
        switch_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slipswitch_MSL.updateSwitchState(!slipswitch_MSL.getSwitchState());
				
				if(slipswitch_MSL.getSwitchState()) {
					Toast.makeText(MainActivity.this, "开关已经开启", 300).show();
				} else {
					Toast.makeText(MainActivity.this, "开关已经关闭", 300).show();
				}
			}
		});
    }
}
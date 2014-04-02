package com.bslee.up;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BangerViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView target = (TextView) findViewById(R.id.target_view);
        BadgeView badge = new BadgeView(this, target);
        badge.setText("2");
        badge.show();
        
        badge.setText("26");
       // badge.hide();
    }
}
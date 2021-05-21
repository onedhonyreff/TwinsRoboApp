package com.codepan.twinsrobo_apps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

public class TryDayNightActivity extends Activity {

    public static boolean isNightMode = false;

    SwitchCompat swToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_day_night);

        swToggle = findViewById(R.id.swToggle);
        swToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isNightMode = b;

                int delayTime = 120;

                compoundButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // method getDelegate() tidak bisa dipanggil apabila mewarisi (Activity)
                        // bisa dipanggil apabila mewarisi (AppCompatActivity)

//                        if(isNightMode){
//                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                        }
//                        else {
//                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                        }
                    }
                }, delayTime);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int lebar = dm.widthPixels;
        int panjang = dm.heightPixels;

        getWindow().setLayout((int) (lebar * 0.8), (int) (panjang * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}
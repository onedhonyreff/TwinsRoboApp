package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.OtherClass.DataHelper;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView ivSplashLogo;
    private LinearLayout llFrameSplashLogo;
    private TextView tvTextSplash;

    private Animation dropAnimation, upAnimation, fadeInAnimation;

    int X = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtil.setTransparent(this);

        ivSplashLogo = findViewById(R.id.ivSplashLogo);
        llFrameSplashLogo = findViewById(R.id.llFrameSplashLogo);
        tvTextSplash = findViewById(R.id.tvTextSplash);

        dropAnimation = AnimationUtils.loadAnimation(this, R.anim.drop_anim);
        upAnimation = AnimationUtils.loadAnimation(this, R.anim.up_anim);
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim);
        llFrameSplashLogo.startAnimation(dropAnimation);
        ivSplashLogo.startAnimation(fadeInAnimation);
        tvTextSplash.startAnimation(upAnimation);

        if (savedInstanceState != null) {
            X = savedInstanceState.getInt("load");
        }

        X++;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (X <= 1) {
                    DataHelper mySessionDb = new DataHelper(MainActivity.this);
                    Cursor res = mySessionDb.getLoginSession();
                    String idUser = "", namaDepan = "";
                    double lastLogged = 4;
                    while (res.moveToNext()){
                        idUser = res.getString(1);
                        namaDepan = res.getString(2);
                        lastLogged = Double.parseDouble(res.getString(4));
                    }
                    if(res.getCount() == 0 || lastLogged > 3){
                        startActivity(new Intent(MainActivity.this, login.class));
                        overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
                    }
                    else {
                        saveLoginSession(idUser, namaDepan);
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("id_user_login", idUser);
                        intent.putExtra("nama_depan_login", namaDepan);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_in_anim, R.anim.slide_up_out_anim);
                    }
                }
                finish();
            }
        }, 2000L);

        RequestOptions requestOpt = new RequestOptions();
        requestOpt.skipMemoryCache(true);
        requestOpt.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOpt.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

//        Glide.with(this)
//                .load(R.drawable.main_loading)
//                .apply(requestOpt)
//                .into(ivLoadingSpash);


//        loginButton = findViewById(R.id.loginbtn);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private void saveLoginSession(String idUser, String firstNameUser){
        DataHelper mySessionDb = new DataHelper(MainActivity.this);
        boolean isRenewedSession = mySessionDb.renewSession(idUser, firstNameUser);
        if(!isRenewedSession){
            Toast.makeText(this, "Failed to logging session", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("load", X);
    }
}

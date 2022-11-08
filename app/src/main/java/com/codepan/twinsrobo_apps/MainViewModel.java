package com.codepan.twinsrobo_apps;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepan.twinsrobo_apps.OtherClass.DataHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _isTimeOut = new MutableLiveData<>();
    public LiveData<Boolean> isTimeOut(){
        return _isTimeOut;
    };

    public Boolean everLoggedIn = false;
    public double lastLogged = 4;
    public String idUser = "", namaDepan = "";
    public Animation dropAnimation, upAnimation, fadeInAnimation;

    private final Context ctx;

    public MainViewModel(Context context) {
        this.ctx = context;
        dropAnimation = AnimationUtils.loadAnimation(ctx, R.anim.drop_anim);
        upAnimation = AnimationUtils.loadAnimation(ctx, R.anim.up_anim);
        fadeInAnimation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in_anim);
        waitToMove();
    }

    private void waitToMove(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
                handler.post(() -> {
                    DataHelper mySessionDb = new DataHelper(ctx);
                    Cursor res = mySessionDb.getLoginSession();
                    everLoggedIn = res.getCount() >= 0;
                    while (res.moveToNext()){
                        idUser = res.getString(1);
                        namaDepan = res.getString(2);
                        lastLogged = Double.parseDouble(res.getString(4));
                    }
                    _isTimeOut.setValue(true);
                });
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    public void saveLoginSession(){
        DataHelper mySessionDb = new DataHelper(ctx);
        boolean isRenewedSession = mySessionDb.renewSession(this.idUser, this.namaDepan);
        if(!isRenewedSession){
            Toast.makeText(ctx, "Failed to logging session", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.codepan.twinsrobo_apps.databinding.ActivityMainBinding;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StatusBarUtil.setTransparent(this);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this)).get(MainViewModel.class);

        binding.llFrameSplashLogo.startAnimation(viewModel.dropAnimation);
        binding.ivSplashLogo.startAnimation(viewModel.fadeInAnimation);
        binding.tvTextSplash.startAnimation(viewModel.upAnimation);

        viewModel.isTimeOut().observe(this, timesUp -> {
            if(timesUp){
                if(!viewModel.everLoggedIn || viewModel.lastLogged > 3){
                    startActivity(
                            new Intent(MainActivity.this, login.class)
                    );
                    overridePendingTransition(R.anim.fade_in_anim, R.anim.slide_up_out_anim);
                } else {
                    viewModel.saveLoginSession();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("id_user_login", viewModel.idUser);
                    intent.putExtra("nama_depan_login", viewModel.namaDepan);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up_in_anim, R.anim.slide_up_out_anim);
                }
                finish();
            }
        });
    }
}
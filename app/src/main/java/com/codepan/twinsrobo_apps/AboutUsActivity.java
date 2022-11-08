package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepan.twinsrobo_apps.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvAppVersion
                .setText("App Version: " + BuildConfig.VERSION_NAME);

        binding.ivBackArrowAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.llLinkWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.codepanstudio.co.id/portfolio_describ/twins-robo-media-belajar-robot"));
                startActivity(browserIntent);
            }
        });
    }
}




/////////////////////////////////////// catatan retain state fragment ///////////////////////////////

//class MyFragment extends Fragment {
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ...
//        if (savedInstanceState != null) {
//            //Restore the fragment's state here
//        }
//    }
//    ...
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //Save the fragment's state here
//    }
//
//}


//class MyActivity extends Activity {
//
//    private MyFragment
//
//    public void onCreate(Bundle savedInstanceState) {
//        ...
//        if (savedInstanceState != null) {
//            //Restore the fragment's instance
//            mMyFragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
//            ...
//        }
//        ...
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, "myFragmentName", mMyFragment);
//    }
//
//}
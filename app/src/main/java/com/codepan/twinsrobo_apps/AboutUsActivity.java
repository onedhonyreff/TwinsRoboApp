package com.codepan.twinsrobo_apps;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView ivBackArrowAU;
    private TextView tvAboutDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ivBackArrowAU = findViewById(R.id.ivBackArrowAU);
        tvAboutDescription = findViewById(R.id.tvAboutDescription);

//        tvAboutDescription.setText(Html.fromHtml("<p style = \"text-align: justify;\">" + getResources().getString(R.string.aboutText) + "</p>"));

        ivBackArrowAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
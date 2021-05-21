package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.Adapters.AdapterImageMateri;
import com.codepan.twinsrobo_apps.Adapters.AdapterModul;
import com.codepan.twinsrobo_apps.Models.DataModelImageMateri;
import com.codepan.twinsrobo_apps.Models.DataModelModul;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MateriActivity extends AppCompatActivity {

    private Dialog imageMateriZoom;

    private RatingBar rbRatingMateri;
    private RecyclerView rvListImageMateri;
    private AdapterImageMateri adDataImageMateri;
    private RecyclerView.LayoutManager lmDataImageMateri;

//    private WebView wvWebsite;
    private YouTubePlayerView ytpMateri;

    private ImageView ivBackArrowMateri;
    private TextView tvMateriContent;
    private EditText etTextFeedbackMateri;
    private Button btnSendFeedbackMateri;

    private ImageView ivImageZoom;
    private float currentSecondVideo;
    private int imageMateri;
    private float currentRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        imageMateriZoom = new Dialog(this);

        Bundle bundle = getIntent().getExtras();

//        wvWebsite = findViewById(R.id.wvWebsite);
        ytpMateri = findViewById(R.id.ytpMateri);
        rbRatingMateri = findViewById(R.id.rbRatingMateri);
        rvListImageMateri = findViewById(R.id.rvListImageMateri);
        tvMateriContent = findViewById(R.id.tvMateriContent);
        etTextFeedbackMateri = findViewById(R.id.etTextFeedbackMateri);
        btnSendFeedbackMateri = findViewById(R.id.btnSendFeedbackMateri);
        ivBackArrowMateri = findViewById(R.id.ivBackArrowMateri);

        if(savedInstanceState != null){
            currentSecondVideo = savedInstanceState.getFloat("current_second_video");
            currentRating = savedInstanceState.getFloat("current_rating");
            etTextFeedbackMateri.setText(savedInstanceState.getString("feedback_text"));

            if(savedInstanceState.getBoolean("image_is_zooming")){
                showImageMateriZoom(savedInstanceState.getInt("image_materi"));
            }
        }

//        wvWebsite.getSettings().setJavaScriptEnabled(true);
//        wvWebsite.setWebChromeClient(new WebChromeClient());
//        wvWebsite.setWebViewClient(new WebViewClient());
//        wvWebsite.loadUrl("https://youtu.be/Za9LY6Q3EuI");

        getLifecycle().addObserver(ytpMateri);

        ytpMateri.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(bundle.getString("video_materi"), currentSecondVideo);
            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float second) {
                currentSecondVideo = second;
                super.onCurrentSecond(youTubePlayer, second);
            }
        });

        btnSendFeedbackMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MateriActivity.this, "Anda memberi rating " + rbRatingMateri.getRating() + "\n Terimakasih Atas feedback yang diberikan", Toast.LENGTH_SHORT).show();
            }
        });

        ivBackArrowMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setRating(currentRating);
        showListImageMateri();

        tvMateriContent.setText(Html.fromHtml("<h1>Robotika</h1>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\" ><p>Pendidikan merupakan faktor utama dalam pembentukan pribadi setiap manusia yang juga merupakan salah satu usaha untuk mencerdaskan kehidupan bangsa. Dalam memasuki era globalisasi yang penuh dengan persaingan, peran pendidikan sangatlah penting sebagai sarana peningkatan kualitas sumber daya manusia untuk membentuk individu yang memiliki jiwa kreatif, mandiri dan profesional. Kecanggihan teknologi elektronika dan sistem otomasi diperlukan untuk mempermudah pekerjaan manusia (Wang, Wang and McLeod, 2018).</p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Robot, sebagai salah satu hasil dari perkembangan teknologi yang pada dasarnya di desain untuk memberikan kemudahan kepada manusia. Sejalan dengan kemajuan ilmu dan teknologi, maka pada saat ini telah banyak ditemukan berbagai peralatan canggih yang dapat dimanfaatkan pada pelayanan kesehatan. Peralatan yang sudah ada sekarang kebanyakan impor dari luar negeri sehingga kebanyakan orang hanyak bisa menggunakanya (Mamzer et al., 2017). </p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Maka dari itu diharapkan dengan adanya pengenalan dan pelatihan robotika pada penerapan ilmu kesehatan generasi muda diajak untuk belajar lebih tentang robot untuk kesehatan. Sehingga mampu untuk menciptakan alat-alat kesehatan sendiri yang dapat membantu, mempercepat pemeriksaan kesehatan di masyarakat.<p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><h3>Pendidikan Robotika di Indonesia</h3>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Robotika kini menjadi terobosan baru di bidang pendidikan karena pentingnya teknologi robotika untuk mengatasi masalah-masalah di masa deapan (Linert and Kopacek, 2016). Saat ini, pendidikan robotika menjadi tren pendidikan disekolah dan komunitas (Budiharto et al., 2017). </p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Beberapa sekolah di Indonesia menerapkan pendidikan robotika sebagai  mata pelajaran tersendiri. Ada juga yang menerapkan ekstrakurikuler robotika. Hal ini didorong oleh banyaknya kompetisi-kompetisi robot yang diadakan di tingkat nasional maupun internasional diberbagai jenjang, baik SD, SMP, SMA, Perguruan Tinggi, dan umum. </p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Untuk mempermudah kegiatan belajr mengajar robotika, maka diperlukan media untuk belajar robotik yaitu dengan menggunakan robot edukasi (Borisov et al., 2016). Robot edukasi ini diciptakan agar biaya yang digunakan untuk belajar maupun riset menjadi lebih murah dan terjangkau (Zamisyak et al., 2016). </p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><h3>Kemajuan Teknologi di Bidang Kesehatan</h3>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Kemajuan teknologi telah merambah diberbagai aspek kehidupan, banyak temuan-temuan yang dihasilkan dari kemajuan ini baik dalam bidang pengorganisasian rumah sakit, pengobatan maupun penelitian pengembangan dari ilmu kesehatan itu sendiri (Thijssen and Bregantini, 2017). Dalam kesehatan itu sendiri kemajuan teknologi sangat menunjang kesehatan baik klinis, dasar, msupun komunitas. </p></div>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Dengan perkembangan teknologi banyak manfaat yang diperoleh, banyak peralatan canggih yang sangat berguna untuk meningkatkan derajat kesehatan manusia (Marsh et al., 2016). Akibat dari kemajuan teknologi dibidang kesehatan akan mendapatkan kemudahan kerjanya dalam memberikan pelayanan kesehatan. </p></div>\n" +
                "<h3>Materi and Metode Pelaksanaan</h3>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Pelatihan dan workshop Pengenalan dan Pelatihan Robotika dalam rangka pengabdian masyarakat ini diberikan dan dipandu langsungoleh Dr. Tole Sutikno (dosen Teknik Elektro dan Kepala LPPI UAD), Dr. Lina Handayani (Dekan Fak. Kesehatan Masyarakat UAD), dan Tim Riset Sistem Embedded dan Robotika, Universitas Ahmad Dahlan. Pada kegiatan Pengenalan dan Pelatihan Robotika pada Bidang Kesehatan meliputi: </p></div>\n" +
                "<ul>\n" +
                "<li>Pengenalan robotika berserta komponennya</li>\n" +
                "<li>Mengakses komponen output dan pengembangannya</li>\n" +
                "<li>Mengakses komponen Input dan pengembangannya</li>\n" +
                "<li>Mengakses sensor denyut jantung dan pengembangannya</li>\n" +
                "</ul>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Metode yang dilakukan dalam kegiatan ini adalah menggunakan ceramah, praktik, serta diskusi. </p></div>\n" +
                "<h3>Instal Sofware Arduino pada PC</h3>\n" +
                "<div style=\"text-align: justify; text-justify: inter-word\"><p>Program yang digunakan untuk membuat program Arduino dinamakan Integreted Develomen Environment (ArduinoIDE). Program tersebut dapat diunduh secara gratis disitus www.arduino.cc untuk memudahkan peserta pelatihan pengabdian masyarakat ini.</p></div>"));
    }

    private void showListImageMateri() {

        rvListImageMateri.setVisibility(View.VISIBLE);

        List<DataModelImageMateri> dataImageMateri = new ArrayList<>();

        TypedArray imageMateri = getResources().obtainTypedArray(R.array.image_materi);

        for (int i = 0; i < getResources().getStringArray(R.array.image_materi).length; i++){
            DataModelImageMateri tempDataImageMateri = new DataModelImageMateri();
            tempDataImageMateri.setImageMateri(imageMateri.getResourceId(i, 0));
            tempDataImageMateri.setTitleImageMateri(getResources().getStringArray(R.array.title_image_materi)[i]);

            dataImageMateri.add(tempDataImageMateri);
        }

        adDataImageMateri = new AdapterImageMateri(MateriActivity.this, dataImageMateri);
        if(findViewById(R.id.rlLayoutMateri_potrait) != null){
            lmDataImageMateri = new LinearLayoutManager(MateriActivity.this, LinearLayoutManager.HORIZONTAL, false);
        }
        else {
            lmDataImageMateri = new LinearLayoutManager(MateriActivity.this, LinearLayoutManager.VERTICAL, false);
        }
        rvListImageMateri.setLayoutManager(lmDataImageMateri);
        rvListImageMateri.setAdapter(adDataImageMateri);
        adDataImageMateri.notifyDataSetChanged();
    }

    public void showImageMateriZoom(int image){
        imageMateri = image;
        imageMateriZoom.setContentView(R.layout.photo_zoom);
        ivImageZoom = imageMateriZoom.findViewById(R.id.ivImageZoom);

        ivImageZoom.setImageResource(image);

        imageMateriZoom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageMateriZoom.setCancelable(true);
        imageMateriZoom.show();
    }

    private void setRating(float rating){
        currentRating = rating;
        rbRatingMateri.setRating(rating);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("current_second_video", currentSecondVideo);
        outState.putBoolean("image_is_zooming", imageMateriZoom.isShowing());
        outState.putInt("image_materi", imageMateri);
        outState.putFloat("current_rating", rbRatingMateri.getRating());
        outState.putString("feedback_text", etTextFeedbackMateri.getText().toString().trim());
    }
}
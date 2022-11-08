package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.Models.ResponseModelWishlist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedEventActivity extends AppCompatActivity {

    private ImageView ivBackArrowDL, ivPamfletDetail, ivImageZoom, ivPointerToZoomPamfletDetail;
    private TextView tvDurasiTanggalDetail, tvJudulLombaDetail, tvDeskripsiDetail, tvNumberOfSharesDetail, tvNumberOfViewsDetail, tvIdEventDetail, tvTextToZoom;
    private Button btnCatSumoRcDetail, btnCatMazeSolvingDetail, btnCatTransporterDetail, btnCatDroneRaceDetail, btnCatUnderWaterDetail;
    private LinearLayout llZoomPamfletDetail;
    private RelativeLayout rlBackgroundPointer;
    private Dialog pamfletZoom;
    private FloatingActionButton fabLikeEvent, fabShareEvent;

    private Boolean isFirstOpened = true, isLikedEvent = false;
    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        Bundle bundle = getIntent().getExtras();

        ivBackArrowDL = findViewById(R.id.ivBackArrowDL);
        ivPamfletDetail = findViewById(R.id.ivPamfletDetail);
        tvDurasiTanggalDetail = findViewById(R.id.tvDurasiTanggalDetail);
        tvJudulLombaDetail = findViewById(R.id.tvJudulLombaDetail);
        tvDeskripsiDetail = findViewById(R.id.tvDeskripsiDetail);
        tvNumberOfSharesDetail = findViewById(R.id.tvNumberOfSharesDetail);
        tvNumberOfViewsDetail = findViewById(R.id.tvNumberOfViewsDetail);
        tvIdEventDetail = findViewById(R.id.tvIdEventDetail);
        btnCatSumoRcDetail = findViewById(R.id.btnCatSumoRcDetail);
        btnCatMazeSolvingDetail = findViewById(R.id.btnCatMazeSolvingDetail);
        btnCatTransporterDetail = findViewById(R.id.btnCatTransporterDetail);
        btnCatDroneRaceDetail = findViewById(R.id.btnCatDroneRaceDetail);
        btnCatUnderWaterDetail = findViewById(R.id.btnCatUnderWaterDetail);
        fabLikeEvent = findViewById(R.id.fabLikeEvent);
        fabShareEvent = findViewById(R.id.fabShareEvent);

        if(savedInstanceState != null){
            isFirstOpened = savedInstanceState.getBoolean("isFirstOpened");
            isLikedEvent = savedInstanceState.getBoolean("isLikedEvent");
        }
        else {
            retrieveLikeState(bundle.getString("id_user"), bundle.getString("id"));
        }

        id_user = bundle.getString("id_user");
        tvIdEventDetail.setText(bundle.getString("id"));
        tvDurasiTanggalDetail.setText(bundle.getString("durasi"));
        tvJudulLombaDetail.setText(bundle.getString("judul"));
        tvDeskripsiDetail.setText(Html.fromHtml(bundle.getString("deskripsi")));
        tvNumberOfSharesDetail.setText(bundle.getString("jumlahShare"));
        tvNumberOfViewsDetail.setText(bundle.getString("jumlahView"));
        if(bundle.getString("catSumoRc").equals("0")){btnCatSumoRcDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.dissable_input_field));} else {btnCatSumoRcDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));}
        if(bundle.getString("catMazeSolving").equals("0")){btnCatMazeSolvingDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.dissable_input_field));} else {btnCatMazeSolvingDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));}
        if(bundle.getString("catTransporter").equals("0")){btnCatTransporterDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.dissable_input_field));} else {btnCatTransporterDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));}
        if(bundle.getString("catDroneRace").equals("0")){btnCatDroneRaceDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.dissable_input_field));} else {btnCatDroneRaceDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));}
        if(bundle.getString("catUnderWater").equals("0")){btnCatUnderWaterDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.dissable_input_field));} else {btnCatUnderWaterDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_selected));}

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        requestOptions.placeholder(R.drawable.anim_loading);
        requestOptions.error(R.drawable.broken_image);

        Glide.with(this)
                .load(bundle.getString("linkPamflet"))
                .apply(requestOptions)
                .into(ivPamfletDetail);

        ivBackArrowDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabLikeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ResponseModelWishlist> requestWishlist = ardData.ardWishlistInfoLomba(bundle.getString("id_user"), bundle.getString("id"), "update");

                requestWishlist.enqueue(new Callback<ResponseModelWishlist>() {
                    @Override
                    public void onResponse(Call<ResponseModelWishlist> call, Response<ResponseModelWishlist> response) {
                        if(response.body().getKodeWishlist() == 100){
                            Toast.makeText(SelectedEventActivity.this, "Ditambahkan ke wishlist", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.body().getKodeWishlist() == 101){
                            Toast.makeText(SelectedEventActivity.this, "Dihapus dari wishlist", Toast.LENGTH_SHORT).show();
                        }
                        implementResponseCodeWishlist(response.body().getKodeWishlist());
                    }

                    @Override
                    public void onFailure(Call<ResponseModelWishlist> call, Throwable t) {
                        Toast.makeText(SelectedEventActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fabShareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semuaPesan = "https://www.codepan.twinsrobo.com/info_lomba/" +
                        bundle.getString("id") +
                        "\n\"" + bundle.getString("judul") + "\"\n" +
                        "Berbagi tautan bermanfaat sesama teman dengan TwinsRobo App.";
                Intent kirim = new Intent(Intent.ACTION_SEND);
                kirim.setType("text/plain");
                kirim.putExtra(Intent.EXTRA_TEXT, semuaPesan);
                startActivity(kirim);
            }
        });

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            llZoomPamfletDetail = findViewById(R.id.llZoomPamfletDetail);
            rlBackgroundPointer = findViewById(R.id.rlBackgroundPointer);
            ivPointerToZoomPamfletDetail = findViewById(R.id.ivPointerToZoomPamfletDetail);
            tvTextToZoom = findViewById(R.id.tvTextToZoom);

            rlBackgroundPointer.setVisibility(View.GONE);
            ivPointerToZoomPamfletDetail.setVisibility(View.GONE);
            tvTextToZoom.setVisibility(View.GONE);

            if(isFirstOpened){

                rlBackgroundPointer.setVisibility(View.VISIBLE);
                ivPointerToZoomPamfletDetail.setVisibility(View.VISIBLE);
                tvTextToZoom.setVisibility(View.VISIBLE);

                RequestOptions requestOpt = new RequestOptions();
                requestOpt.skipMemoryCache(true);
                requestOpt.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOpt.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                Glide.with(this)
                        .load(R.drawable.pointing_anim2)
                        .apply(requestOpt)
                        .into(ivPointerToZoomPamfletDetail);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rlBackgroundPointer.setVisibility(View.GONE);
                        ivPointerToZoomPamfletDetail.setVisibility(View.GONE);
                        tvTextToZoom.setVisibility(View.GONE);
                    }
                }, 2100L);
            }

            isFirstOpened = false;

            llZoomPamfletDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPamfletZoomDetail(bundle.getString("linkPamflet"));
                }
            });
        }
    }

    private void showPamfletZoomDetail(String linkPamflet){
        pamfletZoom = new Dialog(this);
        pamfletZoom.setContentView(R.layout.photo_zoom);
        ivImageZoom = pamfletZoom.findViewById(R.id.ivImageZoom);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        requestOptions.error(R.drawable.broken_image);

        Glide.with(this)
                .load(linkPamflet)
                .apply(requestOptions)
                .into(ivImageZoom);

        pamfletZoom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pamfletZoom.setCancelable(true);
        pamfletZoom.show();
    }

    private void retrieveLikeState(String idUser, String idEvent){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModelWishlist> requestLikeStateWishlist = ardData.ardWishlistInfoLomba(idUser, idEvent, "cek");

        requestLikeStateWishlist.enqueue(new Callback<ResponseModelWishlist>() {
            @Override
            public void onResponse(Call<ResponseModelWishlist> call, Response<ResponseModelWishlist> response) {
                implementResponseCodeWishlist(response.body().getKodeWishlist());
            }

            @Override
            public void onFailure(Call<ResponseModelWishlist> call, Throwable t) {
                Toast.makeText(SelectedEventActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void implementResponseCodeWishlist(int code){
        switch (code){
            case 100 : isLikedEvent = true;
                break;
            case 101 : isLikedEvent = false;
                break;
            case 300 : isLikedEvent = true;
                break;
            case 301 : isLikedEvent = false;
                break;
            default: Toast.makeText(SelectedEventActivity.this, "Something Wrong | Kode = " + code, Toast.LENGTH_SHORT).show();
        }
        showEventLikeState();
    }

    private void showEventLikeState(){
        if(isLikedEvent){
            fabLikeEvent.setImageDrawable(ContextCompat.getDrawable(SelectedEventActivity.this, R.drawable.ic_like));
        }
        else {
            fabLikeEvent.setImageDrawable(ContextCompat.getDrawable(SelectedEventActivity.this, R.drawable.ic_disslike));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showEventLikeState();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isFirstOpened", isFirstOpened);
        outState.putBoolean("isLikedEvent", isLikedEvent);
    }
}
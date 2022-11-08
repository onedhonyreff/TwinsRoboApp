package com.codepan.twinsrobo_apps.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codepan.twinsrobo_apps.APIs.APIRequestData;
import com.codepan.twinsrobo_apps.APIs.RetroServer;
import com.codepan.twinsrobo_apps.HomeActivity;
import com.codepan.twinsrobo_apps.Models.DataModelUser;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.OtherClass.DataHelper;
import com.codepan.twinsrobo_apps.OtherClass.ListAvatar;
import com.codepan.twinsrobo_apps.OtherInterfaces.FragActCommunication;
import com.codepan.twinsrobo_apps.R;
import com.codepan.twinsrobo_apps.ScannerActivity;
import com.codepan.twinsrobo_apps.login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }

    private View thisFragment;

    private FloatingActionButton fabEditProfile;
    private LinearLayout llSuccessLoadProfil, llFailLoadProfil, llLogOut;
    private ImageView ivFotoProfil, ivIconGenderProfil, ivFailImageProfil, ivVipBadge, ivQrCodeUser;
    private TextView tvNamaProfil, tvNamaSekolahProfil, tvUsernameProfil, tvEmailProfil, tvAgeProfil, tvGenderProfil;
    private ProgressBar pbProfil;
    private FragActCommunication FAC;

    private String tglLahir = "",
            namaDepan = "",
            namaBelakang = "",
            namaSekolah = "",
            gender = "",
            linkFoto = "",
            umur = "",
            vipLimit ="";

    private boolean lookForUpdate = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bdl = getArguments();
        lookForUpdate = bdl.getBoolean("pascaUpdateProfile");
        if(savedInstanceState != null){
            lookForUpdate = savedInstanceState.getBoolean("lookForUpdate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisFragment = inflater.inflate(R.layout.fragment_user_profile, container, false);

        llSuccessLoadProfil = thisFragment.findViewById(R.id.llSuccessLoadProfil);
        llFailLoadProfil = thisFragment.findViewById(R.id.llFailLoadProfil);
        fabEditProfile = thisFragment.findViewById(R.id.fabEditProfile);
        llLogOut = thisFragment.findViewById(R.id.llLogOut);
        ivFotoProfil = thisFragment.findViewById(R.id.ivFotoProfil);
        ivVipBadge = thisFragment.findViewById(R.id.ivVipBadge);
        ivIconGenderProfil = thisFragment.findViewById(R.id.ivIconGenderProfil);
        ivFailImageProfil = thisFragment.findViewById(R.id.ivFailImageProfil);
        ivQrCodeUser = thisFragment.findViewById(R.id.ivQrCodeUser);
        tvNamaProfil = thisFragment.findViewById(R.id.tvNamaProfil);
        tvNamaSekolahProfil = thisFragment.findViewById(R.id.tvNamaSekolahProfil);
        tvUsernameProfil = thisFragment.findViewById(R.id.tvUsernameProfil);
        tvEmailProfil = thisFragment.findViewById(R.id.tvEmailProfil);
        tvAgeProfil = thisFragment.findViewById(R.id.tvAgeProfil);
        tvGenderProfil = thisFragment.findViewById(R.id.tvGenderProfil);
        pbProfil = thisFragment.findViewById(R.id.pbProfileFragment);

        ivQrCodeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 5398);
                }
                else {
                    startActivity(new Intent(getActivity(), ScannerActivity.class));
                }
            }
        });

        Glide.with(thisFragment)
                .load(R.drawable.fail_cloud)
                .into(ivFailImageProfil);

        Bundle bundle = getArguments();
        if(!bundle.getString("username_user").equals("")){
            pbProfil.setVisibility(View.VISIBLE);
            tglLahir = bundle.getString("tgl_lahir_user");
            namaDepan = bundle.getString("nama_depan_user");
            namaBelakang = bundle.getString("nama_belakang_user");
            namaSekolah = bundle.getString("nama_sekolah_user");
            gender = bundle.getString("jk_user");
            linkFoto = bundle.getString("link_foto_user");
            vipLimit = bundle.getString("vip_limit_user");

            if(lookForUpdate){
                Glide.get(thisFragment.getContext()).clearMemory();
//                Toast.makeText(thisFragment.getContext(), "Clearing cache", Toast.LENGTH_SHORT).show();
                lookForUpdate = false;
            }

            showingData(bundle.getString("username_user"),
                    bundle.getString("nama_depan_user"),
                    bundle.getString("nama_belakang_user"),
                    bundle.getString("nama_sekolah_user"),
                    bundle.getString("email_user"),
                    bundle.getString("umur_user"),
                    bundle.getString("jk_user"),
                    bundle.getString("link_foto_user"),
                    bundle.getString("vip_limit_user"));

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(bundle.getString("email_user"), BarcodeFormat.QR_CODE,1000, 1000);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap qrCodeBmp = encoder.createBitmap(matrix);
                ivQrCodeUser.setImageBitmap(qrCodeBmp);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        }
        else {
            pbProfil.setVisibility(View.VISIBLE);
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModelUser> requestUserData = ardData.ardRetrieveUser(bundle.getString("id_user"));
            requestUserData.enqueue(new Callback<ResponseModelUser>() {
                @Override
                public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                    if(response.body().getKodeUser() == 1){
                        List<DataModelUser> listData = response.body().getDataUser();
                        DataModelUser dmu = listData.get(0);

                        ivIconGenderProfil.setVisibility(View.VISIBLE);

                        FAC.sendData(dmu.getUsernameUser(),
                                dmu.getNamaDepanUser(),
                                dmu.getNamaBelakangUser(),
                                dmu.getNamaSekolahUser(),
                                dmu.getEmailUser(),
                                dmu.getUmurUser(),
                                dmu.getTanggalLahirUser(),
                                dmu.getJenisKelaminUser(),
                                dmu.getFotoProfilUser(),
                                dmu.getVipLimit(),
                                false,
                                lookForUpdate);
                    }
                    else {
                        ivIconGenderProfil.setVisibility(View.GONE);
                    }
                    pbProfil.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                    pbProfil.setVisibility(View.GONE);
                    llSuccessLoadProfil.setVisibility(View.GONE);
                    llFailLoadProfil.setVisibility(View.VISIBLE);
                    pbProfil.setVisibility(View.GONE);
                }
            });
        }

        fabEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!tvUsernameProfil.getText().toString().equals("")){
                    FAC.sendData(tvUsernameProfil.getText().toString(),
                            namaDepan,
                            namaBelakang,
                            namaSekolah,
                            tvEmailProfil.getText().toString(),
                            umur,
                            tglLahir,
                            gender,
                            linkFoto,
                            vipLimit,
                            true,
                            lookForUpdate);
                }
            }
        });

        llLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLogOut();
            }
        });

        return thisFragment;
    }

    private void showingData(String username,
                             String namaDepan,
                             String namaBelakang,
                             String namaSekolah,
                             String email,
                             String umur,
                             String jk,
                             String linkFotoArgs,
                             String vipLimitArgs){
        tvUsernameProfil.setText(username);
        tvNamaProfil.setText(namaDepan + " " + namaBelakang);
        tvNamaSekolahProfil.setText("Sekolah di " + namaSekolah);
        tvEmailProfil.setText(email);
        tvAgeProfil.setText(umur + " tahun");
        if(jk.equals("Male")){
            tvGenderProfil.setText("Pria");
            Glide.with(thisFragment).load(R.drawable.ic_male).into(ivIconGenderProfil);
        }
        else {
            tvGenderProfil.setText("Wanita");
            Glide.with(thisFragment).load(R.drawable.ic_female).into(ivIconGenderProfil);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.skipMemoryCache(false);
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.placeholder(R.drawable.anim_loading);
                requestOptions.circleCrop();
                requestOptions.error(R.drawable.broken_image);

                if(linkFotoArgs.length() > 15){
                    if(!linkFotoArgs.equals("/twinsrobo/image/image-user/default/none.png")){
                        Glide.with(thisFragment)
                                .load(linkFotoArgs)
                                .apply(requestOptions)
                                .into(ivFotoProfil);
                    }
                    else {
                        Glide.with(thisFragment)
                                .load(R.drawable.ic_account_circle2)
                                .apply(requestOptions)
                                .into(ivFotoProfil);
                    }
                }
                else {
                    Glide.with(thisFragment)
                            .load(new ListAvatar().getAvatar(jk, Integer.parseInt(linkFotoArgs)))
                            .apply(requestOptions)
                            .into(ivFotoProfil);
                }
            }
        }, 500L);

        if(Integer.parseInt(vipLimitArgs) >= 0){
            ivVipBadge.setVisibility(View.VISIBLE);
        }
        else {
            ivVipBadge.setVisibility(View.GONE);
        }

        pbProfil.setVisibility(View.GONE);
    }

    private void showDialogLogOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title dialog
        alertDialogBuilder.setTitle("Want to log out?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("The login session will be deleted.")
                .setIcon(R.drawable.logo_apps_twins_robo)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        DataHelper mySessionDb = new DataHelper(getContext());
                        mySessionDb.deleteSession();
                        startActivity(new Intent(getActivity(), login.class));
                        getActivity().overridePendingTransition(R.anim.slide_up_in_anim, R.anim.slide_up_out_anim);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FAC = (FragActCommunication) context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("lookForUpdate", lookForUpdate);
    }
}
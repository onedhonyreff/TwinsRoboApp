package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.Fragments.HomeFragment;
import com.codepan.twinsrobo_apps.Fragments.MemberFragment;
import com.codepan.twinsrobo_apps.Fragments.UserProfileFragment;
import com.codepan.twinsrobo_apps.OtherClass.ListAvatar;
import com.codepan.twinsrobo_apps.OtherInterfaces.FragActCommunication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements FragActCommunication{

    private Dialog popUpMember;
    private BottomNavigationView bottomNav;
    private ProgressBar pbHome;

    private String selectedMenu = "homeX", idUserLogin, namaDepanLogin, idMemberPreview, namaMemberPreview, genderMemberPreview, linkFotoMemberPreview;
    private boolean alreadyOpened = false, pascaUpdateProfile = false, isMemberPreviewed = false;

    private String usernameUser ="",
            namaDepanUser ="",
            namaBelakangUser ="",
            namaSekolahUser ="",
            emailUser ="",
            umurUser ="",
            tglLahirUser ="",
            jkUser ="",
            linkFotoUser ="",
            vipLimitUser ="";

    // Untuk menu fragment yang tercommit akan otomatis terjaga walau orientasi berubah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.flMain, new UserProfileFragment())
//                .commit();

        Bundle extras = getIntent().getExtras();
        idUserLogin = extras.getString("id_user_login");
        namaDepanLogin = extras.getString("nama_depan_login");

        Uri uri = getIntent().getData();
        if(uri != null){
            String path = uri.toString();
            String[] partPath = path.split("/");
            idUserLogin = partPath[4];
            namaDepanLogin = partPath[5];
        }

        if (savedInstanceState != null) {
            selectedMenu = savedInstanceState.getString("menu");
            alreadyOpened = savedInstanceState.getBoolean("alreadyOpened");
            isMemberPreviewed = savedInstanceState.getBoolean("isMemberPreviewed");

            usernameUser = savedInstanceState.getString("username_user");
            namaDepanUser = savedInstanceState.getString("nama_depan_user");
            namaBelakangUser = savedInstanceState.getString("nama_belakang_user");
            namaSekolahUser = savedInstanceState.getString("nama_sekolah_user");
            emailUser = savedInstanceState.getString("email_user");
            umurUser = savedInstanceState.getString("umur_user");
            tglLahirUser = savedInstanceState.getString("tgl_lahir");
            jkUser = savedInstanceState.getString("jk_user");
            linkFotoUser = savedInstanceState.getString("link_foto_user");
            vipLimitUser = savedInstanceState.getString("vip_limit_user");
            idMemberPreview = savedInstanceState.getString("id_member_preview");
            namaMemberPreview = savedInstanceState.getString("nama_member_preview");
            linkFotoMemberPreview = savedInstanceState.getString("link_foto_member_preview");
            genderMemberPreview = savedInstanceState.getString("gender_member_preview");
        }

        pbHome = findViewById(R.id.pbHome);
        bottomNav = findViewById(R.id.btmNavMain);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        openMainMenuFragment();

        if(isMemberPreviewed){
            showMemberPopupPreview(idMemberPreview, namaMemberPreview, genderMemberPreview, linkFotoMemberPreview);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Bundle bundle = new Bundle();
                    switch (item.getItemId()) {
                        case R.id.navBtnHome:
                            if(!selectedMenu.equals("home")){
                                selectedFragment = new HomeFragment();
                                bundle.putString("id_user", idUserLogin);
                                bundle.putString("nama_depan_home", namaDepanLogin);
                                selectedMenu = "home";
                                alreadyOpened = false; // must include each case to open commit access
                            }
                            break;
                        case R.id.navBtnAccount:
                            if(!selectedMenu.equals("account")){ // tidak akan membuka menu apabila menu yang tebuka sama
                                selectedFragment = new UserProfileFragment();
                                bundle.putString("id_user", idUserLogin);
                                bundle.putString("username_user", usernameUser);
                                bundle.putString("nama_depan_user", namaDepanUser);
                                bundle.putString("nama_belakang_user", namaBelakangUser);
                                bundle.putString("nama_sekolah_user", namaSekolahUser);
                                bundle.putString("email_user", emailUser);
                                bundle.putString("umur_user", umurUser);
                                bundle.putString("tgl_lahir_user", tglLahirUser);
                                bundle.putString("jk_user", jkUser);
                                bundle.putString("link_foto_user", linkFotoUser);
                                bundle.putString("vip_limit_user", vipLimitUser);
                                bundle.putBoolean("pascaUpdateProfile", pascaUpdateProfile);
                                selectedMenu = "account"; // just to avoid opening the same menu
                                alreadyOpened = false;
                            }
                            break;
                        default:
                            if(!selectedMenu.equals("member")){
                                selectedFragment = new MemberFragment();
                                selectedMenu = "member";
                                alreadyOpened = false;
                            }
                            break;
                    }

                    if(!alreadyOpened){
                        selectedFragment.setArguments(bundle);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.fragment_in_anim, R.anim.fragment_out_anim)
                                .replace(R.id.flMain, selectedFragment)
                                .commit();
                        alreadyOpened = true;
                    }

                    return true;
                }
            };

    private void openMainMenuFragment() {  // only for the initial opening
        switch (selectedMenu) {
            case "homeX":
                bottomNav.setSelectedItemId(R.id.navBtnHome);
                break;
            case "accountX":
                bottomNav.setSelectedItemId(R.id.navBtnAccount);
                break;
        }
    }

    public void showMemberPopupPreview(String idMemberArgs, String namaMemberArgs, String genderMemberArgs, String linkFotoMemberArgs){
        isMemberPreviewed = true;

        idMemberPreview = idMemberArgs;
        namaMemberPreview = namaMemberArgs;
        genderMemberPreview = genderMemberArgs;
        linkFotoMemberPreview = linkFotoMemberArgs;

        TextView tvMemberNamePreview;
        ImageView ivMemberFotoPreview;

        popUpMember = new Dialog(this);
        popUpMember.setContentView(R.layout.member_popup_preview);

        tvMemberNamePreview = popUpMember.findViewById(R.id.tvMemberNamePreview);
        ivMemberFotoPreview = popUpMember.findViewById(R.id.ivMemberFotoPreview);

        tvMemberNamePreview.setText(namaMemberArgs);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.anim_loading);
        requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        requestOptions.error(R.drawable.broken_image);

        Glide.with(this)
                .load(linkFotoMemberArgs)
                .apply(requestOptions)
                .into(ivMemberFotoPreview);

        if(linkFotoMemberArgs.length() > 15){
            if(!linkFotoMemberArgs.equals("/twinsrobo/image/image-user/default/none.png")){
                Glide.with(this)
                        .load(linkFotoMemberArgs)
                        .apply(requestOptions)
                        .into(ivMemberFotoPreview);
            }
            else {
                Glide.with(this)
                        .load(R.drawable.ic_account_circle2)
                        .apply(requestOptions)
                        .into(ivMemberFotoPreview);
            }
        }
        else {
            Glide.with(this)
                    .load(new ListAvatar().getAvatar(genderMemberArgs, Integer.parseInt(linkFotoMemberArgs)))
                    .apply(requestOptions)
                    .into(ivMemberFotoPreview);
        }

        popUpMember.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpMember.setCancelable(true);
        popUpMember.show();

        popUpMember.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isMemberPreviewed = false;
            }
        });
    }

    private void isProcess(boolean process){
        if(process){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            pbHome.setVisibility(View.VISIBLE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            pbHome.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 387) {
            if (resultCode == RESULT_OK) {
                usernameUser = "";
                switch (selectedMenu) {
                    case "home":
                        selectedMenu = "homeX";
                        bottomNav.setSelectedItemId(R.id.navBtnHome);
                        break;
                    case "account":
                        pascaUpdateProfile = true;
                        selectedMenu = "accountX"; // ini hanya pengalihan agar dapat memuat ulang menu fragment yang sama
                        bottomNav.setSelectedItemId(R.id.navBtnAccount);
                        break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // camera permission when first take qr code scanner
        if (requestCode == 5398) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(HomeActivity.this, ScannerActivity.class));
            } else {
                Toast.makeText(HomeActivity.this, "Aplikasi tidak diizinkan untuk mengakses kamera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("menu", selectedMenu); // saves the status of the last menu open
        outState.putBoolean("alreadyOpened", alreadyOpened);
        outState.putBoolean("isMemberPreviewed", isMemberPreviewed);

        outState.putString("username_user", usernameUser);
        outState.putString("nama_depan_user", namaDepanUser);
        outState.putString("nama_belakang_user", namaBelakangUser);
        outState.putString("nama_sekolah_user", namaSekolahUser);
        outState.putString("email_user", emailUser);
        outState.putString("umur_user", umurUser);
        outState.putString("tgl_lahir", tglLahirUser);
        outState.putString("jk_user", jkUser);
        outState.putString("link_foto_user", linkFotoUser);
        outState.putString("vip_limit_user", vipLimitUser);
        outState.putString("id_member_preview", idMemberPreview);
        outState.putString("nama_member_preview", namaMemberPreview);
        outState.putString("link_foto_member_preview", linkFotoMemberPreview);
        outState.putString("gender_member_preview", genderMemberPreview);
    }

    @Override
    public void onBackPressed() {
        if (selectedMenu != "home") {
            bottomNav.setSelectedItemId(R.id.navBtnHome);
            selectedMenu = "home";
            return;
        } else {
            this.finish();
        }
    }

    @Override
    public void sendData(String username, String namaDepan, String namaBelakang, String namaSekolah, String email, String umur, String tglLahir, String jenisKelamin, String linkFoto, String vipLimit, boolean startAnActivity, boolean pascaUpdate){
        usernameUser = username;
        namaDepanUser = namaDepan;
        namaBelakangUser = namaBelakang;
        namaSekolahUser = namaSekolah;
        emailUser = email;
        umurUser = umur;
        tglLahirUser = tglLahir;
        jkUser = jenisKelamin;
        linkFotoUser = linkFoto;
        vipLimitUser = vipLimit;

        if(startAnActivity){
            Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
            intent.putExtra("id", idUserLogin);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            intent.putExtra("namaDepan", namaDepan);
            intent.putExtra("namaBelakang", namaBelakang);
            intent.putExtra("gender", jenisKelamin);
            intent.putExtra("tglLahir", tglLahir);
            intent.putExtra("namaSekolah", namaSekolah);
            intent.putExtra("linkFoto", linkFoto);
            startActivityForResult(intent, 387);
        }
        else {
            selectedMenu = "accountX";
            openMainMenuFragment();
        }

        pascaUpdateProfile = pascaUpdate;
    }
}

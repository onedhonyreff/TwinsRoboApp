package com.codepan.twinsrobo_apps;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.Adapters.AdapterModul;
import com.codepan.twinsrobo_apps.Models.DataModelModul;

import java.util.ArrayList;
import java.util.List;

public class LearnRobotActivity extends AppCompatActivity {

    private RecyclerView rvListModul;
    private AdapterModul adDataModul;
    private RecyclerView.LayoutManager lmDataModul;

    private List<DataModelModul> dataModul = new ArrayList<>();

    private Dialog popUpPembelianModul;

    private ImageView ivBackArrowLR, ivButtonSearchLearnCategory;
    private EditText etSearchLearnCategory;

    private int imageToBuy = 0;
    private  String titleToBuy, priceToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_robot);

        popUpPembelianModul = new Dialog(this);

        ivBackArrowLR = findViewById(R.id.ivBackArrowLR);
        etSearchLearnCategory = findViewById(R.id.etSearchLearnCategory);
        ivButtonSearchLearnCategory = findViewById(R.id.ivButtonSearchLearnCategory);
        rvListModul = findViewById(R.id.rvListModul);

        if (savedInstanceState != null) {
            etSearchLearnCategory.setText(savedInstanceState.getString("etSearchLearnCategory"));

            if(savedInstanceState.getBoolean("isShowingPembelianModul")){
                showBuyModulDialog(savedInstanceState.getInt("imageToBuy"),
                        savedInstanceState.getString("titleToBuy"),
                        savedInstanceState.getString("priceToBuy"));
            }
        }

        ivBackArrowLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivButtonSearchLearnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSearchLearnCategory.getText().toString().equals("")){
                    filterModul(etSearchLearnCategory.getText().toString().trim());
                }
            }
        });

        etSearchLearnCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")){
                    showListModul();
                }
            }
        });
        showListModul();
    }

    private void showListModul() {

        dataModul = new ArrayList<>();

        TypedArray imageModul = getResources().obtainTypedArray(R.array.image_modul);

        for (int i = 0; i < getResources().getStringArray(R.array.title_modul).length; i++){
            DataModelModul tempDataModul = new DataModelModul();
            tempDataModul.setModul_image(imageModul.getResourceId(i, 0));
            tempDataModul.setModul_title(getResources().getStringArray(R.array.title_modul)[i]);
            tempDataModul.setModul_price(getResources().getStringArray(R.array.harga_modul)[i]);
            tempDataModul.setModul_status(getResources().getStringArray(R.array.status_modul)[i]);

            dataModul.add(tempDataModul);
        }

        adDataModul = new AdapterModul(LearnRobotActivity.this, dataModul);
        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            lmDataModul = new GridLayoutManager(LearnRobotActivity.this, 2);
        }
        else {
            lmDataModul = new GridLayoutManager(LearnRobotActivity.this, 3);
        }
        rvListModul.setLayoutManager(lmDataModul);
        rvListModul.setAdapter(adDataModul);
        adDataModul.notifyDataSetChanged();
    }

    public void openModul(int modulId){
        Intent intentModul = new Intent(LearnRobotActivity.this, LearningListActivity.class);
        intentModul.putExtra("id_modul", modulId);
        startActivity(intentModul);
    }

    public void showBuyModulDialog(int image, String title, String price){

        imageToBuy = image;
        titleToBuy = title;
        priceToBuy = price;

        popUpPembelianModul.setContentView(R.layout.buy_modul_field);
        ImageView ivClosePopUpPembelian = (ImageView) popUpPembelianModul.findViewById(R.id.ivClosePopUpPembelian);
        ImageView ivModulItemPembelian = (ImageView) popUpPembelianModul.findViewById(R.id.ivModulItemPembelian);
        TextView tvTitleModulPembelian = (TextView) popUpPembelianModul.findViewById(R.id.tvTitleModulPembelian);
        TextView tvPopupHargaPembelian = (TextView) popUpPembelianModul.findViewById(R.id.tvPopupHargaPembelian);
        Button btnPopupBeliPembelian = (Button) popUpPembelianModul.findViewById(R.id.btnPopupBeliPembelian);

        ivModulItemPembelian.setImageResource(image);
        tvTitleModulPembelian.setText(title);
        tvPopupHargaPembelian.setText("Rp. " + price + " ,-");

        ivClosePopUpPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpPembelianModul.dismiss();
            }
        });

        btnPopupBeliPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LearnRobotActivity.this, "Anda membeli Modul " + title + " seharga Rp. " + price + " ,-", Toast.LENGTH_SHORT).show();
                popUpPembelianModul.dismiss();
            }
        });

        popUpPembelianModul.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpPembelianModul.setCancelable(false);
        popUpPembelianModul.show();
    }

    private void filterModul(String text){
        ArrayList<DataModelModul> filteredModul = new ArrayList<>();
        for (DataModelModul item : dataModul){
            if (item.getModul_title().toLowerCase().contains(text.toLowerCase())){
                filteredModul.add(item);
            }
        }
        adDataModul.filterList(filteredModul);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("etSearchLearnCategory", String.valueOf(etSearchLearnCategory));
        outState.putString("titleToBuy", titleToBuy);
        outState.putString("priceToBuy", priceToBuy);
        outState.putInt("imageToBuy", imageToBuy);
        outState.putBoolean("isShowingPembelianModul", popUpPembelianModul.isShowing());
    }

    @Override
    protected void onResume() {
        super.onResume();

        filterModul(etSearchLearnCategory.getText().toString().trim());
    }
}
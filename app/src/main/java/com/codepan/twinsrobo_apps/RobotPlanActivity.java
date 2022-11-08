package com.codepan.twinsrobo_apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RobotPlanActivity extends AppCompatActivity {

    private TextView tvPlanSelector, tvListLineNumberProgramPreview, tvListProgramPreview;
    private ImageView ivBackArrowRP;
    private LinearLayout llDecrementPlan, llIncrementPlan;
    private Button btnUsePlan;

    private int planNumber = 1;

    private String[] myProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_plan);

        if(savedInstanceState != null){
            planNumber = savedInstanceState.getInt("plan_number");
        }

        tvPlanSelector = findViewById(R.id.tvPlanSelector);
        ivBackArrowRP = findViewById(R.id.ivBackArrowRP);
        llDecrementPlan = findViewById(R.id.llDecrementPlan);
        llIncrementPlan = findViewById(R.id.llIncrementPlan);
        tvListLineNumberProgramPreview = findViewById(R.id.tvListLineNumberProgramPreview);
        tvListProgramPreview = findViewById(R.id.tvListProgramPreview);
        btnUsePlan = findViewById(R.id.btnUsePlan);

        myProgram = getResources().getStringArray(R.array.list_programs);
        showProgramPreview();

        ivBackArrowRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llDecrementPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (planNumber <= 1) {
                    return;
                }
                changePlaneNumber(false);
            }
        });

        llIncrementPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (planNumber >= myProgram.length){
                    return;
                }
                changePlaneNumber(true);
            }
        });

        btnUsePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("number_text", tvListLineNumberProgramPreview.getText().toString());
                returnIntent.putExtra("program_text", tvListProgramPreview.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

//        if(findViewById(R.id.llRoboPlan_landscape) != null){
        ImageView ivAnimRoboPlan = findViewById(R.id.ivAnimRoboPlan);
        Glide.with(RobotPlanActivity.this)
                // LOAD URL DARI LOKAL DRAWABLE
                .load(R.drawable.giphy)
                .into(ivAnimRoboPlan);
//        }
    }

    private void changePlaneNumber(boolean increment) {
        if (increment) {
            planNumber++;
        } else {
            planNumber--;
        }
        showProgramPreview();
    }

    private void showProgramPreview(){
        tvPlanSelector.setText("Plan " + planNumber);
        tvListProgramPreview.setText(myProgram[planNumber-1]);

        tvListLineNumberProgramPreview.setText("");
        for (int i = 0; i < myProgram[planNumber-1].split("\n").length; i++){
            if (i != 0 ){
                tvListLineNumberProgramPreview.setText(tvListLineNumberProgramPreview.getText().toString() + "\n");
            }
            tvListLineNumberProgramPreview.setText(tvListLineNumberProgramPreview.getText().toString() + (i + 1) + ".");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("plan_number", planNumber);
    }
}
package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.InfoLombaRobotActivity;
import com.codepan.twinsrobo_apps.Models.DataModelInfoLomba;
import com.codepan.twinsrobo_apps.OtherClass.DateRange;
import com.codepan.twinsrobo_apps.R;

import java.util.List;

public class AdapterInfoLomba extends RecyclerView.Adapter<AdapterInfoLomba.HolderData> {

    private Context ctx;
    private List<DataModelInfoLomba> listDataInfoLomba;
    private String linkPhoto;

    public AdapterInfoLomba(Context ctx, List<DataModelInfoLomba> listDataInfoLomba){
        this.ctx = ctx;
        this.listDataInfoLomba = listDataInfoLomba;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_lomba_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelInfoLomba dm = listDataInfoLomba.get(position);

        linkPhoto = dm.getPamflet();
        holder.tv_id.setText(String.valueOf(dm.getId()));
        holder.tvJudulLomba.setText(dm.getJudul_lomba());
        holder.tvDeskripsi.setText(Html.fromHtml(dm.getDeskripsi()));
        holder.tvDurasiTanggal.setText(new DateRange().getStringRangeDate(dm.getStart_at(), dm.getEnd_at()));
        holder.tvNumberOfShares.setText(dm.getNumber_of_shares());
        holder.tvNumberOfViews.setText(dm.getNumber_of_views());
        if(dm.getCatSumoRc().equals("0")){holder.btnCatSumoRc.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dissable_input_field));} else{holder.btnCatSumoRc.setBackground(ContextCompat.getDrawable(ctx, R.drawable.blue_selected));}
        if(dm.getCatTransporter().equals("0")){holder.btnCatTransporter.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dissable_input_field));} else{holder.btnCatTransporter.setBackground(ContextCompat.getDrawable(ctx, R.drawable.blue_selected));}
        if(dm.getCatDroneRace().equals("0")){holder.btnCatDroneRace.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dissable_input_field));} else{holder.btnCatDroneRace.setBackground(ContextCompat.getDrawable(ctx, R.drawable.blue_selected));}
        if(dm.getCatUnderWater().equals("0")){holder.btnCatUnderWater.setBackground(ContextCompat.getDrawable(ctx, R.drawable.dissable_input_field));} else{holder.btnCatUnderWater.setBackground(ContextCompat.getDrawable(ctx, R.drawable.blue_selected));}

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        requestOptions.placeholder(R.drawable.anim_loading);
        requestOptions.error(R.drawable.broken_image);

        Glide.with(ctx)
                .load(dm.getPamflet())
                .apply(requestOptions)
                .into(holder.ivPamflet);

        holder.ivPamflet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InfoLombaRobotActivity) ctx).showPamfletZoom(dm.getPamflet());
            }
        });

        holder.cvInfoLombaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InfoLombaRobotActivity) ctx).showDetailEvent(dm.getId(),
                                                                dm.getJudul_lomba(),
                                                                dm.getDeskripsi(),
                                                                new DateRange().getStringRangeDate(dm.getStart_at(), dm.getEnd_at()),
                                                                dm.getNumber_of_shares(),
                                                                dm.getNumber_of_views(),
                                                                dm.getCatSumoRc(),
                                                                dm.getCatMazeSolving(),
                                                                dm.getCatTransporter(),
                                                                dm.getCatDroneRace(),
                                                                dm.getCatUnderWater(),
                                                                dm.getPamflet());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataInfoLomba.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView tv_id, tvJudulLomba, tvDeskripsi, tvDurasiTanggal, tvNumberOfShares, tvNumberOfViews;
        ImageView ivPamflet;
        Button btnCatSumoRc, btnCatMazeSolving, btnCatTransporter, btnCatDroneRace, btnCatUnderWater;
        CardView cvInfoLombaItem;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            cvInfoLombaItem = itemView.findViewById(R.id.cvInfoLombaItem);
            tv_id = itemView.findViewById(R.id.tv_id);
            tvJudulLomba = itemView.findViewById(R.id.tvJudulLomba);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvDurasiTanggal = itemView.findViewById(R.id.tvDurasiTanggal);
            tvNumberOfShares = itemView.findViewById(R.id.tvNumberOfShares);
            tvNumberOfViews = itemView.findViewById(R.id.tvNumberOfViews);
            ivPamflet = itemView.findViewById(R.id.ivPamflet);
            btnCatSumoRc = itemView.findViewById(R.id.btnCatSumoRc);
            btnCatDroneRace = itemView.findViewById(R.id.btnCatDroneRace);
            btnCatMazeSolving = itemView.findViewById(R.id.btnCatMazeSolving);
            btnCatTransporter = itemView.findViewById(R.id.btnCatTransporter);
            btnCatUnderWater = itemView.findViewById(R.id.btnCatUnderWater);
        }
    }
}

package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepan.twinsrobo_apps.HomeActivity;
import com.codepan.twinsrobo_apps.Models.DataModelUser;
import com.codepan.twinsrobo_apps.OtherClass.ListAvatar;
import com.codepan.twinsrobo_apps.R;
import com.codepan.twinsrobo_apps.SelectedEventActivity;

import java.util.List;

public class AdapterMember extends RecyclerView.Adapter<AdapterMember.HolderData> {

    private Context ctx;
    private List<DataModelUser> listDataUser;

    public AdapterMember(Context ctx, List<DataModelUser> listDataUser){
        this.ctx = ctx;
        this.listDataUser = listDataUser;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        AdapterMember.HolderData holder = new AdapterMember.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelUser dm = listDataUser.get(position);

        holder.tvMemberNameItemList.setText(dm.getNamaDepanUser() + " " + dm.getNamaBelakangUser());
        holder.tvMemberSchoolItemList.setText(dm.getNamaSekolahUser());

        if(dm.getJenisKelaminUser().equals("Male")){
            holder.ivMemberGenderPreview.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_male));
        }
        else {
            holder.ivMemberGenderPreview.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_female));
        }

        if(Integer.parseInt(dm.getVipLimit()) >= 0){
            holder.ivMemberBadgePreview.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.vip_badge2));
        }
        else {
            holder.ivMemberBadgePreview.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.free_badge));
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.circleCrop();
        requestOptions.placeholder(R.drawable.anim_loading);
        requestOptions.error(R.drawable.broken_image);

        if(dm.getFotoProfilUser().length() > 15){
            if(!dm.getFotoProfilUser().equals("/twinsrobo/image/image-user/default/none.png")){
                Glide.with(ctx)
                        .load(dm.getFotoProfilUser())
                        .apply(requestOptions)
                        .into(holder.ivMemberThumbnails);
            }
            else {
                Glide.with(ctx)
                        .load(R.drawable.ic_account_circle2)
                        .apply(requestOptions)
                        .into(holder.ivMemberThumbnails);
            }
        }
        else {
            Glide.with(ctx)
                    .load(new ListAvatar().getAvatar(dm.getJenisKelaminUser(), Integer.parseInt(dm.getFotoProfilUser())))
                    .apply(requestOptions)
                    .into(holder.ivMemberThumbnails);
        }

        holder.rlMemberItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) ctx).showMemberPopupPreview(dm.getIdUser(),
                        dm.getNamaDepanUser()
                                + " " + dm.getNamaBelakangUser(),
                        dm.getJenisKelaminUser(),
                        dm.getFotoProfilUser());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataUser.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView tvMemberNameItemList, tvMemberSchoolItemList;
        ImageView ivMemberThumbnails, ivMemberBadgePreview, ivMemberGenderPreview;
        RelativeLayout rlMemberItem;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvMemberNameItemList = itemView.findViewById(R.id.tvMemberNameItemList);
            tvMemberSchoolItemList = itemView.findViewById(R.id.tvMemberSchoolItemList);
            ivMemberThumbnails = itemView.findViewById(R.id.ivMemberThumbnails);
            ivMemberBadgePreview = itemView.findViewById(R.id.ivMemberBadgePreview);
            ivMemberGenderPreview = itemView.findViewById(R.id.ivMemberGenderPreview);
            rlMemberItem = itemView.findViewById(R.id.rlMemberItem);
        }
    }
}

package com.codepan.twinsrobo_apps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepan.twinsrobo_apps.R;
import com.codepan.twinsrobo_apps.RegistrasiProfilActivity;

import java.util.List;

public class AdapterAvatar extends RecyclerView.Adapter<AdapterAvatar.ViewHolder> {

    List<Integer> imageAvatar;
    LayoutInflater inflater;

    Context ctx;

    public AdapterAvatar(Context ctx, List<Integer> imageAvatar) {
        this.ctx = ctx;

        this.imageAvatar = imageAvatar;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.each_avatar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ivImageAvatarItem.setImageResource(imageAvatar.get(position));

    }

    @Override
    public int getItemCount() {
        return imageAvatar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageAvatarItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageAvatarItem = itemView.findViewById(R.id.ivImageAvatarItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RegistrasiProfilActivity) ctx).applyingAvatarPickerDialogFromRPA(getAdapterPosition());
//                    Toast.makeText(view.getContext(), "Kamu Memilih " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

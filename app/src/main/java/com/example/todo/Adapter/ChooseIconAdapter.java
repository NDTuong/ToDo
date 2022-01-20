package com.example.todo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.R;

import java.util.List;

public class ChooseIconAdapter extends RecyclerView.Adapter<ChooseIconAdapter.ChooseIconViewHolder> {
    private final Context mContext;
    private int[] listIcon;

    public ChooseIconAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(int[] listIcon) {
        this.listIcon = listIcon;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ChooseIconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
        return new ChooseIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseIconViewHolder holder, int position) {
        int icon = listIcon[position];
        Log.d("TYPE", "onBindViewHolder: " + icon);
        holder.ivChooseIcon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        if (listIcon != null) {
            return listIcon.length;
        }
        return 0;
    }

    public static class ChooseIconViewHolder extends RecyclerView.ViewHolder {
        ImageView ivChooseIcon;

        public ChooseIconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivChooseIcon = (ImageView) itemView.findViewById(R.id.ivChooseIcon);
        }
    }
}

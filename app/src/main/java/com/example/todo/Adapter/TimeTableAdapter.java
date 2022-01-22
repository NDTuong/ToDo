package com.example.todo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Model.TimeTable;
import com.example.todo.R;
import com.example.todo.SelectListener;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>{
        private final Context mContext;
        private List<TimeTable> listTb;
        private SelectListener listener;

        public TimeTableAdapter(Context mContext) {

            this.mContext = mContext;
            this.listener = listener;
        }

        public void setData(List<TimeTable> listTb, SelectListener listener) {
            this.listTb = listTb;
            this.listener = listener;
            notifyDataSetChanged();

        }
        @NonNull
        @Override
        public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
            return new TimeTableViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TimeTableViewHolder holder, int position) {
            TimeTable t = listTb.get(position);
            holder.tvItemTbName.setText(t.getSubject());
            holder.tvItemTbClass.setText(t.getLocation());
            holder.tvItemTbTime.setText(t.getDuration());
//            if(position == 0){
//                holder.llItemTb.setBackgroundResource(R.color.bg_color);
//                holder.tvItemTbName.setVisibility(View.INVISIBLE);
//                holder.tvItemTbClass.setVisibility(View.INVISIBLE);
//                holder.tvItemTbTime.setVisibility(View.INVISIBLE);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                lp.setMargins(0,0,0,5);
//                holder.llItemTb.setLayoutParams(lp);
//                holder.llItemTb.setPadding(0,0,3,5);
//            }
//            holder.tvItemTbKey.setText(t.getKey());

            holder.llItemTb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemClicked(t);
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            if (listTb != null) {
                return listTb.size();
            }
            return 0;
        }

        public static class TimeTableViewHolder extends RecyclerView.ViewHolder {
            TextView tvItemTbName, tvItemTbClass, tvItemTbTime, tvItemTbKey;
            LinearLayout llItemTb;

            public TimeTableViewHolder(@NonNull View itemView) {
                super(itemView);
                tvItemTbName = (TextView) itemView.findViewById(R.id.tvItemTimeTableName);
                tvItemTbClass = (TextView) itemView.findViewById(R.id.tvItemTimeTableClass);
                tvItemTbTime = (TextView) itemView.findViewById(R.id.tvItemTimeTableTime);
//                tvItemTbKey = (TextView) itemView.findViewById(R.id.tvItemTimeTableKey);
                llItemTb = (LinearLayout) itemView.findViewById(R.id.llItemTb);
            }
        }
    }


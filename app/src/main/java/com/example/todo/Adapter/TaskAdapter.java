package com.example.todo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todo.Model.Task;
import com.example.todo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> mListTask;
    private String UID;

    public TaskAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Task> tasks, String UID){
        this.mListTask = tasks;
        this.UID = UID;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = mListTask.get(position);
        String taskID = task.getTaskID();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(UID)
                .child("task").child(taskID).child("complete");
        RelativeLayout.LayoutParams taskNameParams =
                (RelativeLayout.LayoutParams) holder.taskName.getLayoutParams();
        RelativeLayout.LayoutParams icon1Params =
                (RelativeLayout.LayoutParams) holder.icon1.getLayoutParams();
        RelativeLayout.LayoutParams icon2Params =
                (RelativeLayout.LayoutParams) holder.icon2.getLayoutParams();

        if(task == null){
            return;
        }

        holder.taskName.setText(task.getTaskName());

        if(task.getDeadLine() == null){
            holder.iconCalendar.setVisibility(View.INVISIBLE);
            holder.endDay.setVisibility(View.INVISIBLE);
            holder.Deadline.setVisibility(View.INVISIBLE);
            taskNameParams.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.taskName.setLayoutParams(taskNameParams);
            icon1Params.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.icon1.setLayoutParams(icon1Params);
            icon2Params.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.icon2.setLayoutParams(icon2Params);
        } else {
            holder.Deadline.setText(task.getDeadLine());
        }

        if(task.isShareTask()){
            holder.icon1.setVisibility(View.VISIBLE);
            if(task.getNotify() != null){
                holder.icon2.setVisibility(View.VISIBLE);
            }
        } else {
            if(task.getNotify() != null){
                holder.icon1.setVisibility(View.VISIBLE);
                holder.icon1.setImageResource(R.drawable.ic_baseline_notifications_24);
            }
        }


        if(task.isImportant()){
            holder.important.setImageResource((R.drawable.ic_baseline_star_24));
        }

        if(task.isComplete()){
            holder.cb.setChecked(true);
            SpannableString ss = new SpannableString(task.getTaskName());
            ss.setSpan(new StrikethroughSpan(),0, task.getTaskName().length(),0);
            holder.taskName.setText(ss);
            holder.taskName.setTextColor(Color.parseColor("#757575"));
        }
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.cb.isChecked()){
                    SpannableString ss = new SpannableString(holder.taskName.getText().toString());
                    ss.setSpan(new StrikethroughSpan(),0, holder.taskName.getText().toString().length(),0);
                    holder.taskName.setText(ss);
                    holder.taskName.setTextColor(Color.parseColor("#757575"));
                    mDatabase.setValue(true);
                }
                if(!holder.cb.isChecked()){
                    holder.taskName.setText(holder.taskName.getText().toString());
                    holder.taskName.setTextColor(Color.parseColor("#000000"));
                    mDatabase.setValue(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListTask != null){
            return mListTask.size();
        }
        return 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName, Deadline, endDay;
        private final ImageView icon2, important, icon1, iconCalendar;
        private final CheckBox cb;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.tvTaskName);
            Deadline =  itemView.findViewById(R.id.tvDeadline);
            icon2 = itemView.findViewById(R.id.icon2);
            important = itemView.findViewById(R.id.iconImportant);
            icon1 = itemView.findViewById(R.id.icon1);
            endDay = itemView.findViewById(R.id.tvEndDay);
            iconCalendar = itemView.findViewById(R.id.iconCalendar);
            cb = itemView.findViewById(R.id.cbIsCompleted);

        }
    }
}

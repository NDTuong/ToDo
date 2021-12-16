package com.example.todo.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todo.Model.Task;
import com.example.todo.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mContext;
    private List<Task> mListTask;

    public TaskAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Task> tasks){
        this.mListTask = tasks;
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
        SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy");

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) holder.taskName.getLayoutParams();

        if(task == null){
            return;
        }
        holder.taskName.setText(task.getTaskName());
        if(task.getNotify() != null){
            holder.notify.setVisibility(View.VISIBLE);
        }

        if(task.isImportant()){
            holder.important.setImageResource((R.drawable.ic_baseline_star_24));
        }

        if(task.isShareTask()){
            holder.shareTask.setVisibility(View.VISIBLE);
        }
        if(task.getDeadLine() == null){
            holder.iconCalendar.setVisibility(View.INVISIBLE);
            holder.endDay.setVisibility(View.INVISIBLE);
            holder.Deadline.setVisibility(View.INVISIBLE);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.taskName.setLayoutParams(layoutParams);
        } else {
            holder.Deadline.setText(format.format(task.getDeadLine().getTime()));
        }

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
        private final ImageView notify, important, shareTask, iconCalendar;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.tvTaskName);
            Deadline =  itemView.findViewById(R.id.tvDeadline);
            notify = itemView.findViewById(R.id.iconNotification);
            important = itemView.findViewById(R.id.iconImportant);
            shareTask = itemView.findViewById(R.id.iconShare);
            endDay = itemView.findViewById(R.id.tvEndDay);
            iconCalendar = itemView.findViewById(R.id.iconCalendar);
//            Process =  itemView.findViewById(R.id.tvProcess);
        }
    }
}

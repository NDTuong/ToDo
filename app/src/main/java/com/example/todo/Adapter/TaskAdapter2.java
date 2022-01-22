//package com.example.todo.Adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.FragmentActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.example.todo.Model.Task;
//import com.example.todo.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TaskAdapter2 extends RecyclerView.Adapter<TaskAdapter2.TaskViewHolder> {
//    // initialize variables
//    Context context;
//    ArrayList<Task> listTask;
//    MainViewModel mainViewModel;
//    boolean isEnable = false;
//    boolean isSelectAll = false;
//    ArrayList<Task> selectListTask = new ArrayList<>();
//
//    public TaskAdapter2(Context context) {
//        this.context = context;
//    }
//
//    public void setData(ArrayList<Task> listTask){
//        this.listTask = listTask;
//        notifyDataSetChanged();
//
//    }
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task2, parent, false);
//        // initialize view Model
//        mainViewModel= ViewModelProviders.of((FragmentActivity) context)
//                .get(MainViewModel.class);
//        return new TaskAdapter2.TaskViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if(listTask != null){
//            return listTask.size();
//        }
//        return 0;
//    }
//    public class TaskViewHolder extends RecyclerView.ViewHolder{
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}

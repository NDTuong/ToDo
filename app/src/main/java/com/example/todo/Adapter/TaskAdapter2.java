package com.example.todo.Adapter;

import android.annotation.SuppressLint;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todo.Model.Task;
import com.example.todo.R;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter2 extends RecyclerView.Adapter<TaskAdapter2.ViewHolder> {

    Context context;
    String UID;
    ArrayList<Task> listTask;
    MainViewModel mainViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<Task> selectListTask = new ArrayList<>();

    public TaskAdapter2(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Task> listTask, String UID){
        this.listTask = listTask;
        this.UID = UID;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task2, parent, false);
        // initialize view Model
        mainViewModel= ViewModelProviders.of((FragmentActivity) context)
                .get(MainViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = listTask.get(position);

        holder.tvTaskName.setText(task.getTaskName());
        // Có phải task quan trọng hay không?
        if(task.isImportant()){
            holder.ivIsImportant.setImageResource(R.drawable.ic_baseline_star_24);
        }
        holder.ivIsImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.isImportant()){
                    task.setImportant(false);
                    holder.ivIsImportant.setImageResource(R.drawable.ic_baseline_star_border_24);
                } else {
                    task.setImportant(true);
                    holder.ivIsImportant.setImageResource(R.drawable.ic_baseline_star_24);
                }
            }
        });

        //Set màu cho Card view: nếu đang bấm chọn task thì màu xám còn không thì màu trắng
        if(task.isSelected()){
            holder.cvItemTask.setCardBackgroundColor(context.getResources().getColor(R.color.gray_color));
        }
        else {
            holder.cvItemTask.setCardBackgroundColor(context.getResources().getColor(R.color.bg_color));
        }

        // Chọn sửa, xóa, đánh dấu hoàn thành
        holder.ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenu(v, position);
            }
        });

        // set ngày đến hạn
        if(task.getDeadLine() != null){
            String deadLine = task.getDeadLine();
            String[] date = convertString2NewStringDate(deadLine);
            holder.tvDay.setText(date[0]);
            holder.tvDate.setText(date[2]);
            holder.tvMonth.setText(date[1] + "," + date[3]);
            Log.d("TYPE", task.getDeadLine());
        }
        if(task.getNotify() != null){
            holder.llNotify.setVisibility(View.VISIBLE);
            holder.tvTime.setText(task.getNotify());
        }
        else {
            holder.llNotify.setVisibility(View.INVISIBLE);
        }


        // Mổ tả cv
        if(task.getNote() != null){
            holder.tvDescription.setText(task.getNote());
        }



        // Xử lý khi nhấn giữ item sẽ có thể chọn nhiều item để xóa
        holder.itemView.setOnLongClickListener(v -> {
            // check condition
            if (!isEnable)
            {
                // when action mode is not enable
                // initialize action mode
                ActionMode.Callback callback=new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // initialize menu inflater
                        MenuInflater menuInflater= mode.getMenuInflater();
                        // inflate menu
                        menuInflater.inflate(R.menu.menu_del_task,menu);
                        // return true
                        return true;
                    }
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        // when action mode is prepare
                        // set isEnable true
                        isEnable=true;
                        // create method
                        ClickItem(holder);
                        // set observer on getText method
                        mainViewModel.getText().observe((LifecycleOwner) context
                                , new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        // when text change
                                        // set text on action mode title
                                        mode.setTitle(String.format("%s Selected",s));
                                    }
                                });
                        // return true
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        // when click on action mode item
                        // get item  id
                        int id=item.getItemId();
                        // use switch condition
                        switch(id)
                        {
                            case R.id.menu_delete:
                                // when click on delete
                                // use for loop
                                for(Task t:selectListTask)
                                {
                                    // remove selected item list
                                    listTask.remove(t);
                                }
                                // check condition
                                if(listTask.size()==0)
                                {
                                    // when array list is empty
                                    // visible text view
//                                        tvEmpty.setVisibility(View.VISIBLE);
                                }
                                // finish action mode
                                mode.finish();
                                break;

                            case R.id.menu_select_all:
                                // when click on select all
                                // check condition
                                if(selectListTask.size()==listTask.size())
                                {
                                    // when all item selected
                                    // set is selected all false
                                    isSelectAll=false;
                                    // create select array list
                                    selectListTask.clear();
                                    setAllItemColor(0);
                                }
                                else
                                {
                                    // when  all item unselected
                                    // set isSelectALL true
                                    isSelectAll=true;
                                    // clear select array list
                                    selectListTask.clear();
                                    setAllItemColor(1);
                                    // add value in select array list
                                    selectListTask.addAll(listTask);
                                }
                                // set text on view model
                                mainViewModel.setText(String .valueOf(selectListTask.size()));
                                // notify adapter
                                notifyDataSetChanged();
                                break;
                        }
                        // return true
                        return true;
                    }
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        // when action mode is destroy
                        // set isEnable false
                        isEnable=false;
                        // set isSelectAll false
                        isSelectAll=false;
                        // clear select array list
                        selectListTask.clear();
                        setAllItemColor(0);
                        // notify adapter
                        notifyDataSetChanged();
                    }
                };
                // start action mode
                ((AppCompatActivity) v.getContext()).startActionMode(callback);
            }
            else
            {
                // when action mode is already enable
                // call method
                ClickItem(holder);
            }
            // return true
            return true;
        });

    }
    private void ClickItem(ViewHolder holder) {
//        // get selected item value
        Task t = listTask.get(holder.getAdapterPosition());
        // check condition
        if (t.isSelected()) {
            // if statement body
            // when item not selected
            // visible check box image
            // set background color
            holder.cvItemTask.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_color));
            // add value in select array list
            selectListTask.add(t);
        }
        else
        {
            // when item selected
            // set background color
            holder.cvItemTask.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_color));
            // remove value from select arrayList
            selectListTask.remove(t);
        }
        // set text on view model
        mainViewModel.setText(String.valueOf(selectListTask.size()));
    }
    @Override
    public int getItemCount() {
        if(listTask != null){
            return listTask.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTaskName, tvDescription, tvTime, tvDay, tvDate, tvMonth;
        ImageView ivOptions, ivIsImportant;
        CardView cvItemTask;
        LinearLayout llNotify;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = (TextView) itemView.findViewById(R.id.tvTaskName2);
            ivOptions = (ImageView) itemView.findViewById(R.id.ivOptions);
            cvItemTask = (CardView) itemView.findViewById(R.id.cvItemTask);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            ivIsImportant = (ImageView) itemView.findViewById(R.id.tvIsImportant);
            llNotify = (LinearLayout) itemView.findViewById(R.id.llNotify);
        }
    }

    public void showPopUpMenu(View view, int position) {
        final Task task = listTask.get(position);
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item_task, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuDelete:
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
//                    alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
//                            setPositiveButton(R.string.yes, (dialog, which) -> {
//                                deleteTaskFromId(task.getTaskId(), position);
//                            })
//                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
                    break;
                case R.id.menuUpdate:
//                    CreateTaskBottomSheetFragment createTaskBottomSheetFragment = new CreateTaskBottomSheetFragment();
//                    createTaskBottomSheetFragment.setTaskId(task.getTaskId(), true, context, context);
//                    createTaskBottomSheetFragment.show(context.getSupportFragmentManager(), createTaskBottomSheetFragment.getTag());
                    break;
                case R.id.menuComplete:
//                    AlertDialog.Builder completeAlertDialog = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
//                    completeAlertDialog.setTitle(R.string.confirmation).setMessage(R.string.sureToMarkAsComplete).
//                            setPositiveButton(R.string.yes, (dialog, which) -> showCompleteDialog(task.getTaskId(), position))
//                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
                    break;
            }
            return false;
        });
        popupMenu.show();
    }
    private void setAllItemColor(int flag){
        if(flag == 0){
            for(Task t : listTask){
                t.setSelected(false);
            }
        }
        if(flag == 1){
            for(Task t : listTask){
                t.setSelected(true);
            }
        }
    }
    private String[] convertString2NewStringDate(String s){
        String[] date = {};
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/mm/yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EE/MMM/dd/yy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat1.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newDate = dateFormat2.format(convertedDate);
        date = newDate.split("/");
        return date;
    }
}

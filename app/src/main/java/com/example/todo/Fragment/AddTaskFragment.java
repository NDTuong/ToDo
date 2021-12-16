package com.example.todo.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo.Model.Task;
import com.example.todo.Model.TimeTable;
import com.example.todo.R;
import com.example.todo.TaskActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskFragment extends Fragment {

    View view;
    //LinearLayout llAddTaskName, llAddSubTask;
    TextInputEditText taskName, note;
    TextInputLayout tilTaskName;
    TextView tvSetNotification, tvSetShare, tvSetDeadLine;
    Button btnSaveTask;
    ImageView iconSetNotification, iconSetDeadline, iconSetShare,
            iconDeleteNotification, iconDeleteDeadline, iconDeleteShare;
//    Task newTask = new Task();
    boolean isImportant = false;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_task, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            UID = currentUser.getUid();
        }

        // trỏ vào ô task name và hiện bàn phím khi click fab để thêm task
        taskName = (TextInputEditText) view.findViewById(R.id.etTaskName);
        taskName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        note = (TextInputEditText) view.findViewById(R.id.etNote);

        // Lấy isImportant để lưu vào task ở phần sau
        tilTaskName = (TextInputLayout) view.findViewById(R.id.tilTaskName);
        tilTaskName.setEndIconOnClickListener(v -> {
            if (!isImportant) {
                tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_24);
            } else {
                tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_border_24);
            }
            isImportant = !isImportant;
        });

        // Set Deadline
        tvSetDeadLine = view.findViewById(R.id.tvSetDeadline);
        tvSetDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                openDateTimePickerDialog(tvSetDeadLine, 0);
            }
        });

        tvSetNotification = view.findViewById(R.id.tvSetNotification);
        tvSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                openDateTimePickerDialog(tvSetNotification, 1);
            }
        });

        //Lưu Task khi nhấn save
        iconSetNotification = view.findViewById(R.id.iconSetNotification);
        iconSetDeadline = view.findViewById(R.id.iconSetDeadline);
        iconSetShare = view.findViewById(R.id.iconSetShare);
        btnSaveTask = view.findViewById(R.id.btnSaveTask);
        iconDeleteNotification = view.findViewById(R.id.iconDeleteNotification);
        iconDeleteDeadline = view.findViewById(R.id.iconDeleteDeadline);
        iconDeleteShare = view.findViewById(R.id.iconShare);
        
        // Khi bấm icon xóa share
//        clickIconDelete(iconDeleteShare, iconSetShare, tvSetShare);
//        if(iconDeleteShare.getVisibility()==View.VISIBLE){
//            iconDeleteShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    tvSetShare.setText(R.string.share_task);
//                    tvSetShare.setTextColor(getResources().getColor(R.color.gray_color));
//                    iconSetShare.setColorFilter(getResources().getColor(R.color.gray_color));
//                    iconDeleteShare.setVisibility(View.INVISIBLE);
//                }
//            });
//        }

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task newTask = new Task();
                newTask.setImportant(isImportant);
            }
        });

        return view;
    }



    // Mở dialog chọn ngày giờ (flag: 0 - chỉ chọn ngày | flag: 1 - chọn ngày và giờ)
    private Calendar openDateTimePickerDialog(TextView tv, int flag) {
        final Dialog dateTimePickerDialog = new Dialog(getContext());
        SimpleDateFormat format1 = new SimpleDateFormat("d/MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("d/MM/yyyy - hh:mm");
        Calendar calendar = Calendar.getInstance();
        // Đóng fragment thêm thời khóa biểu nếu đang tồn tại

        dateTimePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateTimePickerDialog.setContentView(R.layout.dialog_set_date_time);
        Window window = dateTimePickerDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            //Set vị trí cho dialog
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            //click ra ngoài sẽ tắt dialog
            dateTimePickerDialog.setCancelable(true);

            DatePicker datePicker = dateTimePickerDialog.findViewById(R.id.datePiker);
            TimePicker timePicker = dateTimePickerDialog.findViewById(R.id.timePicker);
            TextView tvSelectTime = dateTimePickerDialog.findViewById(R.id.tvSelectTime);
            Button btnSaveDateTime = dateTimePickerDialog.findViewById(R.id.btnSaveDateTime);

            //ẩn phần chọn giờ nếu tv là deadline
            LinearLayout linearLayout = dateTimePickerDialog.findViewById(R.id.linearLayoutSetTime);
            if(flag==0){
                if(linearLayout.getVisibility()!=View.GONE) {
                    linearLayout.setVisibility(View.GONE);
                }
            }

            btnSaveDateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year =  datePicker.getYear();

                    if(flag==1){
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        calendar.set(year, month, day, hour, minute);
                        tv.setText(format2.format(calendar.getTime()));
                        tv.setTextColor(getResources().getColor(R.color.dark_color));
                        iconSetNotification.setColorFilter(getResources().getColor(R.color.dark_color));
                        iconDeleteNotification.setVisibility(View.VISIBLE);
                        dateTimePickerDialog.dismiss();
                        clickIconDelete(iconDeleteNotification, iconSetNotification, tvSetNotification);
                    }
                    if(flag==0){
                        calendar.set(year, month, day);
                        tv.setText(format1.format(calendar.getTime()));
                        tv.setTextColor(getResources().getColor(R.color.dark_color));
                        iconSetDeadline.setColorFilter(getResources().getColor(R.color.dark_color));
                        iconDeleteDeadline.setVisibility(View.VISIBLE);
                        dateTimePickerDialog.dismiss();
                        clickIconDelete(iconDeleteDeadline, iconSetDeadline, tvSetDeadLine);
                    }
                }
            });

        }
        //show dialog
        dateTimePickerDialog.show();
        return calendar;
    }

    // Khi bấm icon delete
    private void clickIconDelete(ImageView iconDelete, ImageView iconX, TextView tvX){
        if(iconDelete.getVisibility()==View.VISIBLE){
            iconDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvX.setText(R.string.share_task);
                    tvX.setTextColor(getResources().getColor(R.color.gray_color));
                    iconX.setColorFilter(getResources().getColor(R.color.gray_color));
                    iconDelete.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}


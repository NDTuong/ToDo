package com.example.todo.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.Model.Task;
import com.example.todo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BottomSheetDialogTask extends BottomSheetDialogFragment {

    TextView tvSetDeadline, tvSetNotify;
    TextInputLayout tilTaskName, tilNote;
    Button btnSaveTask, btnCloseTask;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String UID;
    boolean isImportant = false;

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear,myHour, myMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_task_layout,
                container, false);


        //Lây UID của người dùng hiện tại
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            UID = currentUser.getUid();
        }

        //Kết nối database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Lấy isImportant để lưu vào task ở phần sau
        tilTaskName = (TextInputLayout) v.findViewById(R.id.tilTaskName);
        tilNote = (TextInputLayout) v.findViewById(R.id.tilNote);
        tilTaskName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isImportant) {
                    tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_24);
                } else {
                    tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_border_24);
                }
                isImportant = !isImportant;
            }
        });

        tvSetDeadline = (TextView) v.findViewById(R.id.tvSetDeadline);
        tvSetNotify = (TextView) v.findViewById(R.id.tvSetNotify);
        tvSetDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePiker(0);
            }
        });
        tvSetNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePiker(1);
            }
        });
//        Log.d("SetH", "dd/mm/yyyy: " + myMinute + ":" + myHour + "-" + myYear + "/" + myMonth + "/" + myDay);
        btnSaveTask = v.findViewById(R.id.btnSaveTask);
        btnCloseTask = v.findViewById(R.id.btnCloseTask);

        btnCloseTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = tilTaskName.getEditText().getText().toString();
                if (TextUtils.isEmpty(taskName)) {
                    tilTaskName.setError(getString(R.string.empty_task_name));
                    return;
                } else {
                    tilTaskName.setError(null);
                }
                String deadLine = tvSetDeadline.getText().toString();
                if (deadLine.equals(getString(R.string.deadline))) {
                    Toast.makeText(getContext(), R.string.empty_deadline, Toast.LENGTH_SHORT).show();
                } else {
                    String notify = tvSetNotify.getText().toString();
                    if (notify.equals(getString(R.string.remind))) {
                        notify = null;
                    }
                    String note = tilNote.getEditText().getText().toString();
                    String idTask = mDatabase.push().getKey();
                    Task newTask = new Task();
                    newTask.setTaskName(taskName);
                    newTask.setImportant(isImportant);
                    newTask.setDeadLine(deadLine);
                    newTask.setNotify(notify);
                    newTask.setNote(note);
                    newTask.setTaskID(idTask);
                    mDatabase.child(UID).child("task").child(idTask).setValue(newTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.add_success,
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), R.string.add_failure,
                                        Toast.LENGTH_SHORT).show();
                            }
                            tilTaskName.getEditText().setText("");
                            tvSetNotify.setText(getString(R.string.remind));
                            tvSetDeadline.setText(getString(R.string.deadline));
                            tilNote.getEditText().setText("");
                            dismiss();
                        }
                    });

                }
            }
        });

        return v;
    }

    private void openErrorDialog() {
    }

    private void openDatePiker(int flag){
        HashMap<String, Integer> datetime = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int month, int day) {
                myDay = day;
                myMonth = month;
                myYear = year;
                if (flag == 0){
                    tvSetDeadline.setText(Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year));
                }
                if(flag==1) {
                    openTimePiker();
                }
            }
        };
        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener, year, month, day);

        // Show
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void openTimePiker(){
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        // Time Set Listener.
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String dateTime = Integer.toString(hour) + ":" + Integer.toString(minute) + " - "
                        + Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
                tvSetNotify.setText(dateTime);
                Log.d("SetTime", "hh/mm/: " + hour + "/" + minute);
            }
        };

        // Create TimePickerDialog:
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                timeSetListener, hour, minute, true);

        // Show
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }
}

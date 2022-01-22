package com.example.todo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adapter.TimeTableAdapter;
import com.example.todo.Model.DayOfWeek;
import com.example.todo.Model.TimeTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeTableActivity extends AppCompatActivity implements SelectListener {
    FloatingActionButton fabAddTimeTable;
    ImageView ivBack2Menu;
    int t1Hour, t1Minute, t2Hour, t2Minute;
    int count = 0;
    ArrayList<TimeTable> timeTableMon = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableTue = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableWed = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableThur = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableFri = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableSat = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTableSun = new ArrayList<TimeTable>();
    ArrayList<TimeTable> timeTable = new ArrayList<TimeTable>();
    private DatabaseReference dbTimeTable;
    private FirebaseAuth mAuth;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // Get current user ID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        }
        if (currentUser == null) {
            Intent intent = new Intent(TimeTableActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.ivBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

        // Kết nối database
        dbTimeTable = FirebaseDatabase.getInstance().getReference(UID);
        dbTimeTable.child("time_table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeTable mTimeTable = snapshot.getValue(TimeTable.class);
                    addTb2List(mTimeTable);

                }
//                Log.d("TYPE", "onDataChange2: " + timeTableMon);
//                Log.d("TYPE", "onDataChange3: " + timeTableTue);
//                Log.d("TYPE", "onDataChange4: " + timeTableWed);
//                Log.d("TYPE", "onDataChange5: " + timeTableThur);
//                Log.d("TYPE", "onDataChange6: " + timeTableFri);
//                Log.d("TYPE", "onDataChange7: " + timeTableSat);
//                Log.d("TYPE", "onDataChange8: " + timeTableSun);
//                Log.d("TYPE", "==========================================");
                showTimeTable();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // Set sự kiện khi click fab
        fabAddTimeTable = findViewById(R.id.fabAddTimeTable);
        fabAddTimeTable.setOnClickListener(v -> {
            openEditTimeTbDialog(new TimeTable(), 1);
        });
    }

    private void showTimeTable() {
        RecyclerView rcvMon = findViewById(R.id.rcvMon);
        TimeTableAdapter timeTableAdapterMon = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerMon = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvMon.setLayoutManager(linearLayoutManagerMon);
        timeTableAdapterMon.setData(timeTableMon, this);
        rcvMon.setAdapter(timeTableAdapterMon);

        RecyclerView rcvTue = findViewById(R.id.rcvTue);
        TimeTableAdapter timeTableAdapterTue = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerTue = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvTue.setLayoutManager(linearLayoutManagerTue);
        timeTableAdapterTue.setData(timeTableTue, this);
        rcvTue.setAdapter(timeTableAdapterTue);

        RecyclerView rcvWed = findViewById(R.id.rcvWed);
        TimeTableAdapter timeTableAdapterWed = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerWed = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvWed.setLayoutManager(linearLayoutManagerWed);
        timeTableAdapterWed.setData(timeTableWed, this);
        rcvWed.setAdapter(timeTableAdapterWed);

        RecyclerView rcvThur = findViewById(R.id.rcvThur);
        TimeTableAdapter timeTableAdapterThur = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerThur = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvThur.setLayoutManager(linearLayoutManagerThur);
        timeTableAdapterThur.setData(timeTableThur, this);
        rcvThur.setAdapter(timeTableAdapterThur);

        RecyclerView rcvFri = findViewById(R.id.rcvFri);
        TimeTableAdapter timeTableAdapterFri = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerFri = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvFri.setLayoutManager(linearLayoutManagerFri);
        timeTableAdapterFri.setData(timeTableFri, this);
        rcvFri.setAdapter(timeTableAdapterFri);

        RecyclerView rcvSat = findViewById(R.id.rcvSat);
        TimeTableAdapter timeTableAdapterSat = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerSat = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvSat.setLayoutManager(linearLayoutManagerSat);
        timeTableAdapterSat.setData(timeTableSat, this);
        rcvSat.setAdapter(timeTableAdapterSat);

        RecyclerView rcvSun = findViewById(R.id.rcvSun);
        TimeTableAdapter timeTableAdapterSun = new TimeTableAdapter(TimeTableActivity.this);
        LinearLayoutManager linearLayoutManagerSun = new LinearLayoutManager(TimeTableActivity.this, RecyclerView.HORIZONTAL, false);
        rcvSun.setLayoutManager(linearLayoutManagerSun);
        timeTableAdapterSun.setData(timeTableSun, this);
        rcvSun.setAdapter(timeTableAdapterSun);
    }

    private void addTb2List(TimeTable t) {
        DayOfWeek d = t.getDay();
        switch (d) {
            case MON:
                timeTableMon.add(t);
                break;
            case TUE:
                timeTableTue.add(t);
                break;
            case WED:
                timeTableWed.add(t);
                break;
            case THU:
                timeTableThur.add(t);
                break;
            case FRI:
                timeTableFri.add(t);
                break;
            case SAT:
                timeTableSat.add(t);
                break;
            case SUN:
                timeTableSun.add(t);
                break;
        }
    }

    @Override
    public void onItemClicked(TimeTable tb) {
        openEditTimeTbDialog(tb, 0);
    }
//    // Hàm ẩn bàn phím
//    private void hideKeyboard(View v) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//    }
    public void openEditTimeTbDialog(TimeTable timeTable, int flags) {
        final Dialog editDialog = new Dialog(this);
        String idTimeTable = "";

        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.dialog_timetable);

        Window window = editDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();

            //Set vị trí cho dialog
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            TextView tvEditTimeStart = editDialog.findViewById(R.id.tvEditTimeStart);
            TextView tvEditTimeEnd = editDialog.findViewById(R.id.tvEditTimeEnd);
            ImageView ivEditTimeStart = editDialog.findViewById(R.id.ivEditTimeStart);
            ImageView ivEditTimeEnd = editDialog.findViewById(R.id.ivEditTimeEnd);
            Button btnCancel = editDialog.findViewById(R.id.btnCancel);
            Button btnDelete = editDialog.findViewById(R.id.btnDelete);
            TextInputEditText subject = editDialog.findViewById(R.id.editSubjects);
            TextInputEditText classroom = editDialog.findViewById(R.id.editClassroom);

            //click ra ngoài sẽ tắt dialog
            editDialog.setCancelable(true);

            // thứ
            // Add item for spinner
            ArrayList<String> arrayListDoW = new ArrayList<>();
            arrayListDoW.add(getString(R.string.monday));
            arrayListDoW.add(getString(R.string.tuesday));
            arrayListDoW.add(getString(R.string.wednesday));
            arrayListDoW.add(getString(R.string.thursday));
            arrayListDoW.add(getString(R.string.friday));
            arrayListDoW.add(getString(R.string.saturday));
            arrayListDoW.add(getString(R.string.sunday));

            Spinner spinnerDOF = editDialog.findViewById(R.id.editSpinnerDOF);

            // Creating adapter for spinner
            ArrayAdapter<String> arrayAdapterDoW = new ArrayAdapter<>(TimeTableActivity.this, R.layout.spinner_selected_item, arrayListDoW);

            // Drop down layout style - list view with radio button
            arrayAdapterDoW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerDOF.setAdapter(arrayAdapterDoW);

            // Tắt dialog sửa thời khóa biểu khi bấm cancel
            btnCancel.setOnClickListener(v -> editDialog.dismiss());
            if (flags == 0) {
                idTimeTable = timeTable.getKey();
                int position = arrayListDoW.indexOf(DOF2String(timeTable.getDay()));
                spinnerDOF.setSelection(position);
                // Đặt tên môn học
                subject.setText(timeTable.getSubject());
                // Đặt phòng học
                classroom.setText(timeTable.getLocation());
                // Đặt giờ học
                String duration = timeTable.getDuration();
                String[] startEnd = duration.split("-");
                tvEditTimeStart.setText(startEnd[0].trim());
                tvEditTimeEnd.setText(startEnd[1].trim());
                // xóa lịch học
                btnDelete.setOnClickListener(v -> {
                    dbTimeTable.child("time_table").child(timeTable.getKey()).removeValue()
                            .addOnSuccessListener(unused ->
                            {
                                Toast.makeText(editDialog.getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(editDialog.getContext(), R.string.update_fail, Toast.LENGTH_SHORT).show());
                    clearData();
                    editDialog.dismiss();
                });
            }

            if (flags == 1) {
                btnDelete.setVisibility(View.GONE);
                idTimeTable = dbTimeTable.push().getKey();
            }
            tvEditTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvEditTimeEnd));
            ivEditTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvEditTimeEnd));
            ivEditTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvEditTimeStart));
            tvEditTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvEditTimeStart));

            // Lưu thời khóa biểu mới khi ấn save
            Button btnSave = editDialog.findViewById(R.id.btnSave);
            String finalIdTimeTable = idTimeTable;
            btnSave.setOnClickListener(v -> {
                String mSubject = subject.getText().toString().trim();
                String mClassroom = classroom.getText().toString().trim();
                String day = spinnerDOF.getSelectedItem().toString();
                String startTime = tvEditTimeStart.getText().toString();
                String endTime = tvEditTimeEnd.getText().toString();
                String duration1 = startTime + " - " + endTime;

                if (TextUtils.isEmpty(mSubject)) {
                    Toast.makeText(editDialog.getContext(), R.string.empty_subject, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mClassroom)) {
                    Toast.makeText(editDialog.getContext(), R.string.empty_classroom, Toast.LENGTH_SHORT).show();
                    return;
                }
                int t1 = Integer.parseInt(startTime.split(":")[0]);
                int t2 = Integer.parseInt(endTime.split(":")[0]);
                int m1 = Integer.parseInt(startTime.split(":")[1]);
                int m2 = Integer.parseInt(endTime.split(":")[1]);
                if (t1 > t2) {
                    Toast.makeText(editDialog.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t1 == t2 && m1 > m2) {
                    Toast.makeText(editDialog.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Lưu thông tin vào database
                TimeTable newTimeTable = new TimeTable(mSubject, mClassroom, convert2DOF(day), duration1, finalIdTimeTable);
                if (!newTimeTable.equals(timeTable)) {
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("time_table" + "/" + finalIdTimeTable, newTimeTable);
                    dbTimeTable.updateChildren(childUpdates).addOnSuccessListener(unused -> Toast.makeText(editDialog.getContext(), R.string.update_success, Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(editDialog.getContext(), R.string.update_fail, Toast.LENGTH_SHORT).show());
                    //subject.setText("");
                    //classroom.setText("");
                    clearData();
                }
                // Đóng dialog
                editDialog.dismiss();
            });
            //show dialog
            editDialog.show();
        }
    }

    // Hàm hiển thị time picker (chọn giờ)
    private void showTimePicker(View v, int hour, int minute, TextView tv) {
        final int[] h = {hour};
        final int[] m = {minute};
        TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), R.style.MyTimePickerDialogStyle,
                (view, hourOfDay, minute1) -> {
                    //Initialize hour and minute
                    h[0] = hourOfDay;
                    m[0] = minute1;
                    //Initialize calendar
                    Calendar calendar = Calendar.getInstance();
                    //Set hour and
                    calendar.set(0, 0, 0, h[0], m[0]);
                    //set selected time on text view
                    tv.setText(DateFormat.format("HH:mm", calendar));
                }, 24, 0, true);
        timePickerDialog.updateTime(h[0], m[0]);
        timePickerDialog.show();
    }

    // Chuyển thứ trong tuần từ string sang Enum DayOfWeek
    private DayOfWeek convert2DOF(String s) {
        if (s.equals(getString(R.string.monday))) {
            return DayOfWeek.MON;
        }
        if (s.equals(getString(R.string.tuesday))) {
            return DayOfWeek.TUE;
        }
        if (s.equals(getString(R.string.wednesday))) {
            return DayOfWeek.WED;
        }
        if (s.equals(getString(R.string.thursday))) {
            return DayOfWeek.THU;
        }
        if (s.equals(getString(R.string.friday))) {
            return DayOfWeek.FRI;
        }
        if (s.equals(getString(R.string.saturday))) {
            return DayOfWeek.SAT;
        }
        return DayOfWeek.SUN;
    }

    // Hàm chuyển từ giá trị Enum sang String
    private String DOF2String(DayOfWeek d) {
        if (d.equals(DayOfWeek.MON)) {
            return getString(R.string.monday);
        }
        if (d.equals(DayOfWeek.TUE)) {
            return getString(R.string.tuesday);
        }
        if (d.equals(DayOfWeek.WED)) {
            return getString(R.string.wednesday);
        }
        if (d.equals(DayOfWeek.THU)) {
            return getString(R.string.thursday);
        }
        if (d.equals(DayOfWeek.FRI)) {
            return getString(R.string.friday);
        }
        if (d.equals(DayOfWeek.SAT)) {
            return getString(R.string.saturday);
        }
        return getString(R.string.sunday);
    }

    private void clearData() {
        timeTableMon.clear();
        timeTableTue.clear();
        timeTableWed.clear();
        timeTableThur.clear();
        timeTableFri.clear();
        timeTableSat.clear();
        timeTableSun.clear();
    }
}
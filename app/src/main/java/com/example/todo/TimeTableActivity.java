package com.example.todo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todo.Fragment.AddTimeTableFragment;
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

public class TimeTableActivity extends AppCompatActivity {

    // Các hàng của bảng Thời khóa biểu
    TableRow rowMonday, rowTuesday, rowWednesday, rowThursday, rowFriday, rowSaturday, rowSunday;

    FloatingActionButton fabAddTimeTable;
    ConstraintLayout constraintLayout;
    LinearLayout linearLayoutTimeTable;
    ImageView ivBack2Menu;

    private DatabaseReference dbTimeTable;
    private FirebaseAuth mAuth;

    String UID;
    int t1Hour, t1Minute, t2Hour, t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // Kết nối database
        dbTimeTable = FirebaseDatabase.getInstance().getReference("time_table");

        // Get current user ID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        }

        // Set sự kiện khi click fab
        fabAddTimeTable = findViewById(R.id.fabAddTimeTable);
        fabAddTimeTable.setOnClickListener(v -> {
            fabAddTimeTable.setVisibility(View.GONE);
            loadFragment(new AddTimeTableFragment());

        });

        // Đóng fragment thêm thời khóa biểu khi click ra ngoài
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(v -> closeFragment(v));
        linearLayoutTimeTable = findViewById(R.id.linearLayoutTimeTable);
        linearLayoutTimeTable.setOnClickListener(v -> closeFragment(v));


        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.iconBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

        rowMonday = findViewById(R.id.rowMonday);
        rowTuesday = findViewById(R.id.rowTuesday);
        rowWednesday = findViewById(R.id.rowWednesday);
        rowThursday = findViewById(R.id.rowThursday);
        rowFriday = findViewById(R.id.rowFriday);
        rowSaturday = findViewById(R.id.rowSaturday);
        rowSunday = findViewById(R.id.rowSunday);

        // Lấy dữ liệu từ database và hiển thị
        getTimeTableAndShow(dbTimeTable);

    }

    // [START] Kiểm tra đã đăng nhập chưa?
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(TimeTableActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
    // [END]

    // Hàm load fragment
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment, "AddTimeTable");
        fragmentTransaction.commit(); // save the changes
    }

    // Hàm ẩn bàn phím
    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    // Hàm đóng fragment
    private void closeFragment(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("AddTimeTable");
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            hideKeyboard(v);
            fabAddTimeTable.setVisibility(View.VISIBLE);
        }
    }

    // Hàm set textview ở linear layout thời khóa biểu từng môn
    private void setTextView2Layout(String text, LinearLayout linearLayout) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(1);
        linearLayout.addView(textView);
    }

    // Hàm hiển từng môn học trên thời khóa biểu
    private void showTimeTable(TimeTable timeTable) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
        linearLayout.setPadding(5, 0, 5, 0);
        DayOfWeek d = timeTable.getDay();
        switch (d) {
            case MON:
                rowMonday.addView(linearLayout);
                break;
            case TUE:
                rowTuesday.addView(linearLayout);
                break;
            case WED:
                rowWednesday.addView(linearLayout);
                break;
            case THU:
                rowThursday.addView(linearLayout);
                break;
            case FRI:
                rowFriday.addView(linearLayout);
                break;
            case SAT:
                rowSaturday.addView(linearLayout);
                break;
            case SUN:
                rowSunday.addView(linearLayout);
                break;
        }

        setTextView2Layout(timeTable.getSubject(), linearLayout);
        setTextView2Layout(timeTable.getDuration(), linearLayout);
        setTextView2Layout(timeTable.getLocation(), linearLayout);

        linearLayout.setOnLongClickListener(v -> {
            openEditTimeTableDialog(timeTable, linearLayout);
            return false;
        });
    }

    // Hàm lấy dữ liệu thời khóa biểu từ database và hiển thị
    private void getTimeTableAndShow(DatabaseReference db) {
        ArrayList<TimeTable> listTimeTable = new ArrayList<TimeTable>();
        db.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeTable mTimeTable = snapshot.getValue(TimeTable.class);
                    assert mTimeTable != null;
                    if (listTimeTable.contains(mTimeTable)) {
                        continue;
                    }
                    if (checkUpdateTimeTable(mTimeTable, listTimeTable)) {
                        showTimeTable(mTimeTable);
                        continue;
                    }
                    listTimeTable.add(mTimeTable);
                    showTimeTable(mTimeTable);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    // Mở Dialog sửa thời khóa biểu
    private void openEditTimeTableDialog(TimeTable timeTable, LinearLayout linearLayout) {
        final Dialog editDialog = new Dialog(this);

        // Đóng fragment thêm thời khóa biểu nếu đang tồn tại
        ViewGroup parent = (ViewGroup) linearLayout.getParent();
        closeFragment(parent);

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

            //click ra ngoài sẽ tắt dialog
            editDialog.setCancelable(true);

            // Sửa tên môn học
            TextInputEditText subject = editDialog.findViewById(R.id.editSubjects);
            subject.setText(timeTable.getSubject());

            // Sửa phòng học
            TextInputEditText classroom = editDialog.findViewById(R.id.editClassroom);
            classroom.setText(timeTable.getLocation());

            // Sửa thứ
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
            ArrayAdapter<String> arrayAdapterDoW = new ArrayAdapter<>(TimeTableActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayListDoW);

            // Drop down layout style - list view with radio button
            arrayAdapterDoW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerDOF.setAdapter(arrayAdapterDoW);
            int position = arrayListDoW.indexOf(DOF2String(timeTable.getDay()));
            spinnerDOF.setSelection(position);

            // Sửa giờ học
            String duration = timeTable.getDuration();
            String[] startEnd = duration.split("-");

            TextView tvEditTimeStart = editDialog.findViewById(R.id.tvEditTimeStart);
            tvEditTimeStart.setText(startEnd[0].trim());
            tvEditTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvEditTimeStart));

            TextView tvEditTimeEnd = editDialog.findViewById(R.id.tvEditTimeEnd);
            tvEditTimeEnd.setText(startEnd[1].trim());
            tvEditTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvEditTimeEnd));

            ImageView ivEditTimeStart = editDialog.findViewById(R.id.ivEditTimeStart);
            ivEditTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvEditTimeStart));

            ImageView ivEditTimeEnd = editDialog.findViewById(R.id.ivEditTimeEnd);
            ivEditTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvEditTimeEnd));

            // Tắt dialog sửa thời khóa biểu khi bấm cancel
            Button btnCancel = editDialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(v -> editDialog.dismiss());

            // Lưu thời khóa biểu mới khi ấn save
            Button btnSave = editDialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(v -> {
                String mSubject = subject.getText().toString().trim();
                String mClassroom = classroom.getText().toString().trim();
                String day = spinnerDOF.getSelectedItem().toString();
                String startTime = tvEditTimeStart.getText().toString();
                String endTime = tvEditTimeEnd.getText().toString();
                String duration1 = startTime + " - " + endTime;
                String idTimeTable = timeTable.getKey();

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
                if (t1 > t2){
                    Toast.makeText(editDialog.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t1 == t2 && m1 > m2){
                    Toast.makeText(editDialog.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Lưu thông tin vào database
                TimeTable newTimeTable = new TimeTable(mSubject, mClassroom, convert2DOF(day), duration1, idTimeTable);
                if (!newTimeTable.equals(timeTable)) {
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(UID + "/" + idTimeTable, newTimeTable);
                    dbTimeTable.updateChildren(childUpdates).addOnSuccessListener(unused -> Toast.makeText(editDialog.getContext(),R.string.update_success, Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(editDialog.getContext(),R.string.update_fail, Toast.LENGTH_SHORT).show());
                    subject.setText("");
                    classroom.setText("");
                    // Xóa layout lưu thông tin lịch học cũ nếu có update
                    parent.removeView(linearLayout);
                }
                // Đóng dialog
                editDialog.dismiss();
            });

            // xóa lịch học
            Button btnDelete = editDialog.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(v -> {
                dbTimeTable.child(UID).child(timeTable.getKey()).removeValue()
                .addOnSuccessListener(unused ->
                    Toast.makeText(editDialog.getContext(),R.string.update_success, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                    Toast.makeText(editDialog.getContext(),R.string.update_fail, Toast.LENGTH_SHORT).show());
                parent.removeView(linearLayout);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Initialize hour and minute
                        h[0] = hourOfDay;
                        m[0] = minute;
                        //Initialize calendar
                        Calendar calendar = Calendar.getInstance();
                        //Set hour and
                        calendar.set(0, 0, 0, h[0], m[0]);
                        //set selected time on text view
                        tv.setText(DateFormat.format("HH:mm", calendar));
                    }
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
    private String DOF2String(DayOfWeek d){
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

    // Kiểm tra có môn học nào được update lịch học không?
    private boolean checkUpdateTimeTable(TimeTable t, ArrayList<TimeTable> l) {
        for (TimeTable lt : l) {
            if (t.getKey().equals(lt.getKey())) {
                if (!t.getDay().equals(lt.getDay())) {
                    lt.setDay(t.getDay());
                    return true;
                }
                if (!t.getDuration().equals(lt.getDuration())) {
                    lt.setDuration(t.getDuration());
                }
                if (!t.getLocation().equals(lt.getLocation())) {
                    lt.setLocation(t.getLocation());
                }
                if (!t.getSubject().equals(lt.getSubject())) {
                    lt.setSubject(t.getSubject());
                }

                return true;
            }
        }
        return false;
    }
}
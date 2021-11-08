package com.example.todo.Fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.todo.Model.DayOfWeek;
import com.example.todo.Model.TimeTable;
import com.example.todo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTimeTableFragment extends Fragment {
    View view;

    TextInputEditText editTextSubject, editTextClassroom;
    Button btnAddTimeTable;

    Spinner spinnerDOF;
    ArrayList<String> arrayListDoW;
    ArrayAdapter<String> arrayAdapterDoW;

    int t1Hour, t1Minute, t2Hour, t2Minute;
    TextView tvTimeStart, tvTimeEnd;
    ImageView ivTimeStart, ivTimeEnd;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_time_table, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            UID = currentUser.getUid();
        }

        editTextSubject = view.findViewById(R.id.subjects);
        editTextClassroom = view.findViewById(R.id.classroom);
        btnAddTimeTable = view.findViewById(R.id.btnAddTimeTable);

        // Show keyboard and focus editTextSubject
        editTextSubject.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        // Add item for spinner
        arrayListDoW = new ArrayList<>();
        arrayListDoW.add(getString(R.string.monday));
        arrayListDoW.add(getString(R.string.tuesday));
        arrayListDoW.add(getString(R.string.wednesday));
        arrayListDoW.add(getString(R.string.thursday));
        arrayListDoW.add(getString(R.string.friday));
        arrayListDoW.add(getString(R.string.saturday));
        arrayListDoW.add(getString(R.string.sunday));

        spinnerDOF = view.findViewById(R.id.spinnerDOF);
        // Creating adapter for spinner
        arrayAdapterDoW = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, arrayListDoW);
        // Drop down layout style - list view with radio button
        arrayAdapterDoW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerDOF.setAdapter(arrayAdapterDoW);

        tvTimeStart = view.findViewById(R.id.tvTimeStart);
        tvTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvTimeStart));

        tvTimeEnd = view.findViewById(R.id.tvTimeEnd);
        tvTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvTimeEnd));

        ivTimeStart = view.findViewById(R.id.ivTimeStart);
        ivTimeStart.setOnClickListener(v -> showTimePicker(v, t1Hour, t1Minute, tvTimeStart));

        ivTimeEnd = view.findViewById(R.id.ivTimeEnd);
        ivTimeEnd.setOnClickListener(v -> showTimePicker(v, t2Hour, t2Minute, tvTimeEnd));

        btnAddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editTextSubject.getText().toString().trim();
                String classroom = editTextClassroom.getText().toString().trim();
                String day = spinnerDOF.getSelectedItem().toString();
                System.out.println(day);
                String startTime = tvTimeStart.getText().toString();
                String endTime = tvTimeEnd.getText().toString();
                String duration = startTime + " - " + endTime;

                if (TextUtils.isEmpty(subject)) {
                    Toast.makeText(view.getContext(), R.string.empty_subject, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(classroom)) {
                    Toast.makeText(view.getContext(), R.string.empty_classroom, Toast.LENGTH_SHORT).show();
                    return;
                }
                int t1 = Integer.parseInt(startTime.split(":")[0]);
                int t2 = Integer.parseInt(endTime.split(":")[0]);
                int m1 = Integer.parseInt(startTime.split(":")[1]);
                int m2 = Integer.parseInt(endTime.split(":")[1]);
                if (t1 > t2){
                    Toast.makeText(view.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t1 == t2 && m1 > m2){
                    Toast.makeText(view.getContext(), R.string.duration_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                String idTimeTable = mDatabase.push().getKey();
                TimeTable newTimeTable = new TimeTable(subject, classroom, convert2DOF(day), duration, idTimeTable);
                assert idTimeTable != null;
                mDatabase.child("time_table").child(UID).child(idTimeTable).setValue(newTimeTable);
                editTextSubject.setText("");
                editTextClassroom.setText("");
            }
        });

        return view;
    }

    public void showTimePicker(View v, int hour, int minute, TextView tv) {
        final int[] h = {hour};
        final int[] m = {minute};

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
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

    public DayOfWeek convert2DOF(String s) {
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

}
package com.example.todo;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class EditTimeTableFragment extends Fragment {
    View view;

    TextInputEditText editTextSubject;

    Spinner spinnerDOF;
    ArrayList<String> arrayListDoW;
    ArrayAdapter<String> arrayAdapterDoW;

    int t1Hour, t1Minute, t2Hour, t2Minute;
    TextView tvTimeStart, tvTimeEnd;
    ImageView ivTimeStart, ivTimeEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_time_table, container, false);

        editTextSubject = view.findViewById(R.id.subjects);

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
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v, t1Hour, t1Minute, tvTimeStart);
            }
        });

        tvTimeEnd = view.findViewById(R.id.tvTimeEnd);
        tvTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v, t2Hour, t2Minute, tvTimeEnd);
            }
        });

        ivTimeStart = view.findViewById(R.id.ivTimeStart);
        ivTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v, t1Hour, t1Minute, tvTimeStart);
            }
        });

        ivTimeEnd = view.findViewById(R.id.ivTimeEnd);
        ivTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v, t2Hour, t2Minute, tvTimeEnd);
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
                        tv.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, true);
        timePickerDialog.updateTime(h[0], m[0]);
        timePickerDialog.show();
    }

}
package com.example.todo.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddTaskFragment extends Fragment {

    View view;

    LinearLayout llAddTaskName, llAddSubTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_task, container, false);

        //
        llAddTaskName = view.findViewById(R.id.llAddTaskName);
        llAddSubTask = view.findViewById(R.id.llAddSubTask);
        llAddSubTask.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();
            final EditText edSubTask = new EditText(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(60,0,30,30);
            Drawable edTest = getResources().getDrawable(R.drawable.test_ed);
            edSubTask.setBackground(edTest);
            edSubTask.setLayoutParams(lp);
            edSubTask.setLines(1);
            edSubTask.setMaxLines(1);
            llAddTaskName.addView(edSubTask);
        });


        return view;
    }
}


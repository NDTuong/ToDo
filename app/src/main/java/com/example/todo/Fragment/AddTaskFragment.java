package com.example.todo.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

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
            llAddTaskName.removeView(llAddSubTask);
            Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();
            final TextInputEditText edSubTask = new TextInputEditText(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(60,20,30, 0);
            Drawable edTest = getResources().getDrawable(R.drawable.test_ed);
            edSubTask.setBackground(edTest);
            edSubTask.setLayoutParams(lp);
            edSubTask.setLines(1);
            edSubTask.setMaxLines(1);
            edSubTask.requestFocus();
            llAddTaskName.addView(edSubTask);
            edSubTask.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String newSubTask = edSubTask.getText().toString().trim();
                        Log.d("get Text: ", newSubTask);
                        if(!newSubTask.equals("")) {
                            RelativeLayout rlNewSubTask = new RelativeLayout(getContext());
                            RelativeLayout.LayoutParams rel = new RelativeLayout.LayoutParams
                                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            rel.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                            TextView tvSubTask = new TextView(getContext());
                            tvSubTask.setText(newSubTask);

                            ImageView ivIconDelete = new ImageView(getContext());
                            ivIconDelete.setImageResource(R.drawable.ic_baseline_close_24);
                            ivIconDelete.setLayoutParams(rel);

                            rlNewSubTask.addView(tvSubTask);
                            rlNewSubTask.addView(ivIconDelete);

                            llAddTaskName.removeView(edSubTask);
                            llAddTaskName.addView(rlNewSubTask);
                            llAddTaskName.addView(llAddSubTask);
                        } else {
                            llAddTaskName.removeView(edSubTask);
                            llAddTaskName.addView(llAddSubTask);
                        }
//                        hideKeyboard(v);
                    }
                }
            });

        });


        return view;
    }


}


package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimeTableActivity extends AppCompatActivity {

    TableRow rowMonday;
    FloatingActionButton fabAddTimeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        fabAddTimeTable = (FloatingActionButton) findViewById(R.id.fabAddTimeTable);
        fabAddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAddTimeTable.setVisibility(View.INVISIBLE);

                loadFragment(new EditTimeTableFragment());

            }
        });




        rowMonday = (TableRow) findViewById(R.id.rowMonday);
        String[] textArray = {"Lập trình ứng dụng android", "15:00-17:30", "D9-201"};
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
        linearLayout.setPadding(5,0,5,5);
        rowMonday.addView(linearLayout);
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            switch (i) {
                case 0:
                    textView.setPadding(5, 0, 5, 0);
                    break;
                default:
                    textView.setGravity(1);
                    break;
            }

            linearLayout.addView(textView);
        }


        String[] textArray2 = {"Thiết kế hướng đối tượng", "17:00-17:30", "D9-201"};
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setBackgroundColor(Color.parseColor("#353535"));
        linearLayout2.setPadding(5,0,5,5);
        rowMonday.addView(linearLayout2);
        for( int i = 0; i < textArray2.length; i++ )
        {
            TextView textView2 = new TextView(this);
            textView2.setText(textArray2[i]);
            switch (i) {
                case 0:
                    textView2.setPadding(5, 0, 5, 0);
                    break;
                default:
                    textView2.setGravity(1);
                    break;
            }

            linearLayout2.addView(textView2);
        }
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        //FragmentManager fm = getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
    public void showKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(v.getWindowToken(),InputMethodManager.SHOW_IMPLICIT);
    }
}
package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todo.Fragment.EditTimeTableFragment;
import com.example.todo.Model.DayOfWeek;
import com.example.todo.Model.TimeTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    TableRow rowMonday, rowTuesday, rowWednesday, rowThursday, rowFriday, rowSaturday, rowSunday;
    FloatingActionButton fabAddTimeTable;
    ConstraintLayout constraintLayout;
    LinearLayout linearLayoutTimeTable;
    ImageView ivBack2Menu;
    private DatabaseReference timeTable;
    private FirebaseAuth mAuth;
    String UID;
    ArrayList<TimeTable> listTimeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        timeTable = FirebaseDatabase.getInstance().getReference("time_table");
        // Get current user ID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        }

        // Set onclick for fab
        fabAddTimeTable = (FloatingActionButton) findViewById(R.id.fabAddTimeTable);
        fabAddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAddTimeTable.setVisibility(View.GONE);
                loadFragment(new EditTimeTableFragment());

            }
        });

        // close fragment (if exist) when click on screen
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentByTag("AddTimeTable");
                if (fragment != null) {
                    closeFragment(fragment);
                    hideKeyboard(v);
                    fabAddTimeTable.setVisibility(View.VISIBLE);
                }
            }
        });

        linearLayoutTimeTable = (LinearLayout) findViewById(R.id.linearLayoutTimeTable);
        linearLayoutTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentByTag("AddTimeTable");
                if (fragment != null) {
                    closeFragment(fragment);
                    hideKeyboard(v);
                    fabAddTimeTable.setVisibility(View.VISIBLE);
                }
            }
        });

        // Back to Menu Fragment
        ivBack2Menu = (ImageView) findViewById(R.id.iconBack2Menu);
        ivBack2Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get data from firebase and show
        rowMonday = (TableRow) findViewById(R.id.rowMonday);
        rowTuesday = (TableRow) findViewById(R.id.rowTuesday);
        rowWednesday = (TableRow) findViewById(R.id.rowWednesday);
        rowThursday = (TableRow) findViewById(R.id.rowThursday);
        rowFriday = (TableRow) findViewById(R.id.rowFriday);
        rowSaturday = (TableRow) findViewById(R.id.rowSaturday);
        rowSunday = (TableRow) findViewById(R.id.rowSunday);
        ArrayList<TimeTable> listTimeTable = new ArrayList<TimeTable>();
        timeTable.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeTable mTimeTable = snapshot.getValue(TimeTable.class);
                    assert mTimeTable != null;
                    if (listTimeTable.contains(mTimeTable)) {
                        continue;
                    }
                    listTimeTable.add(mTimeTable);
                    addTimeTable(mTimeTable);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(TimeTableActivity.this, Login.class);
            startActivity(intent);
        }
    }
    // [END on_start_check_user]

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        //FragmentManager fm = getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment, "AddTimeTable");

        fragmentTransaction.commit(); // save the changes
    }

    private void closeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    private void addTimeTable(TimeTable timeTable) {
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

        TextView subject = new TextView(this);
        subject.setText(timeTable.getSubject());
        subject.setGravity(1);
        linearLayout.addView(subject);

        TextView duration = new TextView(this);
        duration.setText(timeTable.getDuration());
        duration.setGravity(1);
        linearLayout.addView(duration);

        TextView location = new TextView(this);
        location.setText(timeTable.getLocation());
        location.setGravity(1);
        linearLayout.addView(location);
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(TimeTableActivity.this, "Click long môn học", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(TimeTableActivity.this);
//                ViewGroup viewGroup = findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_timetable, viewGroup, false);
//                builder.setView(dialogView);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                return false;
            }
        });
    }
}
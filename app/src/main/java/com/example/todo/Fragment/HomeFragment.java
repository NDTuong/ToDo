package com.example.todo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todo.Adapter.TaskAdapter;
import com.example.todo.Model.Task;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment {

    View view;
    private RecyclerView rcvHomeTask;
    private TaskAdapter taskAdapter;
    private TextView tvHelloUser, tvClock, tvDate;
    private ImageView ivAvatar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvHomeTask = (RecyclerView) view.findViewById(R.id.recycleViewHomeTask);
        taskAdapter = new TaskAdapter(view.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        rcvHomeTask.setLayoutManager(linearLayoutManager);

//        taskAdapter.setData(getListTask(), UID);
//        rcvHomeTask.setAdapter(taskAdapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // [HIỂN THỊ LỊCH THEO CHIỀU NGANG]
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1200);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1200);

        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                // on below line we are adding a range
                // as start date and end date to our calendar.
                .range(startDate, endDate)
                // on below line we are providing a number of dates
                // which will be visible on the screen at a time.
                .datesNumberOnScreen(7)
                // at last we are calling a build method
                // to build our horizontal recycler view.
                .build();
        // on below line we are setting calendar listener to our calendar view.
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // on below line we are printing date
                // in the logcat which is selected.
                Log.e("Date", "CURRENT DATE IS " + date);
            }
        });
        // [KẾT THÚC HIỂN THỊ LỊCH THEO CHIỀU NGANG]

        // [SET TÊN, GIỜ, NGÀY THÁNG, ẢNH]
        tvHelloUser = (TextView) view.findViewById(R.id.tvHelloUser);
        tvClock = (TextView) view.findViewById(R.id.tvClock);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);

        String name = currentUser.getDisplayName();
        tvHelloUser.setText(name);
        String currentDate = new SimpleDateFormat("E, dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        tvClock.setText(currentTime);
        tvDate.setText(currentDate);
//        String imageString = "";
//        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        ivAvatar.setImageBitmap(decodedImage);

        return view;
    }

    private List<Task> getListTask(){
        List<Task> tasks = new ArrayList<>();
//        tasks.add(new Task("Task 1", Calendar.getInstance()));
//        tasks.add(new Task("Task 2",Calendar.getInstance()));
//        tasks.add(new Task("Task 3",Calendar.getInstance()));
//        tasks.add(new Task("Task 4",Calendar.getInstance()));
//        tasks.add(new Task("Task 5",Calendar.getInstance()));
//        tasks.add(new Task("Task 6",Calendar.getInstance()));
//        tasks.add(new Task("Task 7",Calendar.getInstance()));
        return tasks;
    }
}
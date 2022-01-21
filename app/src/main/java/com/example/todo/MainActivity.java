package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todo.Adapter.TabFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity  {

    private TextView tvHelloUser, tvClock, tvDate;
    private ImageView ivAvatar;
    private FirebaseAuth mAuth;
    private TabLayout tabLayout;
    private ViewPager2 pager2;
    private TabFragmentAdapter tabFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [CHECK ĐĂNG NHẬP CHƯA?]
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        // [KẾT THÚC CHECK ĐĂNG NHẬP CHƯA?]


        // TAB VÀ FRAGMENT
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager2 = (ViewPager2) findViewById(R.id.viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        tabFragmentAdapter = new TabFragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(tabFragmentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}
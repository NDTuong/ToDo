package com.example.todo.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.todo.Adapter.ChooseIconAdapter;
import com.example.todo.Adapter.MenuAdapter;
import com.example.todo.LoginActivity;
import com.example.todo.Model.Menu;
import com.example.todo.R;
import com.example.todo.TaskActivity;
import com.example.todo.TaskActivity2;
import com.example.todo.TimeTableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    View view;
    GridView gridViewMenu;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String UID;
    List<Menu> listMenu = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_menu, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        } else {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }

        // Click vào thời khóa biểu
//        cvTimeTable = (CardView) view.findViewById(R.id.cvTimeTable);
//        cvTimeTable.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), TimeTableActivity.class);
//            startActivity(intent);
//        });

        // Click vào công việc của tôi
//        cvTask = (CardView) view.findViewById(R.id.cvAllTask);
//        cvTask.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), TaskActivity.class);
//            startActivity(intent);
//        });

//        listMenu.add(menuAddNew);
//        listMenu.add(menuTimeTable);
//        listMenu.add(menuAllTask);
//        listMenu.add(menuNote);
//        listMenu.add(menuAddNew);
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child(UID).child("list_menu").setValue(listMenu);
//        mDatabase.child(UID).child("list_menu").setValue(menuAllTask);
//        mDatabase.child(UID).child("list_menu").setValue(menuNote);
        gridViewMenu = (GridView) view.findViewById(R.id.gridViewMenu); // init GridView
        mDatabase.child(UID).child("list_menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                createMenu();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Menu m = snapshot.getValue(Menu.class);
                    listMenu.add(m);
                }
                Menu menuAddNew = new Menu(R.drawable.add, getResources().getString(R.string.menu_add));
                menuAddNew.setIdMenu(3);
                listMenu.add(menuAddNew);

                gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int go2NewActivity = listMenu.get(position).getIdMenu();
                        if(go2NewActivity == 0){
                            Intent intent = new Intent(getContext(), TimeTableActivity.class);
                            startActivity(intent);
                        }
                        if(go2NewActivity == 1){
                            Intent intent = new Intent(getContext(), TaskActivity2.class);
                            startActivity(intent);
                        }
                        if(go2NewActivity == 2){
                            Intent intent = new Intent(getContext(), TaskActivity.class);
                            startActivity(intent);
                        }
                        if(go2NewActivity == 3){
                            Menu addMenu = listMenu.get(position);
                            listMenu.remove(addMenu);
                            clickAddMenu();
                            listMenu.add(addMenu);
                        }
                    }
                });
                // Create an object of CustomAdapter and set Adapter to GirdView
                MenuAdapter customAdapter = new MenuAdapter(getContext(), listMenu);
                gridViewMenu.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // implement setOnItemClickListener event on GridView
        return view;
    }

    private int getMaxMenuID(List<Menu> menus){
        int max = -1;
        for(Menu menu : menus){
            if(menu.getIdMenu() > max){
                max = menu.getIdMenu();
            }
        }
        return max;
    }
    private void clickAddMenu(){
        Dialog mDialog;
        int [] icons = {R.drawable.add,R.drawable.task,R.drawable.schedule, R.drawable.note, R.drawable.menu_color, R.drawable.timetable, R.drawable.doings, R.drawable.menu,
                        R.drawable.ic_baseline_add_24, R.drawable.ic_baseline_star_border_24};
        mDialog=new Dialog(getContext());
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_add_menu);
        RecyclerView rcvChooseIcon = (RecyclerView) mDialog.findViewById(R.id.recycleViewIcon);
        ChooseIconAdapter chooseIconAdapter = new ChooseIconAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcvChooseIcon.setLayoutManager(linearLayoutManager);
        chooseIconAdapter.setData(icons);
        rcvChooseIcon.setAdapter(chooseIconAdapter);
        mDialog.show();
    }
    private void createMenu(){
        listMenu.clear();
        Menu menuTimeTable = new Menu(R.drawable.timetable, getResources().getString(R.string.menu_timetable));
        Menu menuAllTask = new Menu(R.drawable.task, getResources().getString(R.string.menu_task));
        Menu menuNote = new Menu(R.drawable.note, getResources().getString(R.string.menu_note));
        menuTimeTable.setIdMenu(0);
        menuAllTask.setIdMenu(1);
        menuNote.setIdMenu(2);
        listMenu.add(menuTimeTable);
        listMenu.add(menuAllTask);
        listMenu.add(menuNote);
    }
}
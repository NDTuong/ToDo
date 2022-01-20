package com.example.todo.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo.MUtils;
import com.example.todo.Model.Task;
import com.example.todo.Model.User;
import com.example.todo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.List;
import java.util.Map;

public class AddTaskFragment extends Fragment {

    View view;
    //LinearLayout llAddTaskName, llAddSubTask;
    TextInputEditText taskName, note;
    TextInputLayout tilTaskName;
    TextView tvSetNotification, tvSetShare, tvSetDeadLine, tvListEmailShare, tvHiddenUID;
    Button btnSaveTask;
    ImageView iconSetNotification, iconSetDeadline, iconSetShare,
            iconDeleteNotification, iconDeleteDeadline, iconDeleteShare;
//    Task newTask = new Task();
    boolean isImportant = false;
    String deadline, notification;

    List<String> listUIDShare = new ArrayList<String>();

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_task, container, false);

        //Lây UID của người dùng hiện tại
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            UID = currentUser.getUid();
        }

        //Kết nối database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // trỏ vào ô task name và hiện bàn phím khi click fab để thêm task
        taskName = (TextInputEditText) view.findViewById(R.id.etTaskName);
        taskName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        note = (TextInputEditText) view.findViewById(R.id.etNote);
        taskName = (TextInputEditText) view.findViewById(R.id.etTaskName);
        // set UID lên tv nhưng k hiển thị, mục đích để lấy dữ liệu để lưu.
        tvHiddenUID = (TextView) view.findViewById(R.id.tvHiddenUID);

        // Lấy isImportant để lưu vào task ở phần sau
        tilTaskName = (TextInputLayout) view.findViewById(R.id.tilTaskName);
        tilTaskName.setEndIconOnClickListener(v -> {
            if (!isImportant) {
                tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_24);
            } else {
                tilTaskName.setEndIconDrawable(R.drawable.ic_baseline_star_border_24);
            }
            isImportant = !isImportant;
        });


        // Set Deadline
        iconSetDeadline = view.findViewById(R.id.iconSetDeadline);
        iconDeleteDeadline = view.findViewById(R.id.iconDeleteDeadline);
        tvSetDeadLine = view.findViewById(R.id.tvSetDeadline);
        tvSetDeadLine.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            deadline = openDateTimePickerDialog(tvSetDeadLine, 0);
        });

        // Set Notify
        iconSetNotification = view.findViewById(R.id.iconSetNotification);
        iconDeleteNotification = view.findViewById(R.id.iconDeleteNotification);
        tvSetNotification = view.findViewById(R.id.tvSetNotification);
        tvSetNotification.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            notification = openDateTimePickerDialog(tvSetNotification, 1);
        });

        // Set Share
        tvListEmailShare = view.findViewById(R.id.tvListEmailShare);
        tvSetShare = view.findViewById(R.id.tvSetShare);
        iconSetShare = view.findViewById(R.id.iconSetShare);
        iconDeleteShare = view.findViewById(R.id.iconDeleteShare);
        tvSetShare.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            openShareDialog(tvSetShare, iconSetShare);
        });

        //Lưu Task khi nhấn save
        btnSaveTask = view.findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task newTask = new Task();
                String sTaskName = taskName.getText().toString().trim();
                if (TextUtils.isEmpty(sTaskName)) {
                    tilTaskName.setError(getString(R.string.empty_name));
                    return;
                } else {tilTaskName.setError(null);}

                if(deadline != null){
                    newTask.setDeadLine(deadline);
                }
                if(notification != null){
                    newTask.setNotify(notification);
                }
                newTask.setTaskName(sTaskName);
                String uidShareRaw = tvHiddenUID.getText().toString().trim();
                String[] uidArray = uidShareRaw.split(",");
                List<String> UIDShare = new ArrayList<>();
                List<String> UIDOwner = new ArrayList<>();
                for(String uid : uidArray){
                    UIDShare.add(uid.trim());
                }
                if(uidShareRaw.length() > 10){
                    newTask.setShareTask(true);
                }
                Map<String, List<String>> listShare = new HashMap<>();
                listShare.put("owner", UIDOwner);
                listShare.put("other", UIDShare);

                newTask.setListShare(listShare);
                String sNote = note.getText().toString().trim();
                newTask.setNote(sNote);
                newTask.setImportant(isImportant);
                Log.d("AAAA","Task: " + newTask.toString());
                Log.d("AAAA","size: " + UIDShare);
                String idTask = mDatabase.push().getKey();
                newTask.setTaskID(idTask);
                mDatabase.child(UID).child("task").child(idTask).setValue(newTask).addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm thành công!",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        tilTaskName.getEditText().setText("");
                        tvSetNotification.setText(R.string.remind);
                        tvSetNotification.setTextColor(getResources().getColor(R.color.gray_color));
                        iconSetNotification.setColorFilter(getResources().getColor(R.color.gray_color));

                        tvSetDeadLine.setText(R.string.deadline);
                        tvSetDeadLine.setTextColor(getResources().getColor(R.color.gray_color));
                        iconSetDeadline.setColorFilter(getResources().getColor(R.color.gray_color));

                        tvSetShare.setText(R.string.share_task);
                        tvSetShare.setTextColor(getResources().getColor(R.color.gray_color));
                        iconSetShare.setColorFilter(getResources().getColor(R.color.gray_color));
                        tvListEmailShare.setText("");
                        tvListEmailShare.setVisibility(View.GONE);
                        tvHiddenUID.setText("");
                        note.setText("");
                    }
                });
            }
        });

        return view;
    }



    // Mở dialog chọn ngày giờ (flag: 0 - chỉ chọn ngày | flag: 1 - chọn ngày và giờ)
    private String openDateTimePickerDialog(TextView tv, int flag) {
        final Dialog dateTimePickerDialog = new Dialog(getContext());
        Calendar calendar = Calendar.getInstance();

        dateTimePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateTimePickerDialog.setContentView(R.layout.dialog_set_date_time);
        Window window = dateTimePickerDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            //Set vị trí cho dialog
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            //click ra ngoài sẽ tắt dialog
            dateTimePickerDialog.setCancelable(true);

            DatePicker datePicker = dateTimePickerDialog.findViewById(R.id.datePiker);
            TimePicker timePicker = dateTimePickerDialog.findViewById(R.id.timePicker);
            Button btnSaveDateTime = dateTimePickerDialog.findViewById(R.id.btnSaveDateTime);

            //ẩn phần chọn giờ nếu tv là deadline
            LinearLayout linearLayout = dateTimePickerDialog.findViewById(R.id.linearLayoutSetTime);
            if(flag==0){
                if(linearLayout.getVisibility()!=View.GONE) {
                    linearLayout.setVisibility(View.GONE);
                }
            }

            btnSaveDateTime.setOnClickListener(v -> {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                if(flag==1){
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();
                    calendar.set(year, month, day, hour, minute);
                    tv.setText(MUtils.convertCalendar2String(calendar, 1));
                    tv.setTextColor(getResources().getColor(R.color.dark_color));
                    iconSetNotification.setColorFilter(getResources().getColor(R.color.dark_color));
                    iconDeleteNotification.setVisibility(View.VISIBLE);
                    dateTimePickerDialog.dismiss();
                    clickIconDelete(iconDeleteNotification, iconSetNotification, tvSetNotification, R.string.remind);
                }
                if(flag==0){
                    calendar.set(year, month, day);
                    tv.setText(MUtils.convertCalendar2String(calendar, 2));
                    tv.setTextColor(getResources().getColor(R.color.dark_color));
                    iconSetDeadline.setColorFilter(getResources().getColor(R.color.dark_color));
                    iconDeleteDeadline.setVisibility(View.VISIBLE);
                    dateTimePickerDialog.dismiss();
                    clickIconDelete(iconDeleteDeadline, iconSetDeadline, tvSetDeadLine, R.string.deadline);
                }
            });

        }
        //show dialog
        dateTimePickerDialog.show();
        return MUtils.convertCalendar2String(calendar, flag);
    }

    // Mở dialog share
    private void openShareDialog(TextView tv, ImageView icon) {

        List<String> listEmail = new ArrayList<String>();
        String listEmailString = tvListEmailShare.getText().toString().trim();
        String[] listEmailTemp = listEmailString.split(",");
        StringBuilder sListMail = new StringBuilder();

        for (String i : listEmailTemp){
            listEmail.add(i.trim());
            if(listEmailTemp.length < 2)
                sListMail.append(i);
            else
                sListMail.append(i).append(", ");
        }

//        Log.d("AAAA", "List Email Share: " + listEmail);
//        Map<String, String> listUsers = new HashMap<String, String>();
        List<String> listUidShare = new ArrayList<String>();
        List<String> listEmailShare = new ArrayList<String>();

        final Dialog shareDialog = new Dialog(getContext());
        shareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        shareDialog.setContentView(R.layout.dialog_share_task);

        Window window = shareDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();

            //Set vị trí cho dialog
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            //click ra ngoài sẽ tắt dialog
            shareDialog.setCancelable(true);

            TextInputEditText etShareEmail = shareDialog.findViewById(R.id.etShareEmail);
            etShareEmail.setText(sListMail.toString());
            Button btnSend = shareDialog.findViewById(R.id.btnSend);
            Button btnCancel = shareDialog.findViewById(R.id.btnCancel);
            TextView tvShareFail = shareDialog.findViewById(R.id.tvShareFail);
            ProgressBar progressBar = shareDialog.findViewById(R.id.progressBar);

            btnCancel.setOnClickListener(v -> shareDialog.dismiss());
            btnSend.setOnClickListener(v -> {
                progressBar.setVisibility(View.VISIBLE);
                String emails = etShareEmail.getText().toString().trim();
                String[] emailList = emails.split(",");
                List<String> key = new ArrayList<String>();
                for (String i : emailList){
                    key.add(i.trim());
                }

                // lấy danh sách user - uid
                mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            String uid = snapshot.getKey();
                            String email = user.getEmail();
                            if(key.contains(email) && !listEmailShare.contains(email)){
                                listUidShare.add(uid);
                                listEmailShare.add(email);
                                key.remove(email);
                            }
                        }
                        if(listUidShare.size() > 0){
                            tv.setTextColor(getResources().getColor(R.color.dark_color));
                            icon.setColorFilter(getResources().getColor(R.color.dark_color));
                            iconDeleteShare.setVisibility(View.VISIBLE);
                            tv.setText(R.string.shared_with);
                            tvListEmailShare.setVisibility(View.VISIBLE);

                            setText2TextViewEmail("", tvListEmailShare, listEmailShare);
                            setText2TextViewEmail("",tvHiddenUID, listUidShare);
                            iconDeleteShare.setOnClickListener(v1 -> {
                                icon.setColorFilter(getResources().getColor(R.color.gray_color));
                                iconDeleteShare.setVisibility(View.INVISIBLE);
                                tv.setTextColor(getResources().getColor(R.color.gray_color));
                                tv.setText(R.string.share_task);
                                tvListEmailShare.setText("");
                                tvListEmailShare.setVisibility(View.GONE);
                            });
                        }
                        if(key.size() > 0){
                            int count = 0;
                            tvShareFail.setVisibility(View.VISIBLE);
                            String sFailList = "Không tìm thấy: ";
                            setText2TextViewEmail(sFailList, tvShareFail, key);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            shareDialog.dismiss();
                        }
                        etShareEmail.setText("");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                });

            });
        }
        shareDialog.show();
    }

    // Khi bấm icon delete
    private void clickIconDelete(ImageView iconDelete, ImageView iconX, TextView tvX, int s){
        if(iconDelete.getVisibility()==View.VISIBLE){
            iconDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvX.setText(s);
                    tvX.setTextColor(getResources().getColor(R.color.gray_color));
                    iconX.setColorFilter(getResources().getColor(R.color.gray_color));
                    iconDelete.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private void setText2TextViewEmail(String s, TextView tv, List<String> l){
        int count = 0;
        for (String i : l){
            count++;
            if(count < l.size())  s = s + i + ", ";
            else s = s + i;
        }
        tv.setText(s);
    }

}


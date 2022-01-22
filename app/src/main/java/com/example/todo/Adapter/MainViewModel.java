package com.example.todo.Adapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;

public class MainViewModel extends ViewModel {
    // initialize variables
    MutableLiveData<String> mutableLiveData=new MutableLiveData<>();

    // create set text method
    public void setText(String s)
    {
        // set value
        mutableLiveData.setValue(s);
    }

    // create get text method
    public MutableLiveData<String> getText()
    {
        return mutableLiveData;
    }
}

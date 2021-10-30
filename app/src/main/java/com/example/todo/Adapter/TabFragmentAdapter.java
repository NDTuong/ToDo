package com.example.todo.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todo.Fragment.HomeFragment;
import com.example.todo.Fragment.MenuFragment;

public class TabFragmentAdapter extends FragmentStateAdapter {
    public TabFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new MenuFragment();
        }

        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

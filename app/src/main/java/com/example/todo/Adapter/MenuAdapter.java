package com.example.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todo.Model.Menu;
import com.example.todo.R;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    Context context;
    List<Menu> menu;

    LayoutInflater inflater;

    public MenuAdapter(Context context, List<Menu> menu) {
        this.context = context;
        this.menu = menu;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){

            convertView = inflater.inflate(R.layout.item_menu,null);

        }

        ImageView ivICon = convertView.findViewById(R.id.ivIcon);
        TextView tvMenuName = convertView.findViewById(R.id.tvMenuName);
        ivICon.setImageResource(menu.get(position).getIconName());
        tvMenuName.setText(menu.get(position).getMenuName());


        return convertView;
    }
}
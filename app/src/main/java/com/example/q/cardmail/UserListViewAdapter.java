package com.example.q.cardmail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.q.cardmail.MemoGet;
import com.example.q.cardmail.MemoListViewAdapter;
import com.example.q.cardmail.R;
import com.example.q.cardmail.UserGet;

import java.util.Collections;
import java.util.List;

public class UserListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<UserGet> data = Collections.emptyList();
    int layoutResID;

    public UserListViewAdapter(Context context, int layout, List<UserGet> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layoutResID = layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public String getItem(int position){return data.get(position).getName();}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        UserViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(layoutResID,parent,false);
            viewHolder = new UserViewHolder();
            viewHolder.uName = (TextView) convertView.findViewById(R.id.Name);
            viewHolder.uEmail = (TextView) convertView.findViewById(R.id.Email);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (UserViewHolder) convertView.getTag();
        }
        UserGet userList = data.get(position);
        TextView name = (TextView)convertView.findViewById(R.id.Name);
        name.setText(userList.getName());
        TextView email = (TextView) convertView.findViewById(R.id.Email);
        email.setText(userList.getEmail());
        return convertView;
    }

    class UserViewHolder {
        public TextView uName;
        public TextView uEmail;
    }
}

package com.example.q.cardmail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;


public class MemoListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<MemoGet> data = Collections.emptyList();
    int layoutResID;
    MemoViewHolder viewHolder;
    private Context context;

    public MemoListViewAdapter(Context context,int layout, List<MemoGet> data) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layoutResID = layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public MemoGet getItem(int position){return data.get(position);}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(layoutResID,parent,false);
            viewHolder = new MemoViewHolder();
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mDates = (TextView) convertView.findViewById(R.id.date);
            viewHolder.mContents = (TextView) convertView.findViewById(R.id.contents);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MemoViewHolder) convertView.getTag();
        }
        MemoGet memoList = data.get(position);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(memoList.getTitle());
        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(memoList.getDates());
        TextView contents = (TextView) convertView.findViewById(R.id.contents);
        contents.setText(memoList.getContents());
        return convertView;
    }

    public void setList(List<MemoGet> mg){
        this.data = mg;
    }

    public List<MemoGet> getList() {
        return data;
    }

    public View.OnClickListener listClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Touch", Toast.LENGTH_SHORT).show();
            Log.d("*************", "touch");
        }
    };

    class MemoViewHolder{
        public TextView mTitle;
        public TextView mDates;
        public TextView mContents;
    }
}


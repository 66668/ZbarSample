package com.sfs.zbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by souhitoshiyou on 2018/1/23.
 */

public class DataRecyclerViewAdapter extends RecyclerView.Adapter<DataRecyclerViewAdapter.MyHolder>{
    List<String> dataList;
    Context mContext;
    LayoutInflater inflater;
    public DataRecyclerViewAdapter(Context context){
        this.mContext =context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<String> listdata){
        this.dataList =listdata;
        notifyDataSetChanged();
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String bean = dataList.get(position);
        holder.tv.setText(bean);

    }


    @Override
    public int getItemCount() {
        return dataList.size()==0?0:dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
        }
    }
}

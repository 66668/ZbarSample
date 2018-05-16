package com.sfs.zbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sfs.zbar.R;
import com.sfs.zbar.bean.Delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author practicing
 */
public class MergeListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    //	private List<DataHolder> mDataList = new ArrayList<DataHolder>();
    private List<Delivery> infos = new ArrayList<Delivery>();
    private View.OnClickListener mDelClickListener;
    private MyLinearLayout.OnScrollListener mScrollListener;

//	public MergeListAdapter(Context context, List<DataHolder> dataList,
//			View.OnClickListener delClickListener,
//			MyLinearLayout.OnScrollListener listener) {
//		mContext = context;
//		mInflater = LayoutInflater.from(context);
//		if (dataList != null && dataList.size() > 0) {
//			mDataList.addAll(dataList);
//		}
//		mDelClickListener = delClickListener;
//		mScrollListener = listener;
//	}

    public MergeListAdapter(Context context, List<Delivery> dataList,
                            View.OnClickListener delClickListener,
                            MyLinearLayout.OnScrollListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
//        if (dataList != null && dataList.size() > 0) {
//            infos.addAll(dataList);
//        }
        this.infos = dataList;
        mDelClickListener = delClickListener;
        mScrollListener = listener;
    }

    public void removeItem(int position) {
        infos.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Delivery item) {
        infos.add(item);
        notifyDataSetChanged();
    }

    public void setList(List<Delivery> dataList) {
        this.infos = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_dispatch, null);
            holder.tv_number = (TextView) convertView
                    .findViewById(R.id.tv_number);
            holder.tv_company = (TextView) convertView.findViewById(R.id.tv_company);
            holder.tv_expnum = (TextView) convertView
                    .findViewById(R.id.tv_expnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Delivery item = infos.get(position);
        holder.tv_expnum.setText(infos.get(position).getExpNum());
        holder.tv_number.setText((infos.size() - position) + "");
        holder.tv_company.setText(infos.get(position).getExptitle());
        item.rootView = (MyLinearLayout) convertView.findViewById(R.id.lin_root);
        item.rootView.scrollTo(0, 0);
        item.rootView.setOnScrollListener(mScrollListener);

        // 点击删除实现方法一:直接处理
        // TextView delTv = (TextView) convertView.findViewById(R.id.del);
        // delTv.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // mDataList.remove(position)
        // }
        // });

        // //点击删除实现方法二:交由外部处理:
        TextView delTv = (TextView) convertView.findViewById(R.id.del);
        delTv.setOnClickListener(mDelClickListener);
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_expnum;// 快递编号
        TextView tv_number;// 序号
        TextView tv_company;
    }

}

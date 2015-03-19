package com.example.sampleandroid.biz;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SimpleAdapter extends BaseAdapter {
	protected final String TAG = getClass().getSimpleName();

	public Context mContext;
	public LayoutInflater mInflater;
	public List<? extends Object> mData;
	public View mView;

	public SimpleAdapter(Context context, List<? extends Object> data) {
		this.mContext = context;
		this.mData = data;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public SimpleAdapter(Context context, List<? extends Object> data,
			int resource) {
		this.mContext = context;
		this.mData = data;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(resource, null);
	}

	public List getList() {
		return mData;
	}

	@Override
	public int getCount() {
		if (null == mData) {
			return 0;
		}
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mView;
	}

	public void clearData() {
		if (null != mData && mData.size() > 0) {
			mData.clear();
			notifyDataSetChanged();
		}
	}

	public void updateData(List<? extends Object> data) {
		if (null == data) {
			return;
		} else {
			mData.clear();
			mData = data;
			notifyDataSetChanged();
		}
	}

}

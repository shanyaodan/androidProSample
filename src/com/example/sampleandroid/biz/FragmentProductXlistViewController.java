package com.example.sampleandroid.biz;

import java.util.ArrayList;

import android.os.Handler;
import android.text.TextUtils;

import com.example.sampleandroid.base.BaseFragment;
import com.example.sampleandroid.util.CommonUtils;
import com.example.sampleandroid.util.Constants;
import com.example.sampleandroid.util.PreferencesUtils;
import com.example.sampleandroid.xlistview.XListView;
import com.example.sampleandroid.xlistview.XListView.IXListViewListener;

public class FragmentProductXlistViewController implements IXListViewListener {
	/**
	 * 刷新
	 */
	public static final int REFRESH = 20;
	/**
	 * 加载更多
	 */
	public static final int LOADMORE = 21;

	private ArrayList mArrayList;

	private XListView mXListView;

	private boolean isLoading;

	private SimpleAdapter mBaseAdapter;

	private BaseFragment fragment;
	private final String timesubfix = "freshtime";
	private int listSign = -1;
	private Handler mHandler;

	public FragmentProductXlistViewController(BaseFragment fragment,
			SimpleAdapter mBaseAdapter, XListView mXlistView, int sign,
			Handler mHandler) {
		this.mHandler = mHandler;
		this.listSign = sign;
		this.mXListView = mXlistView;
		this.fragment = fragment;
		this.mBaseAdapter = mBaseAdapter;
		mBaseAdapter.getList();
		if (null != mBaseAdapter) {
			mXListView.setAdapter(mBaseAdapter);
		}
		mArrayList = (ArrayList) mBaseAdapter.getList();
		mXListView.setXListViewListener(this);
	}

	@Override
	public void onLoadMore() {
		if (isLoading)
			return;
		if (null != fragment) {
			isLoading = true;
			fragment.getListData(mHandler, LOADMORE, listSign);
		} else {
			stopLoad(fragment.hashCode() + timesubfix);
		}
	}

	@Override
	public void onRefresh() {
		if (isLoading)
			return;
		if (null != fragment) {
			isLoading = true;
			fragment.getListData(mHandler, REFRESH, listSign);
		}

	}
	public void setLoadState(boolean b) {
		isLoading = b;
	}
	public void stopLoad(String sign) {
		mXListView.stopLoadMore();
		mXListView.stopRefresh();
		isLoading = false;
		if (!TextUtils.isEmpty(sign)) {
			String time = PreferencesUtils.getString(sign + timesubfix);
			if (TextUtils.isEmpty(time)) {
				mXListView.setRefreshTime("刚刚");
			} else {
				mXListView.setRefreshTime(time);
			}

			PreferencesUtils.putString(sign + timesubfix,
					CommonUtils.getFormatTime(Constants.FORMAT_E));
		}
	}

	public boolean getLoadState() {
		return isLoading;
	}

	/**
	 * 追加数据
	 * 
	 * @param list
	 */
	public void addArray(ArrayList list) {
		if (null != mArrayList && null != list && list.size() > 0) {
			mArrayList.addAll(list);
		}
		mBaseAdapter.notifyDataSetChanged();
	}

	public void clearList() {
		if (null != mArrayList && null != mBaseAdapter) {
			mArrayList.clear();
			mBaseAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 刷新列表
	 * 
	 * @param list
	 */
	public void refreshArrayData(ArrayList list) {
		if (null == mArrayList) {
			mArrayList = new ArrayList();
		}

		mArrayList.clear();
		mArrayList.addAll(list);
		mBaseAdapter.notifyDataSetChanged();
	}

	public XListView getXlistView() {
		return mXListView;
	}

	public void notifyDatas() {
		if(null!=mBaseAdapter)
		mBaseAdapter.notifyDataSetChanged();
	}
	
}

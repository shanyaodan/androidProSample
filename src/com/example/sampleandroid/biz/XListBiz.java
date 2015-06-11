package com.example.sampleandroid.biz;

import java.util.ArrayList;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.sampleandroid.R;
import com.example.sampleandroid.base.BaseFragment;
import com.example.sampleandroid.http.API;
import com.example.sampleandroid.mode.BaseEntity;
import com.example.sampleandroid.widget.GifView;
import com.example.sampleandroid.xlistview.XListView;

public class XListBiz<T> implements OnItemClickListener {
	public static int LOAD_WITH_ANIM = 7;
	private XListView listView;
	private BaseFragment baseFragment;
	private ViewGroup rootView;
	private View waitinglayout, errorlayout;
	private GifView loadinggif;
	private FragmentProductXlistViewController mListViewController;
	private int sign;
	private ArrayList mArrayList;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case API.REQUEST_BEGIN:
				if (msg.arg1 == LOAD_WITH_ANIM) {
					showAnimloadingView();
				} else {
					showWaiting();
				}
				break;

			case API.REQUEST_SUCCESS:
				if (msg.arg1 == mListViewController.LOADMORE) {
					setListData((BaseEntity) msg.obj);
				} else if (msg.arg1 == mListViewController.REFRESH
						|| msg.arg1 == LOAD_WITH_ANIM) {
					mArrayList.clear();

					setListData((BaseEntity) msg.obj);
				}
				clearWaiting();
				DismissAnimloadingView();
				break;
			case API.REQUEST_FAIL:
				mListViewController.stopLoad((this.hashCode() + sign) + "");

				clearWaiting();
				DismissAnimloadingView();
				break;
			case API.REQUEST_NO_NETWORK:
				mListViewController.stopLoad((this.hashCode() + sign) + "");
				clearWaiting();
				DismissAnimloadingView();
				break;
			default:
				break;
			}
		}
	};

	public XListBiz(ViewGroup rootview, BaseFragment baseFragment, int sign) {
		this.rootView = rootview;
		this.listView = (XListView) rootview.findViewById(R.id.xlistview);

		waitinglayout = rootview.findViewById(R.id.waitinglayout);
		loadinggif = (GifView) rootview.findViewById(R.id.loading_gif);
		errorlayout = rootview.findViewById(R.id.errorlayout);

		this.baseFragment = baseFragment;
		mArrayList = new ArrayList<T>();
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		SimpleAdapter adapter = new SimpleAdapter(baseFragment.getActivity(),
				mArrayList);
		mListViewController = new FragmentProductXlistViewController(
				baseFragment, adapter, listView, sign, mHandler);
		this.sign = sign;
		listView.setOnItemClickListener(this);
	}

	/**
	 * 显示等待
	 */
	public void showWaiting() {
		 dissmissallLayout();
		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 消除等待
	 */
	public void clearWaiting() {

		if (null != waitinglayout) {
			waitinglayout.setVisibility(View.GONE);
		}

	}

	public void showErrorLayout() {
		 dissmissallLayout();
		if (null != errorlayout) {
			errorlayout.setVisibility(View.VISIBLE);
		}
	}

	public void dismissErrorLayout() {
		if (null != errorlayout) {
			errorlayout.setVisibility(View.GONE);
		}
	}

	protected void showAnimloadingView() {
		 dissmissallLayout();
		if (null != loadinggif) {
			loadinggif.startAnim();
		}
	}

	protected void DismissAnimloadingView() {
		if (null != loadinggif) {
			loadinggif.stopAnim();
		}
	}

	private void dissmissallLayout() {
		waitinglayout.setVisibility(View.VISIBLE);
		errorlayout.setVisibility(View.VISIBLE);
		loadinggif.setVisibility(View.VISIBLE);
	}

	private void setListData(BaseEntity entity) {
		// ArrayList<AskEntity> askItems = null;
		// if (null != entity && null != entity.content) {
		// ContentPaserEntity<ArrayList<AskEntity>> parser =
		// (ContentPaserEntity<ArrayList<AskEntity>>) entity.content;
		// if (null != parser.list) {
		// askItems = parser.list;
		// checkNextPage(parser.pageNo, parser.pageCount);
		// }
		// }
		// mListViewController.addArray(askItems);
		// mListViewController.stopLoad((this.hashCode() + sign) + "");

	}

	/**
	 * 
	 * @param type
	 *            动画（FindListBiz.LOAD_WITH_ANIM），更多，或刷新
	 * @param listSign
	 */
	public void getList(int type, int listSign) {

		baseFragment.getListData(mHandler, type, listSign);
	}

	private boolean checkNextPage(String currentPage, String pageCount) {
		if (TextUtils.isEmpty(pageCount)) {
			listView.setPullLoadEnable(false);
			return false;
		}
		if (TextUtils.isEmpty(currentPage)) {
			listView.setPullLoadEnable(false);
			return false;
		}
		if (TextUtils.isDigitsOnly(pageCount)
				&& TextUtils.isDigitsOnly(currentPage)) {
			int current = Integer.parseInt(currentPage);
			int count = Integer.parseInt(pageCount);
			if (current < count) {
				return true;
			}

		}
		listView.setPullLoadEnable(false);

		return false;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg3 >= 0) {

		}
	}

	public ArrayList getList() {
		return mArrayList;
	}

}

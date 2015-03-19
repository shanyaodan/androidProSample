package com.example.sampleandroid.fragment;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.example.sampleandroid.R;
import com.example.sampleandroid.activity.MainActivity;
import com.example.sampleandroid.base.BaseFragment;

public class FragmentManager {

	public static final String TAG = "FragmentManager";

	public static final int MAINACTIVITY_RECOMMAD_FRAGMENT = 1;
	public static final int MAINACTIVITY_FIND_FRAGMENT = 2;
	public static final int MAINACTIVITY_MINE_FRAGMENT = 3;
	public static final int MAINACTIVITY_ASK_FRAGMENT = 4;
	public static HashMap<String, BaseFragment> fragments = new HashMap<String, BaseFragment>();

	public static void setFragmentWithStr(FragmentActivity fragmentActivity,
			String fragmentName, int layout) {

		fragmentActivity
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(
						layout,
						BaseFragment
								.getInstance(fragmentActivity, fragmentName),
						fragmentName).commit();
	}

	public static void clearCacheFragment() {
		if (null != fragments)
			fragments.clear();
	}

	public static void setMainFragmentWithStr(
			FragmentActivity fragmentActivity, String fragmentName, int layout) {

		BaseFragment fr = BaseFragment.getInstance(fragmentActivity,
				fragmentName);
		fragmentActivity.getSupportFragmentManager().beginTransaction()
				.replace(layout, fr, fragmentName).commit();
		if (!fragments.containsKey(fragmentName)) {
			fragments.put(fragmentName, fr);
		}
	}

	public static void popFragment(FragmentActivity fragmentActivity) {

		fragmentActivity.getSupportFragmentManager().popBackStack();
		if (fragmentActivity.getSupportFragmentManager()
				.getBackStackEntryCount() <= 1) {

		}
		InputMethodManager m = (InputMethodManager) fragmentActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		m.hideSoftInputFromWindow(fragmentActivity.getWindow().getDecorView()
				.getWindowToken(), 0);
	}

	public static void transDataFragment(FragmentActivity fragmentActivity,
			String fragment, Bundle bundle) {

		fragmentActivity.getSupportFragmentManager()
				.findFragmentByTag(fragment).setArguments(bundle);
		fragmentActivity.getSupportFragmentManager().popBackStack();

	}

	public static void showMainFragment(FragmentActivity fragmentActivity,
			int type) {
		if (null == fragmentActivity) {
			return;
		}

		// clearStack(fragmentActivity);
		// List<Fragment> runningFragments = fragmentActivity
		// .getSupportFragmentManager().getFragments();

	}

	public static void clearStack(FragmentActivity fragmentActivity) {
		InputMethodManager m = (InputMethodManager) fragmentActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		m.hideSoftInputFromWindow(fragmentActivity.getWindow().getDecorView()
				.getWindowToken(), 0);
		int count = fragmentActivity.getSupportFragmentManager()
				.getBackStackEntryCount();
		clearStack(fragmentActivity, 0, count);

	}

	public static void clearStack(FragmentActivity fragmentActivity, int start,
			int end) {

		int count = fragmentActivity.getSupportFragmentManager()
				.getBackStackEntryCount();
		if (end > count || end < start) {
			return;
		}

		for (int a = start; a < end - start; a++) {
			fragmentActivity
					.getSupportFragmentManager()
					.popBackStack(
							fragmentActivity.getSupportFragmentManager()
									.getBackStackEntryAt(a).getId(),
							android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

		}

	}

	private static void setMainFragment(FragmentActivity fragmentActivity,
			String name) {
		synchronized (fragmentActivity) {

			if (fragments.containsKey(name) && null != fragments.get(name)) {
				setFragment(fragmentActivity, fragments.get(name));
			} else {
				setMainFragmentWithStr(fragmentActivity, name,
						R.id.fragmentlayout);
			}
		}
	}

	public static void setFragment(FragmentActivity fragmentActivity,
			BaseFragment fragment) {
		if (null != fragmentActivity && null != fragment) {
			fragmentActivity
					.getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.fragmentlayout, fragment,
							fragment.getClass().getName()).commit();
		}
	}

	public static void addStackFragment(FragmentActivity fragmentActivity,
			BaseFragment fragment) {

		fragmentActivity
				.getSupportFragmentManager()
				.beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
				// .setCustomAnimations(
				// R.anim.anim_enter,
				// R.anim.anim_exit,
				// R.anim.back_enter,
				// R.anim.back_exit)
				.replace(R.id.fragmentlayout, fragment)
				.addToBackStack(fragment.getClass().getName())
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commitAllowingStateLoss();

	}
}

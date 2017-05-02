package com.fips.huashun.ui.fragment;


import android.os.Bundle;
import android.view.View;

import com.fips.huashun.R;
import com.umeng.analytics.MobclickAgent;


/**
 * ����
 * 
 * @author
 */
public class ProductFragment extends BaseFragment {
	private View rootView;

	@Override
	public View getContentView() {
		if (rootView == null) {
			rootView = View.inflate(getActivity(), R.layout.fargment_head1, null);
		}
		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initActivity();
		initListener();
	}

	private void initListener() {
	}

	private void initActivity() {
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ProductFragment");
		MobclickAgent.onResume(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ProductFragment");
		MobclickAgent.onPause(getActivity());
	}
}

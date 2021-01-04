package com.daurenzg.betstars.fragments;

import java.util.Timer;
import java.util.TimerTask;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.IDialog;
import com.daurenzg.betstars.wao.ProfileItem;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BalanceFragment extends Fragment /*implements IDialog*/{
	
	public BalanceFragment(){}
	ProfileItem data;
	TextView balance;
	TextView transfer;
	Timer swipeTimer;
	int currentPage;
	TransferDialog transferDialog;
	public static final int TRANSFER_DIALOG = 1;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_balance, container, false);

        if(getArguments() != null)
        	data = (ProfileItem) getArguments().getSerializable("data");
        
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_balance, null);
        
        getActivity().getActionBar().setCustomView(actionBarLayout);
        transfer = (TextView) actionBarLayout.findViewById(R.id.menuBtnTransfer);
        transfer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				transferDialog = new TransferDialog();
				transferDialog.setTargetFragment(BalanceFragment.this, TRANSFER_DIALOG);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", data);
				transferDialog.setArguments(bundle);
				transferDialog.show(getFragmentManager().beginTransaction(), "transfer");
			}
		});
        
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_balance);
		final ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewPager.setAdapter(adapter);

		final CirclePageIndicator circleIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_balance);
		circleIndicator.setViewPager(viewPager);

		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			@Override
			public void run() {
				if (currentPage == 4) {
					currentPage = 0;
				}
				viewPager.setCurrentItem(currentPage++, true);
			}
		};

		swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(Update);
			}
		}, 500, 3000);
		balance = (TextView) rootView.findViewById(R.id.txtCoins);
		balance.setText(String.valueOf(data.getCoins()));
        return rootView;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (requestCode) {
	        case TRANSFER_DIALOG:
	            if (resultCode == Activity.RESULT_OK) {
	                Bundle bundle = data.getExtras();
	                ProfileItem dataTr = (ProfileItem) bundle.getSerializable("data");
	                balance.setText(String.valueOf(dataTr.getCoins()));
	            } else if (resultCode == Activity.RESULT_CANCELED) {
	                Toast.makeText(getActivity(), "Wrong!", Toast.LENGTH_LONG);
	            }
	            break;
	    }
	}
	
	private class ImagePagerAdapter extends PagerAdapter {
		private final int[] mImages = new int[] { R.drawable.i1, R.drawable.i2,
				R.drawable.i3, R.drawable.i4 };

		@Override
		public void destroyItem(final ViewGroup container, final int position,
				final Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}

		@Override
		public int getCount() {
			return this.mImages.length;
		}

		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {
			final Context context = getActivity();
			final ImageView imageView = new ImageView(context);
			final int padding = context.getResources().getDimensionPixelSize(
					R.dimen.padding_medium);
			imageView.setPadding(padding, padding, padding, padding);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setImageResource(this.mImages[position]);
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}

		@Override
		public boolean isViewFromObject(final View view, final Object object) {
			return view == ((ImageView) object);
		}
	}
	
	/*@Override
	public ProfileItem methodToPassDataBackToFragment(ProfileItem data) {
		// TODO Auto-generated method stub
		return null;
	}*/
}

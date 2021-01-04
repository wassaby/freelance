package com.daurenzg.betstars.fragments;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.auth.SignInActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.utils.IDialog;
import com.daurenzg.betstars.wao.ProfileItem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TransferDialog extends DialogFragment {
	IAndroidUtils androidUtils;
	EditText id;
	EditText amount;
	long coins = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.transfer_dialog, null);
		// getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		TextView title = (TextView) getDialog()
				.findViewById(android.R.id.title);
		title.setTextColor(getResources().getColor(R.color.welcome_page));

		getDialog().getWindow().setTitle(
				getResources().getString(R.string.transfer));
		getDialog().getWindow().setTitleColor(
				getResources().getColor(R.color.welcome_page));
		androidUtils = AndroidUtilsFactory.getInstanse(getActivity())
				.getAndroidUtils();
		id = (EditText) v.findViewById(R.id.id_nickname);
		amount = (EditText) v.findViewById(R.id.transferAmount);

		final ProfileItem data = (ProfileItem) getArguments().getSerializable(
				"data");
		// IDialog mHost = (IDialog)getTargetFragment();

		v.findViewById(R.id.btnSend).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new AsyncTask<Void, Long, Long>() {
							BUtilsException ex;
							@Override
							protected Long doInBackground(Void... params) {
								try {
									coins = androidUtils.transferCoins(id
											.getText().toString(), Long
											.valueOf(amount.getText()
													.toString()));
								} catch (BUtilsException e) {
									ex = e;
									e.printStackTrace();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return coins;
							}

							@Override
							protected void onPostExecute(Long result) {
								if (ex != null) {
									String toastMsg = "";
									try {
										JSONObject msg = new JSONObject(ex.getMessage());
										Iterator<String> keys= msg.keys();
										while (keys.hasNext()) 
										{
									        String keyValue = (String)keys.next();
									        String valueString = msg.getString(keyValue);
									        toastMsg = toastMsg +("\n") + valueString;
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
								} else {
									data.setCoins(coins);
									Intent i = new Intent().putExtra("data",
											data);
									getTargetFragment().onActivityResult(
											getTargetRequestCode(),
											Activity.RESULT_OK, i);
									Toast.makeText(getActivity(), "Success!",
											Toast.LENGTH_SHORT).show();
									// ((IDialog)getTargetFragment()).methodToPassDataBackToFragment(data);
									getDialog().dismiss();
								}
							}
						}.execute();
					}
				});

		v.findViewById(R.id.btnBack).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getDialog().dismiss();
					}
				});
		return v;
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

}

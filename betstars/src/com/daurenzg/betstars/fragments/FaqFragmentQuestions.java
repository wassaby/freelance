package com.daurenzg.betstars.fragments;

import com.daurenzg.betstars.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FaqFragmentQuestions extends Fragment {

	String[] items;
	ListView ls;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_faq_answers,
				container, false);
		items = getResources().getStringArray(R.array.faq_questions);
		ls = (ListView) rootView.findViewById(R.id.faqAnswerslistView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		ls.setAdapter(adapter);

		ls.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view;
				String pView = textView.getText().toString();
				Intent newIntent = new Intent(getActivity(),
						FaqAnswersActivity.class);
				newIntent.putExtra("flag", pView);
				startActivity(newIntent);
			}
		});
		return rootView;
	}

}

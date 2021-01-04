package com.daurenzg.betstars.fragments;

import com.daurenzg.betstars.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class FaqAnswersActivity extends Activity {
	TextView txtHeader;
	WebView webFaq;
	ImageView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq_answers);
		
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_faq_answer_activity, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		back = (ImageView) findViewById(R.id.iv_faq_back);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		Intent intent = getIntent();
		String header = intent.getStringExtra("flag");
		txtHeader = (TextView) findViewById(R.id.txtFaqQuestion);
		webFaq = (WebView) findViewById(R.id.webFaq);
// FAQ		
		if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_req_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_req), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_years_old_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_years_old), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_join_tour_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_join_tour), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_create_tour_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_create_tour), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_make_bets_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_make_bets), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_add_balance_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_add_balance), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.how_to_transfer_betcoins_q))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.how_to_transfer_betcoins), "text/html", "utf-8", null);
// Rules			
		}  else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_news))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_news_a), "text/html", "utf-8", null);
			//txtBody.setText(Html.fromHtml(getResources().getString(R.string.rule_news_a)));
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_messages))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_messages_a), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_tournaments))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_tournaments_a), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_prize))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_prize_a), "text/html", "utf-8", null);
			//txtBody.setText(Html.fromHtml(getResources().getString(R.string.rule_prize_a)));
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_game_process))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_game_process_a), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_calculate))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_calculate_a), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_stat))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_stat_a), "text/html", "utf-8", null);
		} else if (header.equalsIgnoreCase(getResources().getString(R.string.rule_balance))) {
			txtHeader.setText(header);
			webFaq.loadDataWithBaseURL(null, getResources().getString(R.string.rule_balance_a), "text/html", "utf-8", null);
		} else {
			
		}
	}
	
	@Override
	public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }

}

package com.daurenzg.betstars.adapter;

import java.util.Formatter;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.fragments.TournamentFragmentCurrent;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class TournamentsListAdapter extends ArrayAdapter<BTournamentItem> {

	private List<BTournamentItem> data = null;
	private Context context = null;
	private boolean isSearchTournaments = false;
	
	public TournamentsListAdapter(Context context, int resource,
			int textViewResourceId, List<BTournamentItem> objects, boolean isSearchTournaments) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
		this.isSearchTournaments = isSearchTournaments;
	}

	@Override
	public int getCount() {
		return data!=null?data.size():0;
	}

	public void setData(List<BTournamentItem> data){
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Formatter format = new Formatter();
		final int positionFinal = position;
		BTournamentItem tournamentItem = data.get(position);
		int cupLogo = R.drawable.ic_cup_almost;
		float state = (float) tournamentItem.getCurrentPlayers()/tournamentItem.getMaxPlayers();
		if (state<0.5)
			cupLogo = R.drawable.ic_cup_disabled;
		else if (state>0.5&&state<1)
			cupLogo = R.drawable.ic_cup_almost;
		else if (state == 1)
			cupLogo = R.drawable.ic_cup_ready;
		View result = inflater.inflate(R.layout.tournaments_list_item, null, true);
		ImageView imageView = (ImageView) result.findViewById(R.id.imageView1);
		imageView.setImageResource(cupLogo);
		TextView tournamentListItemTournamentIdTextView = (TextView) result.findViewById(R.id.tournamentListItemTournamentIdTextView);
		tournamentListItemTournamentIdTextView.setText(new StringBuffer("#").append(format.format("%09d%n", data.get(position).getId()).toString()));
		TextView tournamentListItemRoundTextView = (TextView) result.findViewById(R.id.tournamentListItemDaysCountTextView1);
		tournamentListItemRoundTextView.setText(new StringBuffer().append(data.get(position).getEntranceFee()));
		TextView tournamentListItemPlayersCountTextView = (TextView) result.findViewById(R.id.tournamentListItemPlayersCountTextView1);
		tournamentListItemPlayersCountTextView.setText(new StringBuffer().append(data.get(position).getCurrentPlayers()).append("/").append(data.get(position).getMaxPlayers()));
		TextView tournamentListItemStartDateTextView1 = (TextView) result.findViewById(R.id.tournamentListItemStartDateTextView);
		tournamentListItemStartDateTextView1.setText(new StringBuffer().append(data.get(position).getNumberOfRounds()).append(" days"));
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		final TextView joinImageView = (TextView) result.findViewById(R.id.joinTextView);
		if (isSearchTournaments){
			joinImageView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new AsyncTask<Void, Void, String>() {
						ProgressDialog pd = new ProgressDialog(context);
						protected void onPreExecute() {
							String waitDlgTitle = context.getString(R.string.waitDlgTitle);
							String waitDlgMessage = context.getString(R.string.waitDlgMessage);
							pd.setTitle(waitDlgTitle);
							pd.setMessage(waitDlgMessage);
							pd.setCancelable(false);
							pd.setIndeterminate(true);
							pd.show();
						}
						
						protected String doInBackground(Void... params) {
							try {
								return androidUtils.joinTournament(String.valueOf(data.get(positionFinal).getId()));
							} catch (Exception e) {
								return e.getMessage();
							}
						}
						
						protected void onPostExecute(String result) {
							pd.dismiss();
							String message = result==null||result.length()==0?"Joined successfully":result;
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							joinImageView.setVisibility(View.INVISIBLE);
							if (result==null||result.length()==0){
								data.get(positionFinal).setCurrentPlayers(data.get(positionFinal).getCurrentPlayers()+1);
							}
							try {
								List<Long> tournamentIdList = androidUtils.getCurrentTournamentIdList();
								boolean isAlreadyInList = false;
								for (Long long1 : tournamentIdList){
									if (data.get(positionFinal).getId() == long1.longValue()){
										isAlreadyInList = true;
										break;
									}
								}
								if (!isAlreadyInList){
										androidUtils.addTournamentIdToCurrentList(data.get(positionFinal).getId());
										TournamentFragmentCurrent.currentTournamentList.add(data.get(positionFinal));
										TournamentFragmentCurrent.currentTournamentListAdapter.setData(TournamentFragmentCurrent.currentTournamentList);
										TournamentFragmentCurrent.currentTournamentListAdapter.notifyDataSetChanged();
								}
								} catch (BUtilsException e) {
									Toast.makeText(context, "Could not add tournamentId to localList, "+e.getMessage(), Toast.LENGTH_SHORT).show();
								}
						}
					}.execute();
				}
			});
		}else {
			result.findViewById(R.id.joinTextView).setVisibility(View.INVISIBLE);
		}
		return result;
	}
	
}

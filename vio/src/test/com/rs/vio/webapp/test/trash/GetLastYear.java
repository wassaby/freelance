package com.rs.vio.webapp.test.trash;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetLastYear {
	public static void main(String args[]) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		for (int i = 1; i < 12; i++) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			String month = new StringBuffer().append("month.")
					.append(sdf.format(calendar.getTime())).toString();
			String year = new StringBuffer().append(
					sdfYear.format(calendar.getTime())).toString();
			System.out.println(month + " " + year);
		}
	}
}

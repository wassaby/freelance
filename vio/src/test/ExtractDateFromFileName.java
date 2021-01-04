import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtractDateFromFileName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "/data/data/com.samsung.push.service.client/+77715806779190620141520.3gp";
		String dateFormat = "ddMMyyyyHHmm";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		
		fileName = fileName.substring(fileName.length()-28, fileName.length());
		System.out.println(fileName);
		String dateString = fileName.substring(fileName.length()-16, fileName.length());
		dateString = dateString.substring(0, dateString.length()-4);
		System.out.println(dateString);
		
		try {
			Date fromDate = sdf.parse(dateString);
			System.out.println(sdf.format(fromDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}

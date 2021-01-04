import java.text.SimpleDateFormat;


public class ExtractFileNameFromFullFileName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "/data/data/com.samsung.push.service.client/+77715806779190620141520.3gp";
		String dateFormat = "ddMMyyyyHHmm";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		
		fileName = fileName.substring(fileName.length()-28, fileName.length());
		System.out.println(fileName);
		fileName = fileName.substring(0, 12);
		System.out.println(fileName);
		
		
	}

}

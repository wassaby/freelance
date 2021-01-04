package com.realsoft.utils.converter;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteArrayConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.ClassConverter;
import org.apache.commons.beanutils.converters.DoubleArrayConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FileConverter;
import org.apache.commons.beanutils.converters.FloatArrayConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongArrayConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortArrayConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.beanutils.converters.URLConverter;
import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;
import org.apache.commons.beanutils.locale.converters.BigIntegerLocaleConverter;
import org.apache.commons.beanutils.locale.converters.ByteLocaleConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.beanutils.locale.converters.DoubleLocaleConverter;
import org.apache.commons.beanutils.locale.converters.FloatLocaleConverter;
import org.apache.commons.beanutils.locale.converters.IntegerLocaleConverter;
import org.apache.commons.beanutils.locale.converters.LongLocaleConverter;
import org.apache.commons.beanutils.locale.converters.ShortLocaleConverter;
import org.apache.commons.beanutils.locale.converters.StringLocaleConverter;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.realsoft.utils.UtilsException;

public class ConverterManager {

	private static Logger log;

	private boolean isInitialized = false;

	private String dateFormat = null;

	private Pattern digitPattern = null;

	private Pattern numberPattern = null;

	private SimpleDateFormat datePattern = null;

	public ConverterManager(String dateFormat) {
		this.dateFormat = dateFormat;
		digitPattern = Pattern.compile("(\\d)+");
		numberPattern = Pattern.compile("(\\d)*(\\.){1}(\\d)+");
		datePattern = new SimpleDateFormat(dateFormat);
	}

	public void initialize() {
		if (!isInitialized) {
			ConvertUtils.register(new BooleanArrayConverter(
					new Boolean[] { Boolean.FALSE }), Boolean[].class);
			ConvertUtils.register(new ByteArrayConverter(new Byte[] { Byte
					.valueOf((byte) 0) }), Byte[].class);
			ConvertUtils.register(new CharacterArrayConverter(
					new Character[] { Character.valueOf(' ') }),
					Character[].class);
			ConvertUtils.register(new DoubleArrayConverter(
					new Double[] { Double.valueOf(0.0) }), Double[].class);
			ConvertUtils.register(new FloatArrayConverter(new Float[] { Float
					.valueOf(0) }), Float[].class);
			ConvertUtils.register(new IntegerArrayConverter(
					new Integer[] { Integer.valueOf(0) }), Integer[].class);
			ConvertUtils.register(new LongArrayConverter(new Long[] { Long
					.valueOf(0) }), Long[].class);
			ConvertUtils.register(new ShortArrayConverter(new Short[] { Short
					.valueOf((short) 0) }), Short[].class);
			ConvertUtils.register(
					new StringArrayConverter(new String[] { "" }),
					String[].class);

			ConvertUtils.register(new DateLocaleConverter(Locale.getDefault(),
					dateFormat), Date.class);
			ConvertUtils.register(new CalendarLocaleConverter(Locale
					.getDefault(), dateFormat), Calendar.class);

			ConvertUtils.register(new BigDecimalLocaleConverter(
					BigDecimal.ZERO, Locale.getDefault()), BigDecimal.class);
			ConvertUtils.register(new BigIntegerLocaleConverter(
					BigInteger.ZERO, Locale.getDefault()), BigInteger.class);
			ConvertUtils.register(new ByteLocaleConverter(Byte
					.valueOf((byte) 0), Locale.getDefault()), Byte.class);
			ConvertUtils.register(new ByteLocaleConverter(Byte
					.valueOf((byte) 0), Locale.getDefault()), Byte.TYPE);
			ConvertUtils.register(new DoubleLocaleConverter(Double.valueOf(0)),
					Double.class);
			ConvertUtils.register(new DoubleLocaleConverter(Double.valueOf(0)),
					Double.TYPE);
			ConvertUtils.register(new FloatLocaleConverter(Float.valueOf(0),
					Locale.getDefault()), Float.class);
			ConvertUtils.register(new FloatLocaleConverter(Float.valueOf(0),
					Locale.getDefault()), Float.TYPE);
			ConvertUtils.register(new IntegerLocaleConverter(
					Integer.valueOf(0), Locale.getDefault()), Integer.class);
			ConvertUtils.register(new IntegerLocaleConverter(
					Integer.valueOf(0), Locale.getDefault()), Integer.TYPE);
			ConvertUtils.register(new LongLocaleConverter(Long.valueOf(0),
					Locale.getDefault()), Long.class);
			ConvertUtils.register(new LongLocaleConverter(Long.valueOf(0),
					Locale.getDefault()), Long.TYPE);
			ConvertUtils.register(new ShortLocaleConverter(Short
					.valueOf((short) 0), Locale.getDefault()), Short.class);
			ConvertUtils.register(new ShortLocaleConverter(Short
					.valueOf((short) 0), Locale.getDefault()), Short.TYPE);
			ConvertUtils.register(new StringLocaleConverter("", Locale
					.getDefault()), String.class);

			ConvertUtils.register(new BigDecimalConverter(BigDecimal.ZERO),
					BigDecimal.class);
			ConvertUtils.register(new BigIntegerConverter(BigInteger.ZERO),
					BigInteger.class);
			ConvertUtils.register(new BooleanConverter(Boolean.FALSE),
					Boolean.class);
			ConvertUtils.register(new BooleanConverter(Boolean.FALSE),
					Boolean.TYPE);
			ConvertUtils.register(new ByteConverter(Byte.valueOf((byte) 0)),
					Byte.class);
			ConvertUtils.register(new ByteConverter(Byte.valueOf((byte) 0)),
					Byte.TYPE);
			ConvertUtils.register(
					new CharacterConverter(Character.valueOf(' ')),
					Character.class);
			ConvertUtils.register(
					new CharacterConverter(Character.valueOf(' ')),
					Character.TYPE);
			ConvertUtils.register(new ClassConverter(), Class.class);
			ConvertUtils.register(new DoubleConverter(Double.valueOf(0)),
					Double.class);
			ConvertUtils.register(new DoubleConverter(Double.valueOf(0)),
					Double.TYPE);
			ConvertUtils.register(new FileConverter(), File.class);
			ConvertUtils.register(new FloatConverter(Float.valueOf(0)),
					Float.class);
			ConvertUtils.register(new FloatConverter(Float.valueOf(0)),
					Float.TYPE);
			ConvertUtils.register(new IntegerConverter(Integer.valueOf(0)),
					Integer.class);
			ConvertUtils.register(new IntegerConverter(Integer.valueOf(0)),
					Integer.TYPE);
			ConvertUtils.register(new LongConverter(Long.valueOf(0)),
					Long.class);
			ConvertUtils
					.register(new LongConverter(Long.valueOf(0)), Long.TYPE);
			ConvertUtils.register(new ShortConverter(Short.valueOf((short) 0)),
					Short.class);
			ConvertUtils.register(new ShortConverter(Short.valueOf((short) 0)),
					Short.TYPE);
			ConvertUtils.register(new SqlDateConverter(Calendar.getInstance()
					.getTime()), java.sql.Date.class);
			ConvertUtils.register(new SqlTimeConverter(Calendar.getInstance()
					.getTime()), Time.class);

			ConvertUtils.register(new SqlTimestampConverter(Calendar
					.getInstance().getTime()), Timestamp.class);
			ConvertUtils.register(new StringConverter(), String.class);
			ConvertUtils.register(new URLConverter(""), URL.class);

			isInitialized = true;
		}
	}

	public void register(Converter object, Class clazz) {
		ConvertUtils.register(object, clazz);
	}

	public Object getObject(String value) {
		try {
			datePattern.parse(value);
			return ConvertUtils.convert(value, Date.class);
		} catch (Exception e) {
			if (numberPattern.matcher(value).find()) {
				return ConvertUtils.convert(value, Double.class);
			} else if (digitPattern.matcher(value).find()) {
				return ConvertUtils.convert(value, Long.class);
			} else {
				return ConvertUtils.convert(value, String.class);
			}
		}
	}

	public Object getObject(String value, Class clazz) {
		return ConvertUtils.convert(value, clazz);
	}

	public Object getDefaultObject(Class clazz) throws UtilsException {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new UtilsException(e);
		}
	}

	public Object getObject(String[] value) {
		if (value.length > 0) {
			try {
				datePattern.parse(value[0]);
				return ConvertUtils.convert(value, Date.class);
			} catch (Exception e) {
				if (numberPattern.matcher(value[0]).find()) {
					return ConvertUtils.convert(value, Double.class);
				} else if (digitPattern.matcher(value[0]).find()) {
					return ConvertUtils.convert(value, Long.class);
				} else {
					return ConvertUtils.convert(value, String.class);
				}
			}
		}
		return new String[] { "zero length" };
	}

	public String getString(Timestamp object) {
		return datePattern.format(new Date(object.getTime()));
	}

	public String getString(Date object) {
		return datePattern.format(object);
	}

	public String getString(Calendar object) {
		return datePattern.format(object.getTime());
	}

	public String getString(GregorianCalendar object) {
		return datePattern.format(object.getTime());
	}

	// public String getString(Object object) {
	// return ConvertUtils.convert(object);
	// }

	public String getDateTimeString(Calendar object) {
		return getDateTimeString(object.getTime());
	}

	public String getDateTimeString(Date object) {
		return datePattern.format(object);
	}

	public static void main(String[] args) {
		DOMConfigurator
				.configure("D:/projects/billing/billing-jws/build/dist/router-service-webapp/WEB-INF/log4j.xml");
		log = Logger.getLogger(ConverterManager.class);
		try {
			ConverterManager manager = new ConverterManager("dd.MM.yyyy");
			manager.initialize();
			Date date = (Date) manager.getObject("01.01.2005", Date.class);
			log.info(date);
			Calendar date2 = (Calendar) manager.getObject("01.01.2005",
					Calendar.class);
			log.info(date2);
		} catch (Exception e) {
			log.error("Could not convert", e);
		}
	}

}

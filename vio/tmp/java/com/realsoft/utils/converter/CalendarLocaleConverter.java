/*
 * Created on 03.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CalendarLocaleConverter.java,v 1.2 2016/04/15 10:37:44 dauren_home Exp $
 */
package com.realsoft.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.locale.BaseLocaleConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CalendarLocaleConverter extends BaseLocaleConverter {

	/** All logging goes through this logger */
	private static Log log = LogFactory.getLog(CalendarLocaleConverter.class);

	/** Should the date conversion be lenient? */
	boolean isLenient = false;

	// ----------------------------------------------------------- Constructors

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs. The locale is the default locale for this
	 * instance of the Java Virtual Machine and an unlocalized pattern is used
	 * for the convertion.
	 * 
	 */
	public CalendarLocaleConverter() {

		this(false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs. The locale is the default locale for this
	 * instance of the Java Virtual Machine.
	 * 
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(boolean locPattern) {

		this(Locale.getDefault(), locPattern);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs. An unlocalized pattern is used for the
	 * convertion.
	 * 
	 * @param locale
	 *            The locale
	 */
	public CalendarLocaleConverter(Locale locale) {

		this(locale, false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs.
	 * 
	 * @param locale
	 *            The locale
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(Locale locale, boolean locPattern) {

		this(locale, (String) null, locPattern);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs. An unlocalized pattern is used for the
	 * convertion.
	 * 
	 * @param locale
	 *            The locale
	 * @param pattern
	 *            The convertion pattern
	 */
	public CalendarLocaleConverter(Locale locale, String pattern) {

		this(locale, pattern, false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will throw a {@link org.apache.commons.beanutils.ConversionException} if
	 * a conversion error occurs.
	 * 
	 * @param locale
	 *            The locale
	 * @param pattern
	 *            The convertion pattern
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(Locale locale, String pattern,
			boolean locPattern) {

		super(locale, pattern, locPattern);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs. The
	 * locale is the default locale for this instance of the Java Virtual
	 * Machine and an unlocalized pattern is used for the convertion.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 */
	public CalendarLocaleConverter(Object defaultValue) {

		this(defaultValue, false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs. The
	 * locale is the default locale for this instance of the Java Virtual
	 * Machine.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(Object defaultValue, boolean locPattern) {

		this(defaultValue, Locale.getDefault(), locPattern);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs. An
	 * unlocalized pattern is used for the convertion.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 * @param locale
	 *            The locale
	 */
	public CalendarLocaleConverter(Object defaultValue, Locale locale) {

		this(defaultValue, locale, false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 * @param locale
	 *            The locale
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(Object defaultValue, Locale locale,
			boolean locPattern) {

		this(defaultValue, locale, null, locPattern);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs. An
	 * unlocalized pattern is used for the convertion.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 * @param locale
	 *            The locale
	 * @param pattern
	 *            The convertion pattern
	 */
	public CalendarLocaleConverter(Object defaultValue, Locale locale,
			String pattern) {

		this(defaultValue, locale, pattern, false);
	}

	/**
	 * Create a {@link org.apache.commons.beanutils.locale.LocaleConverter} that
	 * will return the specified default value if a conversion error occurs.
	 * 
	 * @param defaultValue
	 *            The default value to be returned
	 * @param locale
	 *            The locale
	 * @param pattern
	 *            The convertion pattern
	 * @param locPattern
	 *            Indicate whether the pattern is localized or not
	 */
	public CalendarLocaleConverter(Object defaultValue, Locale locale,
			String pattern, boolean locPattern) {

		super(defaultValue, locale, pattern, locPattern);
	}

	// --------------------------------------------------------- Methods

	/**
	 * Returns whether date formatting is lenient.
	 * 
	 * @return true if the <code>DateFormat</code> used for formatting is
	 *         lenient
	 * @see java.text.DateFormat#isLenient
	 */
	public boolean isLenient() {
		return isLenient;
	}

	/**
	 * Specify whether or not date-time parsing should be lenient.
	 * 
	 * @param lenient
	 *            true if the <code>DateFormat</code> used for formatting
	 *            should be lenient
	 * @see java.text.DateFormat#setLenient
	 */
	public void setLenient(boolean lenient) {
		isLenient = lenient;
	}

	// --------------------------------------------------------- Methods

	/**
	 * Convert the specified locale-sensitive input object into an output object
	 * of the specified type.
	 * 
	 * @param value
	 *            The input object to be converted
	 * @param pattern
	 *            The pattern is used for the convertion
	 * 
	 * @exception org.apache.commons.beanutils.ConversionException
	 *                if conversion cannot be performed successfully
	 */
	protected Object parse(Object value, String pattern) throws ParseException {
		SimpleDateFormat formatter = getFormatter(pattern, locale);
		if (locPattern) {
			formatter.applyLocalizedPattern(pattern);
		} else {
			formatter.applyPattern(pattern);
		}
		Date date = formatter.parse((String) value);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Gets an appropriate <code>SimpleDateFormat</code> for given locale,
	 * default Date format pattern is not provided.
	 */
	private SimpleDateFormat getFormatter(String pattern, Locale locale) {
		// This method is a fix for null pattern, which would cause
		// Null pointer exception when applied
		// Note: that many constructors default the pattern to null,
		// so it only makes sense to handle nulls gracefully
		if (pattern == null) {
			pattern = locPattern ? new SimpleDateFormat().toLocalizedPattern()
					: new SimpleDateFormat().toPattern();
			log.warn("Null pattern was provided, defaulting to: " + pattern);
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
		format.setLenient(isLenient);
		return format;
	}
}

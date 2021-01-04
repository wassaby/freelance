/*
 * Created on 14.11.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: PrinterUtils.java,v 1.2 2016/04/15 10:37:45 dauren_home Exp $
 */
package com.realsoft.utils.nativelib;

import org.apache.log4j.Logger;

import com.realsoft.utils.UtilsException;

public class PrinterUtils {

	private static Logger log = Logger.getLogger(PrinterUtils.class);

	public static final String MONITORING_NATIVE_LIBRARY_NAME = "printerMonitoring";

	public static final String PRINTING_NATIVE_LIBRARY_NAME = "printing";

	public PrinterUtils() {
		super();
	}

	public void initialize() throws UtilsException {
		try {
			System.loadLibrary(MONITORING_NATIVE_LIBRARY_NAME);
			System.loadLibrary(PRINTING_NATIVE_LIBRARY_NAME);
		} catch (Exception e) {
			log.error("Could not load native library", e);
		}
		if (!initializeMonitoring()) {
			throw new UtilsException("Could not initalize printer util");
		}
	}

	public static int PRINTER_MODEL_KPM216H_300 = 100;

	/**
	 * Библиотека мониторинга, подгружает драйвер принтера.
	 * 
	 * @return В случае успешной инициализации мониторинга возвращает true. Если
	 *         Функция возвращает false, то (однозначно!) на киоске не
	 *         установлен драйвер для мониторинга принтера.
	 */
	public synchronized native boolean initializeMonitoring();

	/**
	 * Де-инициализирует мониторинг.
	 * 
	 * @return В случае успешной де-инициализации возвращает true.
	 */
	public synchronized native boolean disposeMonitoring();

	/**
	 * Проверяет наличие бумаги в принтере.
	 * 
	 * @return Возвращает true, если в принтере нет бумаги.
	 */
	public synchronized native boolean isPrinterOutOfPaper(int printerIndex,
			int printerModel);

	/**
	 * Проверяет, заканчивается ли бумага в принтере.
	 * 
	 * @return Если бумага заканчивается ф-я возвращает true.
	 */
	public synchronized native boolean isNearEndOfPaper(int printerIndex,
			int printerModel);

	/**
	 * Проверяет открыта ли верхняя крышка принтера.
	 * 
	 * @return Если крышка открыта, ф-я возвращает true.
	 */
	public synchronized native boolean isPrinterCoverOpened(int printerIndex,
			int printerModel);

	/**
	 * Проверяет не застряла ли бумага в принтере.
	 * 
	 * @return Если бумага застряла, ф-я возвращает true.
	 */
	public synchronized native boolean isPaperJam(int printerIndex,
			int printerModel);

	/**
	 * Возвращает модель принтера.
	 * 
	 * @param printerIndex
	 *            Индекс (от 0) принтера
	 * @return Модель принтера
	 */
	public synchronized native int getPrinterModel(int printerIndex);

	/**
	 * Сбрасывает принтер.
	 * 
	 */
	public synchronized native int resetPrinter(int printerIndex,
			int printerModel);

	/**
	 * Возвращает кол-во подключенных к киоску и поддерживающих мониторинг
	 * принтеров.
	 * 
	 * @return Кол-во принтеров.
	 * 
	 */
	public synchronized native int getPrintersCount();

	/**
	 * Выводит на печать строку.
	 * 
	 * @param printerName
	 *            Индекс принтера.
	 * @param fomtSize
	 *            Размер шрифта
	 * @param buff
	 *            Массив байт для печати.
	 * @return Количество нарезанных листов
	 */

	public synchronized native int printBytes(String printerName, int fomtSize,
			byte[] buff);

}
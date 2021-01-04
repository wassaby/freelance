/*
 * Created on 27.06.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBCheckBox.java,v 1.2 2016/04/15 10:37:08 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.util.List;

/**
 * Модель данных для графической компоненты группа checkbox. Документацию по
 * использованию графических компонент вы можете найти в {@link AbstractControl}
 * 
 * @author dimad
 */
public interface IBCheckBox extends IBControl {

	/**
	 * Возращает набор элементов {@link BCheckBoxItem}, которая является
	 * моделью данных для данной компоненты графического интерфейса
	 * 
	 * @return
	 * @throws CommonsControlException
	 */
	List<BCheckBoxItem> getCheckBoxItems() throws CommonsControlException;

	/**
	 * Устанавливает набор элементов {@link BCheckBoxItem}, тем самым
	 * устанавливая модель данных для данной компоненты графического интерфейса
	 * 
	 * @param radioItems
	 *            набор элементов {@link BCheckBoxItem}
	 * @throws CommonsControlException
	 */
	void setCheckBoxItems(List<BCheckBoxItem> radioItems)
			throws CommonsControlException;

	/**
	 * Добавляет элемент в набор элементов {@link BCheckBoxItem}, тем самым
	 * меняя модель данных для данной компоненты графического интерфейса
	 * 
	 * @param item
	 *            элемент {@link BCheckBoxItem}
	 * @throws CommonsControlException
	 */
	void addCheckBoxItem(BCheckBoxItem item) throws CommonsControlException;

	/**
	 * Удаляет элемент из набора элементов {@link BCheckBoxItem}, тем самым
	 * меняя модель данных для данной компоненты графического интерфейса
	 * 
	 * @param item
	 *            элемент {@link BCheckBoxItem}
	 * @throws CommonsControlException
	 */
	void removeValue(BCheckBoxItem item) throws CommonsControlException;

	/**
	 * Проверяет существует ли элемент в наборе элементов {@link BCheckBoxItem}
	 * модели данных данной компоненты графического интерфейса
	 * 
	 * @param item
	 *            {@link BCheckBoxItem}
	 * @return Если елемент существет возращает true, в противном случает false
	 * @throws CommonsControlException
	 */
	boolean isValueExist(BCheckBoxItem item) throws CommonsControlException;
}

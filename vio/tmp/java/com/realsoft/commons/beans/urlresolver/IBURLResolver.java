package com.realsoft.commons.beans.urlresolver;

import java.util.List;
import java.util.Map;

import com.realsoft.commons.beans.autopayment.IBAutopayment;
import com.realsoft.commons.beans.epay.BEpayException;
import com.realsoft.commons.beans.inventory.IBInventory;
import com.realsoft.commons.beans.isalive.IBIsAlive;
import com.realsoft.commons.beans.mail.IBMail;
import com.realsoft.commons.beans.outqueue.IBQueueIsOut;
import com.realsoft.commons.beans.payment.IBPayment;
import com.realsoft.commons.beans.registration.IBRegistration;
import com.realsoft.commons.beans.report.abonent.IBReport;

/**
 * Используется в компонентах: {@link IBAutopayment}, {@link IBInventory},
 * {@link IBIsAlive}, {@link IBMail}, {@link IBQueueIsOut}, {@link IBPayment},
 * {@link IBRegistration}, {@link IBReport}. Эземпляр компоненты используется
 * в компонетном менеджере работающем в режиме 'router'. Эземпляр компоненты
 * инициализируется из XML документа наименование, которого определяется
 * настроечной переменной phone-file Пример XML документа. <code>
 <map>
 <!-- Aktubinsk -->
 <map-item town-name="aktubinsk" host="10.1.1.1" town-id="1" uri="https://10.161.1.32:8443/billing-server/"> 
 <code>313</code>
 <code>314</code>
 </map-item>
 </map>
 </code>
 * 
 * @author dimad
 */
public interface IBURLResolver {

	/**
	 * Получает URI веб сервера биллинга по номеру телефона. Например:
	 * <code>getUriByPhone("3132222000")</code> определит URI веб сервера в
	 * Актюбинске. Т.к. один и тот же URI может обслуживать несколько кодов,
	 * алгоритм определения URI следующий: Производится поиск поданного номера
	 * телефона среди всех кодов городов. Если такой не найден, от номера
	 * телефона отрезается правый символ. Действие повторяется снова. Если
	 * строка с номером телефона достигает нулевой длинны, но код не найден,
	 * выбрасывается исключение.
	 * 
	 * @param phone
	 *            10-и значный номер телефона
	 * @return URI веб сервера
	 * @throws BEpayException
	 */
	public String getUriByPhone(String phone) throws BURLResolverException;
	
	/**
	 * Получает IP адрес хоста веб сервера по номеру телефона. Например:
	 * <code>getHostByPhone("3132222000")</code> определит IP адрес хоста веб
	 * сервера в Актюбинске. Т.к. один и тот же URI может обслуживать несколько
	 * кодов, алгоритм определения URI следующий: Производится поиск поданного
	 * номера телефона среди всех кодов городов. Если такой не найден, от номера
	 * телефона отрезается правый символ. Действие повторяется снова. Если
	 * строка с номером телефона достигает нулевой длинны, но код не найден,
	 * выбрасывается исключение.
	 * 
	 * @param phone
	 *            10-и значный номер телефона
	 * @return IP адрес веб сервера
	 * @throws BEpayException
	 */
	public String getHostByPhone(String phone) throws BURLResolverException;

	/**
	 * Получает URI веб сервера биллинга по коду города. Например:
	 * <code>getUriByCode("313")</code> определит URI веб сервера в
	 * Актюбинске. Производит поиск кодов среди всех кодов загруженных из
	 * настроечного файла. Если код не найден, выбрасывается исключение.
	 * 
	 * @param code
	 *            код города
	 * @return URI веб сервера
	 * @throws BEpayException
	 */
	public String getUriByCode(String code) throws BURLResolverException;

	/**
	 * Генерирует список объектов аттрибутов городов
	 * 
	 * @see BTownItem
	 * @return набор {@link BTownItem}
	 * @throws BEpayException
	 */
	public List<BTownItem> getTownList() throws BURLResolverException;

	/**
	 * Получает URI веб сервера биллинга по идентификатору города. Например:
	 * <code>getUriByTownId(1)</code> определит URI веб сервера в Актюбинске.
	 * Производит поиск идентификаторов городов всех идентификаторов загруженных
	 * из настроечного файла. Если идентификатор города не найден, выбрасывается
	 * исключение.
	 * 
	 * @param townId
	 *            идентификатор города
	 * @return URI веб сервера
	 * @throws BEpayException
	 */
	public String getUriByTownId(long townId) throws BURLResolverException;

	/**
	 * Определяет IP адрес хоста веб сервера биллинга по идентификатору города.
	 * Например: <code>getHostByTownId(1)</code> определит IP адрес хоста веб
	 * сервера в Актюбинске. Производит поиск идентификаторов городов всех
	 * идентификаторов загруженных из настроечного файла. Если идентификатор
	 * города не найден, выбрасывается исключение.
	 * 
	 * @param townId
	 *            идентификатор города
	 * @return IP адрес хоста веб сервера биллинга
	 * @throws BEpayException
	 */
	public String getHostByTownId(long townId) throws BURLResolverException;

	/**
	 * Генерирует список URI всех хостов веб серверов биллинга
	 * 
	 * @return набор URI веб сервера
	 * @throws BEpayException
	 */
	public List<String> getAllUri() throws BURLResolverException;

	/**
	 * Генерирует список URI всех хостов веб серверов биллинга соотретсвующих
	 * списку телефонов. Генерирует карту соответсвий между URI адресами и
	 * наборами телефонов. Разбивает набор телефонов по группам, каждая из
	 * которых соотвует отдельному URI.
	 * 
	 * @param phones
	 *            набор телефонов
	 * @return
	 * @throws BEpayException
	 */
	public Map<String, Object> getAllUri(List<String> phones)
			throws BURLResolverException;

}

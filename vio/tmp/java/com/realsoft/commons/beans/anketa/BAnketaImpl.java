/*
 * Created on 11.07.2006
 *
 * Realsoft Ltd.
 * Roman Rychkov.
 * $Id: BAnketaImpl.java,v 1.2 2016/04/15 10:37:35 dauren_home Exp $
 */
package com.realsoft.commons.beans.anketa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.db.CommonOperations;
import com.realsoft.utils.string.StringUtils;

public class BAnketaImpl implements IBAnketa {

	private static Logger log = Logger.getLogger(BAnketaImpl.class);

	private BasicDataSource dataSource = null;

	private long nullParent = -111;

	private static final String GET_QUESTIONS_STMNT = new StringBuffer()
			.append("select t.id, t.name from un.questions t").toString();

	private static final String GET_ANSWERES_STMNT = new StringBuffer()
			.append(
					"select a.id, a.question_id, a.name, q.note, nvl(a.parent_id,-111) ")
			.append(
					"from un.answers a, un.questions q where q.id = a.question_id")
			.toString();

	public SortedMap<BQuestionItem, SortedMap<Long, BAnswereItem>> getQuestionsAnsweres()
			throws BAnketaException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Long, String> questions = new HashMap<Long, String>();
		List<BAnswereItem> answeres = new ArrayList<BAnswereItem>();
		SortedMap<BQuestionItem, SortedMap<Long, BAnswereItem>> retval = new TreeMap<BQuestionItem, SortedMap<Long, BAnswereItem>>(
				new BQuestionComparator());
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(GET_QUESTIONS_STMNT);
			log.debug("GET_QUESTIONS_STMNT = " + GET_QUESTIONS_STMNT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				questions.put(rs.getLong(1), rs.getString(2));
			}
			pstmt.close();
			pstmt = conn.prepareStatement(GET_ANSWERES_STMNT);
			log.debug("GET_ANSWERES_STMNT = " + GET_ANSWERES_STMNT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answeres.add(new BAnswereItem(rs.getLong(1), rs.getLong(2), rs
						.getString(3), rs.getString(4), rs.getLong(5), null,
						null));
			}
			if (questions.size() == 0 || answeres.size() == 0)
				throw new BAnketaException(
						"The quwestions or answeres are empty",
						"anketa.empty.error", "anketa.empty.error.message");
			/**
			 * builds tree
			 */
			for (Iterator<BAnswereItem> iterator = answeres.iterator(); iterator
					.hasNext();) {
				BAnswereItem item = iterator.next();
				if (item.getParentId() == nullParent)
					continue;
				for (Iterator<BAnswereItem> iter = answeres.iterator(); iter
						.hasNext();) {
					BAnswereItem answereItem = iter.next();
					if (item.getParentId() == answereItem.getId()) {
						answereItem.addChildren(item);
						item.setParent(answereItem);
					}
				}
			}

			log.info("Tracing ...");
			for (Iterator<BAnswereItem> iter = answeres.iterator(); iter
					.hasNext();) {
				BAnswereItem answereItem = iter.next();
				if (answereItem.hasChildren()) {
					log.info("Answere: " + answereItem.getName()
							+ " has the folowing subansweres:");
					for (Iterator<BAnswereItem> iterator = answereItem
							.getChildren().iterator(); iterator.hasNext();) {
						BAnswereItem item = iterator.next();
						log.info(item.toString() + "\n\t");
					}
				}
			}

			log.info("Trace success");

			for (Iterator<Long> iter = questions.keySet().iterator(); iter
					.hasNext();) {
				long questionId = iter.next();
				SortedMap<Long, BAnswereItem> mapAnsweres = new TreeMap<Long, BAnswereItem>(
						new BAnswereComparator());
				BAnswereItem item = null;
				for (Iterator<BAnswereItem> answeresIter = answeres.iterator(); answeresIter
						.hasNext();) {
					item = answeresIter.next();
					if (item.getQuestionId() == questionId) {
						mapAnsweres.put(item.getId(), new BAnswereItem(item
								.getId(), item.getQuestionId(), item.getName(),
								item.getDisplayType(), item.getParentId(), item
										.getParent(), item.getChildren()));
					}
				}
				retval.put(new BQuestionItem(questionId, questions
						.get(questionId)), mapAnsweres);
			}
		} catch (SQLException e) {
			throw new BAnketaException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ABONENT_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} catch (NoSuchElementException exception) {
			log.error("Could not get abonent info", exception);
			throw new BAnketaException(CommonsBeansConstants.ERR_ACCOUNT,
					CommonsBeansConstants.ABONENT_ERROR,
					exception.getMessage(), exception);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
		return retval;
	}

	private static String FILL_ANKETA_STMT = "{call un.pkg_questionnaire.set_questionnaire( ?, ?, ?, ? ) }";

	public void fillAnketa(List<BRowToInsertItem> list) throws BAnketaException {
		for (BRowToInsertItem insertItem : list)
			log.debug(insertItem.toString());
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			log.debug("FILL_ANKETA_STMT = " + FILL_ANKETA_STMT);
			for (BRowToInsertItem insertItem : list) {
				cstmt = conn.prepareCall(FILL_ANKETA_STMT);
				cstmt.setLong(1, insertItem.getQuestionId());
				cstmt.setLong(2, insertItem.getAnswereId());
				cstmt.setLong(3, insertItem.getAccountUsrId());
				cstmt.setString(4, insertItem.getNote());
				cstmt.execute();
				cstmt.close();
			}
			CommonOperations.commitConnection(conn);
		} catch (SQLException e) {
			throw new BAnketaException(
					CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.FILL_ANKETA_ERROR
							+ StringUtils.getOraErrorCodeDotend(e.getMessage())[0],
					e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(cstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	private static String IS_ANKETA_ALREARY_FILLED_STMNT = new StringBuffer()
			.append(
					"select count(1) from un.questionnaire t where t.account_usr_id = ? ")
			.toString();

	public boolean isAlreadyFilled(long accountId) throws BAnketaException {
		log.debug("IS_ANKETA_ALREARY_FILLED_STMNT = "
				+ IS_ANKETA_ALREARY_FILLED_STMNT);
		log.debug("accountId = " + accountId);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(IS_ANKETA_ALREARY_FILLED_STMNT);
			pstmt.setLong(1, accountId);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getLong(1) > 0;
		} catch (SQLException e) {
			log.error("Could not check: is anketa filled", e);
			throw new BAnketaException(CommonsBeansConstants.ERR_SYSTEM,
					CommonsBeansConstants.ANKETA_ERROR, e.getMessage(), e);
		} finally {
			CommonOperations.closeStatement(pstmt);
			CommonOperations.closeConnection(conn);
		}
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}

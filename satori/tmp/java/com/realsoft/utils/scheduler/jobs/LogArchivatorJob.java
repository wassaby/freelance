/*
 * Created on Oct 24, 2007 10:02:23 AM
 *
 * Realsoft Ltd.
 * < --!-- JMan: Esanov Emil --!-- >
 */
package com.realsoft.utils.scheduler.jobs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.realsoft.commons.utils14.RealsoftConstants;
import com.realsoft.commons.utils14.RealsoftException;

public class LogArchivatorJob implements IJob {

	private static Logger log = Logger.getLogger(LogArchivatorJob.class);

	private static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			"yyyyMMdd");

	private String logsPath;

	private String dateFormat;

	private long beforeInterval = 60 * 1000;

	public LogArchivatorJob() {
		super();
	}

	public void initialize() {
	}

	public void destroy() {
	}

	public void execute() throws RealsoftException {
		SimpleDateFormat simpleDateFormat = defaultDateFormat;
		if (dateFormat != null) {
			try {
				simpleDateFormat = new SimpleDateFormat(dateFormat);
			} catch (Exception e) {
				log.error("Can not create SimpleDateFormat for patern "
						+ dateFormat, e);
			}
		}
		try {
			log.info("executing job with logsPath " + logsPath + " ...");
			File[] logPackages = new File(logsPath).listFiles();
			if (logPackages != null && logPackages.length > 0) {
				for (int i = 0; i < logPackages.length; i++) {
					File file = logPackages[i];
					if (file != null && file.exists() && file.isDirectory()) {
						Date fileDate = null;
						Date compDate = null;
						try {
							fileDate = simpleDateFormat.parse(file.getName());
						} catch (Exception e) {
							log.warn("Could not parse data for file "
									+ file.getPath(), e);
						}
						compDate = new Date(simpleDateFormat.parse(
								simpleDateFormat.format(new Date(System
										.currentTimeMillis()))).getTime()
								- beforeInterval);
						if (fileDate != null && fileDate.before(compDate)) {
							if (archivate(file, new File(file.getPath()
									+ ".zip"))) {
								log.warn("file " + file.getName()
										+ " is archivated");
								if (delete(file)) {
									log.info("file " + file.getName()
											+ " is deleted");
								} else {
									log.warn("can not delete file "
											+ file.getName());
								}
							} else {
								log.warn("can not archivate file "
										+ file.getName());
							}
						}
					}
				}
			}
			log.info("executing job with logsPath " + logsPath + " done");
		} catch (Exception e) {
			log.error("Could not archivate logs in path " + logsPath, e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"commons-scheduler.LogArchivatorJob.execute.error",
					new Object[] { logsPath }, "Could not execute job", e);
		}
	}

	public boolean archivate(File srcDir, File outFile) {
		try {
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(outFile)));
			File[] logFiles = srcDir.listFiles();
			if (logFiles != null && logFiles.length > 0) {
				for (int i = 0; i < logFiles.length; i++) {
					zos.putNextEntry(new ZipEntry(logFiles[i].getName()));
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(logFiles[i]));
					int length = 0;
					byte[] buff = new byte[2048];
					while ((length = bis.read(buff)) >= 0) {
						if (length > 0) {
							zos.write(buff, 0, length);
						}
					}
					zos.closeEntry();
				}
			}
			zos.flush();
			zos.close();
			return true;
		} catch (Exception e) {
			log.error("Could not archivate logs for path " + srcDir.getPath(),
					e);
		}
		return false;
	}

	public boolean delete(File srcDir) {
		if (srcDir.isFile()) {
			return srcDir.delete();
		} else {
			boolean delFlag = true;
			File[] logFiles = srcDir.listFiles();
			if (logFiles != null && logFiles.length > 0) {
				for (int i = 0; i < logFiles.length; i++) {
					delFlag = delFlag && delete(logFiles[i]);
					if (!delFlag) {
						return false;
					}
				}
			}
			delFlag = delFlag && srcDir.delete();
			return delFlag;
		}
	}

	public String getLogsPath() {
		return logsPath;
	}

	public void setLogsPath(String logsPath) {
		this.logsPath = logsPath;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}

/*
 * Created on 28.02.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IOUtils.java,v 1.2 2016/04/15 10:37:40 dauren_home Exp $
 */
package com.realsoft.commons.utils14.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.realsoft.commons.utils14.RealsoftConstants;
import com.realsoft.commons.utils14.RealsoftException;

public class IOUtils {

	private static Logger log = Logger.getLogger(IOUtils.class);

	private IOUtils() {
		super();
	}

	public static final void copyStream(InputStream in, OutputStream out)
			throws RealsoftException {
		try {
			OutputStream output = new BufferedOutputStream(out);
			InputStream input = new BufferedInputStream(in);
			byte[] buffer = new byte[10];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			log.error("Could not copy input stream", e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"utils14.IOUtils.copyStream.error", null,
					"Could not copy input stream", e);
		}
	}

	public static synchronized final void writeCommand(DataOutputStream dos,
			String command) throws RealsoftException {
		try {
			dos.write(command.getBytes());
			dos.flush();
		} catch (IOException e) {
			log.error("Could not write command to DataOutputStream", e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"utils14.IOUtils.writeCommand.error", null,
					"Could not write command to DataOutputStream", e);
		}
	}

	public static synchronized final void writeObject(DataOutputStream dos,
			Object obj) throws RealsoftException {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = null;

		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(baos));
			oos.writeObject(obj);
			oos.flush();
			buffer = baos.toByteArray();
		} catch (IOException e) {
			log.error("Could not write object " + obj
					+ " to ObjectOutputStream", e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"utils14.IOUtils.writeObject.error", null,
					"Could not write object " + obj + " to ObjectOutputStream",
					e);
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				log.error("Could not close to ObjectOutputStream", e);
			}
		}

		if (buffer != null && buffer.length != 0) {
			log.debug("object size = " + buffer.length);
			try {
				dos.writeShort(buffer.length);
				dos.write(buffer);
				dos.flush();
			} catch (IOException e) {
				log.error("Could not write buffer to DataOutputStream", e);
				throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
						RealsoftConstants.BUNDLE_NAME,
						"utils14.IOUtils.writeObject.error", null,
						"Could not write buffer to DataOutputStream", e);
			}
		}

	}

	public static synchronized final String readCommand(DataInputStream dis,
			int length) throws RealsoftException {

		byte[] commBuff = read(dis, length);

		if (commBuff == null) {
			return null;
		}

		return new String(commBuff);

	}

	public static synchronized final Object readObject(DataInputStream dis)
			throws RealsoftException {
		try {
			short ln = 0;
			try {
				ln = dis.readShort();
				log.debug("java object size = " + ln);
			} catch (EOFException e) {
				log.error("Could not read length of stream", e);
			}
			if (ln > 0) {
				byte[] commBuff = read(dis, ln);
				if (commBuff != null) {
					ObjectInputStream ois = new ObjectInputStream(
							new BufferedInputStream(new ByteArrayInputStream(
									commBuff)));
					return ois.readObject();
				}
			}
			return null;
		} catch (Exception e) {
			log.error("Could not read object from DataInputStream", e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"utils14.IOUtils.readObject.error", null,
					"Could not read object from DataInputStream", e);
		}
	}

	public static synchronized final byte[] read(InputStream dis, int length)
			throws RealsoftException {
		log.debug("reading from DataInputStream with length = " + length);
		byte[] buffer = new byte[length];
		int off = 0;
		int len = buffer.length;
		try {
			while ((len = dis.read(buffer, off, len)) != -1) {
				log.debug("readed len = " + len);
				off += len;
				if (off >= length) {
					return buffer;
				} else {
					len = length - off;
				}
				log.debug("left len = " + len + "; off = " + off);
			}
			log.debug("read done");
		} catch (IOException e) {
			log.error("Could not read object with length " + length
					+ " from DataInputStream", e);
			throw new RealsoftException(RealsoftConstants.ERR_SYSTEM,
					RealsoftConstants.BUNDLE_NAME,
					"utils14.IOUtils.read.error", null,
					"Could not read object with length " + length
							+ " from DataInputStream", e);
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			InputStream in = new ByteArrayInputStream(
					"Hello wirld -------------------------------------------------------------"
							.getBytes());
			OutputStream out = new FileOutputStream("hello-world.txt");
			copyStream(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

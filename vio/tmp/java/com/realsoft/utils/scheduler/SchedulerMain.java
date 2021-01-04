/*
 * Created on 07.11.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: SchedulerMain.java,v 1.2 2016/04/15 10:37:50 dauren_home Exp $
 */
package com.realsoft.utils.scheduler;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.Scheduler;
import org.xsocket.stream.BlockingConnection;
import org.xsocket.stream.IBlockingConnection;
import org.xsocket.stream.IDataHandler;
import org.xsocket.stream.INonBlockingConnection;
import org.xsocket.stream.MultithreadedServer;

import com.realsoft.utils.spring.ISpringFactory;
import com.realsoft.utils.spring.SpringFactoryImpl;
import com.realsoft.utils.thread.BlockWaiter;

public class SchedulerMain {

	public static String LOGGER_FILE = "LOGGER_FILE";

	public static String SPRING_FILE = "SPRING_FILE";

	public static String SCHEDULER_BEAN = "scheduler";

	private static Logger log = null;

	private static ISpringFactory factory = null;

	private static BlockWaiter waiter = null;

	private static MultithreadedServer adminServer = null;

	private static class ShutdownHook implements Runnable {

		private Thread thread = null;

		public ShutdownHook() {
			super();
			thread = new Thread(this);
		}

		public Thread getThread() {
			return thread;
		}

		public void run() {
			if (factory != null) {
				log.info("Destroying component manager ...");
				factory.destroy();
				log.info("Destroying component manager done");
			}
			if (adminServer != null) {
				adminServer.close();
			}
		}

	}

	private static class EchoHandler implements IDataHandler {
		public boolean onData(INonBlockingConnection con) throws IOException {
			String command = con.readStringByDelimiter("\r\n");
			log.info("command: " + command + " is readed.");
			if (command.equals("stop")) {
				stop();
			}
			return true;
		}
	}

	public static void stop() {
		waiter.setCondition(false);
	}

	private static void usage() {
		System.out.println("usage:");
		System.out.println("scheduler to started");
		System.out.println("> scheduler.sh start");
		System.out.println("scheduler to stoped");
		System.out.println("> scheduler.sh stop");
	}

	public static void main(String[] args) {
		if (args != null && args.length > 1 && args[0] != null
				&& args[0].equalsIgnoreCase("start")) {

			try {
				String loggerFile = System.getProperty(LOGGER_FILE);
				System.out.println("LOGGER_FILE = " + loggerFile);
				if (loggerFile == null) {
					System.out.println("System property " + LOGGER_FILE
							+ " must be set");
					System.exit(1);
				}
				URL loggerUrl = new File(loggerFile).toURI().toURL();
				System.out.println("LOGGER URL = " + loggerUrl);
				DOMConfigurator.configure(loggerUrl);
				log = Logger.getLogger(SchedulerMain.class);
				ServerSocket serverSocket = new ServerSocket(Integer
						.valueOf(args[1]));
				serverSocket.close();

				log.info("starting admin server on " + args[1] + " ...");
				adminServer = new MultithreadedServer(Integer.valueOf(args[1]),
						new EchoHandler());
				Thread sThread = new Thread(adminServer);
				sThread.start();
				log.info("admin server started on " + args[1]);

				String springFile = System.getProperty(SPRING_FILE);
				System.out.println("SPRING_FILE = " + springFile);
				if (springFile == null) {
					System.out.println("System property " + SPRING_FILE
							+ " must be set");
					System.exit(1);
				}
				URL springUrl = new File(springFile).toURI().toURL();
				System.out.println("SPRING URL = " + springUrl);
				log.debug(springUrl);
				if (springUrl == null) {
					System.out.println("Wrong system property " + SPRING_FILE);
					System.exit(1);
				}
				factory = SpringFactoryImpl.getInstance(springUrl.toURI());

				Runtime.getRuntime().addShutdownHook(
						new ShutdownHook().getThread());

				Scheduler scheduler = (Scheduler) factory
						.getBean(SCHEDULER_BEAN);
				scheduler.start();
				log.info("Scheduler started");

				waiter = new BlockWaiter();
				waiter.waiting();

				log.info("Exiting!!!");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Could not start scheduler", e);
				System.exit(1);
			}
		} else if (args != null && args.length > 1 && args[0] != null
				&& args[0].equalsIgnoreCase("stop")) {
			try {
				ServerSocket serverSocket = new ServerSocket(Integer
						.valueOf(args[1]));
				serverSocket.close();
				System.out.println("Scheduler is not started");
			} catch (Exception e) {
				try {
					IBlockingConnection con = new BlockingConnection(
							"localhost", Integer.valueOf(args[1]));
					con.write("stop\r\n");
					con.flush();
					con.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else {
			usage();
		}
		System.exit(0);

	}

}

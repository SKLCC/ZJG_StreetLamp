package iot.mike.streetlamp;

import iot.mike.rmiserver.interfaces.RMI_Module_Interface;
import iot.mike.streetlamp.lamps.LampBaseManager;
import iot.mike.streetlamp.net.NetServer;
import iot.mike.streetlamp.net.PHPServer;
import iot.mike.streetlamp.setting.SettingManager;
import iot.mike.streetlamp.sql.SQLDruidPool;
import iot.mike.streetlamp.sql.SQLManager;
import iot.mike.streetlamp.threadpool.ThreadPool;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Main_Server implements RMI_Module_Interface{
	private Logger logger = Logger.getLogger("Main_Server");
	private LampBaseManager lampBaseManager;
	private SQLManager sqlManager;
	private ThreadPool threadPool;
	private SQLDruidPool druidPool;
	private NetServer netServer;
	private SettingManager settingManager;
	private PHPServer phpServer;
	
	public static void main(String[] args) throws RemoteException {
		Main_Server main_Server = new Main_Server();
		main_Server.init();
		main_Server.start();
	}
	
	public void init(){
		logger.log(Level.INFO, "系统初始化!");
		sqlManager = SQLManager.getInstance();
		settingManager = SettingManager.getInstance();
		lampBaseManager = LampBaseManager.getInstance();
		threadPool = ThreadPool.getInstance();
		druidPool = SQLDruidPool.getInstance();
		netServer = NetServer.getInstance();
		phpServer = PHPServer.getInstance();
	}
	
	public void start() throws RemoteException {
		lampBaseManager.start();
		netServer.start();
		sqlManager.start();
		logger.log(Level.INFO, "系统开始运行!");
		phpServer.start();
	}

	public void end() throws RemoteException {
		logger.log(Level.INFO, "系统结束!");
		this.stop();
		System.exit(0);
	}

	public void restart() throws RemoteException {
		logger.log(Level.INFO, "系统重启!");
		sqlManager.restart();
	}

	public void stop() throws RemoteException {
		logger.log(Level.INFO, "系统停止!");
		phpServer.shutdown();
		netServer.shutdown();
		sqlManager.shutdown();
	}

	public String getStatus() throws RemoteException {
		logger.log(Level.INFO, "返回系统状态信息!");
		return null;
	}

	public String doCommand(String command) throws RemoteException {
		logger.log(Level.INFO, "执行指令:" + command);
		return null;
	}

	
	/**
	 * 返回模块描述
	 */
	public String getDescription() throws RemoteException {
		logger.log(Level.INFO, "返回系统描述!");
		return "这是张家港路灯协议的后台网关,负责从路灯数据的采集.\n"
				+ "由Mike编写,时间:2013-7-29\n"
				+ "Copyright@Mike http://mikecoder.net";
	}
}
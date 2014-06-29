package common.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import common.config.AppContent;
import common.config.Config;
import common.config.UnionsContent;
import common.db.C3P0Utils;
import common.route.RouteController;

/**
 * 
 * @Description 启服关服控制器
 * @author liangyx
 * @date 2014-6-26
 * @version V1.0
 */
public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//清理c3p0
		C3P0Utils.destroy();
		//回写应用数据
		AppContent.flush();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//初始化配置
		initConfig(arg0);
		//log4j初始化
		PropertyConfigurator.configure(Config.CONFIG_DIR+File.separator+"log4j.properties");
		//初始化路由解析器
		RouteController.parsePaths();
		//初始化渠道配置
		UnionsContent.init();
		//初始化应用配置
		AppContent.init();
	}
	
	private void initConfig(ServletContextEvent sce){
		Config.CONFIG_DIR=sce.getServletContext().getRealPath("")+File.separator+"WEB-INF"+File.separator+File.separator+Config.CONFIG_DIR;
	}

}

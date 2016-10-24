package com.webhybird.framework.util;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 项目工程函数类
 *
 * @author 李轩邈
 *
 */
public class ProjUtil {

	private static Logger logger = Logger.getLogger(ProjUtil.class);

	public enum ConfigEnum{
		CONFIG_PATH,application
	}
	/**
	 * 属性值
	 */
	public static String propValue = null;

	/**
	 * 获取配置
	 * @return
	 */
	public static Properties getConfigProperties(){
		Properties properties = new Properties();
		InputStream input = null;
		String configPath = System.getenv(ConfigEnum.CONFIG_PATH.name());
		String filePath = null;
		if(configPath != null && !"".equals(configPath.replaceAll(" ",""))){
			filePath = configPath + "/" + ConfigEnum.application + ".properties";
		}else{
			filePath = getPropFilePath(ConfigEnum.application + ".properties");
		}
		Assert.hasText(filePath, "未找到工程配置文件，请确认已配置CONFIG_PATH 环境变量或 工程下有application.properties 文件");

		try {
			input = new FileInputStream(filePath);
			properties.load(input);
		} catch (IOException e) {
			logger.info("加载配置出错",e);
			e.printStackTrace();
		}
		return properties;
	}
	/**
	 * 获取属性值
	 *
	 * @param filePath
	 * @param propName
	 * @return
	 * @throws IOException
	 */
	public static String buildPropValue(String filePath, String propName)
			throws IOException {
		InputStream input = null;
		Properties props = new Properties();
		input = new FileInputStream(filePath);
		props.load(input);

		String propValue = props.getProperty(propName);
		return propValue;
	}

	/**
	 * 获取属性文件全文件路径
	 * modify by chenyu 20120319 添加获取本地路径的方法,取消getProjPath方法获取项目路径
	 * @param propFileName
	 * @return
	 */
	public static String getPropFilePath(String propFileName) {
//		String basePath = ProjUtil.getProjPath();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final String resource = ProjUtil.class.getName().replace('.', '/')
				.concat(".class");

		URL url = classLoader.getResource(resource);
		String path = null;

		File file = new File(url.getFile());
		String fileName = file.getAbsolutePath();

		int indexweb = fileName.indexOf("WEB-INF");
		int indexLocal = fileName.indexOf("classes");
		if (indexweb != -1) {
			//web运行时
			path = fileName.substring(0, indexweb)+"WEB-INF"+File.separator + "classes";
		}else{
			//本地运行时
			path = fileName.substring(0, indexLocal) + "classes";
		}

		String propFilePath = path + File.separator + propFileName;
		return propFilePath;
	}

	public static void main(String[] args) {
//		String str = "/ibm/WebSphere/AppServer/profiles/AppSrv01/installedApps/jifen1Cell01/BOCGIFTORDER_war.ear/BOCGIFTORDER.war/";
//
//		str = str.substring(0, str.length() - 1);
//		str = str.substring(0, str.lastIndexOf("/"));
//		str = str.substring(str.lastIndexOf("/") + 1, str.length());
//
//		System.out.println(str);
//
//		getProjName(this.);
//		System.out.println(ProjUtil.buildProjPath());
		System.out.println(ProjUtil.getPropFilePath("application.properties"));
	}

}

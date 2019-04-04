package com.esgov.myself.controller;

import java.io.*;

import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import ch.qos.logback.core.net.server.Client;

@Controller
@RequestMapping("/mySelf")
@PropertySource("classpath:user.properties")
public class MySelfPageController {
	
	/**
	 * 初始化方法
	 * @param request
	 * @return
	 */
	@GetMapping("/myPage")
	@ResponseBody
	public ModelAndView showMyPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("myPage");
		return mv;
	}
	
	/**
	 * 简历预览方法
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/priview")
	public void priview(HttpServletResponse response) throws Exception{
		//File file = new File("src/main/resources/static/file/詹磊_软件开发工程师_简历.doc");
		Properties prop=new Properties();         
		prop.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("user.properties"), "UTF-8"));  
		String filePath = prop.getProperty("user.filePath");
		String home = prop.getProperty("user.home");
		String command = prop.getProperty("user.command");
		String host = prop.getProperty("user.host");
		int port = Integer.parseInt((String)prop.get("user.port"));
		File file = new File(filePath);
		InputStream inputFile = new FileInputStream(file); 
		InputStream filepdf = wordToPdf(inputFile, home, command, host, port);
		String fileName = file.getName();
		
		ServletOutputStream out;
        response.setCharacterEncoding("utf-8");
        //设置类型及文件名
        response.setHeader("Content-Disposition", "inline;filename="
                .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
        response.setContentType("application/pdf");
        byte[] buffer = new byte[512];  // 缓冲区
        out = response.getOutputStream();
        int bytesToRead;
        while((bytesToRead = filepdf.read(buffer)) != -1) {
            out.write(buffer, 0, bytesToRead);
        }
        out.close();
		
	}
	
	/**
	 * 将word转成pdf文件输出
	 * @param wordStream
	 * @param home
	 * @param command
	 * @param host
	 * @param port
	 * @return
	 */
	public static InputStream wordToPdf(InputStream wordStream, String home, String command, String host, int port){
		if (home.charAt(home.length() - 1) != '/') {
            home += "/";
        }
        Process pro = null;
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
		try {
			// 先尝试连接，失败时启动服务
            try {
				connection.connect();
			}catch (Exception e) {
				// 启动OpenOffice的服务
				command = home + command;
				pro = Runtime.getRuntime().exec(command);
				// 重新连接
				connection.connect();
			}
            //设置转换前后文件类型
            DefaultDocumentFormatRegistry formatRegistry = new DefaultDocumentFormatRegistry();
			DocumentFormat doc = formatRegistry.getFormatByFileExtension("doc");
			DocumentFormat pdf = formatRegistry.getFormatByFileExtension("pdf");
			//定义输出流
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			//进行转换
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(wordStream,doc,pdfStream,pdf);
            //把pdf流转成输入流
            InputStream pdfInput = new BufferedInputStream(new ByteArrayInputStream(pdfStream.toByteArray()));
            pdfStream.flush();
            pdfStream.close();
            return pdfInput;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(connection != null) connection.disconnect();
            if(pro != null) pro.destroy();
        }
	}
}

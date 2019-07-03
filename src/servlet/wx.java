package servlet;

import java.awt.SystemTray;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
//import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
//import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;

import service.WxService;


@WebServlet("/wx")
public class wx extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("get success!");
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		//校验请求
		try {
			if(WxService.check(timestamp,nonce,signature)) {
				System.out.println("接入成功！");
				PrintWriter out=response.getWriter();
				out.print(echostr);
				out.flush();
				out.close();
			}else {
				System.out.println("接入失败！");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	//接收信息和事件推送
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post success!");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//打印出数据具体格式
//		ServletInputStream is=request.getInputStream();
//		byte[] b=new byte[1024];
//		int len;
//		StringBuilder sb=new StringBuilder();
//		while((len=is.read(b))!=-1) {
//			sb.append(new String(b,0,len));
//		}
//		System.out.println(sb.toString());
		//处理消息和事件推送
//		ServletInputStream rg = request.getInputStream();
//		java.util.Map<String, String> requestMap = WxService.parseRequest(rg);
		//大bug，后来通过将jar放在lib下（https://blog.csdn.net/monly_jiajia/article/details/60577757），并将Map类型转换一下解决
		java.util.Map<String,String> requestMap=  (java.util.Map<String, String>) WxService.parseRequest(request.getInputStream());
//		Map<String, String> parseRequest = (Map<String, String>) WxService.parseRequest(request.getInputStream());
//		Map<String,String> requestMap=  parseRequest;
		System.out.println(requestMap);
		//gh_9eec43cbf18b
		//准备回复的数据包
//		String respXml="<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
//				"<FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
//				"<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>\r\n" + 
//				"<MsgType><![CDATA[text]]></MsgType>\r\n" + 
//				"<Content><![CDATA[[Facepalm]]]></Content>\r\n" + 
//				"<MsgId>22328809780312388</MsgId>\r\n" + 
//				"</xml>";
		String respXml=WxService.getResponse(requestMap);
		//System.out.println("应答成功！");
		System.out.println(respXml);
		PrintWriter out=response.getWriter();
		out.print(respXml);
		out.flush();
		out.close();
	}
	//weichat/src/servlet/wx.java
}
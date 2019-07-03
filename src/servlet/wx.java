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
		//У������
		try {
			if(WxService.check(timestamp,nonce,signature)) {
				System.out.println("����ɹ���");
				PrintWriter out=response.getWriter();
				out.print(echostr);
				out.flush();
				out.close();
			}else {
				System.out.println("����ʧ�ܣ�");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	//������Ϣ���¼�����
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post success!");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//��ӡ�����ݾ����ʽ
//		ServletInputStream is=request.getInputStream();
//		byte[] b=new byte[1024];
//		int len;
//		StringBuilder sb=new StringBuilder();
//		while((len=is.read(b))!=-1) {
//			sb.append(new String(b,0,len));
//		}
//		System.out.println(sb.toString());
		//������Ϣ���¼�����
//		ServletInputStream rg = request.getInputStream();
//		java.util.Map<String, String> requestMap = WxService.parseRequest(rg);
		//��bug������ͨ����jar����lib�£�https://blog.csdn.net/monly_jiajia/article/details/60577757��������Map����ת��һ�½��
		java.util.Map<String,String> requestMap=  (java.util.Map<String, String>) WxService.parseRequest(request.getInputStream());
//		Map<String, String> parseRequest = (Map<String, String>) WxService.parseRequest(request.getInputStream());
//		Map<String,String> requestMap=  parseRequest;
		System.out.println(requestMap);
		//gh_9eec43cbf18b
		//׼���ظ������ݰ�
//		String respXml="<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName>\r\n" + 
//				"<FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName>\r\n" + 
//				"<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>\r\n" + 
//				"<MsgType><![CDATA[text]]></MsgType>\r\n" + 
//				"<Content><![CDATA[[Facepalm]]]></Content>\r\n" + 
//				"<MsgId>22328809780312388</MsgId>\r\n" + 
//				"</xml>";
		String respXml=WxService.getResponse(requestMap);
		//System.out.println("Ӧ��ɹ���");
		System.out.println(respXml);
		PrintWriter out=response.getWriter();
		out.print(respXml);
		out.flush();
		out.close();
	}
	//weichat/src/servlet/wx.java
}
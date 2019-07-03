package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import Util.Util;


 //Servlet implementation class GetUserInfo
 
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GetUserInfo() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡcode
		//request.setCharacterEncoding("utf-8");//����ͳһ����
		response.setContentType("utf-8");
		String code = request.getParameter("code");
		//��ȡaccesstoken�ĵ�ַ
		System.out.println("��ȡ����code");
		System.out.println(code);
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		System.out.println(url);
		url=url.replace("APPID", "wx0c74afc71de77910").replace("SECRET", "35d930c988860d543c1999559f0a5e5d").replace("CODE", code);
		String result = Util.get(url);
		//System.out.println(result+"��һ��result�ǲ���������+++");
		String at = JSONObject.fromObject(result).getString("access_token");
		String openid = JSONObject.fromObject(result).getString("openid");
		//��ȡ�û��Ļ�����Ϣ
		url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url=url.replace("ACCESS_TOKEN", at).replace("OPENID", openid);
		result = Util.get(url);
		//���ñ����ʽ���������
		java.net.URLEncoder.encode(result, "utf-8");
		System.out.println(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

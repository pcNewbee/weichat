package Util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;
import service.WxService;

public class Util {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	// �����������KEY
	public static final String APPKEY = "*************************";
	
	//��ָ���ĵ�ַ����get����,����get���󣬵���access_token��
	public static String getTOKEN(String url) {
		try {
			URL urlObj=new URL(url);
			//����
			URLConnection conn=urlObj.openConnection();
			InputStream is=conn.getInputStream();
			byte[] b=new byte[1024];
			int len;
			StringBuilder sb=new StringBuilder();
			while((len=is.read(b))!=-1) {
				sb.append(new String(b,0,len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	 //��ָ���ĵ�ַ����һ��post���󣬴���data����
	public static String post(String url, String data) {
		try {
			URL urlObj = new URL(url);
			URLConnection connection = urlObj.openConnection();
			// Ҫ�������ݳ�ȥ������Ҫ����Ϊ�ɷ�������״̬
			connection.setDoOutput(true);
			// ��ȡ�����
			OutputStream os = connection.getOutputStream();
			// д������
			
			os.write(data.getBytes());
			os.close();
			// ��ȡ������
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 // ��ָ���ĵ�ַ����get����
	public static String get(String url) {
		try {
			URL urlObj = new URL(url);
			// ������
			URLConnection connection = urlObj.openConnection();
			connection.setRequestProperty("Accept-Encoding", "utf-8");
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			//int len;
			String line=null;
			//���url��������
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
//			while ((len = is.read(b)) != -1) {
//				sb.append(new String(b, 0, len));
//			}
//			return sb.toString();
			//���url��������
			 while ((line= reader.readLine()) != null) {
				 sb.append(line + "\n");
			 }
             return  sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	 //@param strUrl �����ַ
	 // @param params �������
	 //@param method ���󷽷�
	//@return ���������ַ���
	// @throws Exception
	public static String net(String strUrl, Map params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// ��map��תΪ���������
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}

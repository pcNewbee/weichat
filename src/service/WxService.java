package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import Util.Util;
import entity.AccessToken;
import entity.Article;
import entity.BaseMessage;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.videoMessage;
import entity.voiceMessage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.portable.OutputStream;

import com.baidu.aip.ocr.AipOcr;
import javax.net.ssl.HttpsURLConnection;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class WxService {
	//���������
	private static final String TOKEN = "llzs";
	private static final String APPKEY="1fec136dbd19f44743803f89bd55ca62";
	//΢�Ź��ں�
	private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//	private static final String APPID="wxa967ff121710246d";
//	private static final String APPSECRET="85c873a98abbae95284863c079de82d1";
	private static final String APPID="wx0c74afc71de77910";
	private static final String APPSECRET="35d930c988860d543c1999559f0a5e5d";
	//�ٶ�AI
	public static final String APP_ID = "11519092";
	public static final String API_KEY = "q3TlGWWqEBG9uGvlFIBtpvY5";
	public static final String SECRET_KEY = "A14W5VRNG8my1GXYYAyNND0RjzBwxI8A";
	//No permission to access data
//	public static final String APP_ID = "16451820";
//	public static final String API_KEY = "TRvUwqFNTneybvZUEBH3F3xx";
//	public static final String SECRET_KEY = "eMlGd8SPNvbl64R3vlvEgoaSWy1cbxC7";
	//���ڴ洢token
	private static AccessToken at;
	//��ȡtoken
	private static void getToken() {
		String url=GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET",APPSECRET);
		String tokenStr =Util.getTOKEN(url);
		System.out.println(tokenStr);
		JSONObject jsonObject=JSONObject.fromObject(tokenStr);
		String token=jsonObject.getString("access_token");
		String expiresIn=jsonObject.getString("expires_in");
		//����token���󣬲�������
		at=new AccessToken( token, expiresIn);
	}
	//���⴦��©�Ļ�ȡtoken�ķ���
	public static String getAccessToken() {
		if(at==null||at.isExpired()) {
			getToken();
		}
		return at.getAccessToken();
	}
	//���ڴ洢token
	//private static AccessToken at;
	private static final String token="cpc";
	//��֤ǩ��
	public static boolean check(String timestamp,String nonce,String signature) throws NoSuchAlgorithmException {
		//1����token��timestamp��nonce�������������ֵ�������
		String[] strs=new String[] {token,timestamp,nonce};
		//System.out.println(strs);
		Arrays.sort(strs);
		//System.out.println(strs);
		//2�������������ַ���ƴ�ӳ�һ���ַ�������sha1���� 
		String str=strs[0]+strs[1]+strs[2];
		String mysig=sha1(str);
//		System.out.println(mysig);
//		System.out.println(signature);
		
		//3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		return mysig.equalsIgnoreCase(signature);
	}
	private static String sha1(String src) throws NoSuchAlgorithmException {
		try{
		MessageDigest md=MessageDigest.getInstance("sha1");
		byte[] digest=md.digest(src.getBytes());
		//������ܽ��
		char[] chars= {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		StringBuilder sb=new StringBuilder();
		for(byte b:digest) {
			sb.append(chars[(b>>4)&15]);//����λ
			sb.append(chars[b&15]);//����λ
		}
		return sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return null;
	}
	//����xml���ݰ�
	public static Map<String, String> parseRequest(InputStream is) {
		Map<String,String> map=new HashMap<>();
		SAXReader reader=new SAXReader();
		try {
			//��ȡ����������ȡ�ĵ�����
			Document document=reader.read(is);
			//�����ĵ������ȡ���ڵ�
			Element root=document.getRootElement();
			//��ȡ���ڵ�����е��ӽڵ�
			List<Element> elements=root.elements();
			for(Element e:elements) {
			map.put(e.getName(), e.getStringValue());		
			}
					
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//System.out.println(map);
		return map;
	}
	//���ڴ������е��¼�����Ϣ�Ļظ�
	//����xml���ݰ�
	public static String getResponse(Map<String, String> requestMap) {
		BaseMessage msg=null;
		String msgType=requestMap.get("MsgType");
		switch(msgType) {
		//�����ı���Ϣ
		case "text":
			msg=dealTextMessage(requestMap);
			break;
		case "image":
			msg=dealImage(requestMap);
			break;	
		case "voice":
			msg=dealVoice(requestMap);
			break;
			//��δʵ�����¹���
//		case "video":
//			break;
//		case "shortvideo":
//			break;
//		case "location":
//			break;
//		case "link":
//			break;
		case "event":
			msg = dealEvent(requestMap);
			break;
		default:
			break;
		}
		//System.out.println(msg);
		//����Ϣ������Ϊxml���ݰ�
		if(msg!=null) {
		return beanToXml(msg);
	}else
		return null;
	}
	//��������ʶ��
	private static BaseMessage dealVoice(Map<String, String> requestMap) {
		
		return null;
	}
	//����ͼƬʶ��
	private static BaseMessage dealImage(Map<String, String> requestMap) {
		// ��ʼ��һ��AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		// ��ѡ�������������Ӳ���
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		// ���ýӿ�
		String path = requestMap.get("PicUrl");
		//String path = "E:/picture/1.png";
		//String path = "http://mmbiz.qpic.cn/mmbiz_jpg/wxJBNFHn46C4lAOXibxbF9HNCoFs7qPUicGe0ML4uJv1SdfyticdiaHoicclfMVuw55RQiaRhJbnbIhBnCuiaicb6KrC8Q/0";
		//��������ͼƬ��ʶ��
		org.json.JSONObject res = client.generalUrl(path, new HashMap<String,String>());
		String json = res.toString();
		//תΪjsonObject
		JSONObject jsonObject = JSONObject.fromObject(json);
		System.out.println(jsonObject);
		//System.out.println("���ýӿ�ʧ�ܣ�");
		JSONArray jsonArray = jsonObject.getJSONArray("words_result");
		//System.out.println("���ýӿڣ�ȡ��words_result�ɹ�");
		Iterator<JSONObject> it = jsonArray.iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			JSONObject next = it.next();
			sb.append(next.getString("words"));
		}
		return new TextMessage(requestMap, sb.toString());
	}
	 //�����¼�����
	private static BaseMessage dealEvent(Map<String, String> requestMap) {
		String event = requestMap.get("Event");
		switch (event) {
			case "CLICK":
				return dealClick(requestMap);
			case "VIEW":
				return dealView(requestMap);
			default:
				break;
		}
		return null;
	}

	//����view���͵İ�ť�Ĳ˵�
	private static BaseMessage dealView(Map<String, String> requestMap) {
		
		return null;
	}

	//����click�˵�
	private static BaseMessage dealClick(Map<String, String> requestMap) {
		String key = requestMap.get("EventKey");
		switch (key) {
			//���һ�˵���
			case "1":
				//�������˵�һ��һ���˵�
				return new TextMessage(requestMap, "�����һ�µ�һ��һ���˵�");
			case "32":
				//�������˵�����һ���˵��ĵڶ����Ӳ˵�
				return new TextMessage(requestMap, "����˵�����һ���˵��ĵڶ����Ӳ˵�");
				//break;
			default:
				break;
		}
		return null;
	}
	
	//����Ϣ������Ϊxml���ݰ�
	private static String beanToXml(BaseMessage msg) {
		XStream stream=new XStream();
		//��<enity.TextMessage><enity.TextMessage>����xml
		//������Ҫ�����@XStreamAlias("xml")ע�͵���
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(videoMessage.class);
		stream.processAnnotations(voiceMessage.class);
		//stream.processAnnotations(BaseMessage.class);
		String xml=stream.toXML(msg);
		return xml;
	}
	//�����ı���Ϣ
	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		//�û�����������
		String msg=requestMap.get("Content");
		if(msg.equals("ͼ��")) {
			List<Article> articles = new ArrayList<>();
			articles.add(new Article("title", "��ϸ����", "http://mmbiz.qpic.cn/mmbiz_jpg/wxJBNFHn46C0Gzlgb9klIcbCanGA8GOZ8fSicHwV5XgLoQcjdenkTFWenGJzhpXq2Dc7nZSiby0Xt786oT2SCLAw/0", "http://www.baidu.com"));
			NewsMessage nm = new NewsMessage(requestMap, articles);
			return nm;
		}
		if(msg.equals("��¼")) {
			String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0c74afc71de77910&redirect_uri=http://cpc.free.idcfengye.com/weichat/GetUserInfo&response_type=code&scope=snsapi_userinfo#wechat_redirect";
			TextMessage tm = new TextMessage(requestMap, "���<a href=\""+url+"\">����</a>��¼");
			return tm;
		}
		//���÷����������������
		String resp=chat(msg);
		TextMessage tm=new TextMessage(requestMap,resp);
		return tm;
	}
	//����ͼ�����������mima:juhe@pc666
	private static String chat(String msg) {
        String result =null;
        String url ="http://op.juhe.cn/robot/index";//����ӿڵ�ַ
        Map params = new HashMap();//�������
        params.put("key",APPKEY);//�����뵽�ı��ӿ�ר�õ�APPKEY
        params.put("info",msg);//Ҫ���͸������˵����ݣ���Ҫ����30���ַ�
        params.put("dtype","");//���ص����ݵĸ�ʽ��json��xml��Ĭ��Ϊjson
        params.put("loc","");//�ص㣬�籱���йش�
        params.put("lon","");//���ȣ�����116.234632��С�������6λ������ҪдΪ116234632
        params.put("lat","");//γ�ȣ���γ40.234632��С�������6λ������ҪдΪ40234632
        params.put("userid","");//1~32λ����userid������Լ���ÿһ���û������������ĵĹ���
        try {
            result =Util.net(url, params, "GET");
            //����json
            JSONObject jsonObject = JSONObject.fromObject(result);
            System.out.println(jsonObject);
            //ȡ��error_code
            int code = jsonObject.getInt("error_code");
            if(code!=0) {
            		return null;
            }
            //ȡ�����ص���Ϣ������
            String resp = jsonObject.getJSONObject("result").getString("text");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	 //�ϴ���ʱ�ز�
	//��Ϊûɶ���ã��ѱ��˸�
	 //param path	�ϴ����ļ���·��
	 //param type	�ϴ����ļ�����
	/*public static String upload(String path,String type) {
		File file = new File(path);
		//��ַ
		String url="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
		try {
			URL urlObj = new URL(url);
			//ǿתΪ��������
			HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
			//�������ӵ���Ϣ
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			//��������ͷ��Ϣ
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "utf8");
			//���ݵı߽�
			String boundary = "-----"+System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			//��ȡ�����
			OutputStream out = (OutputStream) conn.getOutputStream();
			//�����ļ���������
			InputStream is = new FileInputStream(file);
			//��һ���֣�ͷ����Ϣ
			//׼��ͷ����Ϣ
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			out.write(sb.toString().getBytes());
			System.out.println(sb.toString());
			//�ڶ����֣��ļ�����
			byte[] b = new byte[1024];
			int len;
			while((len=is.read(b))!=-1) {
				out.write(b, 0, len);
			}
			is.close();
			//�������֣�β����Ϣ
			String foot = "\r\n--"+boundary+"--\r\n";
			out.write(foot.getBytes());
			out.flush();
			out.close();
			//��ȡ����
			InputStream is2 = conn.getInputStream();
			StringBuilder resp = new StringBuilder();
			while((len=is2.read(b))!=-1) {
				resp.append(new String(b,0,len));
			}
			return resp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	 //��ȡ��������ά���ticket
	public static String getQrCodeTicket() {
		String at = getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+at;
		//������ʱ�ַ���ά��
		//String data="{\"expire_seconds\": 600, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"�Ұ���cpc\"}}}";
		//���������ַ���ά��
		String data="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"�Ұ���cpc\"}}}";
		String result = Util.post(url, data);
		String ticket = JSONObject.fromObject(result).getString("ticket");
		return ticket;
	}

	 // ��ȡ�û��Ļ�����Ϣ
	public static String getUserInfo(String openid) {
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("OPENID", openid);
		String result = Util.get(url);
		return result;
	}
}

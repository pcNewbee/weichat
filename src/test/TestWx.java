package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baidu.aip.ocr.AipOcr;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import entity.AbstractButon;
import entity.BaseMessage;
import entity.Button;
import entity.ClickButton;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.PhotoOrAlbumButton;
import entity.SubButton;
import entity.TextMessage;
import entity.ViewButton;
import entity.videoMessage;
import entity.voiceMessage;
import net.sf.json.JSONObject;
import service.WxService;

public class TestWx {
	//����APPID/AK/SK
//		public static final String APP_ID = "16451820";
//		public static final String API_KEY = "TRvUwqFNTneybvZUEBH3F3xx";
//		public static final String SECRET_KEY = "eMlGd8SPNvbl64R3vlvEgoaSWy1cbxC7";
		//�ٶ�AI
		public static final String APP_ID = "11519092";
		public static final String API_KEY = "q3TlGWWqEBG9uGvlFIBtpvY5";
		public static final String SECRET_KEY = "A14W5VRNG8my1GXYYAyNND0RjzBwxI8A";
		//��ȡ��ά��ticket
		@Test
		public void testQrCode() {
			String ticket = WxService.getQrCodeTicket();
			System.out.println("����ticket======");
			System.out.println(ticket);
			//gQGg7jwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyV2FrT3BzMFBkSmwxU3RuV2hzY1UAAgRFVfpcAwRYAgAA
		}
		//��ȡ�û�������Ϣ
		@Test
		public void testGetUserInfo() {
			String user="ogHNt1aNUwav4T1X06lmtnsFr5BE";
			String info = WxService.getUserInfo(user);
			System.out.println("�û�������Ϣ===");
			System.out.println(info);
		}
//		
//		@Test
//		public void testUpload() {
//			String file = "E:/picture/1.png";
//			String result = WxService.upload(file, "image");
//			System.out.println(result);
//			//TLfodwngsUr9rjHdITKiB9uT4Dq7K-QnV00MVd3_U6LnZeAqpZl3vYIICjUq48BY
//		}
		@Test
		public void test() {
			System.out.println(WxService.getAccessToken());
			//15_9DHAkyQuqkpLhvqAN5SAWsamv1ifJhoRBaofIMbavU4Q7qFwjjgTd-a1b13drz2ANkCaJsAXuvSvB3Z7BwJ-giQTCBwOI1ifDdtqccarOQUwWvgt2XYACAdqD3yT3PvZYlBiAMnwfWaxNQfILERcABAOHU
		}
		@Test
	public void testPic() {
		// ��ʼ��һ��AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// ��ѡ�������������Ӳ���
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
		//client.setHttpProxy("proxy_host", proxy_port); // ����http����
		//client.setSocketProxy("proxy_host", proxy_port); // ����socket����

		// ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
		// Ҳ����ֱ��ͨ��jvm�����������ô˻�������
		//System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		// ���ýӿ�
		//String path = "/weichat/src/֤����.jpg"E:/picture/֤����.jpg;
		String path = "E:/picture/1.png";
		
		//org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//		System.out.println("����ͼƬ����ʶ��=====");
//		System.out.println(res.toString(2));
	}

	@Test
	public void testButton() {
		Button btn = new Button();
		// ��һ��һ���˵�
		btn.getButton().add(new ClickButton("һ�����", "1"));
		// �ڶ���һ���˵�
		btn.getButton().add(new ViewButton("һ����ת", "http://www.baidu.com"));
		// ����������һ���˵�
		SubButton sb = new SubButton("���Ӳ˵�");
		// Ϊ������һ���˵������Ӳ˵�
		sb.getSub_button().add(new PhotoOrAlbumButton("��ͼ", "31"));
		sb.getSub_button().add(new ClickButton("��� ", "32"));
		sb.getSub_button().add(new ViewButton("��������", "http://news.163.com"));
		// ���������һ���˵�
		btn.getButton().add(sb);
		// תΪjson
		JSONObject jsonObject = JSONObject.fromObject(btn);
		//System.out.println(jsonObject.toString());
	}
	@Test
	public void testToken() {
		System.out.println("����token=====");
		System.out.println(WxService.getAccessToken());
		//System.out.println(WxService.getAccessToken());
	}
	
@Test	
//���Ե�����import java.util.Map;
	public void testMsg() {
		Map<String,String> map=new HashMap<>();
		map.put("ToUserName", "to");
		map.put("FromUserName", "from");
		map.put("MsgType", "type");
		TextMessage tm=new TextMessage(map,"ţƤ");
		//System.out.println(tm);
		//��mapת��Ϊxml
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
		String xml=stream.toXML(tm);
		//System.out.println(xml);
		
	}
}

package Util;

import entity.Button;
import entity.ClickButton;
import entity.PhotoOrAlbumButton;
import entity.SubButton;
import entity.ViewButton;
import net.sf.json.JSONObject;
import service.WxService;

public class CreateMenu {

	public static void main(String[] args) {
		//菜单对象
		Button btn = new Button();
		//第一个一级菜单
		btn.getButton().add(new ClickButton("按了没啥用的button", "1"));
		//第二个一级菜单
		btn.getButton().add(new ViewButton("百度一下", "http://www.baidu.com"));
		//创建第三个一级菜单
		SubButton sb = new SubButton("提取文字");
		//为第三个一级菜单增加子菜单
		sb.getSub_button().add(new PhotoOrAlbumButton("拍照传图", "31"));
		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
		//加入第三个一级菜单
		btn.getButton().add(sb);
		//转为json
		JSONObject jsonObject = JSONObject.fromObject(btn);
		//准备url
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", WxService.getAccessToken());
		//发送请求
		String result = Util.post(url, jsonObject.toString());
		System.out.println(result);
		
	}
}

package entity;

import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class ImageMessage extends BaseMessage {
	@XStreamAlias("MediaId")
	private String MediaId;
	public String getMediaID() {
		return MediaId;
	}
	public void setMediaID(String MediaId) {
		this.MediaId = MediaId;
	}
	public ImageMessage() {
		super();
	}
	public ImageMessage(Map<String, String> requestMap,String MediaId) {
		super(requestMap);
		//设置图片消息的msgtype为image
				this.setMsgType("image");
				this.MediaId=MediaId;
	}
	@Override
	public String toString() {
		return "ImageMessage [MediaId=" + MediaId + ", getMediaID()=" + getMediaID() + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName() + ", getCreateTime()=" + getCreateTime()
				+ ", getMsgType()=" + getMsgType() + "]";
	}
	

}

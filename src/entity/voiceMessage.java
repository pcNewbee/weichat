package entity;

import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class voiceMessage extends BaseMessage {
	@XStreamAlias("MediaId")
	private String MediaId;
	public String getMediaID() {
		return MediaId;
	}
	public void setMediaID(String MediaId) {
		this.MediaId = MediaId;
	}
	public voiceMessage() {
		super();
	}
	public voiceMessage(Map<String, String> requestMap,String MediaId) {
		super(requestMap);
		//设置语音消息的msgtype为voice
		this.setMsgType("voice");
		this.MediaId=MediaId;
	}
	@Override
	public String toString() {
		return "voiceMessage [MediaId=" + MediaId + ", getMediaID()=" + getMediaID() + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName() + ", getCreateTime()=" + getCreateTime()
				+ ", getMsgType()=" + getMsgType() + "]";
	}
	
}
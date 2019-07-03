package entity;

import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class videoMessage extends BaseMessage {
	@XStreamAlias("MediaId")
	private String MediaId;
	@XStreamAlias("Title")
	private String title;
	@XStreamAlias("Description")
	private String description;
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public videoMessage(Map<String, String> requestMap, String MediaId,
			String title, String description) {
		super(requestMap);
		this.MediaId = MediaId;
		//设置语音消息的msgtype为video
				this.setMsgType("video");
		this.title = title;
		this.description = description;
	}
	
	
}
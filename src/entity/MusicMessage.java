package entity;

import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("xml")
public class MusicMessage extends BaseMessage {
	private Music music;

	public MusicMessage(Map<String, String> requestMap, Music music) {
		super(requestMap);
		//����������Ϣ��msgtypeΪmusic
				this.setMsgType("music");
		this.music = music;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
}

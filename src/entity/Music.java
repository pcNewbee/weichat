package entity;

public class Music {
	private String title;
	private String description;
	private String MusicURL;
	private String HQMusicUrl;
	private String ThumbMediaId;
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
	public String getMusicURL() {
		return MusicURL;
	}
	public void setMusicURL(String MusicURL) {
		this.MusicURL = MusicURL;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String HQMusicUrl) {
		this.HQMusicUrl = HQMusicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String ThumbMediaId) {
		this.ThumbMediaId = ThumbMediaId;
	}
	public Music(String title, String description, String MusicURL, String HQMusicUrl, String ThumbMediaId) {
		super();
		this.title = title;
		this.description = description;
		this.MusicURL = MusicURL;
		this.HQMusicUrl = HQMusicUrl;
		this.ThumbMediaId = ThumbMediaId;
	}
}

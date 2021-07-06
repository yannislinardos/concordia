package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Article {
	
	String title, body, date;
	int imageid, articleid;

	public Article(){
		
	}
	
	
	public int getArticleid() {
		return articleid;
	}


	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getImageid() {
		return imageid;
	}

	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	
}

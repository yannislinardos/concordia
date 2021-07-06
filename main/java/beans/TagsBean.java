package beans;

public class TagsBean {
	
	boolean action;
	boolean horror;
	boolean romance;
	boolean thriller ;
	
	public TagsBean(boolean action,boolean horror,boolean romance,boolean thriller){
		this.action = action;
		this.horror = horror;
		this.romance = romance;
		this.thriller = thriller;
	}

	public boolean getAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public boolean getHorror() {
		return horror;
	}

	public void setHorror(boolean horror) {
		this.horror = horror;
	}

	public boolean getRomance() {
		return romance;
	}

	public void setRomance(boolean romance) {
		this.romance = romance;
	}

	public boolean getThriller() {
		return thriller;
	}

	public void setThriller(boolean thriller) {
		this.thriller = thriller;
	}

	
	
	
}

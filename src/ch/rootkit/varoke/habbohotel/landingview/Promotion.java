package ch.rootkit.varoke.habbohotel.landingview;

import ch.rootkit.varoke.communication.messages.ServerMessage;

public class Promotion {
	 
    private int Id;
    private String Title;
    private String Text;
    private String ButtonText;
    private int ButtonType;
    private String ButtonLink;
    private String ImageLink;
   
    public Promotion(int id, String title, String text, String buttonText, int buttonType, String buttonLink, String imageLink) {
        this.Id = id;
        this.Title = title;
        this.Text = text;
        this.ButtonText = buttonText;
        this.ButtonType = buttonType;
        this.ButtonLink = buttonLink;
        this.ImageLink = imageLink;
    }
    
    public int getId(){ return Id; }
    public String getTitle() { return Title; }
    public String getText() { return Text; }
    public int getButtonType() { return ButtonType; }
    public String getButtonText() { return ButtonText; }
    public String getButtonLink() { return ButtonLink; }
    public String getImageLink() { return ImageLink; }
    
    public void compose(ServerMessage message) throws Exception{
    	message.writeInt(getId());
    	message.writeString(getTitle());
    	message.writeString(getText());
    	message.writeString(getButtonText());
    	message.writeInt(getButtonType());
    	message.writeString(getButtonLink());
    	message.writeString(getImageLink());
    }
}

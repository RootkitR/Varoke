package ch.rootkit.varoke.communication.composers.landing;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class LandingWidgetMessageComposer implements MessageComposer{

	String Text;
	public LandingWidgetMessageComposer(String text){
		Text = text;
	}
	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		if(!StringUtils.isNullOrEmpty(Text))
		{
			message.writeString(Text);
			message.writeString(Text.split(",")[1]);
		}else{
			message.writeString("");
			message.writeString("");
		}
		return message;
	}
}

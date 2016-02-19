package ch.rootkit.varoke.communication.events.relationships;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.relationships.Relation;
import ch.rootkit.varoke.habbohotel.relationships.Relationship;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class SetRelationshipMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int userId = event.readInt();
		int state = event.readInt();
		if(userId == 0)
			return;
		if(!session.getHabbo().getMessenger().getFriends().containsKey(userId))
			return;
		session.getHabbo().removeRelationship(userId);
		Relationship newRelation = new Relationship(
				session.getHabbo().getId(),
				userId,
				Varoke.getFactory().getRelationshipFactory().getRelation(state)
				);
		if(newRelation.getType() == Relation.NONE){
			Varoke.getFactory().getRelationshipFactory().deleteRelationship(newRelation);
		}else{
		session.getHabbo().addRelationship(newRelation);
		Varoke.getFactory().getRelationshipFactory().updateRelationship(newRelation);
		}
		session.getHabbo().getMessenger().updateFriend(userId);
	}

}

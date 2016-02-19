package ch.rootkit.varoke.communication.events.relationships;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.relationships.RelationshipMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.relationships.Relationship;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GetRelationshipsMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int userId = event.readInt();
		List<Relationship> relations = Varoke.getSessionManager().getSessionByUserId(userId) == null ? Varoke.getFactory().getRelationshipFactory().readRelationships(userId) : Varoke.getSessionManager().getSessionByUserId(userId).getHabbo().getRelationships();
		session.sendComposer(new RelationshipMessageComposer(userId, relations));
	}
}

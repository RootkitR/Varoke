package ch.rootkit.varoke.communication.composers.relationships;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.relationships.Relation;
import ch.rootkit.varoke.habbohotel.relationships.Relationship;

public class RelationshipMessageComposer implements MessageComposer {

	
	final List<Relationship> Relations;
	int love = 0;
	int friend = 0;
	int hate = 0;
	int UserId;
	public RelationshipMessageComposer(int userId, List<Relationship> relations){
		for(Relationship r : relations){
			switch(r.getType()){
			case LOVE:
				love++;
				break;
			case FRIEND:
				friend++;
				break;
			case HATE:
				hate++;
				break;
			default:
				break;
			}
		}
		Relations = relations;
		UserId = userId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(UserId);
		result.writeInt(Relations.size());
		for(Relationship r : Relations){
			result.writeInt(Varoke.getFactory().getRelationshipFactory().getRelation(r.getType()));
			result.writeInt(r.getType() == Relation.LOVE ? love : r.getType() == Relation.FRIEND ? friend : hate);
			result.writeInt(r.getTo());
			result.writeString(Varoke.getFactory().getUserFactory().getValueFromUser("username", r.getTo()).toString());
			result.writeString(Varoke.getFactory().getUserFactory().getValueFromUser("look", r.getTo()).toString());
		}
		return result;
	}

}

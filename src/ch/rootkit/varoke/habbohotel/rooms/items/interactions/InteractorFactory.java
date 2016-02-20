package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class InteractorFactory {
	
	public static Interactor getInteractor(RoomItem roomItem){
		switch(roomItem.getBaseItem().getInteractionType()){
		case "pressure_plate":
		case "pressure_pad":
			return new PressurePlateInteractor(roomItem);
		case "gate":
			return new GateInteractor(roomItem);
		default:
			return new DefaultInteractor(roomItem);
		}
	}
	
}

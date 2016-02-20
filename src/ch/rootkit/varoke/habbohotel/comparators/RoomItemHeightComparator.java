package ch.rootkit.varoke.habbohotel.comparators;

import java.util.Comparator;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class RoomItemHeightComparator implements Comparator<RoomItem> {
	
    public int compare(RoomItem item1, RoomItem item2) {
        return (int)(item2.getFullHeight() - item1.getFullHeight());
    }
    
}

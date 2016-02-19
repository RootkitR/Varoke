package ch.rootkit.varoke.habbohotel.landingview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class LandingView {
	
	private List<Promotion> landingViewItems;
	
    public LandingView() {
        this.landingViewItems = new ArrayList<Promotion>();
    }
   
    public void initialize() throws Exception {
    	final long started = new Date().getTime();
		Logger.printVaroke("Initializing Landing View ");
        if(this.landingViewItems.size() > 0)
            this.landingViewItems.clear();
        this.landingViewItems = Varoke.getFactory().getVarokeFactory().loadLandingView();
        Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
    }

    public List<Promotion> getLandingViewItems() {
    	return landingViewItems;
    }
    
    public int size(){
    	return landingViewItems.size();
    }
}

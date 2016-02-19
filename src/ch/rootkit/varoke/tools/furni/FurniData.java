package ch.rootkit.varoke.tools.furni;

public class FurniData {

	private String FurniName;
	private String Revision;
    public FurniData(String furniName, String revision)
    {
        FurniName = furniName;
        Revision = revision;
    }
    public String getFurniName(){
    	if(FurniName.contains("*"))
    		return FurniName.split("\\*")[0];
    	return FurniName;
    }
    public String getRevision() { return Revision; }
}


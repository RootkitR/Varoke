package ch.rootkit.varoke.tools.catalog;

public class FurniData {
    private int Id;
    private int X;
    private int Y;
    private String Name;
    private boolean CanSit;
    private boolean CanWalk;

    public FurniData(int id, String name, int x, int y, boolean canSit, boolean canWalk)
    {
        Id = id;
        Name = name;
        X = x;
        Y = y;
        CanSit = canSit;
        CanWalk = canWalk;
    }
    public int getId(){ return this.Id;}
    public int getX(){ return this.X;}
    public int getY(){ return this.Y;}
    public String getName(){ return this.Name;}
    public boolean canSit(){ return this.CanSit;}
    public boolean canWalk(){ return this.CanWalk;}
}


package ch.rootkit.varoke.habbohotel.pathfinding;

public class Node
extends AbstractNode {
	
    public Node(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((this.absolute(this.getX() - endNode.getX()) + this.absolute(this.getY() - endNode.getY())) * 10);
    }

    private int absolute(int a) {
        return a > 0 ? a : - a;
    }

    public boolean equals(Node node) {
        return this.getX() == node.getX() && this.getY() == node.getY();
    }
}


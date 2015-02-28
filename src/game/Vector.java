package game;

/**
 * Created by jakedavies on 15-02-28.
 */
public class Vector {
    private int xPos;
    private int yPos;

    public Vector(int x,int y){
        this.xPos = x;
        this.yPos = y;
    }
    public int getXPos()
    {
        return xPos;
    }
    public int getYPos()
    {
        return yPos;
    }
    public int getOneDimensional(){
        return (yPos*10+xPos);
    }

    public String toString(){
        String s = "X:" + this.getXPos() + ", Y:" + this.getYPos() + "\n";
        return s;
    }

}


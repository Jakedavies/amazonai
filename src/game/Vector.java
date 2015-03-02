package game;

/**
 * Created by jakedavies on 15-02-28.
 */
public class Vector {
    public int x;
    public int y;

    public Vector(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Vector clone(){
        Vector v = new Vector(x,y);
        return v;
    }

    public int getOneDimensional(){
        return (y*10+x);
    }

    @Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

}


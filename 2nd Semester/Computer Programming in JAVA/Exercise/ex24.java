class Point { 
	private int x = 0; 
	private int y = 0; 
	public Point(int x, int y) { 
		this.x = x; 
		this.y = y; 
	} 
}

class Rectangle {
    private int width = 0;
    private int height = 0;
    private Point origin;

    public Rectangle() {
		origin = new Point(0,0);
    }
	
    public Rectangle(Point p) {
		origin = p;
    }
	
    public Rectangle(int w, int h) {
		this(new Point(0,0), w, h);
    }
	
    public Rectangle(Point p, int w, int h) {
		origin = p;
		width  = w;
		height = h;
    }

    // a method for computing the area of the rectangle
    public int area() {
		return width * height;
    }
}

public class ex24 {
	public static void main (String args[]){
		Rectangle r1 = new Rectangle();
		Rectangle r2 = new Rectangle(new Point(2,3));
		Rectangle r3 = new Rectangle(new Point(2,3),3,7);
		Rectangle r4 = new Rectangle(4,9);
	}
	
}



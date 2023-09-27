class Test {
     void  show() { 	    //it was static
        System.out.println("Show method in Test class");
    }
}

public class Q2 extends Test {
      
	 void show() {		    //it was static
        System.out.println("Show method in Q2 class");
    }
	  
    public static void main(String[] args) {
		  
        Test t = new Test();
        t.show();
		
        Q2 q = new Q2();
        q.show();

		t = q;
        t.show();

		q = (Q2)t;	    	//It was q = t;
        q.show();
    }
 }



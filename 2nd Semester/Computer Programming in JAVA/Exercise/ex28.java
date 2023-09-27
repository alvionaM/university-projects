class product {
      private String name;
      private String price;

     // Fill your code here
	 public product(String name, String price){
		this.name  = name;
		this.price = price;
	}

	public String getName(){
		return name;
	}
	public String getPrice(){
		return price;
	}
	public void setPrice(String newPrice){
		this.price = newPrice;
	}

}

 

class book extends product {
      private String author;
      private String publisher;

      // Fill your code here
	  public book(String name, String price, String publisher, String author){
		super(name,price);
		this.publisher = publisher;
		this.author = author;
	  }
	  public String getPublisher(){
		return publisher;
	  }
	  public String getAuthor(){
		return author;
	  }

} // book

 

class cd extends product {
      private String artist;

      // Fill your code here
	  public cd(String name, String price, String artist){
		super(name,price);
		this.artist = artist;
	  }
	  public String getArtist(){
		return artist;
	  }

} // cd

 

public class ex28 {

      public static void main (String args[]) {

            System.out.println("Welcome to our bookstore!");

            book b = new book("Effective Java", "30", "Addison Wesley", "Joshua Bloch");
            System.out.println("Titlos bibliou: " + b.getName() + "\nTimi: " + b.getPrice() + " \nEkdoseis:" + b.getPublisher()+"\nAuthor "+b.getAuthor());

            cd c = new cd ( "the wall", "20.99", "Pink Floyd");
            System.out.println("Titlos cd : " + c.getName() + "\nTimi: " + c.getPrice()+ "\nArtist: " + c.getArtist());

            c.setPrice("9,99");
            System.out.println("Titlos cd : " + c.getName() + "\nTimi me ektptwsi: " +c.getPrice()+ "\nArtist: " + c.getArtist());
      }
} // bookStore
abstract class Animal {

      String name;
      static int animals = 0;

      public Animal(String n) {
            name = n;
            animals++;
      }

      public abstract void speak();

      public static int numberOfAnimals() {
            return animals;
      }

} // Animal 

 

class Dog extends Animal {

      String sound = "woof";
      static int dogs = 0;

	  public Dog(String n){
			super(n); //Animal.animals increases, as well
			dogs++;
	  }
	  public void speak(){
			System.out.print(name+": "+sound+"\n");
	  }
	  public static int numberOfDogs() {
            return dogs;
      }

} //Dog 

 

class Cat extends Animal {

      String sound = "miaou";
      static int cats = 0;
	  
	  public Cat(String n){
			super(n); //Animal.animals increases, as well
			cats++;
	  }
	  public void speak(){
			System.out.print(name+": "+sound+"\n");
	  }
	  public static int numberOfCats() {
            return cats;
      }
     
	  
} // Cat 

 

public class ex27 {

    public static void main(String[] args) {

        Animal[] animal = { new Cat("stella"), new Cat("ziggy"), new Dog("azor") };

        System.out.println("cats: "+Cat.numberOfCats());
        System.out.println("dogs: "+Dog.numberOfDogs());
        System.out.println("animals: "+Animal.numberOfAnimals());

        for ( int i = 0; i < animal.length; i++ )
            animal[i].speak();
    }
}
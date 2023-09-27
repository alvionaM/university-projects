public class ex25 { 

	public static void main(String args[]) { 

		double[] grades ={7, 9, 10, 8.5, 8, 9.5, 2, 4, 7, 8};
		
		double[] grades_backup = new double[grades.length];
		
		System.out.println( "mean value: " + meanValue (grades) ); 
		System.out.println( "max value: " + maxValue (grades) ); 
		System.out.println( "min value: " + minValue (grades) ); 
		myCopy (grades, grades_backup); 
		printArray (grades); 
		printArray (grades_backup); 
	} 

	private static double meanValue(double[] array) { 
		double sum = 0;
		for (double item: array)
			sum+=item;
		return sum/(array.length);
	} 

	private static double maxValue(double[] array) { 
		double max = array[0]; 
		for (double item: array)
			if(item > max) max = item;
		return max;
	} 

	private static double minValue(double[] array) { 
		double min = array[0]; 
		for (double item: array)
			if(item < min) min = item;
		
		return min;
	} 

	private static void myCopy(double[] grades, double[] grades_backup) { 
		for (int i=0; i < grades.length; i++)
			grades_backup[i] = grades[i];

	} 

	private static void printArray(double[] array) { 
		for (int i=0; i < array.length; i++)
			System.out.print(i==array.length-1 ? array[i]+"  \n" : array[i]+"  ");
	} 
}

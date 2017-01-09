import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Histogram {
	private int[] histogramArray;
	private int numRow;
	private int numCol;
	private int minVal;
	private int maxVal;
    private File file1, file2;
    private Scanner scan;
    private java.io.PrintStream output;
    
    public Histogram (String input, String out){
    	file1 = new File(input);
    	file2 = new File(out);
    	
    	try {
    		output = new java.io.PrintStream(file2);
			scan = new Scanner(file1);
			if(scan.hasNextInt()) numRow = scan.nextInt();
			if(scan.hasNextInt()) numCol = scan.nextInt();
			if(scan.hasNextInt()) minVal = scan.nextInt();
			if(scan.hasNextInt()) maxVal = scan.nextInt();
			
			histogramArray = new int[maxVal+1];
			for(int i=0; i<=maxVal; i++) histogramArray[i] = 0;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public void computeHistogram(){
    	while(scan.hasNextInt()) histogramArray[scan.nextInt()]++;
    	scan.close();
    	
    	for(int i=0; i<=maxVal; i++){
			int count = 0;
			output.print("("+i+"): "+ histogramArray[i]+" ");
			while(count!=histogramArray[i] && count<80){
				output.print("+");
				count++;
			}
			output.println();
		}
    }

}

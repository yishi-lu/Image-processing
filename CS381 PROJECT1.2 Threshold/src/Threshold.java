import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Threshold {
	private int[][] thr_Ary;
	private int numRow;
	private int numCol;
	private int minVal;
	private int maxVal;
	int thresholdVal;
    private File file1, file2;
    private Scanner scan;
    private java.io.PrintStream output;
    
    public Threshold(String input){
    	file1 = new File(input);	
    	
    	try {
			scan = new Scanner(file1);
			if(scan.hasNextInt()) numRow = scan.nextInt();
			if(scan.hasNextInt()) numCol = scan.nextInt();
			if(scan.hasNextInt()) minVal = scan.nextInt();
			if(scan.hasNextInt()) maxVal = scan.nextInt();
			
			thr_Ary = new int[numRow][numCol];
			for(int i=0; i<numRow; i++){
				for(int j=0 ; j<numCol ; j++){
					thr_Ary[i][j] = 0;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    void computeThreshold(){
    	Scanner reader = new Scanner(System.in); 
    	System.out.println("Please enter a number: ");
    	thresholdVal = reader.nextInt();
    	String output_filename = "thr_" + thresholdVal +".txt";
    	file2 = new File(output_filename);
    	
    	try {
			output = new java.io.PrintStream(file2);
			output.println(numRow+" "+numCol+" "+0+" "+1);
			int input;
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					if(scan.hasNextInt()){
						input = scan.nextInt();
						if(input >= thresholdVal) {
							thr_Ary[i][j] = 1;
							output.print(thr_Ary[i][j]+" ");
						}
						else {
							thr_Ary[i][j] = 0;
							output.print(thr_Ary[i][j]+" ");
						}
					}
				}
				output.println();
			}
			output.close();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

}

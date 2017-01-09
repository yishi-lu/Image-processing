import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ImageProcessing {
	protected int numRow;
	protected int numCol;
	protected int minVal;
	protected int maxVal;
	protected int[][] imageAry;
	protected Scanner scan;
	protected java.io.PrintStream[] out = new java.io.PrintStream[2];
	protected File inputFile, output1, output2;
	
	public ImageProcessing(String file1, String file2, String file3){
		inputFile = new File(file1);
		output1 = new File(file2);
		output2 = new File(file3);
		
		try {
			out[0] = new java.io.PrintStream(output1);
			out[1] = new java.io.PrintStream(output2);
			scan = new Scanner(inputFile);
			if(scan.hasNextInt()) numRow = scan.nextInt();
			if(scan.hasNextInt()) numCol = scan.nextInt();
			if(scan.hasNextInt()) minVal = scan.nextInt();
			if(scan.hasNextInt()) maxVal = scan.nextInt();
			
			imageAry = new int[numRow][numCol];			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadImage(){
		for(int i=0; i<numRow; i++){
			for(int j=0; j<numCol; j++){
				imageAry[i][j] = 0;
			}
		}
			
		for(int i=0; i<numRow; i++){
			for(int j=0; j<numCol; j++){
				if(scan.hasNextInt()) imageAry[i][j] = scan.nextInt();		
			}
		}			
		scan.close();
	}
	
	public void prettyPrint(int[][] array, int r, int c){
		out[1].println("Petty print Hough array:");
		for(int i=0; i<r; i++){
			for(int j=0; j<c; j++){
				if(array[i][j]>0) out[1].print(array[i][j]);
				else out[1].print(" ");			
			}
			out[1].println();	
		}
		
		out[1].println();
		
		out[1].println("Petty print Hough array by dot:");
		for(int i=0; i<r; i++){
			for(int j=0; j<c; j++){
				if(array[i][j]>0) out[1].print(".");
				else out[1].print(" ");					
			}
			out[1].println();	
		}
		out[0].close();
		out[1].close();
	}
}

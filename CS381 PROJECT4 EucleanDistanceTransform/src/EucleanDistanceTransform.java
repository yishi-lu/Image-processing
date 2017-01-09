import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;


public class EucleanDistanceTransform {
	private int numRow;
	private int numCol;
	private int minVal;
	private int maxVal;
	private double newMin;
	private double newMax;
	private double sqrt2;
	private double[][] zeroFramedAry;
	private double[] neighborAry = new double[5];
	private Scanner scan;
	private java.io.PrintStream[] out = new java.io.PrintStream[2];
	private File inputFile, output1, output2;
	
	public EucleanDistanceTransform(String file1, String file2, String file3){
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
			
			zeroFramedAry = new double[numRow+2][numCol+2];
			sqrt2 = Math.sqrt(2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void zeroFrameArray(){
		for(int i=0; i<numRow+2; i++){
			for(int j=0; j<numCol+2; j++){
				zeroFramedAry[i][j] = 0;
			}
		}
	}
	
	public void loadImage(){
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(scan.hasNextInt()) zeroFramedAry[i][j] = scan.nextInt();
			}
		}
	}
	
	void firstPass(){
		boolean check = true;
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(zeroFramedAry[i][j]>0){
					zeroFramedAry[i][j] = getMin(i, j, check);
				}
			}
		}
		out[1].println("Result of Pass-1: ");
		prettyPrintDistace();
		out[1].println();
	}
	
	void secondPass(){
		int count = 0;
		boolean check = false;
		for(int i=numRow; i>=1; i--){
			for(int j=numCol; j>=1; j--){
				if(zeroFramedAry[i][j]>0){
					zeroFramedAry[i][j] = getMin(i, j, check);
				}
				if(count == 0){
					newMin = zeroFramedAry[i][j];
					newMax = zeroFramedAry[i][j];
					count++;
				}
				else {
					if(newMin>zeroFramedAry[i][j]+0.5) newMin = zeroFramedAry[i][j];
					if(newMax<zeroFramedAry[i][j]+0.5) newMax = zeroFramedAry[i][j];
				}
			}
		}
		out[1].println("Result of Pass-2: ");
		prettyPrintDistace();
		out[1].println();
	}
	
	public double getMin(int i, int j, boolean check){
		if(check){
			neighborAry[0] = zeroFramedAry[i-1][j-1]+sqrt2;
			neighborAry[1] = zeroFramedAry[i-1][j]+1;
			neighborAry[2] = zeroFramedAry[i-1][j+1]+sqrt2;
			neighborAry[3] = zeroFramedAry[i][j-1]+1;
			double min = neighborAry[0];
			for(int k=1; k<4; k++){
				if(min>neighborAry[k]) min = neighborAry[k];
			}
			return min;
		}
		else{
			neighborAry[0] = zeroFramedAry[i+1][j-1]+sqrt2;
			neighborAry[1] = zeroFramedAry[i+1][j]+1;
			neighborAry[2] = zeroFramedAry[i+1][j+1]+sqrt2;
			neighborAry[3] = zeroFramedAry[i][j+1]+1;
			neighborAry[4] = zeroFramedAry[i][j];
			double min = neighborAry[0];
			for(int k=1; k<=4; k++){
				if(min>neighborAry[k]) min = neighborAry[k];
			}
			return min;
		}
	}
	
	void prettyPrintDistace(){
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(zeroFramedAry[i][j]>0) out[1].print((int)(zeroFramedAry[i][j]+0.5)+" ");
				else out[1].print("  ");
			}
			out[1].println();
		}
	}
	
	void createImage(){
		DecimalFormat df = new DecimalFormat("0.00");  
		out[0].println(numRow+" "+numCol+" "+df.format(newMin)+" "+df.format(newMax));
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
					out[0].print(df.format(zeroFramedAry[i][j])+" ");//print image in two decimal places as Prof.Phillips says
			}
			out[0].println();
		}
		scan.close();
		out[0].close();
		out[1].close();
	}
}

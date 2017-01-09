import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ConnecetedCC {
	private int numRow;
	private int numCol;
	private int minVal;
	private int maxVal;
	private int newMin;
	private int newMax;
	private int newLable;
	private int newLabelUsed;
	private int[][] zeroFramedAry;
	private int[] neighborAry = new int[3];
	private int[] EQAry;
	private int EQArySize;
	private Node[] property;
    private Scanner scan;
    private java.io.PrintStream[] out = new java.io.PrintStream[3];
	private File inputFile, output1, output2, output3;
	
	public ConnecetedCC(String file1, String file2, String file3, String file4){
		inputFile = new File(file1);
		output1 = new File(file2);
		output2 = new File(file3);
		output3 = new File(file4);
		
		try {
			out[0] = new java.io.PrintStream(output1);
			out[1] = new java.io.PrintStream(output2);
			out[2] = new java.io.PrintStream(output3);
			scan = new Scanner(inputFile);
			if(scan.hasNextInt()) numRow = scan.nextInt();
			if(scan.hasNextInt()) numCol = scan.nextInt();
			if(scan.hasNextInt()) minVal = scan.nextInt();
			if(scan.hasNextInt()) maxVal = scan.nextInt();
			
			zeroFramedAry = new int[numRow+2][numCol+2];
			EQArySize = (numRow*numCol)/4;
			EQAry = new int[EQArySize];
			for(int i=0; i<EQArySize; i++) EQAry[i] = i;
			newLable = 0;
			newMin = 0;
			newMax = 0;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadImage(){
		for(int i=0; i<numRow+2; i++){
			for(int j=0; j<numCol+2; j++){
				zeroFramedAry[i][j] = 0;
			}
		}
		
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(scan.hasNextInt()) zeroFramedAry[i][j] = scan.nextInt();
			}
		}
	}
	
	void Pass1(){
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(zeroFramedAry[i][j]>0) {						
					neighborAry[0] = zeroFramedAry[i][j-1];
					neighborAry[1] = zeroFramedAry[i-1][j];
					if(neighborAry[0] == 0 && neighborAry[1] == 0) {
						newLable++;
						zeroFramedAry[i][j] = newLable;
					}
					else {
						if(neighborAry[0] == neighborAry[1]) zeroFramedAry[i][j] = neighborAry[0];
						else {
							if(neighborAry[0] == 0) zeroFramedAry[i][j]= neighborAry[1];
							if(neighborAry[1] == 0) zeroFramedAry[i][j]= neighborAry[0];
							if(neighborAry[0] != 0 && neighborAry[1] != 0){
								if(neighborAry[0]<neighborAry[1]) {
									zeroFramedAry[i][j] = neighborAry[0];
									updateEQAryForPass1(neighborAry[1], neighborAry[0]);
								}
								if(neighborAry[1]<neighborAry[0]) {
									zeroFramedAry[i][j] = neighborAry[1];
									updateEQAryForPass1(neighborAry[0], neighborAry[1]);
								}
							}
						}
					}
				}
			}
		}
		out[0].println("Pass1: ");
		prettyPrint();
		out[0].println("EQ array of Pass1: ");
		for(int i=0; i<=newLable; i++) 	out[0].print(EQAry[i]+" ");
		out[0].println();
		out[0].println();
	}
	
	void Pass2(){			
		int currentLabel = 0;
		boolean changeLabel = false;
		for(int i=numRow; i>=1; i--){
			for(int j=numCol; j>=1; j--){					
				if(zeroFramedAry[i][j]>0){
					currentLabel = zeroFramedAry[i][j];
					neighborAry[0] = zeroFramedAry[i][j+1];
					neighborAry[1] = zeroFramedAry[i+1][j];
					neighborAry[2] = zeroFramedAry[i][j];
					if(neighborAry[0] == 0 && neighborAry[1] == 0) continue;
					else {
						if(neighborAry[0] == neighborAry[1] && neighborAry[1] == neighborAry[2]) continue;
						else {
							getMin();
							for(int k=0; k<3; k++){									
								if(neighborAry[k] != 0) {
									zeroFramedAry[i][j] = neighborAry[k];
									changeLabel = true;
									break;
								}
							}
						}
					}
				}
				if (changeLabel) {
					updateEQAryForPass2(currentLabel, i, j);
					changeLabel = false;
				}
			}
		}
		out[0].println("Pass2: ");
		prettyPrint();
		out[0].println("EQ array of Pass2: ");
		for(int i=0; i<=newLable; i++) 	out[0].print(EQAry[i]+" ");
		out[0].println();
		out[0].println();
	}
	
	void Pass3(){
		boolean check = false;
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(zeroFramedAry[i][j]>0) zeroFramedAry[i][j] = EQAry[zeroFramedAry[i][j]];
				if(zeroFramedAry[i][j] == 0 && !check) check = true;
			}
		}
		out[0].println("Pass3: ");
		prettyPrint();
		out[0].println();
		out[0].println("EQ array of Pass3: ");
		for(int i=0; i<=newLable; i++) out[0].print(EQAry[i]+" ");
		out[0].println();
		out[0].println();
		
		//comupter property
		int label = 1;
		int countPixel = 0;
		property = new Node[newLabelUsed+1];
		for(int i=0; i<=newLabelUsed; i++) property[i] = new Node();
		while(label <= newLabelUsed){
			property[label].label = label;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(zeroFramedAry[i][j] == label) {
						countPixel++;
						if(countPixel == 1) {
							property[label].minRow = i;
							property[label].minCol = j;
							property[label].maxRow = i;
							property[label].maxCol = j;
						}
						else {
							if(i < property[label].minRow) property[label].minRow = i;
							if(j < property[label].minCol) property[label].minCol = j;
							if(i > property[label].maxRow) property[label].maxRow = i;
							if(j > property[label].maxCol) property[label].maxCol = j;
						}
					}
				}
			}
			property[label].numbpixels = countPixel;
			//Because we add 2 rows and 2 cols as zero frame, so we should decrement minRow, minCol, maxRow, and maxCol by one to get the actual position value of pixel in the image 
			property[label].minRow--;
			property[label].minCol--;
			property[label].maxRow--;
			property[label].maxCol--;
			countPixel = 0;
			label++;
		}
		if(check) newMin = 0;
		else newMin = 1;
		newMax = newLabelUsed;
	}
	
	void getMin(){
		for (int j = 0; j < 3; j++) {
			int min = j;
			for (int i = j+1; i < 3; i++) if (neighborAry[i] < neighborAry[min]) min = i;
			if(min != j) swap(j, min);
		}						
	}
	
	void swap(int x, int y){
		int temp = neighborAry[x];
		neighborAry[x] = neighborAry[y];
		neighborAry[y] = temp;
	}
	
	void updateEQAryForPass1(int i, int j){
		EQAry[i] = j;
	}
	
	void updateEQAryForPass2(int currentLabel, int i, int j){
		if(currentLabel != zeroFramedAry[i][j]) EQAry[currentLabel] = zeroFramedAry[i][j];
	}
	
	void manageEQAry(){
		newLabelUsed = 0;
		for(int i=1; i<= newLable; i++){
		    if(i == EQAry[i]) {
		    	newLabelUsed++;
		    	EQAry[i] = newLabelUsed;
		    }
		    else {
		    	EQAry[i] = EQAry[EQAry[i]];
		    }
		}
		out[0].println("After manage the EQAry: ");
		for(int i=0; i<=newLable; i++) 	out[0].print(EQAry[i]+" ");
		out[0].println();
		out[0].println();
	}	
	
	void prettyPrint(){
		for(int i=1; i<=numRow; i++){
			for(int j=1; j<=numCol; j++){
				if(zeroFramedAry[i][j] > 0) out[0].print(zeroFramedAry[i][j]+" ");
				else out[0].print("  ");
			}
			out[0].println();
		}
		out[0].println();
	}
	
	void outputToFile2(){
		out[1].println(numRow+" "+numCol+" "+newMin+" "+newMax);
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++) out[1].print(zeroFramedAry[i][j] + " ");
			out[1].println();
		}
	}
	
	void printProperty(){
		for(int i=1; i<=newLabelUsed; i++){
			out[2].println("label: "+property[i].label+", "+"numbpixels: "+property[i].numbpixels+", "+"minRow: "+property[i].minRow+", "+"minCol: "
			+property[i].minCol+", "+"maxRow: "+property[i].maxRow+", "+"maxCol: "+property[i].maxCol);
		}
		
		scan.close();
		out[0].close();
		out[1].close();
		out[2].close();
	}
}

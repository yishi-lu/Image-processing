#include <iostream>
#include <fstream>
using namespace std;
//8-connecntedCC
class ConnecetedCC{
	struct Node{
	    int label;
    	int numbpixels;
    	int minRow;
    	int minCol;
    	int maxRow;
		int maxCol;
    };//struct of Node
    
	private:
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int newMin;
		int newMax;
		int newLable;
		int newLabelUsed;
		int** zeroFramedAry;
		int neighborAry[5];
		int* EQAry;
		int EQArySize;
		Node* property;
		ifstream in;
	    ofstream out[3];
	    string inputFile, output1, output2, output3;
		
	public:
		ConnecetedCC(string file1, string file2, string file3, string file4){
			inputFile = file1;
			output1 = file2;
			output2 = file3;
			output3 = file4;
			
			in.open(inputFile.c_str());	
			out[0].open(output1.c_str());
			out[1].open(output2.c_str());
			out[2].open(output3.c_str());
			
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			zeroFramedAry = new int*[numRow+2];
			for(int i=0; i<numRow+2; i++) zeroFramedAry[i] = new int[numCol+2];
			
			EQArySize = (numRow*numCol)/4;
			EQAry = new int[EQArySize];
			for(int i=0; i<EQArySize; i++) EQAry[i] = i;
			
			newLable = 0;			
		}
		
		~ConnecetedCC(){
			for(int i=0 ; i<numRow+2; i++) delete[] zeroFramedAry[i];
			delete[] zeroFramedAry;
			delete[] EQAry;
			delete[] property;
		}
		
		void loadImage(){
			for(int i=0; i<numRow+2; i++){
				for(int j=0; j<numCol+2; j++){
					zeroFramedAry[i][j] = 0;
				}
			}
			
			int input;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(in>>input) zeroFramedAry[i][j] = input;
				}
			}
		}
		
		void Pass1(){
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(zeroFramedAry[i][j]>0) {						
						neighborAry[0] = zeroFramedAry[i][j-1];
						neighborAry[1] = zeroFramedAry[i-1][j];
						neighborAry[2] = zeroFramedAry[i-1][j+1]; 
						neighborAry[3] = zeroFramedAry[i-1][j-1]; 
						if(neighborAry[0] == 0 && neighborAry[1] == 0 && neighborAry[2] == 0 && neighborAry[3] == 0) {
							newLable++;
							zeroFramedAry[i][j] = newLable;
						}
						else {							
							getMin1();
							for(int k=0; k<4; k++){									
									if(neighborAry[k] != 0) {
										zeroFramedAry[i][j] = neighborAry[k];
										if(neighborAry[0] != 0) EQAry[neighborAry[0]] = neighborAry[k];
										if(neighborAry[1] != 0) EQAry[neighborAry[1]] = neighborAry[k];
										if(neighborAry[2] != 0) EQAry[neighborAry[2]] = neighborAry[k];
										if(neighborAry[3] != 0) EQAry[neighborAry[3]] = neighborAry[k];
										break;
									}
								}
							}
						}
					}
				}
			
			out[0]<<"Pass1:"<<endl;
			prettyPrint();
			out[0]<<endl;
			out[0]<<"EQ Array of Pass1: "<<endl;
			for(int i=0; i<=newLable; i++) 	out[0]<<EQAry[i]<<" ";
			out[0]<<endl;
			out[0]<<endl;
		}
		
		void Pass2(){			
			int currentLabel;
			//bool changeLabel = false;
			for(int i=numRow; i>=1; i--){
				for(int j=numCol; j>=1; j--){					
					if(zeroFramedAry[i][j]>0){
						currentLabel = zeroFramedAry[i][j];
						neighborAry[0] = zeroFramedAry[i][j+1];
						neighborAry[1] = zeroFramedAry[i+1][j];
						neighborAry[2] = zeroFramedAry[i+1][j+1]; 
						neighborAry[3] = zeroFramedAry[i+1][j-1]; 
						neighborAry[4] = zeroFramedAry[i][j]; 
						if(neighborAry[0] == 0 && neighborAry[1] == 0 && neighborAry[2] == 0 && neighborAry[3] == 0) continue;
						else {					
							getMin2();
							for(int k=0; k<5; k++){									
								if(neighborAry[k] != 0) {
									EQAry[zeroFramedAry[i][j]] = neighborAry[k];
									zeroFramedAry[i][j] = neighborAry[k];								
									updateEQAry(currentLabel, i, j);
									break;
								}
							}
							
						}
					}				
				}
			}
			out[0]<<"Pass2:"<<endl;
			prettyPrint();
			out[0]<<endl;
			out[0]<<"EQ Array of Pass2: "<<endl;
			for(int i=0; i<=newLable; i++) 	out[0]<<EQAry[i]<<" ";
			out[0]<<endl;
			out[0]<<endl;
		}
		
		void Pass3(){
			bool check = false;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(zeroFramedAry[i][j]>0) zeroFramedAry[i][j] = EQAry[zeroFramedAry[i][j]];
					if(zeroFramedAry[i][j] == 0 && !check) check = true;
				}
			}
			out[0]<<"Pass3:"<<endl;
			prettyPrint();
			out[0]<<endl;
			out[0]<<"EQ Array of Pass3: "<<endl;
			for(int i=0; i<=newLable; i++) 	out[0]<<EQAry[i]<<" ";
			out[0]<<endl;
			out[0]<<endl;
			
			//compute property
			int label = 1;
			int countPixel = 0;
			property = new Node[newLabelUsed+1];
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
				//because we add 2 rows and 2 cols as zero frame, so we should decrement minRow, minCol, maxRow, and maxCol by one to get the actual position value of pixel in the image 
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
		
		void getMin1(){
			for (int j = 0; j < 4; j++) {
				int min = j;
				for (int i = j+1; i < 4; i++) if (neighborAry[i] < neighborAry[min]) min = i;
				if(min != j) swap(j, min);
			}						
		}
		
		void getMin2(){
			for (int j = 0; j < 5; j++) {
				int min = j;
				for (int i = j+1; i < 5; i++) if (neighborAry[i] < neighborAry[min]) min = i;
				if(min != j) swap(j, min);
			}						
		}
		
		void swap(int x, int y){
			int temp = neighborAry[x];
			neighborAry[x] = neighborAry[y];
			neighborAry[y] = temp;
		}
		
		
		void updateEQAry(int currentLabel, int i, int j){
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
			out[0]<<"After managing the EQ Array:"<<endl;
			for(int i=0; i<=newLable; i++) 	out[0]<<EQAry[i]<<" ";
			out[0]<<endl;
			out[0]<<endl;
		}	
		
		void prettyPrint(){
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(zeroFramedAry[i][j] > 0) out[0]<<zeroFramedAry[i][j]<<" ";
					else out[0]<<"  ";
				}
			out[0]<<endl;
			}
		}
		
		void outputToFile2(){
			out[1]<<numRow<<" "<<numCol<<" "<<newMin<<" "<<newMax<<endl;
				for(int i=1; i<=numRow; i++){
					for(int j=1; j<=numCol; j++){
						if(zeroFramedAry[i][j] == 0) out[1]<<"0 ";
						else out[1]<<zeroFramedAry[i][j]<<" ";
				}
				out[1]<<endl;
			}
		}
		
		int relabel(int i){
			int result = i;
			if(result != EQAry[result]) return result = relabel(EQAry[result]);
			else return result;
		}
		
		void printProperty(){
			for(int i=1; i<=newLabelUsed; i++){
				out[2]<<"label: "<<property[i].label<<" "<<"numbpixels: "<<property[i].numbpixels<<" "<<"minRow: "<<property[i].minRow<<" "<<"minCol: "
				<<property[i].minCol<<" "<<"maxRow: "<<property[i].maxRow<<" "<<"maxCol: "<<property[i].maxCol<<endl;
			}
			
			in.close();
			out[0].close();
			out[1].close();
			out[2].close();
		}
};

int main(int argc, char* argv[]){
	if(argc < 5){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments
	
	string f1, f2, f3, f4;
	f1 = argv[1];
	f2 = argv[2];
	f3 = argv[3];
	f4 = argv[4];
	
	ConnecetedCC cc(f1, f2, f3, f4);
	cc.loadImage();
	cc.Pass1();
	cc.Pass2();
	cc.manageEQAry();
	cc.Pass3();
	cc.outputToFile2();
	cc.printProperty();
	return 0;
}

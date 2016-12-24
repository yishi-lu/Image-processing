#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>
using namespace std;

class EucleanDistanceTransform{
	private:
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		double newMin;
		double newMax;
		double sqrt2;
		double** zeroFramedAry;
		double neighborAry[5];
		ifstream in;
	    ofstream out[2];
	    string inputFile, output1, output2;
	public:
		EucleanDistanceTransform(string file1, string file2, string file3){
			inputFile = file1;
			output1 = file2;
			output2 = file3;
			
			in.open(inputFile.c_str());	
			out[0].open(output1.c_str());
			out[1].open(output2.c_str());
			
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			zeroFramedAry = new double*[numRow+2];
			for(int i=0; i<numRow+2; i++) zeroFramedAry[i] = new double[numCol+2];
			
			sqrt2 = sqrt(2);
		}
		
		~EucleanDistanceTransform(){
			for(int i=0 ; i<numRow+2; i++) delete[] zeroFramedAry[i];
			delete[] zeroFramedAry;
		}
		
		void zeroFrameArray(){
			for(int i=0; i<numRow+2; i++){
				for(int j=0; j<numCol+2; j++){
					zeroFramedAry[i][j] = 0;
				}
			}
		}
		
		void loadImage(){
			int input;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(in>>input) zeroFramedAry[i][j] = input;
				}
			}
		}
		
		void firstPass(){
			bool check = true;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					if(zeroFramedAry[i][j]>0){
						zeroFramedAry[i][j] = getMin(i, j, check);
					}
				}
			}
			out[1]<<"Result of Pass-1: "<<endl;
			prettyPrintDistace();
			out[1]<<endl;
		}
		
		void secondPass(){
			int count = 0;
			bool check = false;
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
						if(newMin>zeroFramedAry[i][j]) newMin = zeroFramedAry[i][j];
						if(newMax<zeroFramedAry[i][j]) newMax = zeroFramedAry[i][j];
					}
				}
			}
			out[1]<<"Result of Pass-2: "<<endl;
			prettyPrintDistace();
			out[1]<<endl;
		}
		
		double getMin(int i, int j, bool check){
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
					if(zeroFramedAry[i][j]>0) out[1]<<(int)(zeroFramedAry[i][j]+0.5)<<" ";
					else out[1]<<"  ";
				}
				out[1]<<endl;
			}
		}
		
		void createImage(){
			out[0]<<numRow<<" "<<numCol<<" "<<setprecision(2)<<fixed<<newMin<<" "<<newMax<<endl;
			for(int i=1; i<=numRow; i++){
				for(int j=1; j<=numCol; j++){
					out[0]<<setprecision (2) << fixed<<zeroFramedAry[i][j]<<" ";
					//print in two decimal point as Professor. Phillips says
				}
				out[0]<<endl;
			}
			in.close();
			out[0].close();
			out[1].close();
		}
};

int main(int argc, char* argv[]){
	if(argc < 4){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments
	
	string f1, f2, f3, f4;
	f1 = argv[1];
	f2 = argv[2];
	f3 = argv[3];	

	EucleanDistanceTransform e(f1, f2, f3);
	e.zeroFrameArray();
	e.loadImage();
	e.firstPass();
	e.secondPass();
	e.createImage();
	return 0;
}

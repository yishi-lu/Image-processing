#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;

class ImageProcessing{
	friend class HoughTransform;
	
	private:
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int** imageAry;
		ifstream in;
		ofstream out1, out2;
		
	public:
		ImageProcessing(string file1, string file2, string file3){
			in.open(file1.c_str());	
			out1.open(file2.c_str());
			out2.open(file3.c_str());
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			imageAry = new int*[numRow];
			for(int i=0; i<numRow; i++) imageAry[i] = new int[numCol];
		}
		
		~ImageProcessing(){
			for(int i=0 ; i<numRow; i++) delete[] imageAry[i];
			delete[] imageAry;
		}
		
		void loadImage(){
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					imageAry[i][j] = 0;
				}
			}
			
			int input;
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					if(in>>input) imageAry[i][j] = input;
				}
			}			
			in.close();
		}	
		
		void prettyPrint(int** array, int r, int c){
			out2<<"Petty print Hough array:"<<endl;
			for(int i=0; i<r; i++){
				for(int j=0; j<c; j++){
					if(array[i][j]>0) out2<<array[i][j];
					else out2<<" ";				
				}
				out2<<endl;
			}
			
			out2<<endl;
			
			out2<<"Petty print Hough array by dot:"<<endl;
			for(int i=0; i<r; i++){
				for(int j=0; j<c; j++){
					if(array[i][j]>0) out2<<".";
					else out2<<" ";				
				}
				out2<<endl;
			}
			out1.close();
			out2.close();
		}
};

class XYCoord{
	friend class HoughTransform;
	
	private:
		int x;
		int y;
		
	public:
	XYCoord(int i, int j){
		x = i;
		y = j;
	}
};

class HoughTransform {	
	private:		
		int angleInDegree;
		double angleInRadians;
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int** HoughAry;
		double pi;
		ImageProcessing i;
		
	public:
		HoughTransform(string file1, string file2, string file3): i(file1, file2, file3)
		{		
			i.loadImage();	
			numRow = 180;
			numCol = sqrt(i.numRow*i.numRow + i.numCol*i.numCol);
			pi = atan(1)*4;
			
			HoughAry = new int*[numRow];			
			for(int i=0; i<numRow; i++) HoughAry[i] = new int[numCol];
			
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					HoughAry[i][j] = 0;
				}
			}
		}
		
		~HoughTransform(){
			for(int i=0 ; i<numRow; i++) delete[] HoughAry[i];
			delete[] HoughAry;			
		}
		
		void start(){
			int distance;
			for(int k=0; k<i.numRow; k++){
				for(int j=0; j<i.numCol; j++){
					if(i.imageAry[k][j] > 0){
						XYCoord point(k, j);
						angleInDegree = 0;
						while(angleInDegree < 180){
							angleInRadians = angleInDegree / 180.00 * pi;
							distance = computeDistance(point, angleInRadians);							
							HoughAry[angleInDegree][distance]++;
							angleInDegree++;
						}					
					}						
				}							
			}		
		}			
		
		int computeDistance(XYCoord p, double ar){
			int result, x, y;
			double t, d;
			x = i.numRow - p.x - 1;			
			y = p.y;//Here, the origin I choose to set at left-bottom corner 	
			t = ar - atan2(x, y) - pi/2;
			d = abs(sqrt(x*x + y*y) * cos(t));
			result = d;			
			return result;
		}
		
		void determineHeader(){
			for(int m=0; m<numRow; m++){
				for(int n=0; n<numCol; n++){
					if(m == 0 && n == 0){
						minVal = HoughAry[m][n];
						maxVal = HoughAry[m][n];
					}
					else {
						if(HoughAry[m][n] < minVal) minVal = HoughAry[m][n];
						if(HoughAry[m][n] > maxVal) maxVal = HoughAry[m][n];
					}
				}
			}
			
			i.out1<<numRow<<" "<<numCol<<" "<<minVal<<" "<<maxVal<<endl;
			for(int m=0; m<numRow; m++){
				for(int n=0; n<numCol; n++){
					i.out1<<HoughAry[m][n]<<" ";
				}
				i.out1<<endl;
			}
			
			i.prettyPrint(HoughAry, numRow, numCol);
		}		
};

int main(int argc, char* argv[]){
	if(argc < 4){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments	
	
	string f1, f2, f3;
	f1 = argv[1];
	f2 = argv[2];
	f3 = argv[3];
	HoughTransform h(f1, f2, f3);
	h.start();
	h.determineHeader();
	return 0;
}

#include <iostream>
#include <fstream>
using namespace std;

class Decode{
	private:
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int** imageAry;
		ifstream in;
		ofstream out;
		
	public:
		Decode(string file1, string file2){
			in.open(file1.c_str());
			out.open(file2.c_str());
			
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			imageAry = new int*[numRow];
			for(int i=0; i<numRow; i++) imageAry[i] = new int[numCol];
		}
		
		~Decode(){
			for(int i=0 ; i<numRow; i++) delete[] imageAry[i];
			delete[] imageAry;
		}
		
		void zeroFrameAry(){			
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					imageAry[i][j] = 0;
				}
			}
		}
		
		void start(){
			int value;
			int startRow;
			int startCol;
			int pixelValue;
			int numPixel;
			
			while(true){
				if(in>>value) startRow = value;
		    	if(in>>value) startCol = value;
				if(in>>value) pixelValue = value;
				if(in>>value) numPixel = value;
				else break;
				for(int i=startCol; i<startCol+numPixel; i++){
					imageAry[startRow][i] = pixelValue;
				}
			}
		}
		
		void prettyPrint(){
			out<<numRow<<" "<<numCol<<" "<<minVal<<" "<<maxVal<<endl;
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					out<<imageAry[i][j]<<" ";
				}
				out<<endl;
			}
			in.close();
			out.close();
		}
};

int main(int argc, char* argv[]){
	if(argc < 3){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments
	
	string f1, f2;
	f1 = argv[1];
	f2 = argv[2];
	Decode d(f1, f2);
	d.zeroFrameAry();
	d.start();
	d.prettyPrint();
}

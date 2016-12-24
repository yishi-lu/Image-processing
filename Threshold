#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

class Threshold{
	private:
		int** thr_Ary;
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int thresholdVal;
		ifstream in;
	    ofstream out;
		string file1, file2;
		
	public:
		Threshold(string input){
			file1 = input;
			
			in.open(file1.c_str());
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			thr_Ary = new int*[numRow];
			for(int i=0; i<numRow; i++) thr_Ary[i] = new int[numCol];
			
			for(int i=0; i<numRow; i++){
				for(int j=0 ; j<numCol ; j++){
					thr_Ary[i][j] = 0;
				}
			}
		}
		
		~Threshold(){
			for(int i=0 ; i<numRow; i++) delete[] thr_Ary[i];
			delete[] thr_Ary;
		}
		
		void computeThreshold(){
			string num;
			cout<<"Please enter a number: ";
			cin>>num;
			thresholdVal = atoi(num.c_str());
			file2 = "thr_" + num +".txt";
			out.open(file2.c_str());
			out<<numRow<<" "<<numCol<<" "<<0<<" "<<1<<endl;
			
			int input;
			for(int i=0; i<numRow; i++){
				for(int j=0; j<numCol; j++){
					if(in>>input){
						if(input >= thresholdVal) {
							thr_Ary[i][j] = 1;
							out<< thr_Ary[i][j]<<" ";
						}
						else {
							thr_Ary[i][j] = 0;
							out<< thr_Ary[i][j]<<" ";
						}
					}
				}
				out<<endl;
			}
			in.close();
			out.close();
		}	
};

int main(int argc, char* argv[]){
	if(argc < 2){
		cout << "usage : " << argv[0] << " <input file>\n";
		return -1;
	}//check the command-line arguments
	
	string f1;
	f1 = argv[1];
	Threshold h(f1);
	h.computeThreshold();
	return 0;
}

//Project1.1 Histogram
//Yishi Lu
//08/29/2016

#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;

class Histogram{
	private:
		int* histogramArray;
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		ifstream in;
	    ofstream out;
		string file1, file2;
		
	public:
		Histogram(string input, string output){
			file1 = input;
			file2 = output;
			
			in.open(file1.c_str());
			out.open(file2.c_str());
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			histogramArray = new int[maxVal+1];
			for(int i=0; i<=maxVal; i++) histogramArray[i] = 0;
		}
		
		~Histogram(){
			delete[] histogramArray;
		}
		
		void computeHistogram(){			
			int input;	
			while(in>>input) histogramArray[input]++;
			in.close();
			
			for(int i=0; i<=maxVal; i++){
				int count = 0;
				out<<"("<<i<<")"<<": "<<histogramArray[i]<<" ";
				while(count!=histogramArray[i] && count<80){
					out<<"+";
					count++;
				}
				out<<endl;
			}
			out.close();
		}
};

int main(int argc, char* argv[]){
	if(argc < 3){
		cout << "usage : " << argv[0] << " <input file>  <output file>\n";
		return -1;
	}//check the command-line arguments
	
	string f1, f2;
	f1 = argv[1];
	f2 = argv[2];
	Histogram h(f1, f2);
	h.computeHistogram();
	return 0;
}

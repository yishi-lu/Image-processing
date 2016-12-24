#include <iostream>
#include <fstream>
using namespace std;

class RunLength{
	private:
		int currentColor;
		int P;
		int length;
	    int numRow;
		int numCol;
		int minVal;
		int maxVal;
		ifstream in;
		ofstream out;
		
	public:
		RunLength(string file1, string file2){
			in.open(file1.c_str());
			out.open(file2.c_str());
			
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			currentColor = 0;
			length = 0;
		}
		
		void strat(){
			out<<numRow<<" "<<numCol<<" "<<minVal<<" "<<maxVal<<endl;
			bool first = true;
			bool nextIsZero = true; 
			bool changeRow = false;
			int value;
			int previousValue;
			for(int i=0; i<numRow;i++){
				for(int j=0; j<numCol; j++){
					if(in>>value){						
						P = value;
						if(changeRow){
							if(P != 0){
								nextIsZero = false; 
								if (previousValue != 0) {
									currentColor = 0;
									out<<length<<endl;
								}
								currentColor = P;
								out<<i<<" "<<j<<" "<<currentColor<<" ";
								length = 1;
							}							
							changeRow = false;
						}
						else {
							if(P != 0){
								nextIsZero = false; 
								if(P != currentColor){
									if(!first && previousValue == currentColor && previousValue != 0) {
										currentColor = 0;
										out<<length<<endl;
									}
									currentColor = P;
									out<<i<<" "<<j<<" "<<currentColor<<" ";
									length = 1;
								}
								else {								
									length++;
								}
							}
							else {
								if(nextIsZero) continue;
								currentColor = 0;
								out<<length<<endl;	
								nextIsZero = true;						
							}
						}
						first = false;
						previousValue = P;					
					}	
				}
				changeRow = true;
			}
			if(P != 0 ) out<<length;
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
	RunLength r(f1, f2);
	r.strat();
}

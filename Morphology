#include <iostream>
#include <fstream>
using namespace std;
//f1.txt f2.txt f3.txt f4.txt f5.txt f6.txt final.txt 
class Morphology{
	private:
		int numRowsIMG; 
		int numColsIMG; 
	 	int minValIMG; 
	 	int maxValIMG;
  	 	int numRowsStrctElem; 
	 	int numColsStrctElem; 
	 	int minValStrctElem;
	 	int maxValStrctElem; 
	 	int rowOrigin;
	 	int colOrigin; 
	 	int rowFrameSize; 
	 	int colFrameSize;
		int aryRow;
		int aryCol; 
	 	int** imgAry;
	 	int** morphAry;
	 	int** structElem;
	 	ifstream in1, in2;
		ofstream out1, out2, out3, out4;
	
	public:
		Morphology(string file1, string file2, string file3, string file4, string file5, string file6){
			in1.open(file1.c_str());
			in2.open(file2.c_str());	
			out1.open(file3.c_str());
			out2.open(file4.c_str());
			out3.open(file5.c_str());
			out4.open(file6.c_str());
			
			int value1, value2;	
			if(in1>>value1) numRowsIMG = value1;
		    if(in1>>value1) numColsIMG = value1;
			if(in1>>value1) minValIMG = value1;
			if(in1>>value1) maxValIMG = value1;
			
			if(in2>>value2) numRowsStrctElem = value2;
		    if(in2>>value2) numColsStrctElem = value2;
			if(in2>>value2) minValStrctElem = value2;
			if(in2>>value2) maxValStrctElem = value2;
			if(in2>>value2) rowOrigin = value2;
			if(in2>>value2) colOrigin = value2;	
			
			computeFrameSize();
			
			aryRow = numRowsIMG+rowFrameSize;
			aryCol = numColsIMG+colFrameSize;
			
			initialization();														
		}
		
		~Morphology(){
			for(int i=0; i<aryRow; i++) {
				delete[] imgAry[i];
				delete[] morphAry[i];
			}
			for(int i=0; i<numRowsStrctElem; i++) delete[] structElem[i];
			delete[] imgAry;
			delete[] morphAry;
			delete[] structElem;
		}
		
		void computeFrameSize(){
			if(numRowsStrctElem%2 == 0) rowFrameSize = numRowsStrctElem;
			else rowFrameSize = numRowsStrctElem+1;
			if(numColsStrctElem%2 == 0) colFrameSize = numColsStrctElem;
			else colFrameSize = numColsStrctElem+1;			
		}
		
		void initialization(){
			imgAry = new int*[aryRow];
			for(int i=0; i<aryRow; i++) imgAry[i] = new int[aryCol];
			zeroFrame();
			loadImage();
			cout<<"Input Image:"<<endl;
			prettyPrint(imgAry, aryRow, aryCol);
			cout<<endl;
			
			morphAry = new int*[aryRow];
			for(int i=0; i<aryRow; i++) morphAry[i] = new int[aryCol];
			
			structElem = new int*[numRowsStrctElem];
			for(int i=0; i<numRowsStrctElem; i++) structElem[i] = new int[numColsStrctElem];
			loadStructure();
			cout<<"Structuring Element:"<<endl;
			for(int i=0; i<numRowsStrctElem; i++){
				for(int j=0; j<numColsStrctElem; j++){
					if(structElem[i][j]>0) cout<<structElem[i][j];
					else cout<<" ";
				}
				cout<<endl;
			}		
			cout<<endl;		
		}
		
		void zeroFrame(){
			for(int i=0; i<aryRow; i++){
				for(int j=0; j<aryCol; j++){
					imgAry[i][j] = 0;
				}
			}
		}
		
		void resetMorphAry(){
			for(int i=0; i<aryRow; i++){
				for(int j=0; j<aryCol; j++){
					morphAry[i][j] = 0;
				}
			}
		}
		
		void loadImage(){
			int value;
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(in1>>value) imgAry[i][j] = value;
				}
			}		
		}
		
		void loadStructure(){
			int value;
			for(int i=0; i<numRowsStrctElem; i++){
				for(int j=0; j<numColsStrctElem; j++){
					if(in2>>value) structElem[i][j] = value; 					
				}
			}
		}
		
		void delation(){
			resetMorphAry();			
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(imgAry[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(structElem[m-r][n-c]!=0) morphAry[m][n] = structElem[m-r][n-c];								
							}							
						}						
					}
				}
			}
			cout<<"delation result:"<<endl;
			prettyPrint(morphAry, aryRow, aryCol);
			cout<<endl;
			
			out1<<"delation result:"<<endl;
			outPutResult(out1);
		}
		
		void erosion(){
			resetMorphAry();
			bool check = false;			
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(imgAry[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(imgAry[m][n] != structElem[m-r][n-c] && structElem[m-r][n-c]!=0){
									morphAry[i][j] = 0;
									check = true;
									break;								
								}							
							}
							if(check) break;
						}
						if(!check) morphAry[i][j] = 1;					
						check = false;
					}
				}
			}
			cout<<"erosion result:"<<endl;
			prettyPrint(morphAry, aryRow, aryCol);
			cout<<endl;
			
			out2<<"erosion result:"<<endl;
			outPutResult(out2);
		}
		
		void closing(){
			resetMorphAry();
			int** temp;
			temp = new int*[aryRow];
			for(int i=0; i<aryRow; i++) temp[i] = new int[aryCol];
			
			for(int i=0; i<aryRow; i++){
				for(int j=0; j<aryCol; j++){
					temp[i][j] = 0;
				}
			}			
			//delation
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(imgAry[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(structElem[m-r][n-c]!=0) temp[m][n] = structElem[m-r][n-c];								
							}							
						}						
					}
				}
			}			
			//erosion
			bool check = false;	
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(temp[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(temp[m][n] != structElem[m-r][n-c] && structElem[m-r][n-c]!=0){
									morphAry[i][j] = 0;
									check = true;
									break;								
								}							
							}
							if(check) break;
						}
						if(!check) morphAry[i][j] = 1;					
						check = false;
					}
				}
			}
			
			cout<<"closing result:"<<endl;
			prettyPrint(morphAry, aryRow, aryCol);
			cout<<endl;
			
			out3<<"closing result:"<<endl;
			outPutResult(out3);
			
			for(int i=0; i<aryRow; i++) delete[] temp[i];
			delete[] temp;	
		} 
		
		void opening(){
			resetMorphAry();
			int** temp;
			temp = new int*[aryRow];
			for(int i=0; i<aryRow; i++) temp[i] = new int[aryCol];
			
			for(int i=0; i<aryRow; i++){
				for(int j=0; j<aryCol; j++){
					temp[i][j] = 0;
				}
			}
			//erosion
			bool check = false;	
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(imgAry[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(imgAry[m][n] != structElem[m-r][n-c] && structElem[m-r][n-c]!=0){
									temp[i][j] = 0;
									check = true;
									break;								
								}							
							}
							if(check) break;
						}
						if(!check) temp[i][j] = 1;					
						check = false;
					}
				}
			}
			//delation
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					if(temp[i][j] == 1){
						int r = i-rowOrigin;
						int c = j-colOrigin;
						for(int m=i-rowOrigin; m<i-rowOrigin+numRowsStrctElem; m++){
							for(int n=j-colOrigin; n<j-colOrigin+numColsStrctElem; n++){
								if(structElem[m-r][n-c]!=0) morphAry[m][n] = structElem[m-r][n-c];								
							}							
						}						
					}
				}
			}
			
			cout<<"opening result:"<<endl;
			prettyPrint(morphAry, aryRow, aryCol);
			cout<<endl;
			
			out4<<"opening result:"<<endl;
			outPutResult(out4);
			
			for(int i=0; i<aryRow; i++) delete[] temp[i];
			delete[] temp;	
			
			in1.close();
			in2.close();
			out1.close();
			out2.close();
			out3.close();
			out4.close();
		}
		
		void outPutResult(ofstream& o){			
			o<<numRowsIMG<<" "<<numColsIMG<<" "<<minValIMG<<" "<<maxValIMG<<endl;
			for(int i=rowFrameSize/2; i<aryRow-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<aryCol-colFrameSize/2; j++){
					o<<morphAry[i][j]<<" ";
				}
				o<<endl;
			}
		}
		
		void prettyPrint(int** array, int row, int col){
			for(int i=rowFrameSize/2; i<row-rowFrameSize/2; i++){
				for(int j=colFrameSize/2; j<col-colFrameSize/2; j++){
					if(array[i][j]>0) cout<<array[i][j];
					else cout<<" ";
				}
				cout<<endl;
			}	
		}
};

int main(int argc, char* argv[]){
	if(argc < 7){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments	
	
	string f1, f2, f3, f4, f5, f6;
	f1 = argv[1];
	f2 = argv[2];
	f3 = argv[3];
	f4 = argv[4];
	f5 = argv[5];
	f6 = argv[6];
	
	Morphology m(f1, f2, f3, f4, f5, f6);
	m.delation();
	m.erosion();
	m.closing();
	m.opening();
	return 0;
}

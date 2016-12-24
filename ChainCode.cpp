#include <iostream>
#include <fstream>
using namespace std;

class Image{
	friend class ChainCode;
	
	private:
		int numRow;
		int numCol;
		int minVal;
		int maxVal;
		int** zeroFramedAry;
		ifstream in;
		string inputFile;
	public:
		Image(string file){
			inputFile = file;
			in.open(inputFile.c_str());	
			int value;	
			if(in>>value) numRow = value;
		    if(in>>value) numCol = value;
			if(in>>value) minVal = value;
			if(in>>value) maxVal = value;
			
			zeroFramedAry = new int*[numRow+2];
			for(int i=0; i<numRow+2; i++) zeroFramedAry[i] = new int[numCol+2];
		} 
		
		~Image(){
			for(int i=0 ; i<numRow+2; i++) delete[] zeroFramedAry[i];
			delete[] zeroFramedAry;
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
			in.close();
		}
};

class CCproperty{
	friend class ChainCode;
	
	struct Node{
	    int label;
    	int numbpixels;
    	int minRow;
    	int minCol;
    	int maxRow;
		int maxCol;
    };//struct of Node
	
	private:
		int maxCC;
		Node* property;
		ifstream in;
		string inputFile;
		
	public:
	     CCproperty(string file){
	     	inputFile = file;
			in.open(inputFile.c_str());	
			
			string find;
			int value;	
			while(in>>find){
				if(find == "label:") {
					if(in>>value) maxCC = value;
				}
			}	
			in.clear();
            in.seekg(0, in.beg);	
				
			property = new Node[maxCC+1];	
			int count = 1;
			while(count<=maxCC){
				if(in>>find) if(in>>value) property[count].label = value;
				if(in>>find) if(in>>value) property[count].numbpixels = value;
				if(in>>find) if(in>>value) property[count].minRow = value;
				if(in>>find) if(in>>value) property[count].minCol = value;
				if(in>>find) if(in>>value) property[count].maxRow = value;
				if(in>>find) if(in>>value) property[count].maxCol = value;
				count++;
			}
			in.close();
		 }
		 
		 ~CCproperty(){
		 	delete[] property;
		 }
};

class ChainCode{
	struct Point{
		int row;
		int col;
	};
	
	private:
		int currentCC;
		int minRowOffset;
		int maxRowOffset;
		int minColOffset;
		int maxColOffset;
		int nextDirTable[8];		
		Point neighborCoord[8];
		Point currentP;
		Point nextP;
		Point startP;
		int nextQ;
		int Pchain;
		int lastQ;
		ofstream out[2];
		string output1, output2;
		Image i;
		CCproperty cc;
	
	public:
		ChainCode(string file1, string file2, string file3, string file4): i(file1), cc(file2)
		{
			output1 = file3;
			output2 = file4;
			out[0].open(output1.c_str());
			out[1].open(output2.c_str());
			
			setNextDirTable();
			i.loadImage();
			currentCC = 0;
		}
		
		void start(){
			currentCC++;
			out[1]<<"For Debugging: "<<endl;
			while(currentCC<=cc.maxCC){
				bool check = false;
				int index;				
				minRowOffset = cc.property[currentCC].minRow;
				maxRowOffset = cc.property[currentCC].maxRow;
				minColOffset = cc.property[currentCC].minCol;
				maxColOffset = cc.property[currentCC].maxCol;
				for(int m = minRowOffset+1; m<=maxRowOffset+1; m++){
					for(int n = minColOffset+1; n<=maxColOffset+1; n++){						
						if(i.zeroFramedAry[m][n] == currentCC) {							
							out[0]<<"startRow: "<<m-1<<" startCol: "<<n-1<<" CC_Label: "<<currentCC<<" ChainCode: ";
							out[1]<<"startRow: "<<m-1<<" startCol: "<<n-1<<" CC_Label: "<<currentCC<<endl;
							startP.row = m;
							startP.col = n;
							currentP.row = m;
							currentP.col = n;
							lastQ = 4;
							while(true){								
								nextQ = (lastQ+1)%8;
								out[1]<<"currentP: "<<currentP.row<<" "<<currentP.col<<" nextQ: "<<nextQ<<" lastQ: "<<lastQ;								
								Pchain = findNextP(currentP, nextQ, currentCC, nextP);
								nextP.row = neighborCoord[Pchain].row;
								nextP.col = neighborCoord[Pchain].col;
								out[0]<<Pchain<<" ";
								out[1]<<" ChainCode: "<<Pchain<<endl;
								if(Pchain == 0) index = 7;
								else index = Pchain-1;
								lastQ = nextDirTable[index];
								currentP.row = nextP.row;
								currentP.col = nextP.col;
								if(currentP.row == startP.row&&currentP.col == startP.col) {
									out[1]<<"currentP: "<<currentP.row<<" "<<currentP.col<<" nextQ: "<<nextQ<<" lastQ: "<<lastQ<<endl;;								
									check = true;
									out[0]<<endl;
									break;
								}
							}														
						}
						if(check) break;
					}
					if(check) {
						check = false;
						break;
					}
					
				}
				currentCC++;
				out[1]<<endl;
			}
			out[0].close();
			out[1].close();
		}
		
		int findNextP(Point cP, int nQ, int cCC, Point nP){
			int chainDir;
			int direction = nQ;
			loadNeighborCoord(cP);
			while(true){
				if(i.zeroFramedAry[neighborCoord[direction].row][neighborCoord[direction].col] == currentCC) {
					chainDir = direction;
					break;
				}
				direction = (direction+1)%8;
			}			
			return chainDir;
		}
		
		void loadNeighborCoord(Point P){
			neighborCoord[0].row = P.row;
			neighborCoord[0].col = P.col+1;
			neighborCoord[1].row = P.row-1;
			neighborCoord[1].col = P.col+1;
			neighborCoord[2].row = P.row-1;
			neighborCoord[2].col = P.col;
			neighborCoord[3].row = P.row-1;
			neighborCoord[3].col = P.col-1;
			neighborCoord[4].row = P.row;
			neighborCoord[4].col = P.col-1;
			neighborCoord[5].row = P.row+1;
			neighborCoord[5].col = P.col-1;
			neighborCoord[6].row = P.row+1;
			neighborCoord[6].col = P.col;
			neighborCoord[7].row = P.row+1;
			neighborCoord[7].col = P.col+1;
		}
		
		void setNextDirTable(){
			nextDirTable[0] = 6;
			nextDirTable[1] = 0;
			nextDirTable[2] = 0;
			nextDirTable[3] = 2;
			nextDirTable[4] = 2;
			nextDirTable[5] = 4;
			nextDirTable[6] = 4;
			nextDirTable[7] = 6;
		}
};

int main(int argc, char* argv[]){
	if(argc < 5){
		cout << "Invalid number of arguments.\n";
		return -1;
	}//check the command-line arguments
	
	cout<<"Please use following format as property file format:"<<endl;
	cout<<"label: XX numbpixels: XX minRow: XX minCol: XX"<<endl;
	cout<<"For Example:"<<endl;
	cout<<"label: 1 numbpixels: 33 minRow: 1 minCol: 11 maxRow: 9 maxCol: 19"<<endl;
	cout<<"label: 2 numbpixels: 63 minRow: 5 minCol: 7 maxRow: 19 maxCol: 21"<<endl; 
	cout<<"label: 3 numbpixels: 31 minRow: 8 minCol: 15 maxRow: 17 maxCol: 22"<<endl;
	cout<<"label: 4 numbpixels: 42 minRow: 21 minCol: 9 maxRow: 26 maxCol: 21"<<endl;
	cout<<"label: 5 numbpixels: 33 minRow: 30 minCol: 12 maxRow: 36 maxCol: 18"<<endl;
	
	string f1, f2, f3, f4;
	f1 = argv[1];
	f2 = argv[2];
	f3 = argv[3];
	f4 = argv[4];
	ChainCode ccode(f1, f2, f3, f4);
	ccode.start();
	return 0;
}

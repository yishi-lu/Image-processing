
public class HoughTransform {
	private XYCoord point;
	private int angleInDegree;
	private double angleInRadians;
	private int numRow;
	private int numCol;
	private int minVal;
	private int maxVal;
	private int[][] HoughAry;
	private double pi;
	private ImageProcessing i;
	
	public HoughTransform(String file1, String file2, String file3){
		i = new ImageProcessing(file1, file2, file3);
		i.loadImage();
		numRow = 180;
		numCol = (int) Math.sqrt(i.numRow*i.numRow + i.numCol*i.numCol);
		pi = Math.PI;
		
		HoughAry = new int[numRow][numCol];
		for(int i=0; i<numRow; i++){
			for(int j=0; j<numCol; j++){
				HoughAry[i][j] = 0;
			}
		}	
	}
	
	public void start(){
		int distance;
		for(int k=0; k<i.numRow; k++){
			for(int j=0; j<i.numCol; j++){
				if(i.imageAry[k][j] > 0){					
					point = new XYCoord(k, j);
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
	
	public int computeDistance(XYCoord p, double ar){
		int result, x, y;
		double t, d;
		x = i.numRow - p.x - 1;
		y = p.y;//Here, the origin I choose to set at left-bottom corner 
		t = ar - Math.atan2(x, y) - pi/2;
		d = Math.abs(Math.sqrt(x*x + y*y) * Math.cos(t));
		result = (int) d;			
		return result;
	}
	
	public void determineHeader(){
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
		
		i.out[0].println(numRow+" "+numCol+" "+minVal+" "+maxVal);
		for(int m=0; m<numRow; m++){
			for(int n=0; n<numCol; n++){
				i.out[0].print(HoughAry[m][n]+" ");
			}
			i.out[0].println();
		}
		
		i.prettyPrint(HoughAry, numRow, numCol);
	}		

}

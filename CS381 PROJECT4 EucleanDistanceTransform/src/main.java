
public class main {
	public static void main(String[] args){
		if(args.length < 3)
	    {
	        System.out.println("Invalid number of arguments.");
	        System.exit(1);
	    }//check command line
		
		String f1, f2, f3;
		f1 = args[0];
		f2 = args[1];
		f3 = args[2];
		
		EucleanDistanceTransform e = new EucleanDistanceTransform(f1, f2, f3);
		e.zeroFrameArray();
		e.loadImage();
		e.firstPass();
		e.secondPass();
		e.createImage();
	}
}

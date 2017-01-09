
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
		
		HoughTransform h = new HoughTransform(f1, f2, f3);
		h.start();
		h.determineHeader();
	}
}

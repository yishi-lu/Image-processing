public class main {
	public static void main(String[] args){
		if(args.length < 1)
	    {
	        System.out.println("Invalid number of arguments.");
	        System.exit(1);
	    }//check command line
		
		String f1;
		f1 = args[0];
		
		Threshold h = new Threshold(f1);
		h.computeThreshold();
	}

}
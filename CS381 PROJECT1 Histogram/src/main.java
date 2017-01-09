
public class main {
	public static void main(String[] args){
		if(args.length < 2)
	    {
	        System.out.println("Invalid number of arguments.");
	        System.exit(1);
	    }//check command line
		
		String f1, f2;
		f1 = args[0];
		f2 = args[1];
		
		Histogram h = new Histogram(f1, f2);
		h.computeHistogram();
	}

}

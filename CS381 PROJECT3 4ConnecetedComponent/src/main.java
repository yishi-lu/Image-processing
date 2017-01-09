public class main {
	public static void main(String[] args){
		if(args.length < 4)
	    {
	        System.out.println("Invalid number of arguments.");
	        System.exit(1);
	    }//check command line
		
		String f1, f2, f3, f4;
		f1 = args[0];
		f2 = args[1];
		f3 = args[2];
		f4 = args[3];
		
		ConnecetedCC cc = new ConnecetedCC(f1, f2, f3, f4);
		cc.loadImage();
		cc.Pass1();
		cc.Pass2();
		cc.manageEQAry();
		cc.Pass3();
		cc.outputToFile2();
		cc.printProperty();
	}

}
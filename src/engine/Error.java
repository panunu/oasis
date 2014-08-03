package engine;

public class Error {
	private static int errors = 0;
	
	public static void fail(Exception error, String caller) {
		errors++;
		System.out.println(errors + "\tERROR @ " + caller + "\n\t" + error.toString() + "\n");
	}
}

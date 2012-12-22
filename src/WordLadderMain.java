/**
 * WordLadderMain class
 * This class is the main class of the application. It is used to run the Word Ladder Application
 * 
 * @version 2.0
 * @author Sebastian Szczepaniak
 *
 */
public class WordLadderMain {
	
	private static Generation generation;
	private static MainFrame frame;
	
	/**
	 * This is the main method of the class which is used to run the application.
	 * @param args
	 */
	public static void main(String[] args) {
		generation = new Generation();
		frame = new MainFrame(generation);
	}	
}

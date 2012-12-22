import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
/**
 * Generation class
 * This class contains the model of the Word Ladder application. The algorithms and methods needed
 * to run the application properly can be found here.
 * 
 * @version 2.0
 * @author Sebastian Szczepaniak
 *
 */
public class Generation {
	private ArrayList<String> words;
	
	/**
	 * Constructor of the class Generation
	 */
	public Generation(){
		words = new ArrayList<String>();
	}
	
	/**
	 * Method which uses an algorithm to find the path between 2 words - initialWord and finalWord.
	 * @param initialWord The word which starts the path.
	 * @param finalWord The word to which the path should be found.
	 * @return The path as a String to be printed later for user.
	 */
	public String runFindPath(String initialWord, String finalWord){
		words = load("dict.dat", initialWord);
		if((words.contains(initialWord))&&(words.contains(finalWord))){
			SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
			initialise(initialWord, words.size(), graph);
			try{
				DijkstraShortestPath<String, DefaultEdge> algorithm = new DijkstraShortestPath<String, DefaultEdge>(graph, initialWord, finalWord);
				List<String> list = Graphs.getPathVertexList(algorithm.getPath());
				String output="";
				int i=0;
				for(String s: list){
					if(i==15){
						output=output+"\n";
						i=0;
					}
					output=output+s+", ";
					i++;
				}
				output = output.substring(0,output.length()-2);
				return output;
			}catch(Exception e){
				System.err.println("Can't find a path between the words!");
				JOptionPane.showMessageDialog(null,"Can't find a path between the words!", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}else{
			if(!(words.contains(initialWord))){
				System.err.println("There is no such word as '"+initialWord+"' in the dictionary!");
				JOptionPane.showMessageDialog(null,"There is no such word as '"+initialWord+"' in the dictionary!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if(!(words.contains(finalWord))){
				System.err.println("There is no such word as '"+finalWord+"' in the dictionary!");
				JOptionPane.showMessageDialog(null,"There is no such word as '"+finalWord+"' in the dictionary!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}
	}
		
	/**
	 * Method which uses an algorithm to generate the specified amount of words starting with initialWord.
	 * @param initialWord The word which starts the path.
	 * @param number The amount of words that will be generated
	 * @return The path as a String to be printed later for user.
	 */
	public String runGeneration(String initialWord, int number){	
		words = load("dict.dat", initialWord);
		if(words.contains(initialWord)&&(number>0)&&(number<words.size()-1)){
			SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
			initialise(initialWord, number+1, graph);
			try{
				DepthFirstIterator<String, DefaultEdge> alg = new DepthFirstIterator<String, DefaultEdge>(graph, initialWord);
				String output="";
				int j=0;
				for(int i=0; i<=number; i++){
					if(j==15){
						output=output+"\n";
						j=0;
					}
					output=output+alg.next()+", ";
					j++;
				}
				output = output.substring(0,output.length()-2);
				return output;
			}catch(NoSuchElementException e){
				System.err.println("Can't generate so many words!");
				JOptionPane.showMessageDialog(null,"Can't generate so many words!", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}else{
			if(!(words.contains(initialWord))){
				System.err.println("There is no such word as '"+initialWord+"' in the dictionary!");
				JOptionPane.showMessageDialog(null,"There is no such word as '"+initialWord+"' in the dictionary!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if(number<=0){
				System.err.println("You have to create at least one step!");
				JOptionPane.showMessageDialog(null,"You have to create at least one step!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if(number>=words.size()){
				System.err.println("There is not enough words in dictionary!");
				JOptionPane.showMessageDialog(null,"There is not enough words in dictionary!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}
	}
	
	/**
	 * The method which contains the similar part of both generation and finding path and is used in both methods.
	 * @param initialWord The word which starts the path.
	 * @param size The amount of the words from dictionary that are of the same length as the initialWord.
	 * @param graph SimpleGraph to which all the Vertexes and Edges will be added.
	 */
	private void initialise(String initialWord, int size, SimpleGraph<String, DefaultEdge> graph){
		Hashtable<String, ArrayList<String>> progress = new Hashtable<String, ArrayList<String>>(size);					
		for(String s: words){
			if(!(progress.containsKey(s)))
				progress.put(s, matchingWords(s));
		}
		Enumeration<String> key = progress.keys();
		String keyword;
		ArrayList<String> values = new ArrayList<String>();
		while(key.hasMoreElements()){
			keyword=(String)key.nextElement();
			values = progress.get(keyword);
			if(!(graph.containsVertex(keyword))){
				graph.addVertex(keyword);
				for(String s: values){
					graph.addVertex(s);
					graph.addEdge(keyword, s);
				}
			}else{
				for(String s: values){
					if(!(graph.containsEdge(keyword,s))){
						graph.addVertex(s);
						graph.addEdge(keyword, s);
					}
				}
			}
		}
	}
	
	/**
	 * The method which loads words of the same length as the initialWord from the dictionary file.
	 * @param fileName The name of the file from which the words will be loaded.
	 * @param initialWord The word which starts the path.
	 * @return ArrayList<String> which contains the set of words of the same length as the initialWord.
	 */
	private ArrayList<String> load(String fileName, String initialWord)
	{
		ArrayList<String> words = new ArrayList<String>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String word = reader.readLine();
			while(word != null){
			if(word.length()==initialWord.length())
				words.add(word);
			word = reader.readLine();
			}
			reader.close();
		}catch(IOException e){
			System.err.println("Can't find a file '"+fileName+"'!");
			JOptionPane.showMessageDialog(null,"Can't find a file '"+fileName+"'!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return words;
	}
	
	/**
	 * The method which checks if 2 words differ with only one letter.
	 * @param word1
	 * @param word2
	 * @return false if the words differ with more than 1 letter, true when they differ with only one letter.
	 */
	private boolean oneCharOff( String word1, String word2 )
    {
        if( word1.length( ) != word2.length( ) )
            return false;
        int diffs = 0;
        for( int i = 0; i < word1.length( ); i++ )
            if( word1.charAt( i ) != word2.charAt( i ) )
                if( ++diffs > 1 )
                    return false;
        return diffs == 1;
    }
	
	/**
	 * The method which finds the list of words which differ with only 1 letter with the specified word.
	 * @param word
	 * @return ArrayList<String> containing the words which differ with only 1 letter with the specified word.
	 */
	private ArrayList<String> matchingWords(String word){
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<words.size();i++){
			if(oneCharOff(word,words.get(i))){
				list.add(words.get(i));
			}
		}
		return list;
	}
}
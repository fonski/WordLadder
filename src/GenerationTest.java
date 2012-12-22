import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GenerationTest {

	private Generation generation;
	
	@Before
	public void setup(){
		generation = new Generation();
	}
	
	@Test
	public void testRunGeneration() {
		assertEquals("The output isn't the same as expected.","brick, Frick, prick, price, pride, bride", generation.runGeneration("brick", 5));
	}
	
	@Test
	public void testRunGeneration1() {
		assertEquals("The word 'asd' is not in the dictionary.",null, generation.runGeneration("asd", 5));
	}
	
	@Test
	public void testRunGeneration2() {
		assertEquals("There is not enough words in dictionary!",null, generation.runGeneration("he", 10000));
	}
	
	@Test
	public void testRunGeneration3() {
		assertEquals("Can't generate so many words!",null, generation.runGeneration("oxygen", 400));
	}
	
	@Test
	public void testRunGeneration4() {
		assertEquals("You have to create at least one step!",null, generation.runGeneration("oxygen", -1));
	}
	
	@Test
	public void testRunFindPath1() {
		assertEquals("The output isn't the same as expected.","clash, flash, flask, flack, flock, clock", generation.runFindPath("clash", "clock"));
	}
	
	@Test
	public void testRunFindPath2() {
		assertEquals("There is no such word as 'blabl' in the dictionary!",null, generation.runFindPath("blabl", "brick"));
	}
	
	@Test
	public void testRunFindPath3() {
		assertEquals("There is no such word as 'color' in the dictionary!",null, generation.runFindPath("brick", "color"));
	}
	
	@Test
	public void testRunFindPath4() {
		assertEquals("Can't find a path between the words!",null, generation.runFindPath("collude", "wakeful"));
	}
	
	
	

}

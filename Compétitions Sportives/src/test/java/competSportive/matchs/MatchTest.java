package competSportive.matchs;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import competSportive.competitors.Competitor;

public class MatchTest {

	private static Competitor competitor1;
	private static Competitor competitor2;
	private static Match match;
	
	@BeforeClass
	public static void setup() {
		competitor1 = new Competitor("Fnatic");
		competitor2 = new Competitor("SKT T1");
		
		match = new Match(competitor1, competitor2);
	}
	
	@Test
	public void chooseWinnerTest() {
		Competitor randomWinner = match.chooseWinner();
		
		assertTrue(randomWinner.equals(competitor1) || randomWinner.equals(competitor2));
	}

}

package competSportive.competitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import competSportive.competitors.Competitor;

public class CompetitionTest {

	// Competitors of a competition
	private static Competitor  competitor1;
	private static Competitor  competitor2;
	private static Competitor  competitor3;
	private static Competitor  competitor4;
	private static Competition competition;
	
	// Competitors that are not in the competition
	private static Competitor  fakeCompetitor1;
	private static Competitor  fakeCompetitor2;
	
	@BeforeClass
	public static void setup() {
		// Create a competitors list
		competitor1 = new Competitor("Fnatic");
		competitor2 = new Competitor("SKT T1");
		competitor3 = new Competitor("Taipei Assassins");
		competitor4 = new Competitor("Unicorns of love");
		List<Competitor> competitors = new ArrayList<Competitor>();
		competitors.add(competitor1);
		competitors.add(competitor2);
		competitors.add(competitor3);
		competitors.add(competitor4);
		
		// Setup the competition as a league, no need for a mock here
		competition = new League(competitors);
		
		fakeCompetitor1 = new Competitor("fake 1");
		fakeCompetitor2 = new Competitor("fake 2");
		
	}
	
	// ############################################# playMatch method tests #############################################
	
	// Normal test case
	@Test
	public void playMatchTest() throws CompetitionException {
		Competitor winner = competition.playMatch(competitor1, competitor2);
		
		// Assert the winner is one of the competitors
		assertTrue(winner.equals(competitor1) || winner.equals(competitor2));
		
		// Assert the winner got 1 point
		int winnerPoints = competition.getCompetitorsPoints().get(winner);
		assertEquals(1, winnerPoints);
		
		// Assert the other still have 0 points
		if (winner.equals(competitor1)) {
			int loserPoints = competition.getCompetitorsPoints().get(competitor2);
			assertEquals(0, loserPoints);
		} else {
			int loserPoints = competition.getCompetitorsPoints().get(competitor1);
			assertEquals(0, loserPoints);
		}
	}
	
	// Assert there is an exception if the first competitor is not in the competition
	@Test(expected = CompetitionException.class)
	public void playMatchTestWithFirstCompetitorBeingInvalid() throws CompetitionException {
		competition.playMatch(fakeCompetitor1, competitor1);
	}
	
	// Assert there is an exception if the second competitor is not in the competition
	@Test(expected = CompetitionException.class)
	public void playMatchTestWithSecondCompetitorBeingInvalid() throws CompetitionException {
		competition.playMatch(competitor1, fakeCompetitor1);
	}
	
	// Assert there is an exception if both of the competitors are not in the competition
	@Test(expected = CompetitionException.class)
	public void playMatchTestWithBothCompetitorsBeingInvalid() throws CompetitionException {
		competition.playMatch(fakeCompetitor1, fakeCompetitor2);
	}
	
	// ############################################# ranking method tests #############################################
	
	@Test
	public void rankingTest() {
		// Create a disordered map of competitors and points
		Map<Competitor, Integer> disorderdedRanking = new LinkedHashMap<Competitor, Integer>();
		disorderdedRanking.put(competitor1, 2);
		disorderdedRanking.put(competitor2, 1);
		disorderdedRanking.put(competitor3, 4);
		disorderdedRanking.put(competitor4, 3);
		
		// Replace the competition map of competitors point by the disordered map
		competition.setCompetitorsPoints(disorderdedRanking);
		
		// Use the ranking() method and assert we got the disorderdedRanking map correctly sorted
		Map<Competitor, Integer> ranking = competition.ranking();
		Object [] sortedCompetitors = ranking.keySet().toArray();
		Object [] sortedPoints      = ranking.values().toArray();
		
		assertEquals(competitor3, sortedCompetitors[0]);
		assertEquals(competitor4, sortedCompetitors[1]);
		assertEquals(competitor1, sortedCompetitors[2]);
		assertEquals(competitor2, sortedCompetitors[3]);
		
		assertEquals(4, sortedPoints[0]);
		assertEquals(3, sortedPoints[1]);
		assertEquals(2, sortedPoints[2]);
		assertEquals(1, sortedPoints[3]);
	}

}

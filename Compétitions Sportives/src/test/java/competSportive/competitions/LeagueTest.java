package competSportive.competitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import competSportive.competitors.Competitor;
import competSportive.matchs.Match;

public class LeagueTest {

	// Competitors of the league
	private static Competitor  competitor1;
	private static Competitor  competitor2;
	private static Competitor  competitor3;
	private static List<Competitor>  competitors;
	
	// League
	private static MockLeague  league;
	
	// Mock used for the tests
	private static class MockLeague extends League {

		public MockLeague(List<Competitor> competitors) {
			super(competitors);
		}
		
		// Used by the below playMatch overrided method
		public List<Match> playedMatch = new ArrayList<Match>();

		// Save the parameters it was given every time this method is called
		@Override
		public Competitor playMatch(Competitor c1, Competitor c2) throws CompetitionException {
			playedMatch.add(new Match(c1, c2));
			
			return c1;
		}
	};
	
	@BeforeClass
	public static void setup() {
		// Create a competitors list
		competitor1 = new Competitor("Fnatic");
		competitor2 = new Competitor("SKT T1");
		competitor3 = new Competitor("Taipei Assassins");
		competitors = new ArrayList<Competitor>();
		competitors.add(competitor1);
		competitors.add(competitor2);
		competitors.add(competitor3);
		
		// Setup the competition with the mock and the competitors
		league = new MockLeague(competitors);
	}
	
	@Before
	public void resetPlayedMatchList() {
		league.playedMatch.clear();
	}
	
	// ############################################# play method tests #############################################
	
	// Normal case. Test if every competitors played 2 times against the others 
	@Test
	public void playTest() throws CompetitionException {
		// Play the matchs
		league.play(competitors);
		
		// Assert every match that should have been played
		assertTrue(league.playedMatch.contains(new Match(competitor1, competitor2)));
		assertTrue(league.playedMatch.contains(new Match(competitor1, competitor3)));
		assertTrue(league.playedMatch.contains(new Match(competitor2, competitor1)));
		assertTrue(league.playedMatch.contains(new Match(competitor2, competitor3)));
		assertTrue(league.playedMatch.contains(new Match(competitor3, competitor1)));
		assertTrue(league.playedMatch.contains(new Match(competitor3, competitor2)));
		
		// Assert there is exactly 6 matchs that were played
		assertEquals(6, league.playedMatch.size());
	}
	
	// Test if there is no competitor or one competitor
	@Test
	public void playTestWith0Or1Competitor() throws CompetitionException {
		List<Competitor> emptyCompetitorsList = new ArrayList<Competitor>();
		List<Competitor> oneCompetitorsList   = new ArrayList<Competitor>();
		oneCompetitorsList.add(competitor1);
		
		// Play the matchs
		league.play(emptyCompetitorsList);
		league.play(oneCompetitorsList);
		
		// Assert zero match were played
		assertEquals(0, league.playedMatch.size());
	}
	
}

package competSportive.competitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import competSportive.competitors.Competitor;
import competSportive.matchs.Match;

public class TournamentTest {

	// Competitors of the league
	private static Competitor  competitor1;
	private static Competitor  competitor2;
	private static Competitor  competitor3;
	private static Competitor  competitor4;
	private static Competitor  competitor5;
	private static Competitor  competitor6;
	private static Competitor  competitor7;
	private static Competitor  competitor8;
	private static List<Competitor>  competitors;
	
	// Tournament
	private static MockTournament tournament;
	
	// Mock used for the tests
	private static class MockTournament extends Tournament {

		public MockTournament(List<Competitor> competitors) throws CompetitionException {
			super(competitors);
		}
		
		// Used by the below playMatch overrided method
		public List<Match> playedMatch = new ArrayList<Match>();

		// Save the parameters it was given every time this method is called,
		// always return the first competitor (meaning he wins)
		@Override
		public Competitor playMatch(Competitor c1, Competitor c2) throws CompetitionException {
			playedMatch.add(new Match(c1, c2));
			
			return c1;
		}
	};
	
	@BeforeClass
	public static void setup() throws CompetitionException {
		// Create a competitors list
		competitor1 = new Competitor("n1");
		competitor2 = new Competitor("n2");
		competitor3 = new Competitor("n3");
		competitor4 = new Competitor("n4");
		competitor5 = new Competitor("n5");
		competitor6 = new Competitor("n6");
		competitor7 = new Competitor("n7");
		competitor8 = new Competitor("n8");
		competitors = Arrays.asList(competitor1, competitor2, competitor3,
				competitor4, competitor5, competitor6, competitor7, competitor8);
		
		// Setup the competition with the mock and the competitors
		tournament = new MockTournament(competitors);
	}
	
	// ############################################# Tournament constructor tests #############################################

	// Assert the constructor works (by not throwing an error)
	// with a list of competitor which size is x power of 2
	@Test
	public void tournamentConstructorTest() throws CompetitionException {
		// Test with a list of 2 competitors
		List<Competitor> competitors2 = Arrays.asList(competitor1, competitor2);
		new Tournament(competitors2);
		
		// Test with a list of 4 competitors
		List<Competitor> competitors4 = Arrays.asList(competitor1, competitor2,
				competitor3, competitor4);
		new Tournament(competitors4);
		
		// Test with a list of 8 competitors
		new Tournament(competitors);
	}
	
	// Assert the constructor throw an error if the list of competitor
	// size is NOT x power of 2
	@Test(expected = CompetitionException.class)
	public void invalidTournamentConstructorTest() throws CompetitionException {
		// Test with a list of 5 competitors
		List<Competitor> competitors5 = Arrays.asList(competitor1, competitor2, 
				competitor3, competitor4, competitor5);
		new Tournament(competitors5);
	}
	
	// ############################################# play method tests #############################################
	
	// Normal case. Test a 8 competitors tournament  
	@Test
	public void playTest() throws CompetitionException {
		// Play the matchs
		tournament.play(competitors);
		
		// Assert every match that should have been played, considering the
		// first competitor of a match always win -> see playMatch of MockTournament
		// First turn / 4 matchs
		assertTrue(tournament.playedMatch.contains(new Match(competitor1, competitor2)));
		assertTrue(tournament.playedMatch.contains(new Match(competitor3, competitor4)));
		assertTrue(tournament.playedMatch.contains(new Match(competitor5, competitor6)));
		assertTrue(tournament.playedMatch.contains(new Match(competitor7, competitor8)));
		// Second turn / 2 matchs
		assertTrue(tournament.playedMatch.contains(new Match(competitor1, competitor3)));
		assertTrue(tournament.playedMatch.contains(new Match(competitor5, competitor7)));
		// Third turn (final) / 1 match
		assertTrue(tournament.playedMatch.contains(new Match(competitor1, competitor5)));
		
		// Assert there is exactly 7 matchs that were played
		assertEquals(7, tournament.playedMatch.size());
	}
}

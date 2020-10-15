package competSportive.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import competSportive.competitions.Competition;
import competSportive.competitions.CompetitionException;
import competSportive.competitions.League;
import competSportive.competitions.Master;
import competSportive.competitions.Tournament;
import competSportive.competitors.Competitor;

/** Main class which run the program */
public class Main {

	public static void main(String[] args) {
		
		List<Competitor> competitors = new ArrayList<Competitor>();
		competitors.add(new Competitor("Real-Madrid"));
		competitors.add(new Competitor("Barcelone"));
		competitors.add(new Competitor("Lille"));
		competitors.add(new Competitor("Lens"));
		competitors.add(new Competitor("Arsenal"));
		competitors.add(new Competitor("Manchester"));
		competitors.add(new Competitor("Juventus"));
		competitors.add(new Competitor("Mancity"));

		
		try {
			Master master = new Master(competitors, 2);
			
			// League testing
			Competition league = new League(competitors);
			System.out.println("########### League ###########");
			league.play();
			Map<Competitor, Integer> leagueRanking = league.ranking();
			printRanking(leagueRanking);
		
			// Tournament testing
			Competition tournament = new Tournament(competitors);
			System.out.println("\n########### Tournoi ###########");
			tournament.play();
			tournament.ranking();
			Map<Competitor, Integer> tournamentRanking = tournament.ranking();
			printRanking(tournamentRanking);
			
		} catch (CompetitionException e) {
			e.printStackTrace();
		}
	}
	
	/** Print out the ranked list of competitors with their points during a competition
	* @param rankingMap : map of competitors, contains their points.
	*/
	private static void printRanking(Map<Competitor, Integer> rankingMap) {
		System.out.println("\n*** Ranking ***");
		for (Map.Entry<Competitor, Integer> entry : rankingMap.entrySet()) {
			System.out.println(entry.getKey().getName() + " - " + entry.getValue());
		}
	}
	
}

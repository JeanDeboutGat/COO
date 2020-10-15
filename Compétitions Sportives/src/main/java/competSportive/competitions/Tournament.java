package competSportive.competitions;

import java.util.ArrayList;
import java.util.List;

import competSportive.competitors.Competitor;

/**Tournament type sporting competition. Matches are direct elimination until the final.
Each turn, the competitor number is divided by 2.*/
public class Tournament extends Competition {

	/**
	 * Create a tournament and check if there is a valid number of competitors.
	 * @param competitors which play the tournament
	 * @throws CompetitionException if the number of competitors is not valid.
	 */
	public Tournament(List<Competitor> competitors) throws CompetitionException {
		super(competitors);
		if(!isCompetitorSizeValid()) {
			throw new CompetitionException("Le nombre de compétiteurs est invalide dans le cas d'un tournoi : " + competitors.size());
		}
	}

	/**
	 * Play the tournament : direct elimination until the last match.
	 * @throws CompetitionException if an exception occurred
	 */
	@Override
	public void play(List<Competitor> competitors) throws CompetitionException {
		// Copy the competitors list to avoid side effects on the original list since we manipulate it.
		List<Competitor> winners = new ArrayList<Competitor>(competitors);

		// Each iteration is a turn of the tournament where every matchs are played.
		// Then take the winners and go to the next iteration to play the next round.
		// Continue until we have the final winner (only  one competitor left in the list).
		while(true) {
			List<Competitor []> scheduledMatchs = scheduleMatchs(winners);
			winners.clear();

			for(Competitor [] matchCompetitors : scheduledMatchs) {
				// Define the competitors of the match
				Competitor c1 = matchCompetitors[0];
				Competitor c2 = matchCompetitors[1];

				// Play the match and choose the winner
				Competitor winner = playMatch(c1, c2);

				// Add the winner to a list of winner to use for the next turn of the tournament
				winners.add(winner);

				// Print the winner
				System.out.println(c1.getName() + " vs " + c2.getName() + " --> "
						+ winner.getName() + " wins !");
			}

			if(winners.size() <= 1) {
				return;
			}

		}
	}

	/** Create a list composed of multiples peers of competitors which have to play against each other.
	 *@param competitors : list of competitors
	 *@return the list of peers of competitors
	 */
	private List<Competitor []> scheduleMatchs(List<Competitor> competitors) {
		List<Competitor []> result = new ArrayList<Competitor []>();

		for(int index = 0; index < competitors.size(); index = index + 2 ) {
			Competitor [] paire = { competitors.get(index), competitors.get(index + 1) };
			result.add(paire);
		}

		return result;
	}

	/** Check if the numbers of competitors is a power of 2 by using the binary representation of
	 *  this number. "10" = 2, "100" = 4, "1000" = 8, "10000" = 16 etc...
	 *  @return true if the competitor number is a power of 2 AND >= 2. Else false.
	 */
	private boolean isCompetitorSizeValid() {
		int competitorsNumber = competitors.size();

		if(competitorsNumber <= 1) {
			return false;
		}

		String binaryCompetitorsNumber = Integer.toBinaryString(competitorsNumber);
		for(int i = 0; i < binaryCompetitorsNumber.length(); i++) {
			if (i == 0 && binaryCompetitorsNumber.charAt(i) != '1') {
				return false;
			} else if (i > 0 && binaryCompetitorsNumber.charAt(i) != '0') {
				return false;
			}
		}
		return true;
	}

}

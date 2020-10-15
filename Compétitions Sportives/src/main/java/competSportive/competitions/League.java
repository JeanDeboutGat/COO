package competSportive.competitions;

import java.util.List;

import competSportive.competitors.Competitor;

/** League type sporting competition. Each participant in the competition meet 2 times.*/
public class League extends Competition{

	public League(List<Competitor> competitors) {
		super(competitors);
	}

	/**
	 * Play the league : each competitors meet each other 2 times.
	 * @throws CompetitionException if one or more of the competitors of the given list is/are not part of the competition
	 */
	@Override
	public void play(List<Competitor> competitors) throws CompetitionException {
		for(Competitor c1 : competitors) {
			for(Competitor c2 : competitors) {
				if (!c1.equals(c2)) {
					// Choose a winner by playing the match
					Competitor winner = playMatch(c1, c2);

					// Print the match winner
					System.out.println(c1.getName() + " vs " + c2.getName() + " --> "
							+ winner.getName() + " wins !");
				}
			}
		}
	}

}

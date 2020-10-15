package competSportive.competitions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import competSportive.competitors.Competitor;
import competSportive.matchs.Match;

/**Represents a sporting competition in general*/
public abstract class Competition {

	protected final List<Competitor> competitors;
	protected Map<Competitor, Integer> competitorsPoints;

	public Competition(List<Competitor> competitors) {
		this.competitors = competitors;

		// Init competitors points to 0
		this.competitorsPoints = new LinkedHashMap<Competitor, Integer>();
		for(Competitor c : competitors) {
			competitorsPoints.put(c, 0);
		}
	}

	/** Make the competitors play the competition based on the rules of the competition
	 * @throws CompetitionException if an exception occurred
	*/
	public void play() throws CompetitionException {
		play(competitors);
	}

	/**
	 * Give a sorted map by descending values of the competitors with their points.
	 * @return the ranking map
	 */
	public Map<Competitor, Integer> ranking() {
		Map<Competitor, Integer> sortedMap = MapUtil.sortByDescendingValue(competitorsPoints);
		return sortedMap;
	}

	public abstract void play(List<Competitor> competitors) throws CompetitionException;


	/** Make a match between two competitors, determine who wins and add a point to the winner.
	 * @param c1  competitor to play
	 * @param c2  competitor to play
	 * @return Returns the winner of the match
	 * @throws CompetitionException if one of the competitors (or both) is/are not in the competition.
	 */
	public Competitor playMatch(Competitor c1, Competitor c2) throws CompetitionException {

		// Throw an error if a competitor or both of them are not in the competition.
		checkIfMatchCompetitorsAreInCompetition(c1, c2);

		// Create the match and choose the winner
		Match match = new Match(c1, c2);
		Competitor winner = match.chooseWinner();

		// Add one point to the winner
		int winnerPoint = this.competitorsPoints.get(winner);
		this.competitorsPoints.replace(winner, winnerPoint + 1);

		return winner;
	}

	/** Check if both competitors participate in the competition. If not, throw an exception.
		@param c1 : first Competitor
		@param c2 : second Competitor
		@throws CompetitionException if one of the competitors (or both) is/are not in the competition.
	 */
	private void checkIfMatchCompetitorsAreInCompetition(Competitor c1, Competitor c2) throws CompetitionException {
		boolean isC1InCompetition = isInCompetition(c1);
		boolean isC2InCompetition = isInCompetition(c2);

		// If c1 is not in the competition but c2 is :
		if (!isC1InCompetition && isC2InCompetition) {
			throw new CompetitionException("Le match entre " + c1.getName() + " et " + c2.getName() +
					" n'a pas pu être joué car " + c1.getName() + " ne fait pas partie de la compétition.");
		} 
		// Else if c2 is not in the competition but c1 is :
		else if (isC1InCompetition && !isC2InCompetition) {
			throw new CompetitionException("Le match entre " + c1.getName() + " et " + c2.getName() +
					" n'a pas pu être joué car " + c2.getName() + " ne fait pas partie de la compétition.");
		}
		// Else if both are not in the competition : 
		else if (!isC1InCompetition && !isC2InCompetition) {
			throw new CompetitionException("Le match entre " + c1.getName() + " et " + c2.getName() +
					" n'a pas pu être joué car les 2 compétiteurs ne font pas partie de la compétition.");
		}
	}

	/** Determine if the competitor is in the competitors list or not
	 *  @param c : the competitor to check
	 *  @return true if he is in the competition, otherwise false 
	 */
	private boolean isInCompetition(Competitor c) {
		return competitors.contains(c);
	}
	
	/** Give the points of every competitors of the competition.
	 * @return the points of the competitors
	 */
	public Map<Competitor, Integer> getCompetitorsPoints() {
		return this.competitorsPoints;
	}
	/** Set competitors points
	 * @param competitorsPoints points of competitors
	 */
	public void setCompetitorsPoints(Map<Competitor, Integer> competitorsPoints) {
		this.competitorsPoints = competitorsPoints;
	}
}
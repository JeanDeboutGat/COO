package competSportive.matchs;

import competSportive.competitors.Competitor;

/** Define a match of a sport competition.
There is 2 competitors and a method to choose which one win. */
public class Match {

	public Competitor competitor1;
	public Competitor competitor2;

	public Match(Competitor competitor1, Competitor competitor2) {
		this.competitor1 = competitor1;
		this.competitor2 = competitor2;
	}

	/** Determine which competitor wins the match
	 * @return the winner
	 */
	public Competitor chooseWinner() {
		double randomeBetween0And1 = Math.random();
		if (randomeBetween0And1 <= 0.5) {
			return this.competitor1;
		}
		return this.competitor2;
	}

	public Competitor getCompetitor1() {
		return competitor1;
	}

	public void setCompetitor1(Competitor competitor1) {
		this.competitor1 = competitor1;
	}

	public Competitor getCompetitor2() {
		return competitor2;
	}

	public void setCompetitor2(Competitor competitor2) {
		this.competitor2 = competitor2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (competitor1 == null) {
			if (other.competitor1 != null)
				return false;
		} else if (!competitor1.equals(other.competitor1))
			return false;
		if (competitor2 == null) {
			if (other.competitor2 != null)
				return false;
		} else if (!competitor2.equals(other.competitor2))
			return false;
		return true;
	}

}

package competSportive.competitions.master;

/**
 * Describes a rule used on a Master competition to know how the competitors are selected for the final Tournament of the Master.
 * Some parameters of the rule have default values as we can see in the MasterQualificationRuleBuilder class.
 */
public class MasterQualificationRule {

	// Position of the competitors in the poule (0 means the first, 1 the second etc..).
	// Used to create a list of all the competitors that finished on this position by extracting them from each poules.
	// This list is then used for every other parameters this rule describes.
	private Integer positionInPoule;

	// True if we want to take every competitors of this position
	private boolean takeEverythingForPosition;
	
	// Exact number of competitors selected this rule describes. Can either be set directly with the builder
	// or set later if isCompleting is True OR if takeEverythingForPosition is True.
	private Integer numberOfCompetitors;
	
	// Optional parameters which tells if we want to start the search of best/worst competitors at a given position.
	// If not set, we start the search at the index 0.
	private int indexFromWhereToStart;

	public MasterQualificationRule(Integer positionInPoule, boolean takeEverythingForPosition,
			Integer numberOfCompetitors, Integer indexFromWhereToStart) {
		this.positionInPoule = positionInPoule;
		this.takeEverythingForPosition = takeEverythingForPosition;
		this.numberOfCompetitors = numberOfCompetitors;
		this.indexFromWhereToStart = indexFromWhereToStart;
	}

	public Integer getPositionInPoule() {
		return positionInPoule;
	}

	public void setPositionInPoule(Integer positionInPoule) {
		this.positionInPoule = positionInPoule;
	}

	public Boolean getTakeEverythingForPosition() {
		return takeEverythingForPosition;
	}

	public void setTakeEverythingForPosition(Boolean takeEverythingForPosition) {
		this.takeEverythingForPosition = takeEverythingForPosition;
	}

	public Integer getNumberOfCompetitors() {
		return numberOfCompetitors;
	}

	public void setNumberOfCompetitors(Integer numberOfCompetitors) {
		this.numberOfCompetitors = numberOfCompetitors;
	}

	public int getIndexFromWhereToStart() {
		return indexFromWhereToStart;
	}

	public void setIndexFromWhereToStart(int indexFromWhereToStart) {
		this.indexFromWhereToStart = indexFromWhereToStart;
	}
}

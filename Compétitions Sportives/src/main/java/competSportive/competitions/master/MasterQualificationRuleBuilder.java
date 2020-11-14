package competSportive.competitions.master;

/**
 * Builder pattern to easily create a MasterQualificationRule object
 */
public class MasterQualificationRuleBuilder {

	private Integer positionInPoule;
	private boolean takeEverythingForPosition = false; // default to false
	private Integer numberOfCompetitors;
	private int indexFromWhereToStart = 0;             // default to 0
	
	/**
	 * Constructor of a rule builder.
	 * @param positionInPoule : mandatory field of the rule that tells the ranking position
	 * in which the rule extracts the competitor(s) 
	 */
	public MasterQualificationRuleBuilder(Integer positionInPoule) {
		this.positionInPoule = positionInPoule;
	}

	public MasterQualificationRuleBuilder setTakeEverythingForPosition(Boolean takeEverythingForPosition) {
		this.takeEverythingForPosition = takeEverythingForPosition;
		return this;
	}

	public MasterQualificationRuleBuilder setNumberOfCompetitors(Integer numberOfCompetitors) {
		this.numberOfCompetitors = numberOfCompetitors;
		return this;
	}

	public MasterQualificationRuleBuilder setIndexFromWhereToStart(Integer indexFromWhereToStart) {
		this.indexFromWhereToStart = indexFromWhereToStart;
		return this;
	}
	
	public MasterQualificationRule build() {
        return new MasterQualificationRule(positionInPoule, takeEverythingForPosition, numberOfCompetitors, indexFromWhereToStart);
    }
	
}

package competSportive.competitions;

/** Exception class used by Competitions */
public class CompetitionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CompetitionException(String errorMessage) {
        super(errorMessage);
    }
	
}

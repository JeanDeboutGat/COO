package competSportive.competitions.master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import competSportive.competitions.Competition;
import competSportive.competitions.CompetitionException;
import competSportive.competitions.League;
import competSportive.competitions.Tournament;
import competSportive.competitors.Competitor;
import competSportive.competitors.CompetitorWithPoints;
import competSportive.util.MathUtil;
import competSportive.util.MathUtilException;

/**
 * Master type sporting competition. It takes a list of competitors, a number of poules and a list of rules.
 * It constructs the poules (each poule is a League competition), make them play and then extract the competitors
 * that will play a final tournament (a Tournament competition) based on the rules it has. Then play the tournament.
 */
public class Master extends Competition {

	private int nbPoules;
	private List<MasterQualificationRule> rules;
	
	private List<League> poules;
	private Tournament finalTournament;

	/**
	 * @param competitors that will play the Master
	 * @param nbPoules : number of poules to use
	 * @param rules : list of rules to define which competitors will play the final tournament
	 * @throws CompetitionException
	 * @throws MathUtilException 
	 */
	public Master(List<Competitor> competitors, int nbPoules, List<MasterQualificationRule> rules) throws CompetitionException, MathUtilException {
		super(competitors);
		this.nbPoules = nbPoules;
		this.rules = rules;
		
		checkIfCompetitorsNumberIsValid();
		
		checkIfRulesAreValids();
		
		initPoules(competitors);
	}

	/**
	 * Check the validity of the rules.
	 * @param rules to check
	 * @throws CompetitionException if rules are not valid
	 * @throws MathUtilException 
	 */
	private void checkIfRulesAreValids() throws CompetitionException, MathUtilException {
		
		// Check every rules and get the total number of competitors the rules are taking
		int totalCompetitorsNumber = checkEachRuleValidityForThisMaster();
		
		// Check if some rules overlap on others
		checkIfRulesOverlapOnOtherRules();
		
		// If the total number of competitors the rules take is not a power of 2, throw an error
		if(!MathUtil.isPowerOf2(totalCompetitorsNumber)) {
			throw new CompetitionException("La somme des compétiteurs récupérés par les règles donne : " + totalCompetitorsNumber +
					" qui n'est pas une puissance de 2 -> on ne peut pas lancer le tournoi final avec ces règles la.");
		}
	}
	
	/**
	 * Check every rules one by one to be sure it will not cause any problem.
	 * @return the total number of competitors the rules are taking
	 * @throws CompetitionException if a rule is not valid
	 */
	private int checkEachRuleValidityForThisMaster() throws CompetitionException {
		int totalCompetitorsNumber = 0;
		
		for(MasterQualificationRule rule : rules) {
			int positionInPoule = rule.getPositionInPoule();
			int indexOfWhereToStart	= rule.getIndexFromWhereToStart();
			Integer numberOfCompetitorsToTake = rule.getNumberOfCompetitors(); // Nullable that's why it is an Integer and not int
			boolean takeEverythingForPosition = rule.getTakeEverythingForPosition();
			
			if(positionInPoule >= nbPoules || positionInPoule < 0) {
				throw new CompetitionException("La position dans la poule d'une règle n'est pas comrpis entre 0 et " + nbPoules + 
						". La position dans la poule de la règle est : " + positionInPoule);
			}
			
			if(!takeEverythingForPosition && numberOfCompetitorsToTake == null) {
				throw new CompetitionException("L'une des règles ne prend pas tout les compétiteurs de sa position et "
						+ "ne spécifie pas non plus un nombre de compétiteurs à prendre !");
			}
			
			if (takeEverythingForPosition) {
				totalCompetitorsNumber += nbPoules;
			}
			else if (numberOfCompetitorsToTake != null) {
				totalCompetitorsNumber += numberOfCompetitorsToTake;
				
				if (numberOfCompetitorsToTake <= 0 ) {
					throw new CompetitionException("L'une des règles demande de prendre un nombre de compétiteurs <= 0."
							+ " Il en faut au moins 1.");
				} if (numberOfCompetitorsToTake > nbPoules) {
					throw new CompetitionException("L'une des règles demande de prendre un nombre de compétiteurs > au nombre"
							+ " de poules, ce qui est impossible à faire.");
				}
				
				if (indexOfWhereToStart != 0) {
					if (indexOfWhereToStart < 0) {
						throw new CompetitionException("L'une des règles demande de commencer l'extraction des compétiteurs à un index < 0");
					} 
					int maxCompetitorPossibleForRule = nbPoules - indexOfWhereToStart;
					if (numberOfCompetitorsToTake > maxCompetitorPossibleForRule) {
						throw new CompetitionException("L'une des règles demande de prendre plus de compétiteurs que ce qui est possible :"
								+ " elle demande de prendre " + numberOfCompetitorsToTake + " compétiteurs alors qu'il n'y en a que "
								+ maxCompetitorPossibleForRule + " maximum (nb de poules - indexFromWhereToStart).");
					}
				}
			}
		}
		
		return totalCompetitorsNumber;
	}
	
	/**
	 * Check if some rules overlap on others
	 * @throws CompetitionException if one of the rule overlap on other(s) rule(s)
	 * @throws MathUtilException 
	 */
	private void checkIfRulesOverlapOnOtherRules() throws CompetitionException, MathUtilException {
		List<List<MasterQualificationRule>> listsOfRulesOfSamePositionInPoule = createListsOfRulesOfSamePositionInPoule(rules);
		
		for(List<MasterQualificationRule> subListOfRulesOfSamePosition : listsOfRulesOfSamePositionInPoule) {
			MasterQualificationRule ruleToTest = subListOfRulesOfSamePosition.remove(0);
			
			for (MasterQualificationRule otherRule : subListOfRulesOfSamePosition) {
				if (ruleToTest.getTakeEverythingForPosition() || otherRule.getTakeEverythingForPosition()) {
					throw new CompetitionException("Il est impossible d'avoir plus d'une règle sur une position si la règle en question "
							+ "prend tout les compétiteurs de la position avec l'attribut takeEverythingForPosition à true (sinon on "
							+ "overlap forcément des compétiteurs dans plusieurs règles). "
							+ "Exception jeté par la position : " + ruleToTest.getPositionInPoule());
				}
				
				int ruleToTestLeftBoundary  = ruleToTest.getIndexFromWhereToStart();
				int ruleToTestRightBoundary = ruleToTest.getIndexFromWhereToStart() + ruleToTest.getNumberOfCompetitors() -1;
				int otherRuleLeftBoundary   = otherRule.getIndexFromWhereToStart();
				int otherRuleRightBoundary  = otherRule.getIndexFromWhereToStart() + otherRule.getNumberOfCompetitors() -1;
				if (MathUtil.doesIntervalOverlap(ruleToTestLeftBoundary, ruleToTestRightBoundary, otherRuleLeftBoundary, otherRuleRightBoundary)) {
					throw new CompetitionException("Les intervalles de 2 règles parmis celles de la position " +
							ruleToTest.getPositionInPoule() + " se chevauchent.");
				}
			}
		}
	}
	
	/**
	 * Take a list of rules and make a new list composed of subLists of rules based on the position in poules each rules affect.
	 * For instance List<MasterQualificationRule> rules = {r1(indexInPoule=0), r2(indexInPoule=2), r3(indexInPoule=0)}
	 * will give : List<List<MasterQualificationRule>> result = {{r1(indexInPoule=0), r3(indexInPoule=0)}, {r3(indexInPoule=2)}}
	 * @param rules : the rules to sort
	 * @return the sorted rules on sublists
	 */
	private List<List<MasterQualificationRule>> createListsOfRulesOfSamePositionInPoule(List<MasterQualificationRule> rules) {
		List<List<MasterQualificationRule>> result = new ArrayList<>();

		for (MasterQualificationRule rule : rules) {
			boolean isContainingListAlreadyInResult = false;
			
			// If the list which contains the rules of the same position as the current rule, add the current rule to the list
			for (List<MasterQualificationRule> listOfRulesOfSamePosition : result) {
				if (listOfRulesOfSamePosition.get(0).getPositionInPoule() == rule.getPositionInPoule()) {
					listOfRulesOfSamePosition.add(rule);
					isContainingListAlreadyInResult = true;
					break;
				}
			}
			
			// If not in any list, create it and add the new list to the result
			if(!isContainingListAlreadyInResult) {
				List<MasterQualificationRule> newPositionList = new ArrayList<>();
				newPositionList.add(rule);
				result.add(newPositionList);
			}
		}
		
		return result;
	}

	// Throws a CompetitionException if the competitors number is not valid
	private void checkIfCompetitorsNumberIsValid() throws CompetitionException {
		int nbParticipants = super.getCompetitors().size();
		
		if (nbParticipants % nbPoules != 0) {
			throw new CompetitionException("Le nombre de participants dans le master est invalide :"
					+ " le nombre de compétiteurs (" + nbParticipants + ") modulo le nombre de poules ("  + nbPoules + ") n'est pas égal à 0 !");
		}
	}
	
	/**
	 * Initialize the poules
	 * @param competitors of the poules
	 */
	private void initPoules(List<Competitor> competitors) {
		poules = new ArrayList<League>();
		int nbParticipantsParPoule = competitors.size() / nbPoules;
		
		// Prevent side effects for the competitors list received in parameters
		List<Competitor> competitorsCopie = new ArrayList<Competitor>(competitors);
		
		// add competitors to the poules
		for (int pouleIndex = 0; pouleIndex < nbPoules ; pouleIndex++) {
			List<Competitor> poule = new ArrayList<Competitor>();
			
			for(int i = 0; i < nbParticipantsParPoule; i++) {
				Competitor competitor = competitorsCopie.get(0);
				
				poule.add(competitor);
				competitorsCopie.remove(0);
			}
			
			poules.add(new League(poule));
		}
	}

	@Override
	public void play() throws CompetitionException {
		// Play every poules and take their rankings
		List<List<CompetitorWithPoints>> poulesRankings = new ArrayList<>();
		for (int pouleIndex = 0; pouleIndex < poules.size(); pouleIndex++) {
			System.out.println("\n######## Poule n:" + pouleIndex + " ########");
			League poule = poules.get(pouleIndex);
			poule.play();
			poulesRankings.add(poule.rankingAsList());
		}
		
		// Sort the rankings of the poules to get the rankings based on the position of a competitor in its poule
		List<List<CompetitorWithPoints>> positionRankings =
				convertPoulesRankingsToSortedListByPositionOfCompetitors(poulesRankings);

		// Apply the rules of the Master to retrieve the competitors to use for the final tournament
		List<Competitor> tournamentCompetitors = getTournamentCompetitorBasedOnRules(positionRankings);
		
		// Create the tournament
		finalTournament = new Tournament(tournamentCompetitors);
		
		// Play the tournament
		System.out.println("\n######## Master final tournament ########");
		finalTournament.play();
	}
	
	/**
	 * Create a List of sorted maps of every competitors that got the x position on their poule.
	 * I.e if there is 2 poules that contains each 4 competitors, we will create a list
	 * which contains 4 subLists of competitors : one for each position (1st, 2nd, 3rd and 4th).
	 * in this case these lists would contain 2 values : the first and the second for the position
	 * @param poulesRankings : the poules rankings to sort on a list
	 * @return the sorted list of competitors based on their positions in their poules
	 */
	private List<List<CompetitorWithPoints>> 
		convertPoulesRankingsToSortedListByPositionOfCompetitors(List<List<CompetitorWithPoints>> poulesRankings) {
		
		// Create a list of list of competitors for each positions in the poules,
		// the competitors in the sublists are NOT sorted yet
		List<List<CompetitorWithPoints>> positionRankings = new ArrayList<>();
		for(int position = 0; position < nbPoules; position++) {
			List<CompetitorWithPoints> rankingsForPosition = new ArrayList<>();
			for (int pouleIndex = 0; pouleIndex < nbPoules; pouleIndex++) {
				rankingsForPosition.add(poulesRankings.get(pouleIndex).get(position));
			}
			positionRankings.add(rankingsForPosition);
		}
		
		// Sort the competitors from the best to worst for each positions
		for (List<CompetitorWithPoints> positionCompetitors : positionRankings) {
			Collections.sort(positionCompetitors, Collections.reverseOrder()); 
		}
		
		return positionRankings;
	}
	
	/**
	 * Retrieve the Competitors we want to use for the final tournament based on the rules and
	 * the rankings of the poules sorted by the competitors positions.
	 * @param sortedCompetitors
	 * @return the competitors to use for the tournament
	 */
	private List<Competitor> getTournamentCompetitorBasedOnRules(List<List<CompetitorWithPoints>> sortedCompetitors) {
		List<Competitor> result = new ArrayList<Competitor>();
		
		// Iterate over each rule and add the competitors it takes in the result
		for (MasterQualificationRule rule : rules) {
			int rulePosition = rule.getPositionInPoule();
			
			// If the flag to take everything for the pos is true, take everything for the pos
			if (rule.getTakeEverythingForPosition()) {
				List<Competitor> everyCompetitorForThePosition = sortedCompetitors.get(rulePosition).stream() 
						.map(x -> x.getCompetitor()).collect(Collectors.toList());
				
				result.addAll(everyCompetitorForThePosition);
			}
			
			// Else it means the rule has a specific number of competitors to take
			else {
				int numberOfCompetitorToTake = rule.getNumberOfCompetitors();
				int indexFromWhereToStart = rule.getIndexFromWhereToStart();
				
				List<Competitor> ruleCompetitors = sortedCompetitors.get(rulePosition)
						.subList(indexFromWhereToStart, indexFromWhereToStart + numberOfCompetitorToTake).stream()
						.map(x -> x.getCompetitor()).collect(Collectors.toList());
				
				result.addAll(ruleCompetitors);
			}
		}
		
		return result;
	}

	public List<League> getPoules() {
		return new ArrayList<>(poules);
	}

	/**
	 * Get the final tournament.
	 * Should be used after the play() method has been used. If not, it will return null
	 * because the final tournament has not been set yet.
	 * @return the final tournament (already played so it has a ranking)
	 */
	public Tournament getFinalTournament() {
		return this.finalTournament;
	}

}

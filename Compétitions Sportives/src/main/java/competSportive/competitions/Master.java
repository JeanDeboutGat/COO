package competSportive.competitions;

import java.util.ArrayList;
import java.util.List;

import competSportive.competitors.Competitor;

public class Master extends Competition {

	private List<List<Competitor>> poules;
	private Competition tournament;
	
	public Master(List<Competitor> competitors, int nbPoules) throws CompetitionException {
		super(competitors);
		
		poules = new ArrayList<List<Competitor>>();
//		tournament = new Tournament(competitors);
		
		
		List<Competitor> competitorsCopie = new ArrayList<Competitor>(competitors);
		
		if (competitorsCopie.size() % nbPoules != 0) {
			throw new CompetitionException("Le nombre de participants dans le master est invalide.");
		}
		
		int nbParticipantsParPoule = competitorsCopie.size() / nbPoules;
		
		for (int i = 0; i < nbPoules ; i++) {
			
			List<Competitor> poule = new ArrayList<Competitor>();
			
			for(int j = 0; j < nbParticipantsParPoule; j++) {
				Competitor competitor = competitorsCopie.get(0);
				
				poule.add(competitor);
				competitorsCopie.remove(0);
			}
			
			poules.add(poule);
			
		}
		
		System.out.println("machin");
		
	}

	@Override
	public void play(List<Competitor> competitors) throws CompetitionException {
		
	}

}

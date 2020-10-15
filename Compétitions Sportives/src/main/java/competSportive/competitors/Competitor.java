package competSportive.competitors;

/** Competitor of a sport competition. */
public class Competitor {
	
	private String name;
	
	public Competitor(String name) {
		this.name   = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Competitor other = (Competitor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}

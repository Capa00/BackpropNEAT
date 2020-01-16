package gene;

public class Connection {
	private Node from ,to;
    private double weight;
    
    
    // COSTR ////////////////////////////////////
    
	public Connection(Node from, Node to, double weight) {
		super();
		
		this.from = from;
		this.to = to;
		this.weight = weight;
		
	}
	
	
	public Connection(Connection connectionGene){
		this.from = connectionGene.from;
		this.to = connectionGene.to;
		this.weight = connectionGene.weight;
		
    }

	public void setWeight(double weight){this.weight = weight;}
	
	public Node getNodeFrom() {return from;}
	public Node getNodeTo() {return to;}
	public double getWeight() {return weight;}
	
	public double getPreviousOutput() {

		/*debug*
		System.out.println("ConnectionGene_bpn.getValue(): "+this);
		System.out.println("\tarriva "+from.getOutput());
		System.out.println("\tweight = "+weight);
		System.out.println();
		/**/
		
		return from.getOutput();
		

	}
	
	public double getNextValue() {
		return to.getValue();
	}
	
	public double getNextError() {
		return to.getError();
	}
	
	public void setNextBias(double value) {
		to.setBias(value);
	}
	
	public double getNextBias() {
		return to.getBias();
	}
	
	
	public String toString() {
		return from.toString()+" -> "+to.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Connection other = (Connection) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		
		} else if (!from.equals(other.from))
			return false;
		
		if (to == null) {
			if (other.to != null)
				return false;
		
		} else if (!to.equals(other.to))
			return false;
		
		return true;
	}

}

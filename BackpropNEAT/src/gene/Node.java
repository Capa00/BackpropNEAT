/**NodeGene_bpn.java
 * 
//TODO
 *
 */
package gene;

import java.util.ArrayList;
import java.util.List;

import activationFunction.ActivationFunction;
import activationFunction.Sigmoid;

public class Node {
	private double output;
	private double value;
	private double error;
	private double bias;
	
	private int layer;
	private int id;

	private ActivationFunction attivazione = new Sigmoid(); //default
	
	private ArrayList<Connection> archiEntranti = new ArrayList<>();
	private ArrayList<Connection> archiUscenti = new ArrayList<>();
	
	// COSTR //////////////////////////////////////////
	

	public Node(int layer, int id) {
		this.layer = layer;
		this.id = id;
	}
	
	public Node(int layer, int id, double bias) {
		this(layer, id);
		this.bias = bias;
	}
	
	
	// GETTER ////////////////////////////////////////
	
	public int getId() {return id;}
	public int getLayer() {return layer;}
	
	public double getOutput() {return output;}
	public double getValue () {return value;}
	public double getError () {return error;}
	public double getBias()   {return bias;}
	
	public List<Connection> getOutgoingConnection() {return archiUscenti;}
	public List<Connection> getIncomingConnection() {return archiEntranti;}
	
	
	// SETTER ///////////////////////////////////////
	
	public void setLayer(int layer) {this.layer = layer;}
	public void setError(double value) {error = value;}
	public void setBias(double value) {bias = value;}
	public void setValue(double value) {
		this.value = value;
		//ai nodi input non si applica la funzione di attivazione
		output = (archiEntranti.size() == 0) ? value : attivazione.activate(value);
	}
	
	
	// METHODS ////////////////////////////////////////
	
	public void addArcoEntrante(Connection edge) {archiEntranti.add(edge);}
	public void addArcoUscente (Connection edge) {archiUscenti.add(edge);}

	public boolean isConnectedWith(Node to) {
		for (Connection connection : getOutgoingConnection()) {
			if(to.equals(connection.getNodeTo()))return true;
		}
		
		return false;
	}
	
	
	// FEED FORWARD /////////////////////////////////
	
	public void feedForward() {
		value = 0;
		
		/*debug*
		System.out.println("NodeGene_bpn.feedForward(): "+this);
		/**/

		for (Connection arcoEntrante : archiEntranti) {
			
			/*debug*
			System.out.println("\tingresso da "+arcoEntrante+" = "+(arcoEntrante.getNodeFromOutput()*arcoEntrante.getWeight()));
			System.out.println("\toutput da from "+arcoEntrante.getNodeFromOutput());
			System.out.println("\tpeso = "+arcoEntrante.getWeight());
			/**/

			value += arcoEntrante.getPreviousOutput()*arcoEntrante.getWeight();
		}
		value += bias;
		output = attivazione.activate(value);

		/*debug*
		System.out.println();
		System.out.println("\tnuovo value = "+value);
		System.out.println("\tnuovo output = "+output);
		/**/
		
	}
	
	
	// BACK PROPAGATION ///////////////////////////
	
	public void backPropagation(double learningRate) {
		
		/*debug*
		System.out.println("NodeGene_bpn.backPropagation(): "+this);
		System.out.println("\tvalue = "+value);
		/**/

		double delta = 0;
		double gradient = 0;
		error = 0;
		for (Connection arcoUscente : archiUscenti) {
			error += arcoUscente.getNextError()*arcoUscente.getWeight();
			
			gradient = arcoUscente.getNextError() * attivazione.applyDerivative(arcoUscente.getNextValue());
			delta = learningRate * gradient * output;
			
			
			
			arcoUscente.setWeight(arcoUscente.getWeight() + delta);
			arcoUscente.setNextBias(arcoUscente.getNextBias() + gradient);
			
			/*debug*
			System.out.println("\t"+arcoUscente+":");
			System.out.println("\t"delta = "+delta*1000);
			System.out.println("\tpeso = "+(arcoUscente.getWeight() - delta));
			System.out.println("\terrore nodo uscente = "+arcoUscente.getNodeToError());
			System.out.println("\tderivata = "+attivazione.applyDerivative(arcoUscente.getNodeToValue()));
			System.out.println("\tnuovo peso = "+arcoUscente.getWeight());
			/**/

			
		}
		
	}
	// OBJECT ///////////////////////////////////////////////
	
	public String toString() {
		return "{"+layer+"."+id+"}";
	}

	@Override
	public int hashCode() {
		return 6*id + 35*layer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node other = (Node) obj;

			return(id == other.id && layer == other.layer);
		}
		return false;
	}
	
}

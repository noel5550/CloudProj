package foo;

import java.util.ArrayList;


public class UserPetition implements java.io.Serializable {
	
	private static final long serialVersionUID = 7178457972188933516L;
	ArrayList<String> listPetitions;
	ArrayList<String> listPetitionsSignes;
	String name;
	
	public UserPetition() {
		listPetitions = new ArrayList<>();
		listPetitionsSignes = new ArrayList<>();
	}
	
	public UserPetition(String nom) {
		name = nom;
		listPetitions = new ArrayList<>();
		listPetitionsSignes = new ArrayList<>();
	}

	public ArrayList<String> getListPetitions() {
		return listPetitions;
	}

	public void setListPetitions(ArrayList<String> petitions) {
		this.listPetitions = petitions;
	}
	
	public ArrayList<String> getListPetitionsSignes() {
		return listPetitionsSignes;
	}

	public void setListPetitionsSignes(ArrayList<String> petitions) {
		this.listPetitionsSignes = petitions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
package ood.chess;

public class Player {
	private Person person;
	private boolean whiteSide;
	private boolean isChecked;
	
	public Player(Person person, boolean whiteSide) {
		this.person = person;
		this.whiteSide = whiteSide;
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public boolean isWhiteSide() {
		return whiteSide;
	}
	public void setWhiteSide(boolean whiteSide) {
		this.whiteSide = whiteSide;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}

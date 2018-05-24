package parserdef;

public class Unit {
	private int id, phydim;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPhydim() {
		return phydim;
	}
	public void setPhydim(int phydim) {
		this.phydim = phydim;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public String toString() {
        return "Unit:: ID="+this.id+" Name=" + this.name;
    }
}

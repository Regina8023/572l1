package parserdef;

public class Meaquantity {
	private int id;
	private String name;
	private String datatype;
	private int unit;
	private int quantity;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
    public String toString() {
        return "Meaquantity:: ID="+this.id+" Name=" + this.name + " DataType=" + this.datatype + " Unit=" + this.unit + " Quantity=" + this.quantity;
    }
}
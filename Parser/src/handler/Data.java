package handler;
import parserdef.ExternalComponent;

public class Data {
	private ExternalComponent e;
	private int id, start, end, number, datalen;
	private String name, unit, datatype, quantity;
	
	public ExternalComponent getE() {
		return e;
	}
	public void setE(ExternalComponent e) {
		this.e = e;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
		switch(datatype) {
			case("DT_SHORT"):
				this.datalen = 2;break;
			case("DT_FLOAT"):
				this.datalen = 4;break;
			case("DT_DOUBLE"):
				this.datalen = 8;break;
			case("DT_LONG"):
				this.datalen = 4;break;
			case("DT_LONGLONG"):
				this.datalen = 8;break;
		}
	}
	public int getDatalen() {
		return datalen;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}

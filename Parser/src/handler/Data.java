package handler;
import java.util.Comparator;
import java.util.PriorityQueue;

import parserdef.ExternalComponent;

public class Data<T extends Number & Comparable<T>> {
	private ExternalComponent e;
	private int id, start, end, number=0, datalen;
	private String name, unit, datatype, quantity;
	private T max, min;
	private double sum;
	private PriorityQueue<T> maxHeap = new PriorityQueue<T>(new Comparator<T>() {  
	    @Override  
	    public int compare(T o1, T o2) {  
	        return o2.compareTo(o1);  
	    }    
	});
    private PriorityQueue<T> minHeap = new PriorityQueue<T>();
    private boolean isFirst = true;
	
    public void HandleData(T data) {
    	if (isFirst) {
    		max = data;
    		min = data;
    		minHeap.offer(data);
    		sum += data.doubleValue();
    		number++;
    		isFirst = false;
    	} else {
    		if (data.compareTo(max) > 0) max = data;
    		if (data.compareTo(min) < 0) min = data;
    		if (number % 2 == 0) {
    			maxHeap.offer(data);
    			T filteredMaxNum = maxHeap.poll();
    	        minHeap.offer(filteredMaxNum);
    		} else {
    			minHeap.offer(data);
    	        T filteredMinNum = minHeap.poll();
    	        maxHeap.offer(filteredMinNum);
    		}
    		sum += data.doubleValue();
    		number++;
    	}
    }
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
	public T getMax() {
		return max;
	}
	public T getMin() {
		return min;
	}
	public double getAvg() {
		return sum/number;
	}
	public double getSum() {
		return sum;
	}
	public double getMed() {
		return (number % 2 == 0)?(minHeap.peek().doubleValue()+maxHeap.peek().doubleValue())/2:minHeap.peek().doubleValue();
	}
}

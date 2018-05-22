package parserdef;

public class ExternalComponent {
	private int id;
	private String filenameURL;
	private int start;
	private int length;
	private int blocksize;
	private int perblock;
	//size = blocksize/perblock
	private int size;
	private int valueoffset;
	
	public void setSize() {
		this.size = this.getBlocksize()/this.getPerblock();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilenameURL() {
		return filenameURL;
	}
	public void setFilenameURL(String filenameURL) {
		this.filenameURL = filenameURL;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getBlocksize() {
		return blocksize;
	}
	public void setBlocksize(int blocksize) {
		this.blocksize = blocksize;
	}
	public int getValueoffset() {
		return valueoffset;
	}
	public void setValueoffset(int valueoffset) {
		this.valueoffset = valueoffset;
	}
	public int getSize() {
		return size;
	}
	public int getPerblock() {
		return perblock;
	}
	public void setPerblock(int perblock) {
		this.perblock = perblock;
	}
	
	@Override
    public String toString() {
        return "ExternalComponent:: ID="+this.id+" FilenameURL=" + this.filenameURL + " Start=" + this.start + " Size=" + this.size + " Length=" + this.length + " Valueoffset=" + this.valueoffset;
    }
}

package de.ddkfm.micro.util.transform;

import de.ddkfm.micro.util.MicroUtils;

/**
 * The programline represents a row int the memory. as a 12-bit sequence splited into instruction, address and data
 * */
public class ProgramLine {
	private boolean[] instruction = new boolean[4];
	private boolean[] address = new boolean[4];
	private boolean[] data = new boolean[4];
	
	private String comment = "";

	public ProgramLine(boolean[] instruction, boolean[] address, boolean[] data, String comment) {
		super();
		this.instruction = instruction;
		this.address = address;
		this.data = data;
		this.comment = comment;
	}
	public ProgramLine(String instruction, String address, String data, String comment) {
		super();
		this.instruction = MicroUtils.getBinaryNotationByString(instruction);
		this.address = MicroUtils.getBinaryNotationByString(address);
		this.data = MicroUtils.getBinaryNotationByString(data);
		this.comment = comment;
	}
	public boolean[] getInstruction() {
		return instruction;
	}
	public void setInstruction(boolean[] instruction) {
		this.instruction = instruction;
	}
	public boolean[] getAddress() {
		return address;
	}
	public void setAddress(boolean[] address) {
		this.address = address;
	}
	public boolean[] getData() {
		return data;
	}
	public void setData(boolean[] data) {
		this.data = data;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

package org.sagebionetworks.web.shared.provenance;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class ProvGraphNode implements IsSerializable {

	public abstract String getId();
	
	boolean startingNode = false;
	boolean pushRight = false;
	double xPos;
	double yPos;
	
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}	
	public boolean isStartingNode() {
		return startingNode;
	}
	public void setStartingNode(boolean startingNode) {
		this.startingNode = startingNode;
	}	
	public boolean isPushRight() {
		return pushRight;
	}
	public void setPushRight(boolean pushRight) {
		this.pushRight = pushRight;
	}
	
	/**
	 * NOT AUTOGENERATED!!
	 * NOTE: This is not auto-generated by Eclipse. GWT does not have a Double.doubleToLongBits(...) method
	 */		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (startingNode ? 1231 : 1237);
		result = prime * result + (pushRight ? 1231 : 1237);
		long temp;
		temp = Double.toString(xPos).hashCode();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.toString(yPos).hashCode();
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * NOT AUTOGENERATED!!
	 * NOTE: This is not auto-generated by Eclipse. GWT does not have a Double.doubleToLongBits(...) method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProvGraphNode other = (ProvGraphNode) obj;
		if (startingNode != other.startingNode)
			return false;
		if (pushRight != other.pushRight)
			return false;
		if (!Double.toString(xPos).equals(Double
				.toString(other.xPos)))
			return false;
		if (!Double.toString(yPos).equals(Double
				.toString(other.yPos)))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProvGraphNode [startingNode=" + startingNode + ", pushRight="
				+ pushRight + ", xPos=" + xPos + ", yPos=" + yPos + "]";
	}


}

package ar.edu.iua.treban.model.util;

import java.io.Serializable;



public class ChangeStateMessage<T> implements Serializable{
	private static final long serialVersionUID = -4703656162373755957L;

	public static String TYPE_GRAPH_DATA="GRAPH_DATA_BAR";
	
	private T payload;
	private String type;
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public ChangeStateMessage(String type, T payload) {
		super();
		this.type = type;
		this.payload = payload;
	}
}

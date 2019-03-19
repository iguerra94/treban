package ar.edu.iua.treban.model.util;

public class LabelValue {
	private String label;
	private double value;

	public LabelValue(String label, double value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", getLabel(), getValue());
	}
}

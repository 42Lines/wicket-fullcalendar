package net.ftlines.web.fullcalendar;

import java.io.Serializable;

public class Header implements Serializable {

	private String left;
	private String center;
	private String right;

	public String getLeft() {
		return left;
	}

	public Header setLeft(String left) {
		this.left = left;
		return this;
	}

	public String getCenter() {
		return center;
	}

	public Header setCenter(String center) {
		this.center = center;
		return this;
	}

	public String getRight() {
		return right;
	}

	public Header setRight(String right) {
		this.right = right;
		return this;
	}

}

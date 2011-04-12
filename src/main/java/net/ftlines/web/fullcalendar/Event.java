package net.ftlines.web.fullcalendar;

import java.util.Date;

public class Event {

	private String id;

	private String title;

	private boolean allDay=false;

	private Date start;

	private Date end;

	private String url;

	private String className;

	private Boolean editable;

	private String color;

	private String backgroundColor;

	private String borderColor;

	private String textColor;

	public String getId() {
		return id;
	}

	public Event setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Event setTitle(String title) {
		this.title = title;
		return this;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public Event setAllDay(boolean allDay) {
		this.allDay = allDay;
		return this;
	}

	public Date getStart() {
		return start;
	}

	public Event setStart(Date start) {
		this.start = start;
		return this;
	}

	public Date getEnd() {
		return end;
	}

	public Event setEnd(Date end) {
		this.end = end;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Event setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getClassName() {
		return className;
	}

	public Event setClassName(String className) {
		this.className = className;
		return this;
	}

	public Boolean isEditable() {
		return editable;
	}

	public Event setEditable(Boolean editable) {
		this.editable = editable;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Event setColor(String color) {
		this.color = color;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public Event setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public Event setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public String getTextColor() {
		return textColor;
	}

	public Event setTextColor(String textColor) {
		this.textColor = textColor;
		return this;
	}

}

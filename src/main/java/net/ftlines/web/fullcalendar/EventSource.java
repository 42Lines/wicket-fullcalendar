package net.ftlines.web.fullcalendar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class EventSource implements Serializable {

	private String color;
	private String backgroundColor;
	private String borderColor;
	private String textColor;
	private String className;
	private Boolean editable;
	private Boolean allDayDefault;
	private Boolean ignoreTimezone;
	private String error;
	private Map<String, Object> data = new HashMap<String, Object>();
	private String url;

	private EventProvider eventProvider;

	public String getColor() {
		return color;
	}

	public EventSource setColor(String color) {
		this.color = color;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public EventSource setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public EventSource setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public String getTextColor() {
		return textColor;
	}

	public EventSource setTextColor(String textColor) {
		this.textColor = textColor;
		return this;
	}

	public String getClassName() {
		return className;
	}

	public EventSource setClassName(String className) {
		this.className = className;
		return this;
	}

	public Boolean isEditable() {
		return editable;
	}

	public EventSource setEditable(Boolean editable) {
		this.editable = editable;
		return this;
	}

	public Boolean isAllDayDefault() {
		return allDayDefault;
	}

	public EventSource setAllDayDefault(Boolean allDayDefault) {
		this.allDayDefault = allDayDefault;
		return this;
	}

	public Boolean isIgnoreTimezone() {
		return ignoreTimezone;
	}

	public EventSource setIgnoreTimezone(Boolean ignoreTimezone) {
		this.ignoreTimezone = ignoreTimezone;
		return this;
	}

	public String getError() {
		return error;
	}

	public EventSource setError(String error) {
		this.error = error;
		return this;
	}

	@JsonIgnore
	public EventProvider getEventProvider() {
		return eventProvider;
	}

	public EventSource setEventsProvider(EventProvider eventsProvider) {
		this.eventProvider = eventsProvider;
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}

	@JsonProperty
	String getUrl() {
		return url;
	}

	void setUrl(String url) {
		this.url = url;
	}

	
	public EventSource setTitle(String title) {
		data.put(Const.TITLE, title);
		return this;
	}

	@JsonIgnore
	public String getTitle() {
		return (String)data.get(Const.TITLE);
	}
	
	@JsonIgnore
	public String getUuid() {
		return (String)data.get(Const.UUID);
	}
	
	
	public EventSource setUuid(String uuid) {
		data.put(Const.UUID, uuid);
		return this;
	}

	public static class Const {
		public static final String TITLE = "fcxTitle";
		public static final String UUID = "fcxUuid";
	}

}

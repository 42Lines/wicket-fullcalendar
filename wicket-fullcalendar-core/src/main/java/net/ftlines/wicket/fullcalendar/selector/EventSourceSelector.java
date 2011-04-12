package net.ftlines.wicket.fullcalendar.selector;

import net.ftlines.wicket.fullcalendar.FullCalendar;

import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;

public class EventSourceSelector extends WebComponent implements
		IHeaderContributor {

	private final FullCalendar calendar;

	public EventSourceSelector(String id, FullCalendar calendar) {
		super(id);
		this.calendar = calendar;
		setOutputMarkupId(true);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderOnLoadJavascript("$('#" + calendar.getMarkupId()
				+ "').fullCalendarExt('createEventSourceSelector', '"
				+ getMarkupId() + "');");
	}

}

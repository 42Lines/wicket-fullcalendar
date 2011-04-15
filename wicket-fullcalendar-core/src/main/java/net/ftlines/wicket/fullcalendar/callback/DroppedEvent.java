package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

public class DroppedEvent extends AbstractShiftedEventParam
{
	private final boolean allDay;

	public DroppedEvent(EventSource source, Event event, int hoursDelta, int minutesDelta, boolean allDay)
	{
		super(source, event, hoursDelta, minutesDelta);
		this.allDay = allDay;
	}

	public boolean isAllDay()
	{
		return allDay;
	}


}

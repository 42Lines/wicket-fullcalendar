package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

public class ResizedEvent extends AbstractShiftedEventParam
{

	public ResizedEvent(EventSource source, Event event, int hoursDelta, int minutesDelta)
	{
		super(source, event, hoursDelta, minutesDelta);
	}


}

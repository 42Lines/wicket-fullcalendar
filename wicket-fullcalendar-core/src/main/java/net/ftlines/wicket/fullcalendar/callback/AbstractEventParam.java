package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

abstract class AbstractEventParam
{
	private final EventSource source;
	private final Event event;

	public AbstractEventParam(EventSource source, Event event)
	{
		this.source = source;
		this.event = event;
	}

	public EventSource getSource()
	{
		return source;
	}

	public Event getEvent()
	{
		return event;
	}


}

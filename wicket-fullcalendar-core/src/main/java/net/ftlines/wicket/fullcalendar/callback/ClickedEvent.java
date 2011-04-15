package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

public class ClickedEvent extends AbstractEventParam
{

	public ClickedEvent(EventSource source, Event event)
	{
		super(source, event);
	}

}

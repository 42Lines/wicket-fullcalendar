package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;


class AbstractShiftedEventParam extends AbstractEventParam
{

	private final int daysDelta;
	private final int minutesDelta;

	public AbstractShiftedEventParam(EventSource source, Event event, int hoursDelta, int minutesDelta)
	{
		super(source, event);
		this.daysDelta = hoursDelta;
		this.minutesDelta = minutesDelta;
	}

	public int getDaysDelta()
	{
		return daysDelta;
	}

	public int getMinutesDelta()
	{
		return minutesDelta;
	}


}

package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

import org.joda.time.DateTime;
import org.joda.time.Period;


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

	public DateTime getNewStartTime()
	{
		return shift(getEvent().getStart());
	}

	public DateTime getNewEndTime()
	{
		return shift(getEvent().getEnd());
	}

	public Period getDelta()
	{
		return Period.days(daysDelta).plusMinutes(minutesDelta);
	}

	private DateTime shift(DateTime start)
	{
		return start.plusDays(daysDelta).plusMinutes(minutesDelta);
	}


}

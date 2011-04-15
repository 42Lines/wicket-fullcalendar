package net.ftlines.wicket.fullcalendar.callback;

import org.joda.time.DateTime;

public class SelectedRange
{
	private DateTime start;
	private DateTime end;
	private boolean allDay;

	public SelectedRange(DateTime start, DateTime end, boolean allDay)
	{
		this.start = start;
		this.end = end;
		this.allDay = allDay;
	}

	public DateTime getStart()
	{
		return start;
	}

	public void setStart(DateTime start)
	{
		this.start = start;
	}

	public DateTime getEnd()
	{
		return end;
	}

	public void setEnd(DateTime end)
	{
		this.end = end;
	}

	public boolean isAllDay()
	{
		return allDay;
	}

	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}


}

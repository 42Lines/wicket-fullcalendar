package net.ftlines.wicket.fullcalendar.callback;

import java.util.Date;

public class SelectedRange
{
	private Date start;
	private Date end;
	private boolean allDay;

	public SelectedRange(Date start, Date end, boolean allDay)
	{
		this.start = start;
		this.end = end;
		this.allDay = allDay;
	}

	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEnd(Date end)
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

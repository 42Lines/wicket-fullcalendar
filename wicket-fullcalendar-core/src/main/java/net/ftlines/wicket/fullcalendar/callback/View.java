package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.ViewType;

import org.joda.time.DateMidnight;

public class View
{
	private ViewType type;
	private DateMidnight start;
	private DateMidnight end;
	private DateMidnight visibleStart;
	private DateMidnight visibleEnd;

	public View(ViewType type, DateMidnight start, DateMidnight end, DateMidnight visibleStart, DateMidnight visibleEnd)
	{
		this.type = type;
		this.start = start;
		this.end = end;
		this.visibleStart = visibleStart;
		this.visibleEnd = visibleEnd;
	}

	public ViewType getType()
	{
		return type;
	}

	public DateMidnight getStart()
	{
		return start;
	}

	public DateMidnight getEnd()
	{
		return end;
	}

	public DateMidnight getVisibleStart()
	{
		return visibleStart;
	}

	public DateMidnight getVisibleEnd()
	{
		return visibleEnd;
	}


}

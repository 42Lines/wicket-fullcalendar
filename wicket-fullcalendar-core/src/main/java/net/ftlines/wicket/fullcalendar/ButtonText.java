package net.ftlines.wicket.fullcalendar;

import java.io.Serializable;

public class ButtonText implements Serializable
{
	private String prev, next, prevYear, nextYear, today, month, week, day;

	public String getPrev()
	{
		return prev;
	}

	public ButtonText setPrev(String prev)
	{
		this.prev = prev;
		return this;
	}

	public String getNext()
	{
		return next;
	}

	public ButtonText setNext(String next)
	{
		this.next = next;
		return this;
	}

	public String getPrevYear()
	{
		return prevYear;
	}

	public ButtonText setPrevYear(String prevYear)
	{
		this.prevYear = prevYear;
		return this;
	}

	public String getNextYear()
	{
		return nextYear;
	}

	public ButtonText setNextYear(String nextYear)
	{
		this.nextYear = nextYear;
		return this;
	}

	public String getToday()
	{
		return today;
	}

	public ButtonText setToday(String today)
	{
		this.today = today;
		return this;
	}

	public String getMonth()
	{
		return month;
	}

	public ButtonText setMonth(String month)
	{
		this.month = month;
		return this;
	}

	public String getWeek()
	{
		return week;
	}

	public ButtonText setWeek(String week)
	{
		this.week = week;
		return this;
	}

	public String getDay()
	{
		return day;
	}

	public ButtonText setDay(String day)
	{
		this.day = day;
		return this;
	}


}

package net.ftlines.wicket.fullcalendar;

public enum ViewType
{
	MONTH("month"), BASIC_WEEK("basicWeek"), BASIC_DAY("basicDay"), AGENDA_WEEK("agendaWeek"), AGENDA_DAY("agendaDay");

	private final String code;

	private ViewType(String code)
	{
		this.code = code;
	}

	public static ViewType forCode(String code)
	{
		for (ViewType type : values())
		{
			if (type.code.equals(code))
				return type;
		}
		throw new IllegalStateException("Invalid view type code: " + code);
	}


}

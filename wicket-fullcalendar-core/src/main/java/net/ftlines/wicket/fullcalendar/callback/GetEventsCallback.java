package net.ftlines.wicket.fullcalendar.callback;

import java.util.Date;

import net.ftlines.wicket.fullcalendar.EventProvider;
import net.ftlines.wicket.fullcalendar.EventSource;

import org.apache.wicket.Request;
import org.apache.wicket.request.target.basic.StringRequestTarget;
import org.apache.wicket.util.collections.MicroMap;


public class GetEventsCallback extends AbstractCallback
{
	private static final String SOURCE_ID = "sid";

	public String getUrl(EventSource source)
	{
		return getUrl(new MicroMap<String, Object>(SOURCE_ID, source.getUuid()));
	}

	@Override
	protected void respond()
	{
		Request r = getCalendar().getRequest();
		String sid = r.getParameter(SOURCE_ID);
		Date start = new Date(Long.valueOf(r.getParameter("start")) * 1000);
		Date end = new Date(Long.valueOf(r.getParameter("end")) * 1000);

		EventSource source = getCalendar().getEventManager().getEventSource(sid);
		EventProvider provider = source.getEventProvider();
		String response = getCalendar().toJson(provider.getEvents(start, end));
		getCalendar().getRequestCycle().setRequestTarget(new StringRequestTarget(response));
	}


}

package net.ftlines.wicket.fullcalendar;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventProvider extends Serializable {
	Collection<Event> getEvents(Date start, Date end);
	Event getEventForId(String id) throws EventNotFoundException;
}

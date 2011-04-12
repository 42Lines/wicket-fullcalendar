package net.ftlines.web.fullcalendar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface EventProvider extends Serializable {
	List<Event> getEvents(Date start, Date end);
}

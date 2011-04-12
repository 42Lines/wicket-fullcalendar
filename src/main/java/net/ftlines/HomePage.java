package net.ftlines;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.ftlines.web.fullcalendar.Config;
import net.ftlines.web.fullcalendar.Event;
import net.ftlines.web.fullcalendar.EventProvider;
import net.ftlines.web.fullcalendar.EventSource;
import net.ftlines.web.fullcalendar.FullCalendar;
import net.ftlines.web.fullcalendar.selector.EventSourceSelector;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.time.Duration;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	public HomePage(final PageParameters parameters) {

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Config config = new Config();
		config.setSelectable(true);
		config.setSelectHelper(false);

		EventSource reservations = new EventSource();
		reservations.setTitle("Reservations");
		reservations
				.setEventsProvider(new RandomEventsProvider("Reservation "));
		reservations.setEditable(true);
		reservations.setBackgroundColor("#63BA68");
		reservations.setBorderColor("#63BA68");
		config.add(reservations);

		EventSource downtimes = new EventSource();
		downtimes.setTitle("Maintenance");
		downtimes.setBackgroundColor("#B1ADAC");
		downtimes.setBorderColor("#B1ADAC");
		downtimes.setEventsProvider(new RandomEventsProvider("Maintenance "));
		config.add(downtimes);

		EventSource other = new EventSource();
		other.setTitle("Other Lab Reservations");
		other.setBackgroundColor("#E6CC7F");
		other.setBorderColor("#E6CC7F");
		other.setEventsProvider(new RandomEventsProvider("Other Reservations "));
		config.add(other);

		config.getHeader().setLeft("prev,next today");
		config.getHeader().setCenter("title");
		config.getHeader().setRight("month,agendaWeek,agendaDay");

		config.setLoading("function(bool) { if (bool) $(\"#loading\").show(); else $(\"#loading\").hide(); }");
		// config.setEventDrop("function(event, delta) {alert(event.title+' was moved '+delta+' days\\n' +'(should probably update your database)');}");

		FullCalendar calendar = new FullCalendar("cal", config) {
			@Override
			protected void onSelect(Date start, Date end, boolean allDay,
					AjaxRequestTarget target) {
				info("Selected region: " + start + " - " + end + " / allDay: "
						+ allDay);
				target.addComponent(feedback);
			}

			@Override
			protected boolean onEventDrop(String eventId, String sourceId,
					int dayDelta, int minuteDelta, boolean allDay,
					AjaxRequestTarget target) {
				info("Event drop. eventId: " + eventId + " sourceId: "
						+ sourceId + " dayDelta: " + dayDelta
						+ " minuteDelta: " + minuteDelta + " allDay: " + allDay);
				target.addComponent(feedback);
				return false;
			}

			@Override
			protected boolean onEventResize(String eventId, String sourceId,
					int dayDelta, int minuteDelta, AjaxRequestTarget target) {
				info("Event resized. eventId: " + eventId + " sourceId: "
						+ sourceId + " dayDelta: " + dayDelta
						+ " minuteDelta: " + minuteDelta);
				target.addComponent(feedback);
				return false;
			}
		};
		calendar.setMarkupId("calendar");
		add(calendar);
		add(new EventSourceSelector("selector", calendar));
	}

	private static class RandomEventsProvider implements EventProvider {
		private final String title;

		public RandomEventsProvider(String title) {
			this.title = title;
		}

		@Override
		public List<Event> getEvents(Date start, Date end) {
			List<Event> events = new ArrayList<Event>();
			SecureRandom random = new SecureRandom();

			Duration duration = Duration.valueOf(end.getTime()
					- start.getTime());

			for (int j = 0; j < 1; j++) {
				for (int i = 0; i < duration.days() + 1; i++) {
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(start);

					calendar.add(Calendar.DAY_OF_YEAR, i);

					calendar.set(Calendar.HOUR_OF_DAY, 6 + random.nextInt(10));

					Event event = new Event();
					event.setId("" + (j * duration.days() + i));
					event.setTitle(title + (1 + i));
					event.setStart(calendar.getTime());
					calendar.add(Calendar.HOUR_OF_DAY, random.nextInt(8));
					event.setEnd(calendar.getTime());

					events.add(event);
				}
			}
			return events;
		}

	}

}

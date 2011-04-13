/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package net.ftlines;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import net.ftlines.wicket.fullcalendar.Config;
import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventNotFoundException;
import net.ftlines.wicket.fullcalendar.EventProvider;
import net.ftlines.wicket.fullcalendar.EventSource;
import net.ftlines.wicket.fullcalendar.FullCalendar;
import net.ftlines.wicket.fullcalendar.selector.EventSourceSelector;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.time.Duration;

public class HomePage extends WebPage
{

	public HomePage(final PageParameters parameters)
	{

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Config config = new Config();
		config.setSelectable(true);
		config.setSelectHelper(false);

		EventSource reservations = new EventSource();
		reservations.setTitle("Reservations");
		reservations.setEventsProvider(new RandomEventsProvider("Reservation "));
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
		other.setTitle("Other Reservations");
		other.setBackgroundColor("#E6CC7F");
		other.setBorderColor("#E6CC7F");
		other.setEventsProvider(new RandomEventsProvider("Other Reservations "));
		config.add(other);

		config.getHeader().setLeft("prev,next today");
		config.getHeader().setCenter("title");
		config.getHeader().setRight("month,agendaWeek,agendaDay");

		config.setLoading("function(bool) { if (bool) $(\"#loading\").show(); else $(\"#loading\").hide(); }");

		FullCalendar calendar = new FullCalendar("cal", config)
		{
			@Override
			protected void onSelect(Date start, Date end, boolean allDay, AjaxRequestTarget target)
			{
				info("Selected region: " + start + " - " + end + " / allDay: " + allDay);
				target.addComponent(feedback);
			}

			@Override
			protected boolean onEventDrop(EventSource source, Event event, int dayDelta, int minuteDelta,
				boolean allDay, AjaxRequestTarget target)
			{
				info("Event drop. eventId: " + event.getId() + " sourceId: " + source.getUuid() + " dayDelta: " +
					dayDelta + " minuteDelta: " + minuteDelta + " allDay: " + allDay);
				target.addComponent(feedback);
				return false;
			}

			@Override
			protected boolean onEventResize(EventSource source, Event event, int dayDelta, int minuteDelta,
				AjaxRequestTarget target)
			{
				info("Event resized. eventId: " + event.getId() + " sourceId: " + source.getUuid() + " dayDelta: " +
					dayDelta + " minuteDelta: " + minuteDelta);
				target.addComponent(feedback);
				return false;
			}
		};
		calendar.setMarkupId("calendar");
		add(calendar);
		add(new EventSourceSelector("selector", calendar));
	}

	private static class RandomEventsProvider implements EventProvider
	{
		Map<Integer, Event> events = new HashMap<Integer, Event>();

		private final String title;

		public RandomEventsProvider(String title)
		{
			this.title = title;
		}

		@Override
		public Collection<Event> getEvents(Date start, Date end)
		{
			events.clear();
			SecureRandom random = new SecureRandom();

			Duration duration = Duration.valueOf(end.getTime() - start.getTime());

			for (int j = 0; j < 1; j++)
			{
				for (int i = 0; i < duration.days() + 1; i++)
				{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(start);

					calendar.add(Calendar.DAY_OF_YEAR, i);

					calendar.set(Calendar.HOUR_OF_DAY, 6 + random.nextInt(10));

					Event event = new Event();
					int id = (int)(j * duration.days() + i);
					event.setId("" + id);
					event.setTitle(title + (1 + i));
					event.setStart(calendar.getTime());
					calendar.add(Calendar.HOUR_OF_DAY, random.nextInt(8));
					event.setEnd(calendar.getTime());

					events.put(id, event);
				}
			}
			return events.values();
		}

		@Override
		public Event getEventForId(String id) throws EventNotFoundException
		{
			Integer idd = Integer.valueOf(id);
			Event event = events.get(idd);
			if (event != null)
			{
				return event;
			}
			throw new EventNotFoundException("Event with id: " + id + " not found");
		}

	}

}

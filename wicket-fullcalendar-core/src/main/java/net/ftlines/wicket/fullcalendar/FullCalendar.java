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

package net.ftlines.wicket.fullcalendar;

import java.util.Date;
import java.util.UUID;

import net.ftlines.wicket.fullcalendar.callback.DateRangeSelectedCallback;
import net.ftlines.wicket.fullcalendar.callback.EventClickedCallback;
import net.ftlines.wicket.fullcalendar.callback.EventDroppedCallback;
import net.ftlines.wicket.fullcalendar.callback.EventResizedCallback;
import net.ftlines.wicket.fullcalendar.callback.GetEventsCallback;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.string.Strings;

public class FullCalendar extends AbstractFullCalendar
{

	private final Config config;
	private EventDroppedCallback eventDropped;
	private EventResizedCallback eventResized;
	private GetEventsCallback getEvents;
	private DateRangeSelectedCallback dateRangeSelected;
	private EventClickedCallback eventClicked;

	public FullCalendar(String id, Config config)
	{
		super(id);
		this.config = config;
	}

	public Config getConfig()
	{
		return config;
	}

	public EventManager getEventManager()
	{
		return new EventManager(this);
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		setupCallbacks();
	}

	private void setupCallbacks()
	{
		getEvents = new GetEventsCallback();
		add(getEvents);
		for (EventSource source : config.getEventSources())
		{
			source.setUuid(UUID.randomUUID().toString());
			source.setUrl(getEvents.getUrl(source));
		}

		if (Strings.isEmpty(config.getEventClick()))
		{
			add(eventClicked = new EventClickedCallback()
			{
				@Override
				protected void onClicked(EventSource source, Event event, AjaxRequestTarget target,
					CalendarResponse response)
				{
					onEventClicked(source, event, target, response);
				}
			});
			config.setEventClick(eventClicked.getHandlerScript());
		}

		if (Strings.isEmpty(config.getSelect()))
		{
			add(dateRangeSelected = new DateRangeSelectedCallback()
			{
				@Override
				protected void onSelect(Date start, Date end, boolean allDay, AjaxRequestTarget target, CalendarResponse response)
				{
					FullCalendar.this.onDateRangeSelected(start, end, allDay, target, response);
				}
			});
			config.setSelect(dateRangeSelected.getHandlerScript());
		}

		if (Strings.isEmpty(config.getEventDrop()))
		{
			add(eventDropped = new EventDroppedCallback()
			{

				@Override
				protected boolean onEventDropped(EventSource source, Event event, int dayDelta, int minuteDelta,
					boolean allDay, AjaxRequestTarget target, CalendarResponse response)
				{
					return FullCalendar.this.onEventDropped(source, event, dayDelta, minuteDelta, allDay, target, response);
				}
			});
			config.setEventDrop(eventDropped.getHandlerScript());
		}

		if (Strings.isEmpty(config.getEventResize()))
		{
			add(eventResized = new EventResizedCallback()
			{

				@Override
				protected boolean onEventResized(EventSource source, Event event, int dayDelta, int minuteDelta,
					AjaxRequestTarget target, CalendarResponse response)
				{
					return FullCalendar.this.onEventResized(source, event, dayDelta, minuteDelta, target, response);
				}

			});

			config.setEventResize(eventResized.getHandlerScript());
		}

	}


	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		String configuration = "$(\"#" + getMarkupId() + "\").fullCalendarExt(";
		configuration += Json.toJson(config);
		configuration += ");";

		response.renderOnLoadJavascript(configuration);
	}

	protected boolean onEventDropped(EventSource source, Event event, int dayDelta, int minuteDelta, boolean allDay,
		AjaxRequestTarget target, CalendarResponse response)
	{
		return false;
	}

	protected boolean onEventResized(EventSource source, Event event, int dayDelta, int minuteDelta,
		AjaxRequestTarget target, CalendarResponse response)
	{
		return false;
	}

	protected void onDateRangeSelected(Date start, Date end, boolean allDay, AjaxRequestTarget target,
		CalendarResponse response)
	{

	}

	protected void onEventClicked(EventSource source, Event event, AjaxRequestTarget target, CalendarResponse response)
	{

	}

}

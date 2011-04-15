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

package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.CalendarResponse;
import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class EventDroppedCallback extends AbstractAjaxCallbackWithClientsideRevert
	implements
		CallbackWithHandler
{
	@Override
	protected String configureCallbackScript(String script, String urlTail)
	{
		return script.replace(urlTail, "&eventId='+event.id+'&sourceId='+event.source.data." + EventSource.Const.UUID +
			"+'&dayDelta='+dayDelta+'&minuteDelta='+minuteDelta+'&allDay='+allDay+'");
	}

	@Override
	public String getHandlerScript()
	{
		return "function(event, dayDelta, minuteDelta, allDay, revertFunc) { " + getCallbackScript(true) + "}";
	}

	@Override
	protected boolean onEvent(AjaxRequestTarget target)
	{
		Request r = getCalendar().getRequest();
		String eventId = r.getParameter("eventId");
		String sourceId = r.getParameter("sourceId");

		EventSource source = getCalendar().getEventManager().getEventSource(sourceId);
		Event event = source.getEventProvider().getEventForId(eventId);

		int dayDelta = Integer.valueOf(r.getParameter("dayDelta"));
		int minuteDelta = Integer.valueOf(r.getParameter("minuteDelta"));
		boolean allDay = Boolean.valueOf(r.getParameter("allDay"));

		return onEventDropped(source, event, dayDelta, minuteDelta, allDay, target, new CalendarResponse(getCalendar(),
			target));
	}

	protected abstract boolean onEventDropped(EventSource source, Event event, int dayDelta, int minuteDelta,
		boolean allDay, AjaxRequestTarget target, CalendarResponse response);

	@Override
	protected String getRevertScript()
	{
		return "revertFunc();";
	}

}

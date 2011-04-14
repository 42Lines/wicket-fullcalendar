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

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class EventClickedCallback extends AbstractAjaxCallback implements CallbackWithHandler
{
	@Override
	protected String configureCallbackScript(String script, String urlTail)
	{
		return script.replace(urlTail, "&eventId='+event.id+'&sourceId='+event.source.data." + EventSource.Const.UUID +
			"+'");
	}

	@Override
	public String getHandlerScript()
	{
		return "function(event) { " + getCallbackScript(true) + "}";
	}


	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request r = getCalendar().getRequest();
		String eventId = r.getParameter("eventId");
		String sourceId = r.getParameter("sourceId");

		EventSource source = getCalendar().getEventManager().getEventSource(sourceId);
		Event event = source.getEventProvider().getEventForId(eventId);

		onClicked(source, event, target);
	}


	protected abstract void onClicked(EventSource source, Event event, AjaxRequestTarget target);


}

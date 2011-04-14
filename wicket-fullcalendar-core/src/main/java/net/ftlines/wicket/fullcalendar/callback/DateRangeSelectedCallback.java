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

import java.util.Date;

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class DateRangeSelectedCallback extends AbstractAjaxCallback implements CallbackWithHandler
{
	@Override
	protected String configureCallbackScript(String script, String urlTail)
	{
		return script.replace(urlTail,
			"&startDate='+startDate.getTime()+'&endDate='+endDate.getTime()+'&allDay='+allDay+'");
	}

	@Override
	public String getHandlerScript()
	{
		return "function(startDate, endDate, allDay) { " + getCallbackScript(true) + "}";
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request r = getCalendar().getRequest();
		Date start = new Date(Long.valueOf(r.getParameter("startDate")));
		Date end = new Date(Long.valueOf(r.getParameter("endDate")));
		boolean allDay = Boolean.valueOf(r.getParameter("allDay"));
		onSelect(start, end, allDay, target);

	}

	protected abstract void onSelect(Date start, Date end, boolean allDay, AjaxRequestTarget target);

}

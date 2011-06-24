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

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class DateRangeSelectedCallback extends AbstractAjaxCallback implements CallbackWithHandler
{
	private final boolean ignoreTimezone;

	/**
	 * If <var>ignoreTimezone</var> is {@code true}, then the remote client's time zone will be
	 * ignored when determining the selected date range, resulting in a range with the selected
	 * start and end values, but in the server's time zone.
	 * 
	 * @param ignoreTimezone
	 *            whether or not to ignore the remote client's time zone when determining the
	 *            selected date range
	 */
	public DateRangeSelectedCallback(final boolean ignoreTimezone)
	{
		this.ignoreTimezone = ignoreTimezone;
	}
	
	@Override
	protected String configureCallbackScript(String script, String urlTail)
	{
		return script.replace(urlTail,
			"&timezoneOffset='+startDate.getTimezoneOffset()+'&startDate='+startDate.getTime()+'&endDate='+endDate.getTime()+'&allDay='+allDay+'");
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
		DateTime start = new DateTime(Long.valueOf(r.getParameter("startDate")));
		DateTime end = new DateTime(Long.valueOf(r.getParameter("endDate")));
		if (ignoreTimezone)
		{
			// Convert to same DateTime in local time zone.
			int remoteOffset = -Integer.valueOf(r.getParameter("timezoneOffset"));
			int localOffset = DateTimeZone.getDefault().getOffset(null) / 60000;
			int minutesAdjustment = remoteOffset - localOffset;
			start = start.plusMinutes(minutesAdjustment);
			end = end.plusMinutes(minutesAdjustment);
		}
		boolean allDay = Boolean.valueOf(r.getParameter("allDay"));
		onSelect(new SelectedRange(start, end, allDay), new CalendarResponse(getCalendar(), target));

	}

	protected abstract void onSelect(SelectedRange range, CalendarResponse response);

}

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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;

import net.ftlines.wicket.fullcalendar.CalendarResponse;

public abstract class DateRangeSelectedCallback extends AbstractAjaxCallback implements CallbackWithHandler {
	private final boolean ignoreTimezone;

	/**
	 * If <var>ignoreTimezone</var> is {@code true}, then the remote client\"s time zone will be ignored when
	 * determining the selected date range, resulting in a range with the selected start and end values, but in the
	 * server\"s time zone.
	 * 
	 * @param ignoreTimezone
	 *            whether or not to ignore the remote client\"s time zone when determining the selected date range
	 */
	public DateRangeSelectedCallback(final boolean ignoreTimezone) {
		this.ignoreTimezone = ignoreTimezone;
	}

	@Override
	protected String configureCallbackScript(String script, String urlTail) {
		return script.replace(urlTail,
			"&timezoneOffset=\"+startDate.getTimezoneOffset()+\"&startDate=\"+startDate.getTime()+\"&endDate=\"+endDate.getTime()+\"&allDay=\"+allDay+\"");
	}

	@Override
	public String getHandlerScript() {
		return "function(startDate, endDate, allDay) { " + getCallbackScript() + "}";
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		Request r = getCalendar().getRequest();
		LocalDateTime start = LocalDateTime.ofInstant(
			Instant.ofEpochMilli(
				Long.parseLong(r.getRequestParameters().getParameterValue("startDate").toOptionalString())),
			ZoneId.systemDefault());
		// LocalDateTime.parse(r.getRequestParameters().getParameterValue("startDate").toOptionalString(), fmt);
		LocalDateTime end = LocalDateTime.ofInstant(
			Instant
				.ofEpochMilli(Long.parseLong(r.getRequestParameters().getParameterValue("endDate").toOptionalString())),
			ZoneId.systemDefault());
		// LocalDateTime.parse(r.getRequestParameters().getParameterValue("endDate").toOptionalString(), fmt);

		if (ignoreTimezone) {
			// Convert to same DateTime in local time zone.
			int remoteOffset = -r.getRequestParameters().getParameterValue("timezoneOffset").toInt();
			int localOffset = OffsetDateTime.now().getOffset().getTotalSeconds() / 60000;
			int minutesAdjustment = remoteOffset - localOffset;
			start = start.plusMinutes(minutesAdjustment);
			end = end.plusMinutes(minutesAdjustment);
		}
		boolean allDay = r.getRequestParameters().getParameterValue("allDay").toBoolean();
		onSelect(new SelectedRange(start, end, allDay), new CalendarResponse(getCalendar(), target));

	}

	protected abstract void onSelect(SelectedRange range, CalendarResponse response);

}

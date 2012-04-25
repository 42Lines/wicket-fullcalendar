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
import net.ftlines.wicket.fullcalendar.ViewType;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


/**
 * A base callback that passes back calendar's starting date
 * 
 * @author igor
 * 
 */
public abstract class ViewDisplayCallback extends AbstractAjaxCallback implements CallbackWithHandler
{
	@Override
	protected String configureCallbackScript(String script, String urlTail)
	{
		return script.replace(
			urlTail,
			"&view=\"+v.name+\"&start=\"+fullCalendarExtIsoDate(v.start)+\"&end=\"+fullCalendarExtIsoDate(v.end)+\"&visibleStart=\"+fullCalendarExtIsoDate(v.visStart)+\"&visibleEnd=\"+fullCalendarExtIsoDate(v.visEnd)+\"");
	}

	@Override
	public String getHandlerScript()
	{
		return String.format("function(v) {%s;}", getCallbackScript());
	}


	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request r = target.getPage().getRequest();
		ViewType type = ViewType.forCode(r.getRequestParameters().getParameterValue("view").toString());
		DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser().withZone(DateTimeZone.UTC);
		DateMidnight start = fmt.parseDateTime(r.getRequestParameters().getParameterValue("start").toString())
			.toDateMidnight();
		DateMidnight end = fmt.parseDateTime(r.getRequestParameters().getParameterValue("end").toString())
			.toDateMidnight();
		DateMidnight visibleStart = fmt.parseDateTime(
			r.getRequestParameters().getParameterValue("visibleStart").toString()).toDateMidnight();
		DateMidnight visibleEnd = fmt.parseDateTime(r.getRequestParameters().getParameterValue("visibleEnd").toString())
			.toDateMidnight();
		View view = new View(type, start, end, visibleStart, visibleEnd);
		CalendarResponse response = new CalendarResponse(getCalendar(), target);
		onViewDisplayed(view, response);
	}

	protected abstract void onViewDisplayed(View view, CalendarResponse response);
}

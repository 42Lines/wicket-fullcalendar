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

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;


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
			"&view='+v.name+'&start='+v.start.getTime()+'&end='+v.end.getTime()+'&visibleStart='+v.visStart.getTime()+'&visibleEnd='+v.visEnd.getTime()+'");
	}

	@Override
	public String getHandlerScript()
	{
		return String.format("function(v) { console.log('view', v); %s; console.log('ajax call made');}", getCallbackScript(true));
	}

	
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request r=target.getPage().getRequest();
		
		ViewType type=ViewType.forCode(r.getParameter("view"));
		DateMidnight start = new DateTime(Long.valueOf(r.getParameter("start"))).toDateMidnight();
		DateMidnight end = new DateTime(Long.valueOf(r.getParameter("end"))).toDateMidnight();
		DateMidnight visibleStart = new DateTime(Long.valueOf(r.getParameter("visibleStart"))).toDateMidnight();
		DateMidnight visibleEnd = new DateTime(Long.valueOf(r.getParameter("visibleEnd"))).toDateMidnight();
		View view=new View(type, start, end, visibleStart, visibleEnd);
		CalendarResponse response=new CalendarResponse(getCalendar(), target);
		onViewDisplayed(view, response);
	}

	protected abstract void onViewDisplayed(View view, CalendarResponse response);
}

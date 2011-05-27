/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ftlines.wicket.fullcalendar.callback;

import net.ftlines.wicket.fullcalendar.ViewType;

import org.joda.time.DateMidnight;

public class View
{
	private ViewType type;
	private DateMidnight start;
	private DateMidnight end;
	private DateMidnight visibleStart;
	private DateMidnight visibleEnd;

	public View(ViewType type, DateMidnight start, DateMidnight end, DateMidnight visibleStart, DateMidnight visibleEnd)
	{
		this.type = type;
		this.start = start;
		this.end = end;
		this.visibleStart = visibleStart;
		this.visibleEnd = visibleEnd;
	}

	public ViewType getType()
	{
		return type;
	}

	public DateMidnight getStart()
	{
		return start;
	}

	public DateMidnight getEnd()
	{
		return end;
	}

	public DateMidnight getVisibleStart()
	{
		return visibleStart;
	}

	public DateMidnight getVisibleEnd()
	{
		return visibleEnd;
	}


}

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

import net.ftlines.wicket.fullcalendar.Event;
import net.ftlines.wicket.fullcalendar.EventSource;

class AbstractShiftedEventParam extends AbstractEventParam {
	private final int daysDelta;
	private final int minutesDelta;

	public AbstractShiftedEventParam(EventSource source, Event event, int hoursDelta, int minutesDelta) {
		super(source, event);
		this.daysDelta = hoursDelta;
		this.minutesDelta = minutesDelta;
	}

	public int getDaysDelta() {
		return daysDelta;
	}

	public int getMinutesDelta() {
		return minutesDelta;
	}

	public LocalDateTime getNewStartTime() {
		return shift(getEvent().getStart());
	}

	public LocalDateTime getNewEndTime() {
		return shift(getEvent().getEnd());
	}

	public Period getDelta() {
		return Period.ofDays(daysDelta).plus(Duration.ofMinutes(minutesDelta));
	}

	private LocalDateTime shift(LocalDateTime start) {
		return start.plusDays(daysDelta).plusMinutes(minutesDelta);
	}

}

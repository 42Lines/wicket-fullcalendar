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

import java.time.LocalDateTime;

import net.ftlines.wicket.fullcalendar.ViewType;

public class View {
	private ViewType type;
	private LocalDateTime start;
	private LocalDateTime end;
	private LocalDateTime visibleStart;
	private LocalDateTime visibleEnd;

	public View(ViewType type, LocalDateTime start, LocalDateTime end, LocalDateTime visibleStart,
		LocalDateTime visibleEnd) {
		this.type = type;
		this.start = start;
		this.end = end;
		this.visibleStart = visibleStart;
		this.visibleEnd = visibleEnd;
	}

	public ViewType getType() {
		return type;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public LocalDateTime getVisibleStart() {
		return visibleStart;
	}

	public LocalDateTime getVisibleEnd() {
		return visibleEnd;
	}

}

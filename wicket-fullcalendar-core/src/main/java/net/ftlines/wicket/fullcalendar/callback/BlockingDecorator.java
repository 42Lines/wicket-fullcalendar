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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;

/**
 * Prevents multiple clicks while ajax request is executing. We keep a variable that is set to {@code true} while the
 * request is running and to any other value when its done.
 * 
 * @author igor
 */
public class BlockingDecorator extends AjaxCallListener {

	private static String clean(String str) {
		return str != null ? str.replaceAll("[^0-9a-zA-Z]", "") : null;
	}

	private String var(Component component) {
		if (!component.getOutputMarkupId()) {
			throw new IllegalStateException();
		}
		// Calling clean() ensures that no Javascript operators (+, -, etc) are accidentally
		// used in the markup id, which breaks this functionality.
		String id = clean(component.getMarkupId());
		return "window.wicketblock" + id;

	}

	@Override
	public CharSequence getPrecondition(Component component) {
		// before we allow the request we check if one is already running by checking the var

		// return false if the var is set to true (request running)
		return var(component) + "!==true;";
	}

	@Override
	public CharSequence getBeforeSendHandler(Component component) {
		// just before we start the request, we set the var to true
		return var(component) + "=true;";
	}

	@Override
	public CharSequence getCompleteHandler(Component component) {
		// when the request is complete we set the var to false
		return var(component) + "=false;";
	}
}

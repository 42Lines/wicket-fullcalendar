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

package net.ftlines.wicket.fullcalendar;

import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.util.string.Strings;

abstract class AbstractFullCalendar extends WebComponent implements
		IHeaderContributor {

	private final CallbackBehavior ajax;

	public AbstractFullCalendar(String id) {
		super(id);
		ajax = new CallbackBehavior();
		add(ajax);
	}

	private static final ResourceReference CSS = new ResourceReference(
			AbstractFullCalendar.class, "res/fullcalendar.css");
	private static final ResourceReference JS = new ResourceReference(
			AbstractFullCalendar.class, "res/fullcalendar.js");
	private static final ResourceReference JS_EXT = new ResourceReference(
			AbstractFullCalendar.class, "res/fullcalendar.ext.js");
	private static final ResourceReference JS_MIN = new ResourceReference(
			AbstractFullCalendar.class, "res/fullcalendar.min.js");

	@Override
	public void renderHead(IHeaderResponse response) {

		response.renderCSSReference(CSS);
		if (Application.DEPLOYMENT.equals(Application.get()
				.getConfigurationType())) {
			response.renderJavascriptReference(JS_MIN);
		} else {
			response.renderJavascriptReference(JS);
		}
		response.renderJavascriptReference(JS_EXT);
	}

	protected String getActionUrl(String action, Map<String, Object> parameters) {
		String url = action;
		if (parameters != null) {
			for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
				url += "&" + parameter.getKey() + "=" + parameter.getValue();
			}
		}
		return ajax.getCallbackUrl().toString().replace("<URL>", url);
	}

	protected String getActionScript(String action,
			Map<String, Object> parameters, String placeholder) {
		String url = action;
		if (parameters != null) {
			for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
				url += "&" + parameter.getKey() + "=" + parameter.getValue();
			}
		}
		if (!Strings.isEmpty(placeholder)) {
			url += placeholder;
		}
		return ajax.getCallbackScript().toString().replace("<URL>", url);
	}

	protected abstract void onAction(String action, Request request,
			AjaxRequestTarget target);

	/**
	 * Behavior used to construct ajax callbacks to the calendar
	 * 
	 * @author igor
	 * 
	 */
	private class CallbackBehavior extends AbstractDefaultAjaxBehavior {
		@Override
		public CharSequence getCallbackUrl(boolean onlyTargetActivePage) {
			return super.getCallbackUrl(onlyTargetActivePage)
					+ "&fcx:action=<URL>";
		}

		@Override
		protected void respond(AjaxRequestTarget target) {
			final String action = getRequest().getParameter("fcx:action");
			onAction(action, getRequest(), target);
		}

		@Override
		public CharSequence getCallbackScript() {
			return super.getCallbackScript(true);
		}
	}
}

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

import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;

abstract class AbstractAjaxCallbackWithClientsideRevert extends
		AbstractAjaxCallback {
	private String uuid = "u" + UUID.randomUUID().toString().replace("-", "");

	protected abstract String getRevertScript();

	protected abstract boolean onEvent(AjaxRequestTarget target);

	private String getRevertScriptBlock() {
		return "{" + getRevertScript() + ";}";
	}

	@Override
	protected final void respond(AjaxRequestTarget target) {
		boolean result = onEvent(target);
		target.prependJavaScript(String.format("$.data(document, '%s', %s);",
				uuid, String.valueOf(result)));
	}

	@Override
	protected final CharSequence getFailureScript() {
		return getRevertScriptBlock();
	}

	@Override
	protected final CharSequence getSuccessScript() {
		return String
				.format("if (false===$.data(document, '%s')) %s $.removeData(document, '%s');",
						uuid, getRevertScriptBlock(), uuid);
	}

}

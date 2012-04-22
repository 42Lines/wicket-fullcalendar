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

import net.ftlines.wicket.fullcalendar.FullCalendar;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;

abstract class AbstractAjaxCallback extends AbstractDefaultAjaxBehavior
{

	private static final String PLACEHOLDER = "<PLACEHOLDER>";

	protected abstract String configureCallbackScript(String script, String urlTail);

	@Override
	public final CharSequence getCallbackScript()
	{
		return configureCallbackScript(super.getCallbackScript().toString(), PLACEHOLDER);
	}

	@Override
	public final CharSequence getCallbackUrl()
	{
		return super.getCallbackUrl() + PLACEHOLDER;
	}

	protected FullCalendar getCalendar()
	{
		return (FullCalendar)getComponent();
	}

	/*
	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new BlockingDecorator(this);
	}
	*/
	
}

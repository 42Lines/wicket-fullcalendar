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

public enum AjaxConcurrency {
	/** queue and execute all callbacks */
	QUEUE,
	/** drop callbacks if a callback of the same type is already executing */
	DROP_PER_CALLBACK,
	/** drop any callback if one is already executing */
	DROP;
}

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

import java.io.Serializable;

public class Header implements Serializable {

	private String left;
	private String center;
	private String right;

	public String getLeft() {
		return left;
	}

	public Header setLeft(String left) {
		this.left = left;
		return this;
	}

	public String getCenter() {
		return center;
	}

	public Header setCenter(String center) {
		this.center = center;
		return this;
	}

	public String getRight() {
		return right;
	}

	public Header setRight(String right) {
		this.right = right;
		return this;
	}

}

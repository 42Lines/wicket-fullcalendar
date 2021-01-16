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

package net.ftlines.wicket.fullcalendar;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

@JsonSerialize
@JsonInclude(value = Include.NON_NULL)
class Json {
	private Json() {

	}

	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper(new MappingJsonFactory());
		SimpleModule module = new SimpleModule("fullcalendar", new Version(1, 0, 0, null, null, null));
		module.addSerializer(new DateTimeSerializer());
		module.addSerializer(new LocalTimeSerializer());
		mapper.registerModule(module);

		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Error encoding object: " + object + " into JSON string", e);
		}
		return json;
	}

	public static class DateTimeSerializer extends JsonSerializer<LocalDateTime> {
		@Override
		public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
			jgen.writeString(value.toString());
		}

		@Override
		public Class<LocalDateTime> handledType() {
			return LocalDateTime.class;
		}

	}

	public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
		@Override
		public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
			jgen.writeString(value.toString());
		}

		@Override
		public Class<LocalTime> handledType() {
			return LocalTime.class;
		}

	}

	public static class Script implements Serializable {
		private String code;

		public Script(String value) {
			this.code = value;
		}

		public String getDeclaration() {
			return code;
		}

	}

}

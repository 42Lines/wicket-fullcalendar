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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;

public class Json {
	private static final String CODE_START = "ycaBQm9T8UPT---";
	private static final String CODE_END = "---1IRJDEPhW";

	private Json() {

	}

	private static class MyJsonFactory extends MappingJsonFactory {
		@Override
		public JsonGenerator createJsonGenerator(Writer out) throws IOException {
			return super.createJsonGenerator(out).useDefaultPrettyPrinter();
		}

		@Override
		public JsonGenerator createJsonGenerator(File f, JsonEncoding enc)
				throws IOException {
			return super.createJsonGenerator(f, enc).useDefaultPrettyPrinter();
		}

		@Override
		public JsonGenerator createJsonGenerator(OutputStream out,
				JsonEncoding enc) throws IOException {
			return super.createJsonGenerator(out, enc)
					.useDefaultPrettyPrinter();
		}
	}

	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper(new MyJsonFactory());
		SimpleModule module = new SimpleModule("fullcalendar", new Version(1,
				0, 0, null));
		module.addSerializer(new DateSerializer());
		mapper.registerModule(module);
		mapper.getSerializationConfig().setSerializationInclusion(
				Inclusion.NON_NULL);

		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	// private static Gson newGson() {
	// return new GsonBuilder()
	// .registerTypeAdapter(Date.class,
	// new UnixTimestampDateSerializer())
	// .registerTypeAdapter(Script.class, new CodeSerializer())
	// .excludeFieldsWithoutExposeAnnotation().setPrettyPrinting()
	// .create();
	// }
	//
	// private static class CodeSerializer implements JsonSerializer<Script> {
	//
	// @Override
	// public JsonElement serialize(Script src, Type typeOfSrc,
	// JsonSerializationContext context) {
	//
	// return new JsonPrimitive(CODE_START + src.getDeclaration()
	// + CODE_END);
	// }
	// }
	//
	// private static class UnixTimestampDateSerializer implements
	// JsonSerializer<Date> {
	//
	// @Override
	// public JsonElement serialize(Date src, Type typeOfSrc,
	// JsonSerializationContext context) {
	//
	// return new JsonPrimitive(src.getTime() / 1000L);
	// }
	//
	// }

	public static class DateSerializer extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException,
				JsonProcessingException {

			jgen.writeNumber(value.getTime() / 1000);
		}

		@Override
		public Class<Date> handledType() {
			return Date.class;
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

package net.ftlines.web.fullcalendar;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.Request;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.target.basic.StringRequestTarget;
import org.apache.wicket.util.collections.MicroMap;
import org.apache.wicket.util.string.Strings;

public class FullCalendar extends AbstractFullCalendar {

	private final Config config;
	private final EventDropCallback eventDrop;
	private final EventResizeCallback eventResize;

	public FullCalendar(String id, Config config) {
		super(id);
		this.config = config;
		add(eventDrop = new EventDropCallback());
		add(eventResize = new EventResizeCallback());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		setupEventSources();
		setupCallbacks();
	}

	private void setupCallbacks() {
		if (Strings.isEmpty(config.getSelect())) {
			config.setSelect("function(startDate, endDate, allDay, jsEvent, view) { "
					+ getActionScript(Const.ACT_ON_SELECT, null,
							"<PLACEHOLDER>")
							.replace(
									"<PLACEHOLDER>",
									"&startDate='+startDate.getTime()+'&endDate='+endDate.getTime()+'&allDay='+allDay+'")
					+ "}");
		}

		if (Strings.isEmpty(config.getEventDrop())) {
			config.setEventDrop("function(event, dayDelta, minuteDelta, allDay, revertFunc) { "
					+ eventDrop.getCallbackScript(true) + "}");
		}

		if (Strings.isEmpty(config.getEventResize())) {
			config.setEventResize("function(event, dayDelta, minuteDelta,  revertFunc) { "
					+ eventResize.getCallbackScript(true) + "}");
		}

	}

	private void setupEventSources() {
		for (EventSource source : config.getEventSources()) {
			source.setUuid(UUID.randomUUID().toString());
			Map<String, Object> params = new MicroMap<String, Object>(
					EventSource.Const.UUID, source.getUuid());
			source.setUrl(getActionUrl(Const.ACT_GET_EVENTS, params));
		}
	}

	@Override
	protected void onAction(String action, Request request,
			AjaxRequestTarget target) {

		if (Const.ACT_GET_EVENTS.equals(action)) {
			onGetEvents(request);
			return;
		} else if (Const.ACT_ON_SELECT.equals(action)) {
			onSelect(request, target);
			return;
		}
	}

	private void onSelect(Request request, AjaxRequestTarget target) {
		Date start = new Date(Long.valueOf(request.getParameter("startDate")));
		Date end = new Date(Long.valueOf(request.getParameter("endDate")));
		boolean allDay = Boolean.valueOf(request.getParameter("allDay"));
		onSelect(start, end, allDay, target);
	}

	protected void onSelect(Date start, Date end, boolean allDay,
			AjaxRequestTarget target) {

	}

	private void onGetEvents(Request request) {
		Date start = new Date(
				Long.valueOf(request.getParameter("start")) * 1000);
		Date end = new Date(Long.valueOf(request.getParameter("end")) * 1000);
		String sourceId = request.getParameter(EventSource.Const.UUID);

		EventProvider provider = null;
		for (EventSource source : config.getEventSources()) {
			if (source.getUuid().equals(sourceId)) {
				provider = source.getEventProvider();
			}
		}

		String response = Json.toJson(provider.getEvents(start, end));
		getRequestCycle().setRequestTarget(new StringRequestTarget(response));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		String configuration = "$(\"#" + getMarkupId() + "\").fullCalendarExt(";
		configuration += Json.toJson(config);
		configuration += ");";

		response.renderOnLoadJavascript(configuration);
	}

	protected boolean onEventDrop(String eventId, String sourceId,
			int dayDelta, int minuteDelta, boolean allDay,
			AjaxRequestTarget target) {
		return false;
	}

	protected boolean onEventResize(String eventId, String sourceId,
			int dayDelta, int minuteDelta, AjaxRequestTarget target) {
		return false;
	}

	public Config getConfig() {
		return config;
	}

	private static class Const {
		private static final String ACT_GET_EVENTS = "fcxGetEvents";
		private static final String ACT_ON_SELECT = "fcxOnSelect";
	}

	private class EventDropCallback extends AbstractDefaultAjaxBehavior {

		private String uuid = "uuid"
				+ UUID.randomUUID().toString().replace("-", "");

		@Override
		public CharSequence getCallbackUrl(boolean onlyTargetActivePage) {
			return super.getCallbackUrl(onlyTargetActivePage) + "<PLACEHOLDER>";
		}

		@Override
		protected CharSequence getCallbackScript(boolean onlyTargetActivePage) {
			String script = super.getCallbackScript(onlyTargetActivePage)
					.toString();
			script = script
					.replace(
							"<PLACEHOLDER>",
							"&eventId='+event.id+'&sourceId='+event.source.data."
									+ EventSource.Const.UUID
									+ "+'&dayDelta='+dayDelta+'&minuteDelta='+minuteDelta+'&allDay='+allDay+'");

			script = "console.log(event);" + script;

			return script;
		}

		@Override
		protected void respond(AjaxRequestTarget target) {
			Request r = getRequest();
			String eventId = r.getParameter("eventId");
			String sourceId = r.getParameter("sourceId");
			int dayDelta = Integer.valueOf(r.getParameter("dayDelta"));
			int minuteDelta = Integer.valueOf(r.getParameter("minuteDelta"));
			boolean allDay = Boolean.valueOf(r.getParameter("allDay"));
			boolean result = onEventDrop(eventId, sourceId, dayDelta,
					minuteDelta, allDay, target);
			target.prependJavascript("$.data(document, \"" + uuid + "\", "
					+ result + ");");
		}

		@Override
		protected CharSequence getFailureScript() {
			return "revertFunc();";
		}

		@Override
		protected CharSequence getSuccessScript() {
			return ("if (false===$.data(document, \"" + uuid
					+ "\")) {revertFunc();} $.removeData(document, \"" + uuid + "\");");
		}

	}

	private class EventResizeCallback extends AbstractDefaultAjaxBehavior {
		private String uuid = "uuid"
				+ UUID.randomUUID().toString().replace("-", "");

		@Override
		public CharSequence getCallbackUrl(boolean onlyTargetActivePage) {
			return super.getCallbackUrl(onlyTargetActivePage) + "<PLACEHOLDER>";
		}

		@Override
		protected CharSequence getCallbackScript(boolean onlyTargetActivePage) {
			String script = super.getCallbackScript(onlyTargetActivePage)
					.toString();
			script = script
					.replace(
							"<PLACEHOLDER>",
							"&eventId='+event.id+'&sourceId='+event.source.data."
									+ EventSource.Const.UUID
									+ "+'&dayDelta='+dayDelta+'&minuteDelta='+minuteDelta+'");

			script = "console.log(event);" + script;

			return script;
		}

		@Override
		protected void respond(AjaxRequestTarget target) {
			Request r = getRequest();
			String eventId = r.getParameter("eventId");
			String sourceId = r.getParameter("sourceId");
			int dayDelta = Integer.valueOf(r.getParameter("dayDelta"));
			int minuteDelta = Integer.valueOf(r.getParameter("minuteDelta"));
			boolean result = onEventResize(eventId, sourceId, dayDelta,
					minuteDelta, target);
			target.prependJavascript("$.data(document, \"" + uuid + "\", "
					+ result + ");");
		}

		@Override
		protected CharSequence getFailureScript() {
			return "revertFunc();";
		}

		@Override
		protected CharSequence getSuccessScript() {
			return ("if (false===$.data(document, \"" + uuid
					+ "\")) {revertFunc();} $.removeData(document, \"" + uuid + "\");");
		}

	}

}

package net.ftlines.wicket.fullcalendar.callback;

import java.util.Map;

import net.ftlines.wicket.fullcalendar.FullCalendar;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.IBehaviorListener;

public abstract class AbstractCallback extends AbstractBehavior implements IBehaviorListener
{
	private FullCalendar calendar;

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		this.calendar = (FullCalendar)component;
	}

	protected final String getUrl(Map<String, Object> parameters)
	{
		String url = calendar.urlFor((IBehavior)this, IBehaviorListener.INTERFACE).toString();
		if (parameters != null)
		{
			for (Map.Entry<String, Object> parameter : parameters.entrySet())
			{
				url += "&" + parameter.getKey() + "=" + parameter.getValue();
			}
		}
		return url;
	}

	@Override
	public final void onRequest()
	{
		respond();
	}

	protected abstract void respond();

	protected final FullCalendar getCalendar()
	{
		return (FullCalendar)calendar;
	}

}

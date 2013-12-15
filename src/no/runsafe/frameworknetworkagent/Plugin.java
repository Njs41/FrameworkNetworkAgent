package no.runsafe.frameworknetworkagent;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.features.Events;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(Events.class);
		addComponent(AgentHook.class);
	}
}

package no.runsafe.frameworknetworkagent;

import no.runsafe.framework.RunsafePlugin;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void pluginSetup()
	{
		addComponent(AgentHook.class);
	}
}

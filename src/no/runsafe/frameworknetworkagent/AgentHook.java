package no.runsafe.frameworknetworkagent;

import no.runsafe.framework.api.IOutput;

public class AgentHook
{
	public AgentHook(IOutput output)
	{
		output.info("This is a test.");
	}
}

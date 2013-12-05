package no.runsafe.frameworknetworkagent;

import net.minecraft.server.v1_6_R3.DedicatedServer;
import net.minecraft.server.v1_6_R3.MinecraftServer;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.internal.reflection.ReflectionHelper;
import no.runsafe.framework.minecraft.networking.RunsafeServerConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AgentHook implements IPluginEnabled
{
	public AgentHook(IOutput output)
	{
		this.output = output;
	}

	@Override
	public void OnPluginEnabled()
	{
		output.write("Hooker loaded.") ;
		DedicatedServer server = (DedicatedServer) MinecraftServer.getServer();
		String serverIP = server.getServerIp();
		InetAddress address;

		try
		{
			address = (serverIP.length() > 0 ? InetAddress.getByName(serverIP) : null);
			output.info(serverIP) ;
		}
		catch (UnknownHostException exception)
		{
			output.logException(exception);
			return;
		}

		output.write("Stopping current server thread.");
		server.ag().a(); // Make the current server connection terminate it's thread.

		try
		{
			ReflectionHelper.setField(server, "s", new RunsafeServerConnection(server, address, server.I(), output));
		}
		catch (Exception exception)
		{
			output.logException(exception);
		}
	}

	private final IOutput output;
}

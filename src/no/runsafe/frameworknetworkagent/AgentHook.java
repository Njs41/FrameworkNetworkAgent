package no.runsafe.frameworknetworkagent;

import net.minecraft.server.v1_6_R3.DedicatedServer;
import net.minecraft.server.v1_6_R3.MinecraftServer;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.internal.networking.RunsafeServerConnection;
import no.runsafe.framework.internal.reflection.ReflectionHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AgentHook implements IPluginEnabled
{
	public AgentHook(IConsole output)
	{
		this.console = output;
	}

	@Override
	public void OnPluginEnabled()
	{
		DedicatedServer server = (DedicatedServer) MinecraftServer.getServer();
		String serverIP = server.getServerIp();
		InetAddress address;

		try
		{
			address = (serverIP.length() > 0 ? InetAddress.getByName(serverIP) : null);
			console.logInformation(serverIP);
		}
		catch (UnknownHostException exception)
		{
			console.logException(exception);
			return;
		}

		server.ag().a(); // Terminate the current network thread.

		try
		{
			RunsafeServerConnection connection = new RunsafeServerConnection(server, address, server.I(), console);
			ReflectionHelper.setField(server, "s", connection); // Give the connection to the server.
		}
		catch (Exception exception)
		{
			console.logException(exception);
		}
	}

	private final IConsole console;
}

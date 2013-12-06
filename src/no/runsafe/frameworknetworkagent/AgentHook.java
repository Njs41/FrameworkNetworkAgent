package no.runsafe.frameworknetworkagent;

import net.minecraft.server.v1_6_R3.DedicatedServer;
import net.minecraft.server.v1_6_R3.MinecraftServer;
import net.minecraft.server.v1_6_R3.ServerConnection;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.internal.networking.RunsafeServerConnectionThread;
import no.runsafe.framework.internal.reflection.ReflectionHelper;

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
		DedicatedServer server = (DedicatedServer) MinecraftServer.getServer();
		String serverIP = server.getServerIp();
		InetAddress address;

		try
		{
			address = (serverIP.length() > 0 ? InetAddress.getByName(serverIP) : null);
			output.logInformation(serverIP);
		}
		catch (UnknownHostException exception)
		{
			output.logException(exception);
			return;
		}

		ServerConnection connection = server.ag();
		connection.a(); // Terminate the current thread.
		connection.a = true; // This needs to be true or the thread will exit.

		try
		{
			RunsafeServerConnectionThread newThread = new RunsafeServerConnectionThread(connection, address, server.I(), output);
			newThread.start(); // Start the thread before providing it to the connection.
			ReflectionHelper.setField(connection, "b", newThread); // Give the started thread to the connection.
		}
		catch (Exception exception)
		{
			output.logException(exception);
		}
	}

	private final IOutput output;
}

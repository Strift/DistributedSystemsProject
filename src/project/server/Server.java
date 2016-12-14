package project.server;

import java.net.InetAddress;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import project.LocateGlobalRegistry;
import project.remote.Sorter;

public class Server {

	private static final String SERVICE_NAME = "Sorter";

	public static void main(String[] args) throws Exception {
		// check the name of the local machine (two methods)
		System.out.println("Server: running on host " + InetAddress.getLocalHost());
		System.out.println("Server: hostname property " + System.getProperty("java.rmi.server.hostname"));
		
		// instanciate the remote object
		Sorter sorter = new SimpleSorter();
		System.out.println("Server: instanciated SimpleSorter");
		
		// create a skeleton and a stub for that remote object
		Sorter stub = (Sorter) UnicastRemoteObject.exportObject(sorter, 0);
		System.out.println("Server: generated skeleton and stub");
		
		// register the remote object's stub in the registry
		Registry registry = LocateGlobalRegistry.getRegistry();
		registry.rebind(SERVICE_NAME, stub);
		System.out.println("Server: registered remote object's stub");
		
		// Print list
		//Server.printRegistryList(registry.list());
		
		// main terminates here, but the JVM still runs because of the skeleton
		System.out.println("Server: ready");
	}
	
	public static void printRegistryList(String[] list) {
		System.out.println("New registry list contains: ");
		for (final String str : list) {
			System.out.print(str + ", ");
		}
		System.out.print("\n");
	}
}

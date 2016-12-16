package project.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import project.LocateGlobalRegistry;
import project.services.Sorter;

public class Server {

	private static final String SERVICE_NAME = "Sorter";
	
	private static int instances = 0;

	public static void main(String[] args) throws Exception {
		Server server1 = new Server();
		Server server2 = new Server();
		Server server3 = new Server();
		try {
			server1.start();
			server2.start();
			server3.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String name;
	
	public Server() {
		Server.instances++;
		this.name = "Server" + Server.instances;
	}
	
	public void start() throws UnknownHostException, RemoteException, NotBoundException {
		// check the name of the local machine (two methods)
		System.out.println(this.name + ": running on host " + InetAddress.getLocalHost());
		System.out.println(this.name + ": hostname property " + System.getProperty("java.rmi.server.hostname"));
		
		// instanciate the services object
		Sorter sorter = new SimpleSorter();
		System.out.println(this.name + ": instanciated SimpleSorter");
		
		// create a skeleton and a stub for that services object
		Sorter stub = (Sorter) UnicastRemoteObject.exportObject(sorter, 0);
		System.out.println(this.name + ": generated skeleton and stub");
		
		// register the services object's stub in the registry
		Registry registry = LocateGlobalRegistry.getRegistry();
		registry.rebind(/*this.name + "." + */SERVICE_NAME, stub);
		System.out.println(this.name + ": registered services object's stub");
		
		// main terminates here, but the JVM still runs because of the skeleton
		System.out.println(this.name + ": ready");
	}
}

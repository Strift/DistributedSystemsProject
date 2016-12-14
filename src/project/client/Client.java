package project.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

import project.remote.Sorter;
import project.LocateGlobalRegistry;

public class Client {
	
	private static String SERVICE_NAME = "Sorter";
	private static String SERVICE_HOST = "localhost";
	
	public static void main(String[] args) {
		try {
			// locate the registry that runs on the remote object's server
		    Registry registry = LocateGlobalRegistry.getRegistry(SERVICE_HOST);
		    System.out.println("Client: retrieved registry");
		    // retrieve the stub of the remote object by its name
		    Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME);
		    System.out.println("Client: retrieved Sorter stub " + sorter.toString());
		    // call the remote object to perform sort
		    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
		    System.out.println("Client: sending " + list);
		    list = sorter.sort(list);
		    System.out.println("Client: received " + list);
		    // main terminates here
		    System.out.println("Client: exiting");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}

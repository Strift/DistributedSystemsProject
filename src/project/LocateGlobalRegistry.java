package project;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LocateGlobalRegistry {
	
	final static String SERVICE_NAME = "GlobalRegistry";
	final static String DEFAULT_HOST = "localhost";

	public static void createRegistry(int registryPort) throws RemoteException {
		// Registry creation
		Registry registry = LocateRegistry.createRegistry(registryPort);
	    // Skeleton and stub of the GlobalRegistry
	    GlobalRegistry globalRegistry = new GlobalRegistry();
	    Registry stub = (Registry) UnicastRemoteObject.exportObject(globalRegistry, 0);
	    // Register the GlobalRegistry's stub in the registry
	    registry.rebind(SERVICE_NAME, stub);
	}

	public static Registry getRegistry(String host) throws AccessException, RemoteException, NotBoundException {
		return (Registry) LocateRegistry.getRegistry().lookup(SERVICE_NAME);
	}

	public static Registry getRegistry() throws AccessException, RemoteException, NotBoundException {
		return LocateGlobalRegistry.getRegistry(DEFAULT_HOST);
	}

}

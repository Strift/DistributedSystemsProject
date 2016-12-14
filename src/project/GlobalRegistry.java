package project;

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public class GlobalRegistry implements java.rmi.registry.Registry {

	// default listening port for the registry
	private static final int REGISTRY_PORT = 1099;
	
	private HashMap<String, Remote> remotes = new HashMap<String, Remote>();
	
	public static synchronized void main(String[] args) throws Exception {
	    System.out.println("Registry: running on host " + InetAddress.getLocalHost());
	    // create the registry on the local machine, on the default port number
	    LocateGlobalRegistry.createRegistry(REGISTRY_PORT);
	    System.out.println("Registry: listening on port " + REGISTRY_PORT);
	    // block forever
	    GlobalRegistry.class.wait();
	    System.out.println("Registry: exiting (should not happen)");
	}
	
	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
		Remote remote = remotes.get(name);
		if (remote == null) {
			throw new NotBoundException();
		}
		return remote;
	}
	
	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
		if (remotes.containsKey(name)) {
			throw new AlreadyBoundException();
		} else {
			remotes.put(name, obj);
		}
	}
	
	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		if (remotes.containsKey(name)) {
			remotes.remove(name);
		} else {
			throw new NotBoundException();
		}
	}
	
	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		remotes.put(name, obj);
	}
	
	@Override
	public String[] list() throws RemoteException, AccessException {
		return (String[]) remotes.keySet().toArray(new String[remotes.size()]);
	}

}


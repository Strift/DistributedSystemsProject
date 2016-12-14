package project;

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

public class GlobalRegistry implements java.rmi.registry.Registry {

	// default listening port for the registry
	private static final int REGISTRY_PORT = 1099;
	
	private HashMap<String, LinkedList<Remote>> remotes = new HashMap<String, LinkedList<Remote>>();
	
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
		LinkedList<Remote> remoteQueue = this.remotes.get(name);
		if (remoteQueue == null) {
			throw new NotBoundException();
		}
		// Retrieves the first element of the queue then put it at the back
		Remote remote = remoteQueue.pop();
		remoteQueue.add(remote);
		return remote;
	}
	
	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
		// Create list if it does not exist
		if (remotes.containsKey(name) == false) {
			remotes.put(name, new LinkedList<Remote>());
		}
		LinkedList<Remote> remoteQueue = remotes.get(name);
		// Throw exception if already bound
		if (remoteQueue.contains(obj)) {
			throw new AlreadyBoundException();
		} else {
			remoteQueue.addLast(obj);
		}
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
	}	

	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		// Create list if it does not exist
		if (remotes.containsKey(name) == false) {
			remotes.put(name, new LinkedList<Remote>());
		}
		remotes.get(name).add(obj);
	}
	
	@Override
	public String[] list() throws RemoteException, AccessException {
		return (String[]) remotes.keySet().toArray(new String[remotes.size()]);
	}

}


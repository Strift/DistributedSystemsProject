package project;

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class GlobalRegistry implements java.rmi.registry.Registry {

	// default listening port for the registry
	private static final int REGISTRY_PORT = 1099;

	private RemoteHandler remoteHandler;
	
	public static synchronized void main(String[] args) throws Exception {
	    System.out.println("Registry: running on host " + InetAddress.getLocalHost());
	    // create the registry on the local machine, on the default port number
	    LocateGlobalRegistry.createRegistry(REGISTRY_PORT);
	    System.out.println("Registry: listening on port " + REGISTRY_PORT);
	    // block forever
	    GlobalRegistry.class.wait();
	    System.out.println("Registry: exiting (should not happen)");
	}

	public GlobalRegistry(RemoteHandler handler) {
		this.remoteHandler = handler;
	}

	public GlobalRegistry() {
		this.remoteHandler = new RoundRobinRemoteHandler();
	}
	
	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
		return this.remoteHandler.getRemote(name);
	}
	
	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
		this.remoteHandler.addRemote(name, obj, false);
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
	}	

	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		this.remoteHandler.addRemote(name, obj);
	}
	
	@Override
	public String[] list() throws RemoteException, AccessException {
		return this.remoteHandler.getRemotesList();
	}

}


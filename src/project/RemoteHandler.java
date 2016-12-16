package project;

import java.rmi.*;

/**
 * Created by strift on 12/16/16.
 */
public interface RemoteHandler {

    public void addRemote(String name, Remote remote, Boolean force) throws RemoteException, AlreadyBoundException, AccessException;

    public void addRemote(String name, Remote remote) throws RemoteException, AccessException;

    public Remote getRemote(String name) throws RemoteException, NotBoundException, AccessException;

    public String[] getRemotesList();
}

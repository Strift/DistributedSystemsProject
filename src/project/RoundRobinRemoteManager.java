package project;

import java.rmi.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by strift on 12/16/16.
 */
public class RoundRobinRemoteManager implements RemoteManager {

    private HashMap<String, LinkedList<Remote>> remotes = new HashMap<>();

    @Override
    public void addRemote(String name, Remote remote, Boolean force) throws RemoteException, AlreadyBoundException, AccessException {
        // Create list if it does not exist
        if (remotes.containsKey(name) == false) {
            remotes.put(name, new LinkedList<Remote>());
        }
        // Add element to the queue
        LinkedList<Remote> remoteQueue = remotes.get(name);
        if (remoteQueue.contains(remote) && force == false) {
            throw new AlreadyBoundException();
        } else {
            remoteQueue.addLast(remote);
        }
    }

    @Override
    public void addRemote(String name, Remote remote) throws RemoteException, AccessException {
        // Create list if it does not exist
        if (remotes.containsKey(name) == false) {
            remotes.put(name, new LinkedList<Remote>());
        }
        remotes.get(name).addLast(remote);
    }

    @Override
    public Remote getRemote(String name) throws RemoteException, NotBoundException, AccessException {
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
    public String[] getRemotesList() {
        return (String[]) remotes.keySet().toArray(new String[remotes.size()]);
    }
}

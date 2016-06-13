package main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import remote.Subserver;
//java -Djava.security.policy=java.policy main.Server 
public class Server {
    static List<Subserver> servers = new ArrayList<>();

    public static void main(String[] args) {
    	System.setSecurityManager(new SecurityManager());
        try {
            Registry registry = LocateRegistry.createRegistry(22222);
            servers.add(new Subserver(registry, "first"));
            servers.add(new Subserver(registry, "second"));
            servers.add(new Subserver(registry, "third"));
            servers.add(new Subserver(registry, "fourth"));
            while(true) { 
				Thread.sleep(100);
			}
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }
}
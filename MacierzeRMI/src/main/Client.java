package main;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import remote.Calculable;

public class Client {

	private Calculable[] netConn = new Calculable[4];

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try {
			Remote remoteObject1 = Naming.lookup("rmi://127.0.0.1:22222/first");
			Remote remoteObject2 = Naming.lookup("rmi://127.0.0.1:22222/second");
			Remote remoteObject3 = Naming.lookup("rmi://127.0.0.1:22222/third");
			Remote remoteObject4 = Naming.lookup("rmi://127.0.0.1:22222/fourth");
			
			Client client = new Client();
			client.netConn[0] = (Calculable) remoteObject1;
			client.netConn[1] = (Calculable) remoteObject2;
			client.netConn[2] = (Calculable) remoteObject3;
			client.netConn[3] = (Calculable) remoteObject4;
			System.out.println("Connected");
		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public Calculable[] getNetConn() {
		return netConn;
	}
}

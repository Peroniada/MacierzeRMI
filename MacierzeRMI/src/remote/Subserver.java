package remote;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Subserver extends UnicastRemoteObject implements Calculable {

	private static final long serialVersionUID = 1L;

	Registry registry;

	public Subserver(Registry reg, String name) throws RemoteException {
		super();
		this.registry = reg;
		reg.rebind(name, this);
		System.out.println("Serwer " + name + " has started");
	}

	@Override
	public synchronized int multiplyVectors(int[] vector1, int[] vector2) throws RemoteException {

		if (vector1.length != vector2.length) {
			return 0;
		}
		int tmp = 0;
		for (int i = 0; i < vector1.length; i++) {
			tmp = tmp + vector1[i] * vector2[i];
		}
		return tmp;
	}

	@Override
	public synchronized int[] concatVectors(int[] vector1, int[] vector2) throws RemoteException {
		try {
			if (vector1.length != vector2.length) {
				throw new Exception("Nierowne wektory");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] newVector = new int[vector1.length];
		for(int i = 0; i< newVector.length; i++) {
			newVector[i] = vector1[i] + vector2[i];
		}
		return newVector;
	}


	
}

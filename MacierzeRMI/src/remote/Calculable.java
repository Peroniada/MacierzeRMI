package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculable extends Remote	{

	public int multiplyVectors(int[] vector1, int[] vector2 )throws RemoteException;
}

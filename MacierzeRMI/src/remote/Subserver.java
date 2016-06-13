package remote;


import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Subserver extends UnicastRemoteObject implements Calculable {
	
    private static final long serialVersionUID = 1L;
    
    Registry registry;
    public Subserver(Registry reg, String name) throws RemoteException {
        super();
        this.registry=reg;
        reg.rebind(name, this);
        System.out.println("Serwer "+name+" has started");
    }
 
    @Override
    public int multiplyVectors(int[] vector1, int[] vector2) throws RemoteException {
    	
        if(vector1.length != vector2.length) {
            return 0;
        }
        int tmp = 0;
        for(int i=0; i<vector1.length; i++) {
            tmp = tmp + vector1[i]*vector2[i];
        }
        return tmp;
    }
}

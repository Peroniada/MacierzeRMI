package main;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import remote.Calculable;
//java -Djava.security.policy=java.policy main.Client
public class Client {

	private Calculable[] netConn = new Calculable[4];

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		Client client = new Client();
		try {
			Remote remoteObject1 = Naming.lookup("rmi://127.0.0.1:22222/first");
			Remote remoteObject2 = Naming.lookup("rmi://127.0.0.1:22222/second");
			Remote remoteObject3 = Naming.lookup("rmi://127.0.0.1:22222/third");
			Remote remoteObject4 = Naming.lookup("rmi://127.0.0.1:22222/fourth");
			
			client.netConn[0] = (Calculable) remoteObject1;
			client.netConn[1] = (Calculable) remoteObject2;
			client.netConn[2] = (Calculable) remoteObject3;
			client.netConn[3] = (Calculable) remoteObject4;
			System.out.println("Connected");
		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
		
		int[][] MatrixA = new int[60][20];
		int[][] MatrixB = new int[20][40];
		int[][] MatrixC = new int[60][60];
		int[][] MatrixD = new int[60][40];
		int[][] MatrixE = new int[40][60];
		client.fill(MatrixA, 1);
		client.fill(MatrixB, 1);
		client.fill(MatrixC, 1);
		client.fill(MatrixD, 1);
		client.fill(MatrixE, 1);
		
		int[][] MatrixW = new int[MatrixA.length][MatrixE[0].length];
		
		MatrixW = client.multiply(client.concat(client.multiply(MatrixA, MatrixB), 
				client.multiply(MatrixC, MatrixD)), MatrixE);
		client.printMatrix(MatrixW);
	}
	
	public int[] getColumn(int[][] array, int index){
//		printMatrix(array);
	    int[] column = new int[array.length];
//	    System.out.println(column.length);
	    for(int i=0; i<column.length; i++){
	       column[i] = array[i][index];
	    }
//	    for(int i=0; i<column.length; i++){
//		      System.out.print(column[i]+ " ");
//	    }
	    return column;
	}
	
	public int[][] multiply(int[][] mat1, int[][] mat2) {
		int[][] newMatrix = new int[mat1.length][mat2[0].length];
		for(int i = 0; i < newMatrix.length; i++) {
			for(int j = 0; j < newMatrix[i].length-3; j+=4) {
				try {
					newMatrix[i][j] = netConn[0].multiplyVectors(mat1[i], getColumn(mat2,j));
					newMatrix[i][j+1] = netConn[1].multiplyVectors(mat1[i], getColumn(mat2,j+1));
					newMatrix[i][j+2] = netConn[2].multiplyVectors(mat1[i], getColumn(mat2,j+2));
					newMatrix[i][j+3] = netConn[3].multiplyVectors(mat1[i], getColumn(mat2,j+3));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		return newMatrix;
	}
	
	public int[][] concat(int[][] mat1, int[][] mat2) {
		int[][] newMatrix = new int[mat1.length][mat1[0].length];
		for(int i=0; i<newMatrix.length-3; i+=4) {
			try {
				newMatrix[i] = netConn[0].concatVectors(mat1[i], mat2[i]);
				newMatrix[i+1] = netConn[1].concatVectors(mat1[i+1], mat2[i+1]);
				newMatrix[i+2] = netConn[2].concatVectors(mat1[i+2], mat2[i+2]);
				newMatrix[i+3] = netConn[3].concatVectors(mat1[i+3], mat2[i+3]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newMatrix;
	}
	
	public void printMatrix(int[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				System.out.printf("%5d", matrix[i][j]);
			}
			System.out.println();
		}
	}

	public void fill(int[][] matrix, int value) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = value;
			}
		}
	}
}

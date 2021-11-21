/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author ASUS
 */
public class Servidor {

    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL, nombreHost;
        try {
            //Se pide el número de puerto
            /*System.out.println("Introduce el nombre de host:");
            nombreHost = br.readLine();
            System.out.println("Introduce el numero de puerto:");
            portNum = br.readLine();*/
            nombreHost = "localhost";
            portNum = "1500";
            int RMIPortNum = Integer.parseInt(portNum);
            //Se crea un registro(en caso de que no exista previamente)
            startRegistry(RMIPortNum);
            P2PImpl exportedObj = new P2PImpl();
            registryURL = "rmi://" + nombreHost + ":" + RMIPortNum + "/p2p";
            //Se enlaza el objeto con el registro
            Naming.rebind(registryURL, exportedObj);
            listRegistry(registryURL);
            //Imprimimos que el servidor está listo para su funcionamiento
            System.out.println("Servidor P2P listo");
        }//Si hay alguna excepción se recoge y se sale
        catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    //Función que crea un registro en un puerto en caso de que no exista previamente
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
        } catch (RemoteException e) {//Si no hay ningún registro en el puerto
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            registry.list();
        }
    }

    //Función que imprime el nombre de los objetos registrados
    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (String name : names) {
            System.out.println(name);
        }
    }
}

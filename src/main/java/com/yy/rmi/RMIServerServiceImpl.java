package com.yy.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServerServiceImpl extends UnicastRemoteObject implements RMIServerService {
    @Override
    public  List<String> get(String idx) throws RemoteException {
        List<String> list = new ArrayList<>();
        list.add("123");
        return list;
    }
    protected RMIServerServiceImpl() throws RemoteException { }

    public static void main(String[] args) throws Exception{
        try {
        RMIServerService service = new RMIServerServiceImpl();
        LocateRegistry.createRegistry(2005);
        Naming.rebind( "//zhouty:2005/RMIServerService" , service);
        System.out.println("Ready to do time");
    } catch(Exception e) {
        e.printStackTrace();
    }

    }
}

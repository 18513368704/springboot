package com.yy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServerService extends Remote {
    /**
     * 根据指标取对应监控结果
     * @param idx  指标名称
     * @return
     */
    List<String> get(String idx) throws RemoteException;
}

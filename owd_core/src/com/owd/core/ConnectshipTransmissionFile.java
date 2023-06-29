package com.owd.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 1/29/16.
 */
public class ConnectshipTransmissionFile {
    String symbol;
    String name;
    int sequence;
    int status;
    List<String> files = new ArrayList<String>();

    public ConnectshipTransmissionFile(String symbol, String name, int sequence, int status) {
        this.symbol = symbol;
        this.name = name;
        this.sequence = sequence;
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ConnectshipTransmissionFile{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", sequence=" + sequence +
                ", status=" + status +
                ", files=" + files +
                '}';
    }
}

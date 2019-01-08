package com.black.java;

public class PortalMain {
    public static void main(String[] args){
        PortalMain portalMain = new PortalMain();
        portalMain.test();
    }

    private void test(){
        printLog("test...0...");
        printLog("test...1...");
    }

    private void printLog(String logContent){
        System.out.println(logContent);
    }
}

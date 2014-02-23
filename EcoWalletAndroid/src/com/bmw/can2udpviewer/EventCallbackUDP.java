package com.bmw.can2udpviewer;

public interface EventCallbackUDP {
    public void EventReceivedUDP(final UDPData udpData);
    
    public void sessionStarted();
    public void sessionStopped();
}
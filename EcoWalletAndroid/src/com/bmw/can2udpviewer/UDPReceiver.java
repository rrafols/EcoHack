package com.bmw.can2udpviewer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

import android.util.Log;


public class UDPReceiver extends Thread {
    private int m_Port;
    private Boolean m_Terminate = false;
    private EventCallbackUDP m_EventCallback;
    private DatagramSocket m_DatagramSocket = null;
    private boolean sessionStarted = false;


    public UDPReceiver(int Port, EventCallbackUDP eventCallback) {
        super();
        
        m_Port = Port;
        m_EventCallback = eventCallback;
    }

    public void Terminate() {
        this.m_Terminate = true;
        if(m_DatagramSocket != null) m_DatagramSocket.close();
    }

    @Override
    public void run() {
        super.run();

        while (!m_Terminate) {

            try {
                if(m_DatagramSocket == null) {
                	m_DatagramSocket = new DatagramSocket(m_Port);
                }

                byte[] buffer = new byte[128];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                while (true) {
                    m_DatagramSocket.receive(packet);

                    if (packet.getLength() == 50) {

                        ByteBuffer bb = ByteBuffer.wrap(packet.getData());
                        UDPData ud =  MainActivity.udpData;

                        int offset = 0;
                        ud.Magic.read(bb,offset); offset+=4;

                        ud.Version.read(bb, offset); offset+=1;
                        ud.Alive.read(bb, offset); offset+=1;
                        ud.VehicleType.read(bb, offset); offset+=1;
                        ud.MileageTotal.read(bb, offset); offset+=4;
                        ud.SpeedWheelRear.read(bb, offset); offset+=2;
                        ud.SpeedWheelFront.read(bb, offset); offset+=2;

                        java.lang.Short dummyShort = bb.getShort(offset); offset+=2;
                        ud.Temperature.setValue(dummyShort);

                        ud.RockerSwitchRightUp.read(bb, offset); offset+=1;
                        ud.RockerSwitchRightDown.read(bb, offset); offset+=1;
                        ud.PushButtonLeftHand.read(bb, offset); offset+=1;
                        ud.PushButtonRightHand.read(bb, offset); offset+=1;
                        ud.CruiseControlOn.read(bb, offset); offset+=1;
                        ud.CruiseControlAccelerate.read(bb, offset); offset+=1;
                        ud.CruiseControlDecelerator.read(bb, offset); offset+=1;
                        ud.IndicatorSwitchLeft.read(bb, offset); offset+=1;
                        ud.IndicatorSwitchRight.read(bb, offset); offset+=1;
                        ud.IndicatorSwitchReset.read(bb, offset); offset+=1;
                        ud.Horn.read(bb, offset); offset+=1;
                        ud.HighBeam.read(bb, offset); offset+=1;
                        ud.RockerSwitchLeftUp.read(bb, offset); offset+=1;
                        ud.RockerSwitchLeftDown.read(bb, offset); offset+=1;
                        ud.MMCLeft.read(bb, offset); offset+=1;
                        ud.MMCRight.read(bb, offset); offset+=1;
                        ud.MMCPosition.read(bb, offset); offset+=1;

                        ud.ReadinessDriving.read(bb, offset); offset+=1; 

                        ud.ChargingCondition.read(bb, offset); offset+=1;  
                        ud.ChargingDurationExpected.read(bb, offset); offset+=2; 
                        ud.ChargingWirePluggedIn.read(bb, offset); offset+=1;   
                        ud.StateOfCharge.read(bb, offset); offset+=1;                
                        ud.Range.read(bb, offset); offset+=1;                       
                        ud.EnergyDischarged.read(bb, offset); offset+=2;


                        ud.EnergyChargedByRecuperation.read(bb, offset); offset+=2;


                        ud.HighVoltageStorageVoltage.read(bb, offset); offset+=2; 
                        ud.HighVoltageStorageCurrent.read(bb, offset); offset+=2; 

                        ud.ECOPoints.read(bb, offset); offset+=1;             

                        m_EventCallback.EventReceivedUDP(ud);  // calls the EventReceived callback function to hand over the raw structure received (you still have to scale/interpret the values according to the specification given)
                    }

                    packet.setLength(buffer.length);
                }
            } catch (Exception e) {
                Log.d("BMW Motorrad", e.toString());
                e.printStackTrace();
                sessionStarted = false;
                m_EventCallback.sessionStopped();
            }
        }
    }
}


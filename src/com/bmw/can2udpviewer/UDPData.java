package com.bmw.can2udpviewer;


import unsigned.uByte;
import unsigned.uInt;
import unsigned.uShort;
import org.apache.commons.lang.mutable.MutableShort;

public class UDPData {
    uInt Magic;
    
    uByte Version;
    uByte  Alive;
    uByte  VehicleType;
    uInt   MileageTotal;
    uShort SpeedWheelRear;
    uShort SpeedWheelFront;
    MutableShort Temperature;

    uByte  RockerSwitchRightUp;
    uByte  RockerSwitchRightDown;
    uByte  PushButtonLeftHand;
    uByte  PushButtonRightHand;
    uByte  CruiseControlOn;
    uByte  CruiseControlAccelerate;
    uByte  CruiseControlDecelerator;
    uByte  IndicatorSwitchLeft;
    uByte  IndicatorSwitchRight;
    uByte  IndicatorSwitchReset;
    uByte  Horn;
    uByte  HighBeam;
    uByte  RockerSwitchLeftUp;
    uByte  RockerSwitchLeftDown;
    uByte  MMCLeft;
    uByte  MMCRight;
    uByte  MMCPosition;

    uByte  ReadinessDriving;

    uByte  ChargingCondition;
    uShort  ChargingDurationExpected;
    uByte  ChargingWirePluggedIn;
    uByte  StateOfCharge;
    uByte  Range;
    uShort  EnergyDischarged;
	
    uShort  EnergyChargedByRecuperation;

    uShort  HighVoltageStorageVoltage;
    uShort  HighVoltageStorageCurrent;

    uByte  ECOPoints;
}

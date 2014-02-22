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
    
    public UDPData() {
    	Magic = new uInt();
    	Version = new uByte();
    	Alive = new uByte();
    	VehicleType = new uByte();
    	MileageTotal = new uInt();
    	SpeedWheelFront = new uShort();
    	SpeedWheelRear = new uShort();
    	Temperature = new MutableShort();
    	
    	RockerSwitchLeftDown = new uByte();
    	RockerSwitchLeftUp = new uByte();
    	RockerSwitchRightDown = new uByte();
    	RockerSwitchRightUp = new uByte();
    	PushButtonLeftHand = new uByte();
    	PushButtonRightHand = new uByte();
    	CruiseControlAccelerate = new uByte();
    	CruiseControlDecelerator = new uByte();
    	CruiseControlOn = new uByte();
    	IndicatorSwitchLeft = new uByte();
    	IndicatorSwitchRight = new uByte();
    	IndicatorSwitchReset = new uByte();
    	Horn = new uByte();
    	HighBeam = new uByte();
    	MMCLeft = new uByte();
    	MMCRight = new uByte();
    	MMCPosition = new uByte();
    	ReadinessDriving = new uByte();

    	ChargingCondition = new uByte();
    	ChargingDurationExpected = new uShort();
    	ChargingWirePluggedIn = new uByte();
    	StateOfCharge = new uByte();
    	Range = new uByte();
    	
    	EnergyDischarged = new uShort();
    	EnergyChargedByRecuperation = new uShort();
    	HighVoltageStorageCurrent = new uShort();
    	HighVoltageStorageVoltage = new uShort();
    	
    	ECOPoints = new uByte();
	}
}

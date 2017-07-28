package com.lge.sdet3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Arrays;

public class TartanGarageTcpServer implements Runnable{
	private static final int SERVER_PORT_NUM = 5050;
	private BufferedWriter bw;
    private BufferedReader br;
    private ServerSocket serverSock;
    private Socket socket;
    
    private GarageState garageState;
    
    private GarageStateUpdatable stateUpdableImpl;
    
	public TartanGarageTcpServer() {
		super();
		garageState = GarageState.getInstance();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("RUN ");
		System.out.println(Thread.currentThread());
		while (true) {
			try {
				if(serverSock == null)
					serverSock = new ServerSocket(SERVER_PORT_NUM);
				socket = serverSock.accept();
				
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				loop();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					if (socket != null){
						socket.close();
						socket = null;
					}
				} catch (IOException ignore) {}
				
				try {
					if (bw != null){
						bw.close();
						bw = null;
					}
				} catch (IOException ignore) {}
				
				try {
					if (br != null){
						br.close();
						br = null;
					}
				} catch (IOException ignore) {}
			}
		}

	}
	
	private void loop(){
		System.out.println("loop() started...");
		String recvStr = null;
		char msgArr[] = new char[256];
		int c;
		int i;
		while (true) {
			try {
				i = 0;
				Arrays.fill(msgArr, '\0');
				while ((c = br.read()) != -1 && i < 256) {
					msgArr[i] = (char) c;
					if (msgArr[i] == '.') {
						break;
					} else {
						i++;
					}
				}
				recvStr = String.valueOf(msgArr);
				if (recvStr != null) {
					if (recvStr.startsWith("GS.")) {
						bw.write(garageState.toString() + "\n");
						bw.flush();
						System.out.println("Recv from App: " + recvStr);
						System.out.println("Sent from Garage: " + garageState.toString());
					} else
					{
						if(recvStr.startsWith("NL")){
							setEntryLed(recvStr);
						}
						/*else if(recvStr.startsWith("NG")){
							
						}
						else if(recvStr.startsWith("XG")){
							
						}*/
						else if(recvStr.startsWith("XL")){
							setExitLed(recvStr);
						}
						else if(recvStr.startsWith("PL")){
							trunOnSpotLed(recvStr);
						}
						
						System.out.println("Recv from App: " + recvStr);
						bw.write("OK.\n");
						bw.flush();
						
						if(stateUpdableImpl != null)
							stateUpdableImpl.updateUi();
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				break;
			}
		}
	}
	
    public void setStateUpdableImpl(GarageStateUpdatable stateUpdableImpl) {
		this.stateUpdableImpl = stateUpdableImpl;
	}
    
    private void setEntryLed(String cmd){
    	if(cmd.substring(cmd.indexOf("=") + 1).startsWith((GarageState.LED_GREEN)))
    		garageState.setEntryLedState(GarageState.LED_GREEN);
    	else if(cmd.substring(cmd.indexOf("=") + 1).startsWith((GarageState.LED_RED)))
    		garageState.setEntryLedState(GarageState.LED_RED);
    	else garageState.setEntryLedState(GarageState.LED_OFF);
    }
    
    private void setExitLed(String cmd){
    	if(cmd.substring(cmd.indexOf("=") + 1).startsWith((GarageState.LED_GREEN)))
    		garageState.setExitLedState(GarageState.LED_GREEN);
    	else if(cmd.substring(cmd.indexOf("=") + 1).startsWith((GarageState.LED_RED)))
    		garageState.setExitLedState(GarageState.LED_RED);
    	else garageState.setExitLedState(GarageState.LED_OFF);
    }
    private void trunOnSpotLed(String cmd){
		if (cmd.substring(cmd.indexOf("1=") + "1=".length()).startsWith(GarageState.LED_SPOT_ON)){
			garageState.setSpotLedState(0, true);
		} else {
			garageState.setSpotLedState(0, false);
		}
		
		if (cmd.substring(cmd.indexOf("2=") + "2=".length()).startsWith(GarageState.LED_SPOT_ON)) {
			garageState.setSpotLedState(1, true);
		} else {
			garageState.setSpotLedState(1, false);
		}
		
		if (cmd.substring(cmd.indexOf("3=") + "3=".length()).startsWith(GarageState.LED_SPOT_ON)) {
			garageState.setSpotLedState(2, true);
		} else {
			garageState.setSpotLedState(2, false);
		}
		
		if (cmd.substring(cmd.indexOf("4=") + "4=".length()).startsWith(GarageState.LED_SPOT_ON)) {
			garageState.setSpotLedState(3, true);
		} else {
			garageState.setSpotLedState(3, false);
		}
    }
    
}

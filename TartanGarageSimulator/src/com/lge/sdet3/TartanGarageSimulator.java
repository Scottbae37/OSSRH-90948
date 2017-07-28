package com.lge.sdet3;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


class TartanGarageSimulator implements GarageStateUpdatable{
	/* UI */
	private JFrame frmGarageMock;
	private JToggleButton tglBtnEntryGate;
	private JToggleButton tglBtnExitGate;
	private JToggleButton tglBtnSpot01;
	private JToggleButton tglBtnSpot02;
	private JToggleButton tglBtnSpot03;
	private JToggleButton tglBtnSpot04;
	private JPanel barEntryGate;
	private JPanel barExitGate;
	private JRadioButton rdBtnSpotLed01;
	private JRadioButton rdBtnSpotLed02;
	private JRadioButton rdBtnSpotLed03;
	private JRadioButton rdBtnSpotLed04;
	
	/* Constants */
	private final String STR_ENTRY_GATE_CAR 	= "Entry detector (Car is detected)";
	private final String STR_ENTRY_GATE_NOCAR 	= "Entry detector (No Car)";
	private final String STR_EXIT_GATE_CAR 		= "Exit detector (Car is detected)";
	private final String STR_EXIT_GATE_NOCAR 	= "Exit detector (No Car)";

	/* Variables */
	static TartanGarageTcpServer tcpServer;
	private GarageState garageState;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UIManager.LookAndFeelInfo looks[];
		looks = UIManager.getInstalledLookAndFeels();
		try
		{
			UIManager.setLookAndFeel(looks[1].getClassName());
		} catch (Exception err)
		{
			err.printStackTrace();
		}

		tcpServer = new TartanGarageTcpServer();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TartanGarageSimulator window = new TartanGarageSimulator();
					window.frmGarageMock.setVisible(true);
					tcpServer.setStateUpdableImpl(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.out.println("My Thread "+ Thread.currentThread());
				
			}
		});
		
		if(EventQueue.isDispatchThread())
			System.out.println("Yes Dispatch");
		else System.out.println("No Dispatch");
		System.out.println("Main Thread : "+Thread.currentThread());
		
		Thread thread = new Thread(tcpServer);
		thread.start();
	}
	
	/**
	 * Create the application.
	 */
	public TartanGarageSimulator() {
		initialize();
		initUiEventListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGarageMock = new JFrame();
		frmGarageMock.setResizable(false);
		frmGarageMock.getContentPane().setBackground(UIManager.getColor("ScrollBar.thumb"));
		frmGarageMock.setTitle("Garage Simulator (ver0.2)");
		frmGarageMock.setBounds(100, 100, 403, 285);
		frmGarageMock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {250, 165, 30};
		gridBagLayout.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmGarageMock.getContentPane().setLayout(gridBagLayout);
		
		barExitGate = new JPanel();
		barExitGate.setBackground(Color.RED);
		GridBagConstraints gbc_barExitGate = new GridBagConstraints();
		gbc_barExitGate.insets = new Insets(7, 10, 7, 10);
		gbc_barExitGate.fill = GridBagConstraints.BOTH;
		gbc_barExitGate.gridx = 0;
		gbc_barExitGate.gridy = 0;
		frmGarageMock.getContentPane().add(barExitGate, gbc_barExitGate);
		
		JLabel lblNewLabel_1 = new JLabel("Bar (Red:Closed, Green:Open)");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		frmGarageMock.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		tglBtnExitGate = new JToggleButton(STR_EXIT_GATE_NOCAR);
		
		GridBagConstraints gbc_tglBtnExitGate = new GridBagConstraints();
		gbc_tglBtnExitGate.fill = GridBagConstraints.BOTH;
		gbc_tglBtnExitGate.insets = new Insets(0, 0, 5, 5);
		gbc_tglBtnExitGate.gridx = 0;
		gbc_tglBtnExitGate.gridy = 1;
		frmGarageMock.getContentPane().add(tglBtnExitGate, gbc_tglBtnExitGate);
		
		rdBtnSpotLed04 = new JRadioButton("");
		GridBagConstraints gbc_rdBtnSpot04 = new GridBagConstraints();
		gbc_rdBtnSpot04.insets = new Insets(0, 0, 5, 0);
		gbc_rdBtnSpot04.gridx = 2;
		gbc_rdBtnSpot04.gridy = 2;
		frmGarageMock.getContentPane().add(rdBtnSpotLed04, gbc_rdBtnSpot04);
		
		rdBtnSpotLed03 = new JRadioButton("");
		GridBagConstraints gbc_rdBtnSpot03 = new GridBagConstraints();
		gbc_rdBtnSpot03.insets = new Insets(0, 0, 5, 0);
		gbc_rdBtnSpot03.gridx = 2;
		gbc_rdBtnSpot03.gridy = 3;
		frmGarageMock.getContentPane().add(rdBtnSpotLed03, gbc_rdBtnSpot03);
		
		rdBtnSpotLed02 = new JRadioButton("");
		GridBagConstraints gbc_rdBtnSpot02 = new GridBagConstraints();
		gbc_rdBtnSpot02.insets = new Insets(0, 0, 5, 0);
		gbc_rdBtnSpot02.gridx = 2;
		gbc_rdBtnSpot02.gridy = 4;
		frmGarageMock.getContentPane().add(rdBtnSpotLed02, gbc_rdBtnSpot02);
		
		rdBtnSpotLed01 = new JRadioButton("");
		GridBagConstraints gbc_rdBtnSpot01 = new GridBagConstraints();
		gbc_rdBtnSpot01.insets = new Insets(0, 0, 5, 0);
		gbc_rdBtnSpot01.gridx = 2;
		gbc_rdBtnSpot01.gridy = 5;
		frmGarageMock.getContentPane().add(rdBtnSpotLed01, gbc_rdBtnSpot01);
		
		barEntryGate = new JPanel();
		barEntryGate.setBackground(Color.RED);
		GridBagConstraints gbc_barEntryGate = new GridBagConstraints();
		gbc_barEntryGate.insets = new Insets(7, 10, 7, 10);
		gbc_barEntryGate.fill = GridBagConstraints.BOTH;
		gbc_barEntryGate.gridx = 0;
		gbc_barEntryGate.gridy = 6;
		frmGarageMock.getContentPane().add(barEntryGate, gbc_barEntryGate);
		
		JLabel lblNewLabel = new JLabel("Bar (Red:Closed, Green:Open)");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 6;
		frmGarageMock.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		tglBtnSpot04 = new JToggleButton("Spot-4");
		GridBagConstraints gbc_tglBtnSpot04 = new GridBagConstraints();
		gbc_tglBtnSpot04.fill = GridBagConstraints.BOTH;
		gbc_tglBtnSpot04.insets = new Insets(0, 0, 5, 5);
		gbc_tglBtnSpot04.gridx = 1;
		gbc_tglBtnSpot04.gridy = 2;
		frmGarageMock.getContentPane().add(tglBtnSpot04, gbc_tglBtnSpot04);
		
		tglBtnSpot03 = new JToggleButton("Spot-3");
		GridBagConstraints gbc_tglBtnSpot03 = new GridBagConstraints();
		gbc_tglBtnSpot03.fill = GridBagConstraints.BOTH;
		gbc_tglBtnSpot03.insets = new Insets(0, 0, 5, 5);
		gbc_tglBtnSpot03.gridx = 1;
		gbc_tglBtnSpot03.gridy = 3;
		frmGarageMock.getContentPane().add(tglBtnSpot03, gbc_tglBtnSpot03);
		
		tglBtnSpot02 = new JToggleButton("Spot-2");
		GridBagConstraints gbc_tglBtnSpot02 = new GridBagConstraints();
		gbc_tglBtnSpot02.fill = GridBagConstraints.BOTH;
		gbc_tglBtnSpot02.insets = new Insets(0, 0, 5, 5);
		gbc_tglBtnSpot02.gridx = 1;
		gbc_tglBtnSpot02.gridy = 4;
		frmGarageMock.getContentPane().add(tglBtnSpot02, gbc_tglBtnSpot02);
		
		tglBtnSpot01 = new JToggleButton("Spot-1");
		GridBagConstraints gbc_tglBtnSpot01 = new GridBagConstraints();
		gbc_tglBtnSpot01.fill = GridBagConstraints.BOTH;
		gbc_tglBtnSpot01.insets = new Insets(0, 0, 5, 5);
		gbc_tglBtnSpot01.gridx = 1;
		gbc_tglBtnSpot01.gridy = 5;
		frmGarageMock.getContentPane().add(tglBtnSpot01, gbc_tglBtnSpot01);
		
		tglBtnEntryGate = new JToggleButton(STR_ENTRY_GATE_NOCAR);
		GridBagConstraints gbc_tglBtnEntryGate = new GridBagConstraints();
		gbc_tglBtnEntryGate.fill = GridBagConstraints.BOTH;
		gbc_tglBtnEntryGate.insets = new Insets(0, 0, 0, 5);
		gbc_tglBtnEntryGate.gridx = 0;
		gbc_tglBtnEntryGate.gridy = 7;
		frmGarageMock.getContentPane().add(tglBtnEntryGate, gbc_tglBtnEntryGate);
		
		frmGarageMock.pack();
		
		garageState = GarageState.getInstance();
	}
	
	private void initUiEventListener(){
		/* Gates */
		tglBtnEntryGate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					btn.setText(STR_ENTRY_GATE_CAR);
					garageState.setCarDetectedEntry(true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					btn.setText(STR_ENTRY_GATE_NOCAR);
					garageState.setCarDetectedEntry(false);
				}
			}
		});
		tglBtnExitGate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					btn.setText(STR_ENTRY_GATE_CAR);
					garageState.setCarDetectedExit(true);					
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					btn.setText(STR_ENTRY_GATE_NOCAR);
					garageState.setCarDetectedExit(false);
				}
			}
		});
		
		/* Parking spots */
		tglBtnSpot01.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotState(0, true);					
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotState(0, false);
				}
			}
		});
		tglBtnSpot02.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotState(1, true);					
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotState(1, false);	
				}
			}
		});
		tglBtnSpot03.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotState(2, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotState(2, false);
				}
			}
		});
		tglBtnSpot04.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotState(3, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotState(3, false);
				}
			}
		});
		
		/* Parking spots LEDs */
		rdBtnSpotLed01.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotLedState(0, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotLedState(0, false);
				}
			}
		});
		rdBtnSpotLed02.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotLedState(1, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotLedState(1, false);
				}
			}
		});
		rdBtnSpotLed03.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotLedState(2, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotLedState(2, false);
				}
			}
		});
		rdBtnSpotLed04.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					garageState.setSpotLedState(3, true);
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					garageState.setSpotLedState(3, false);
				}
			}
		});
	}

	@Override
	public void updateUi() {
	   if (!SwingUtilities.isEventDispatchThread()) {
	     SwingUtilities.invokeLater(new Runnable() {
	       @Override
	       public void run() {
	    	   updateUi();
	       }
	     });
	   }else{
		  updateEntryExitLedUi();
		  updateSoptRadioUi();
	   }
	}

	private void updateEntryExitLedUi() {
		if (GarageState.LED_RED.equals(garageState.getEntryLedState())) {
			barEntryGate.setBackground(Color.RED);
		} else if (GarageState.LED_GREEN.equals(garageState.getEntryLedState())) {
			barEntryGate.setBackground(Color.GREEN);
		}else{
			barEntryGate.setBackground(Color.BLACK);
		}
		if (GarageState.LED_RED.equals(garageState.getExitLedState())) {
			barExitGate.setBackground(Color.RED);
		} else if (GarageState.LED_GREEN.equals(garageState.getExitLedState())) {
			barExitGate.setBackground(Color.GREEN);
		}else{
			barExitGate.setBackground(Color.BLACK);
		}
	}

	private void updateSoptRadioUi() {
		rdBtnSpotLed01.setSelected(garageState.getSpotLedState(0));
		rdBtnSpotLed02.setSelected(garageState.getSpotLedState(1));
		rdBtnSpotLed03.setSelected(garageState.getSpotLedState(2));
		rdBtnSpotLed04.setSelected(garageState.getSpotLedState(3));
	}
}

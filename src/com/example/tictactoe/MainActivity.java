package com.example.tictactoe;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Stack;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	int[][] board = new int[3][3];
	int ID = -1;
	int MAX_PACKET_SIZE= 512;
	int serverPort = 20000;
	ProgressDialog myDialog;
	Button quitButton;
	Button newGameButton;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	Button button7;
	Button button8;
	Button button9;
	EditText mEdit;
	DatagramSocket socket = null;
	String serverAddress = null;
	InetSocketAddress serverSocketAddress = null;
	Stack<String> messages = new Stack<String>();
	Scanner dataScanner = null;
	volatile String command = null;
	boolean isX = false;
	boolean oWins = false;
	boolean xWins = false;
	boolean draw = false;
	boolean waitingForMatch = true;
	boolean myTurn = false;
	boolean boardReady = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e2) {
			e2.printStackTrace();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Register with Server");
		alert.setMessage("Server IP");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);
		
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  serverAddress = input.getText().toString();
			  serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);
			  mEdit.setText(serverAddress);
		      
			 }
		});

		alert.show();
		
		
		
		quitButton = (Button) this.findViewById(R.id.quitButton);
		newGameButton = (Button) this.findViewById(R.id.newGame);
		mEdit = (EditText)findViewById(R.id.serverIP);
		
		button1 = (Button) this.findViewById(R.id.Button1);
		button1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button1.setText("X");
					board[0][0] = 1;
					command = "MOVE " + ID + " 0 0 true";
					myTurn = false;
				}
				else{
					button1.setText("O");
					board[0][0] = -1;
					command = "MOVE " + ID + " 0 0 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
				
				
			}
		});
		
		button2 = (Button) this.findViewById(R.id.Button2);
		button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button2.setText("X");
					board[0][1] = 1;
					command = "MOVE " + ID + " 0 1 true";
					myTurn = false;
				}
				else{
					button2.setText("O");
					board[0][1] = -1;
					command = "MOVE " + ID + " 0 1 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
				
			}
			
		});
		
		button3 = (Button) this.findViewById(R.id.Button3);
		button3.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button3.setText("X");
					board[0][2] = 1;
					command = "MOVE " + ID + " 0 2 true";
					myTurn = false;
				}
				else{
					button3.setText("O");
					board[0][2] = -1;
					command = "MOVE " + ID + " 0 2 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button4 = (Button) this.findViewById(R.id.Button4);
		button4.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button4.setText("X");
					board[1][0] = 1;
					command = "MOVE " + ID + " 1 0 true";
					myTurn = false;
				}
				else{
					button4.setText("O");
					board[1][0] = -1;
					command = "MOVE " + ID + " 1 0 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button5 = (Button) this.findViewById(R.id.Button5);
		button5.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button5.setText("X");
					board[1][1] = 1;
					command = "MOVE " + ID + " 1 1 true";
					myTurn = false;
				}
				else{
					button5.setText("O");
					board[1][1] = -1;
					command = "MOVE " + ID + " 1 1 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button6 = (Button) this.findViewById(R.id.Button6);
		button6.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button6.setText("X");
					board[1][2] = 1;
					command = "MOVE " + ID + " 1 2 true";
					myTurn = false;
				}
				else{
					button6.setText("O");
					board[1][2] = -1;
					command = "MOVE " + ID + " 1 2 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button7 = (Button) this.findViewById(R.id.Button7);
		button7.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button7.setText("X");
					board[2][0] = 1;
					command = "MOVE " + ID + " 2 0 true";
					myTurn = false;
				}
				else{
					button7.setText("O");
					board[2][0] = -1;
					command = "MOVE " + ID + " 2 0 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button8 = (Button) this.findViewById(R.id.Button8);
		button8.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button8.setText("X");
					board[2][1] = 1;
					command = "MOVE " + ID + " 2 1 true";
					myTurn = false;
				}
				else{
					button8.setText("O");
					board[2][1] = -1;
					command = "MOVE " + ID + " 2 1 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		button9 = (Button) this.findViewById(R.id.Button9);
		button9.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (isX){
					button9.setText("X");
					board[2][2] = 1;
					command = "MOVE " + ID + " 2 2 true";
					myTurn = false;
				}
				else{
					button9.setText("O");
					board[2][2] = -1;
					command = "MOVE " + ID + " 2 2 false";
					myTurn = false;
				}
				isGameOver();
				boardReady = false;
			}
		});
		
		
		newGameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					command = "QUIT " + ID;
					isX = false;
					ID = -1;
					serverAddress = mEdit.getText().toString();
					serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);
					
					command = "REGISTER";
					waitingForMatch=true;
					
					myDialog = new ProgressDialog(MainActivity.this);
					myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					myDialog.setMessage("Waiting for match...");
					myDialog.setCancelable(false);
					
					
					myDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    	command = "QUIT " + ID;
					    	waitingForMatch = false;
					    	ID = -1;
					        dialog.dismiss();
					    }
					});
					myDialog.show();
					
					
					
					
				
			}});
		
		quitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				command = "QUIT " + ID;
				finish();
				System.exit(0);
			}
		});
		
		new ListenerWorkerThread().start();
		new ProcessingWorkerThread().start();
		
	
	}


	
	private class ProcessingWorkerThread extends Thread{

		@Override
		public void run() {
			while (true){
				if (!boardReady){
					if (!myTurn){
						runOnUiThread(new Runnable(){

							@Override
							public void run() {
								button1.setEnabled(false);
								button2.setEnabled(false);
								button3.setEnabled(false);
								button4.setEnabled(false);
								button5.setEnabled(false);
								button6.setEnabled(false);
								button7.setEnabled(false);
								button8.setEnabled(false);
								button9.setEnabled(false);												
							}
							
						});
						
					
					}
					else{
						for (int x=0;x<3;x++){
							for (int y=0;y<3;y++){
								if (board[x][y] == 0){
									if (x == 0 && y == 0 ){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button1.setEnabled(true);												
											}
											
										});
										
									}
									else if ( x == 0 && y ==1){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button2.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 0 && y ==2){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button3.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 1 && y ==0){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button4.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 1 && y ==1){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button5.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 1 && y ==2){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button6.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 2 && y ==0){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button7.setEnabled(true);												
											}
											
										});
									}
									else if ( x == 2 && y ==1){
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button8.setEnabled(true);												
											}
											
										});
									}
									else{
										runOnUiThread(new Runnable(){

											@Override
											public void run() {
												button9.setEnabled(true);												
											}
											
										});
									}
								}
							}
						}
						
					}
					boardReady=true;
				}
				
				
				if (command != null){
					DatagramPacket txPacket;
					
					try {
					txPacket = new DatagramPacket(command.getBytes(), command.length(), serverSocketAddress);
				
					socket.send(txPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
					command = null;
				}
				
				if (!messages.isEmpty()){
					//Log.i("MainActivity", "NowHere");	
					final String payload = messages.pop();
					final Context context = getApplicationContext();
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							Toast.makeText(context, payload, Toast.LENGTH_LONG).show();								
						}
						
					});
					
					
					if (payload.startsWith("SETID")){
						dataScanner = new Scanner(payload);
						dataScanner.next();
						ID = dataScanner.nextInt();
						command = "MATCH " + ID;
						
					}
					else if(payload.startsWith("PLAY")){
						waitingForMatch = false;
						isX = true;
						myTurn = true;
						boardReady = false;
						runOnUiThread(new Runnable(){

							@Override
							public void run() {
								myDialog.dismiss();												
							}
							
						});
						
						
					}
					else if(payload.startsWith("MOVE")){
						waitingForMatch = false;
						runOnUiThread(new Runnable(){

							@Override
							public void run() {
								myDialog.dismiss();												
							}
							
						});
						
						dataScanner = new Scanner(payload);
						dataScanner.next();
						
						int x = dataScanner.nextInt();
						int y = dataScanner.nextInt();
						boolean dataIsX = dataScanner.nextBoolean();
						
						if (dataIsX){
							board[x][y] = 1;
							if (x == 0 && y == 0 ){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button1.setText("X");												
									}
									
								});
								
								
							}
							else if ( x == 0 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button2.setText("X");												
									}
									
								});
								
							}
							else if ( x == 0 && y ==2){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button3.setText("X");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==0){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button4.setText("X");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button5.setText("X");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==2){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button6.setText("X");												
									}
									
								});
								
							}
							else if ( x == 2 && y ==0){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button7.setText("X");												
									}
									
								});
								
							}
							else if ( x == 2 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button8.setText("X");												
									}
									
								});
								
							}
							else{
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button9.setText("X");												
									}
									
								});
								
							}
							
						}else{
							board [x][y] = -1;
							if (x == 0 && y == 0 ){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button1.setText("O");												
									}
									
								});
								
							}
							else if ( x == 0 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button2.setText("O");												
									}
									
								});
								
							}
							else if ( x == 0 && y ==2){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button3.setText("O");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==0){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button4.setText("O");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button5.setText("O");												
									}
									
								});
								
							}
							else if ( x == 1 && y ==3){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button6.setText("O");												
									}
									
								});
								
							}
							else if ( x == 2 && y ==0){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button7.setText("O");												
									}
									
								});
								
							}
							else if ( x == 2 && y ==1){
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button8.setText("O");												
									}
									
								});
								
							}
							else{
								runOnUiThread(new Runnable(){

									@Override
									public void run() {
										button9.setText("O");												
									}
									
								});
								
							}
						}
						isGameOver();
						
						myTurn = true;
						boardReady = false;
						
						
					}
					
				}
			}			
		}
		
	}
	
	void clearBoard(){
		for (int x=0;x<3;x++){
			for (int y=0;y<3;y++){
				board[x][y] = 0;
				
			}
		}
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				button1.setText("");
				button2.setText("");
				button3.setText("");
				button4.setText("");
				button5.setText("");
				button6.setText("");
				button7.setText("");
				button8.setText("");
				button9.setText("");
			}
			
		});
		boardReady = false;
		myTurn=false;
		isX=false;
	}
	void isGameOver(){
		int sum = 0;
		boolean thereIsAMove = true;
		
		//checks rows
		for (int x=0;x<3;x++){
			for (int y=0;y<3;y++){
				sum = sum + board[x][y];
			}
			if (sum == -3){
				oWins = true;
				
			}
			if (sum == 3){
				xWins = true;
				
			}
			sum = 0;

		}
		
		//checks columns
		for (int y=0;y<3;y++){
			for (int x=0;x<3;x++){
				sum = sum + board[x][y];
			}
			if (sum == -3){
				oWins = true;
				
			}
			if (sum == 3){
				xWins = true;
				
			}
			sum = 0;

		}
		
		if (board[0][0] == -1 && 
			board [1][1] == -1 &&
			board [2][2] == -1){
			oWins = true;
			
		}
		if (board[0][0] == 1 && 
			board [1][1] == 1 &&
			board [2][2] == 1){
			xWins = true;
			
		}
		
		if (board[0][2] == -1 && 
			board [1][1] == -1 &&
			board [2][0] == -1){
			oWins = true;
			
		}
		if (board[0][2] == 1 && 
			board [1][1] == 1 &&
			board [2][0] == 1){
			xWins = true;
			
		}
		
		if (!xWins && !oWins){
			thereIsAMove = false;
			for (int x=0;x<3;x++){
				for (int y=0;y<3;y++){
					if (board[x][y] == 0){
						thereIsAMove = true;
					}
				}
			}
		}
		
		
		if (!thereIsAMove){
			draw = true;
			
		}
		
		
		if (xWins){
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
					alert.setTitle("X Wins!");
					alert.show();
				}
				
			});
			
			clearBoard();
			command = "QUIT " + ID;
			ID = -1;
			
		}
		else if (oWins){
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
					alert.setTitle("O Wins!");
					alert.show();
				}
				
			});
			clearBoard();
			command = "QUIT " + ID;
			ID = -1;
		}
		else if (draw){
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
					alert.setTitle("Draw!");
					alert.show();
				}
				
			});
			clearBoard();
			command = "QUIT " + ID;
			ID = -1;
		}
		xWins = false;
		oWins = false;
		draw = false;
		
		return;
		
	}
	
	private class ListenerWorkerThread extends Thread {

		@Override
		public void run() {
			byte[] buf = new byte[MAX_PACKET_SIZE];
			
	        DatagramPacket rxPacket = new DatagramPacket(buf, buf.length);
			while(true){
				
				try {
					socket.receive(rxPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String payload = new String(rxPacket.getData(), 0, rxPacket.getLength()).trim();
				Log.i("MainActivity", payload);
				messages.push(payload);
				 
				
				
			}
		}

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

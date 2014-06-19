/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */

package hdrserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class HDRserver implements Runnable {

    private final int numberOfPlayers;
    private ServerSocket server;
    private final ArrayList<ClientConnection> connections;

    public static void main(String[] args){
        int arg0;
        int arg1;

        try {
            if (args.length == 2) {
                arg0 = Integer.parseInt(args[0]);
                arg1 = Integer.parseInt(args[1]);
                boolean validPort = 0 < arg0 && arg0 <= 65535;
                boolean validNumber = 0 < arg1 && arg1 <= 4;

                if (validPort && validNumber) {
                    new Thread(new HDRserver(arg0, arg1)).start();
                } else {
                    // error: invalid port or number
                }
            } else {
                System.out.println("invalid number of arguments");
            }

        } catch (NumberFormatException e) {
            // error: no number
        }
    }

    public HDRserver(int port, int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

	try {
	    server = new ServerSocket(port);
	} catch(IOException e) {
	    try {
		server.close();
	    } catch (IOException ex){}
	}
	connections = new ArrayList<>(numberOfPlayers);
    }

    public void run() {
	try {
	    System.out.println("Welcome");
	    System.out.println("Server accepting requests");

	    for (int i = 1; i <= numberOfPlayers; i++) {
		Socket client = server.accept();
                System.out.println("Player " + i + " connected.");
		synchronized(connections) {
		    connections.add(new ClientConnection(this, client));
                    connections.get(i - 1).print("color," + i);
		}
	    }
            broadcast("start," + numberOfPlayers);

	} catch(IOException e){
	    e.printStackTrace();
	}
    }

    public void broadcast(String msg) {
	synchronized (connections) {
	    for(int i = 0; i < connections.size(); i++) {
		connections.get(i).print(msg);
            }
	}
    }
}






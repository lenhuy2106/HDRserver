/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */

package hdrserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


class ClientConnection extends Thread {

    private final HDRserver server;
    private PrintWriter pw;
    private BufferedReader br;

    public ClientConnection(HDRserver hdrs, Socket client) {
	this.server = hdrs;
	try {
	    pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
	    br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    this.start();
	} catch (IOException e) {
	    try {
		pw.close();
		br.close();
		e.printStackTrace();
	    } catch (IOException ex) {}
	}
    }

    public void run() {
	try{
	    while(true){
		String line = br.readLine();
                System.out.println(line);

		if(line != null) {
		    server.broadcast(line);
                }
	    }
	} catch (Exception e) {
	    System.out.println("client disconnected");
            server.broadcast("exit");
	}
    }

    public void print(String msg) {
        pw.println(msg);
        pw.flush();
    }


}

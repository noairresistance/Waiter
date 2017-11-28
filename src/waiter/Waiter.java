/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waiter;

import java.net.SocketException;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList; // test for order
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import Food.*;
import GUI.*;
import Listener.MessageListener;

public class Waiter
{
    private Socket WaiterSkt = null; // the waiter's socket
    private ObjectInputStream ObjIn = null; // the waiter's input Stream
    private ObjectOutputStream ObjOut = null; // the waiter's ouput Stream
    private boolean Connected = false; 
    private ArrayList<String> Messages = null; // an arraylist to hold the waiter's notifications
    private int MasterGamePin; // the waiter's master game pin
    private Order Orders[]; // an array that holds an individual table's order
    public MessageListener messageListener;
    private int ID;
    
    public Waiter()
    {
        Orders = new Order[16]; // create a new array
        Messages = new ArrayList(); // create a new array list
        createFrame();//Creates the waiter's GUI frame
    }
    
    // this function is used to connect the waiter to the server
    public void Handshake()
    {
        try
        {
            String Category = "Waiter"; // a string used to tell the server what type of conncection was accepted
            WaiterSkt = new Socket("localHost", 5555); // connect to server
            System.out.println("Connected to server"); // test
            Connected  = true; // the waiter is connected to the server
            
            // create input and output stream so that the waiter can communicate with the server
            ObjOut = new ObjectOutputStream(WaiterSkt.getOutputStream());
            ObjOut.flush();
            ObjIn = new ObjectInputStream(WaiterSkt.getInputStream());
            
            // launch reading thread
            Thread Listening = new Thread(new ListeningThread());
            Listening.start();
            
            ObjOut.writeUTF(Category);
            ObjOut.flush();

            Thread.sleep(100); // allow the server to receive the message       
        }
        catch(Exception e)
        {
            System.out.println("Error connection to server");
        }
    }
    
    // this function is used to close sockets used to communicate with the server
    public void CloseConnection()
    {
        try
        {
            ObjOut.close(); // close the input stream
            ObjIn.close(); // close the output stream
            WaiterSkt.close(); // close the socket
        }
        catch(IOException e)
        {
            System.out.println("Error closing the connection.");
        }
    }
    
    // this is a thread that listens to messages from the server
    public class ListeningThread implements Runnable
    {
        @Override
        public void run()
        {
            String Message; // a string used to receive messages from the server
            int tempID;
            
            try
            {
                tempID = ObjIn.readInt();
                ID = tempID + 1;
                
                // while the server can send messages
                while((Message = ObjIn.readUTF())!= null)
                {
                    System.out.println(Message); // test
                    
                    if(Message.equals("Placed")) // an order was placed so prepare to recieve order
                    {
                        // read the order 
                        Order tempOrder = (Order)ObjIn.readObject();
                        
                        Orders[tempOrder.GetTableNum()-1] = tempOrder; // place the order in the orders array
                    }
                    else if(Message.equals("Help")) // a table called for assistance
                    {
                        // add the help message to the messages array and the GUI
                        String help = ObjIn.readUTF();
                        Messages.add(help);
                        messageListener.sendMessage(help);//Sends a message to the Waiter's GUI
                    }
                    else if(Message.equals("Cash")) //a table has paid for their order
                    {
                        // add the paid by cash message to the Messsages array and the GUI
                        String payment_cash = ObjIn.readUTF();
                        Messages.add(payment_cash);
                        messageListener.sendMessage(payment_cash);//Sends a message to the Waiter's GUI
                        
                    }
                    else if(Message.equals("Card")) //a table has paid for their order
                    {
                        // add the pay by card to Messages array and the GUI
                        String payment_card = ObjIn.readUTF();
                        Messages.add(payment_card);
                        messageListener.sendMessage(payment_card);//Sends a message to the Waiter's GUI
                        
                    }
                    else if(Message.equals("togo"))
                    {
                        // add the to go box message to the array and the GUI
                        String togobox = ObjIn.readUTF();
                        Messages.add(togobox);
                        messageListener.sendMessage(togobox);//Sends a message to the Waiter's GUI
                    }
                    else if(Message.equals("Refill"))
                    {
                        // add the Refill message to the array and the GUI
                        String refill = ObjIn.readUTF();
                        Messages.add(refill);
                        messageListener.sendMessage(refill);//Sends a message to the Waiter's GUI
                    }
                    else if(Message.equals("Free"))
                    {
                        // add the won a free dessert message to the arraylist and the GUI
                        String free = ObjIn.readUTF();
                        Messages.add(free);
                        messageListener.sendMessage(free);//Sends a message to the Waiter's GUI
                    }
                    else if(Message.equals("Waiter"))
                    {
                        // add the kitchen requested help message to the arraylist and the GUI
                        String requestWaiter = ObjIn.readUTF();
                        Messages.add(requestWaiter);
                        messageListener.sendMessage(requestWaiter);//Sends a message to the Waiter's GUI
                        System.out.println(requestWaiter);
                    }
                    else if(Message.equals("Ready"))
                    {
                        // add the kitchen completed an order message to the GUI and the array
                        String ready = ObjIn.readUTF();
                        Messages.add(ready);
                        messageListener.sendMessage(ready);//Sends a message to the Waiter's GUI
                        System.out.println(ready);
                    }
                    else if(Message.equals("Shutdown"))
                    {
                        break; // break the loop
                    }       
                } // end of while
                
                Connected = false;
            }
            catch(Exception e)
            {
                System.out.println("Failed to receive message from server." + e);
            }
        }
    }
    
    // this function is used to send a modified order
    public void SendModifiedOrder(int TableNum)
    {
        try
        {
            // send a message to the server which order was modified
            ObjOut.writeUTF("Modify@"+Integer.toString(TableNum));    
            ObjOut.flush();
            Thread.sleep(100); // allow time for the server to process request
            
            // send the modified order
            ObjOut.writeObject(Orders[TableNum-1]);
            ObjOut.flush();
        }
        catch(Exception e)
        {
            System.out.println("Error sending modified order" + e);
        }
    }
    
    // test function used to prevent immediate execution of functions
    public void waittest()
    {
        try 
        {
            Thread.sleep(4000);
        } catch (InterruptedException ex) 
        {
            System.out.println("Error in test sleep thread" + ex);
        }
    }
    
    public static void main(String argv[])
    {
        Waiter newWaiter = new Waiter(); // create a new waiter
        newWaiter.Handshake(); // connect to the server 
        newWaiter.waittest(); // allow server to catch up
        
       /* // loop while connected
        while(newWaiter.Connected)
        {
            
        }
        // close the connection
        newWaiter.CloseConnection();*/
    }
    
    //Creates a new Frame and passes this waiter object to it
    public void createFrame()
    {
        new Thread(new Frame(this)).start();
    }
    
    // this function returns the array of orders
    public Order[] getOrders()
    {
        return Orders;
    }
    
    // this function returns the messages array list
    public ArrayList<String> getMessagesArrayList()
    {
        return Messages;
    }
    
    // this function returns a specific message from the messages list
    public String getMessage(int i)
    {
        return Messages.get(i);
    }
    
    //Called by MainPanel to give the messageListener to the Waiter
    public void setMessageListerner(MessageListener messageListener)
    {
        this.messageListener = messageListener;
    }
    
    // this function removes a specific message from the Messages arraylist
    public void removeMessage(int i)
    {
        Messages.remove(i);
    }
}

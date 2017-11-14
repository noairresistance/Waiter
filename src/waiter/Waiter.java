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
/*
import allclasses.Food;
import allclasses.RestaurantItem;
import allclasses.Order;
*/
import java.util.logging.Level;
import java.util.logging.Logger;
import Food.*;
import GUI.*;

public class Waiter
{
    private Socket WaiterSkt = null; // the waiter's socket
    private ObjectInputStream ObjIn = null; // the waiter's input Stream
    private ObjectOutputStream ObjOut = null; // the waiter's ouput Stream
    private boolean Connected = false; //
    private PriorityQueue<String> Notifications = null;
    private ArrayList<String> ActiveTables = null;
    private int MasterGamePin;
    
    private Order Orders[];
    
    
    
    public Waiter()
    {
        Orders = new Order[16];
        Notifications = new PriorityQueue();
        ActiveTables = new ArrayList();
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
            
            System.out.println("Sending Category"); // test
            ObjOut.writeUTF(Category);
            ObjOut.flush();
      
            Thread.sleep(500); // test
        }
        catch(Exception e)
        {
            System.out.println("Error connection to server");
        }
    }
    
    public void CloseConnection()
    {
        try
        {
            ObjOut.close();
            ObjIn.close();
            WaiterSkt.close();
        }
        catch(IOException e)
        {
            System.out.println("Error closing the connection.");
        }
    }
    
    public class ListeningThread implements Runnable
    {
        @Override
        public void run()
        {
            String Message;
            
            try
            {
                while((Message = ObjIn.readUTF())!= null)
                {
                    System.out.println(Message);
                    
                    if(Message.equals("Placed")) // an order was placed so prepare to recieve order
                    {
                        System.out.println("About to receive order.");
                        
                        // read the order 
                        Order tempOrder = (Order)ObjIn.readObject();
                        
                        Orders[tempOrder.GetTableNum()-1] = tempOrder;
                        
                        // test loop checking contents
                        for(int i = 0; i < tempOrder.getDrink().size(); i++)
                        {
                            System.out.println(tempOrder.getDrink().get(i).GetName());
                        }
                        for(int i = 0; i < tempOrder.GetOrderSize(); i++)
                        {
                            System.out.println(tempOrder.getFoodItem().get(i).GetName());
                        }
                        // add order to list of orders
                        System.out.println("Order Received");
                        
                    }
                    else if(Message.startsWith("Help")) // a table called for assistance
                    {
                        System.out.println("Customer Requested Help.");
                        // parse the command
                        // put the message in the form table # requested help
                        // put the message in the queue
                    }
                    else if(Message.startsWith("Paid")) //a table has paid for their order
                    {
                        System.out.println("Table has paid for their order!");
                    }
                    
                    else if(Message.equals("Shutdown"))
                    {
                        break;
                    }
                }
                
                Connected = false;
            }
            catch(Exception e)
            {
                System.out.println("Failed to receive message from server." + e);
            }
        }
    }
    
    // test function
    public void ModifyOrder(int TableNum)
    {
        System.out.print(Orders[TableNum-1].GetTableNum());
        ((Food)Orders[TableNum-1].GetItem(0)).SetIngredients("Chili");
        ((Food)Orders[TableNum-1].GetItem(1)).SetIngredients("Cheese");     
    }
    
    public void SendModifiedOrder(int TableNum)
    {
        try
        {
            ObjOut.writeUTF("Modify@"+Integer.toString(TableNum));    
            ObjOut.flush();
            Thread.sleep(500);
            
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
        // test cases
        Waiter newWaiter = new Waiter();
        newWaiter.Handshake();
        newWaiter.waittest();
       
        //newWaiter.ModifyOrder(1);
        //newWaiter.SendModifiedOrder(1);
        
        while(newWaiter.Connected)
        {
            
        }
        newWaiter.CloseConnection();
    }
    
    public void createFrame()
    {
        new Thread(new Frame()).start();
    }
}

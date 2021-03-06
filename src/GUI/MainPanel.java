/*
 * CSCE 4444
 * 
 * 
 */
package GUI;

import Listener.MessageListener;
import Listener.Navigator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import waiter.Waiter;

public class MainPanel extends javax.swing.JPanel 
{

    Waiter waiter;
    
    /**
     * Creates new form MainPanel
     */
        
    MessageListener messageListener = new MessageListener()
    {
        @Override
        public void sendMessage(String message)
        {
            for(int i = 0; i < 3; i++)    
            {
                notification.setText(message);
                try
                {
                    if(i == 2)
                    {
                        Thread.sleep(1000);
                    }
                    else
                    {
                        Thread.sleep(500);
                    }
                    
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                notification.setText("");
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }    
        }
    
    };
    public MainPanel(Waiter waiter)
    {
        initComponents();
        setSize(400, 500);
        
        this.waiter = waiter;
        getMessageListener();//Gives the messageListener to the Waiter
    }
    
    Navigator navigator = new Navigator()
    {
        void swapPanel(JPanel newPanel)
        {
            infoPanel.add(newPanel);
            infoPanel.repaint();
            infoPanel.revalidate();
        }
        
        @Override
        public void goToAssignedTables()
        {
            swapPanel(new AssignedTables(waiter.getOrders(), infoPanel));
        }

        @Override
        public void goToAlerts()
        {
            swapPanel(new Alerts(waiter.getMessagesArrayList(), infoPanel));
        }
    
    };
    
    //Gives the messageListener to the Waiter
    void getMessageListener()
    {
        waiter.setMessageListerner(messageListener);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel4 = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        assignedTables = new javax.swing.JLabel();
        alerts = new javax.swing.JLabel();
        paymentOptions = new javax.swing.JLabel();
        notificationPanel = new javax.swing.JPanel();
        notification = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();

        jLabel4.setText("jLabel4");

        setBackground(new java.awt.Color(153, 0, 0));
        setPreferredSize(new java.awt.Dimension(400, 500));
        setSize(new java.awt.Dimension(400, 5000));

        buttonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new java.awt.GridLayout(1, 0));

        assignedTables.setForeground(new java.awt.Color(255, 255, 0));
        assignedTables.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assignedTables.setText("ASSIGNED TABLES");
        assignedTables.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        assignedTables.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                assignedTablesMouseClicked(evt);
            }
        });
        buttonPanel.add(assignedTables);

        alerts.setForeground(new java.awt.Color(255, 255, 0));
        alerts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        alerts.setText("ALERTS");
        alerts.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        alerts.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                alertsMouseClicked(evt);
            }
        });
        buttonPanel.add(alerts);

        paymentOptions.setForeground(new java.awt.Color(255, 255, 0));
        paymentOptions.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        paymentOptions.setText("PAYMENT OPTIONS");
        paymentOptions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        buttonPanel.add(paymentOptions);

        notificationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
        notificationPanel.setOpaque(false);

        notification.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        notification.setForeground(new java.awt.Color(255, 255, 0));
        notification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout notificationPanelLayout = new javax.swing.GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        notificationPanelLayout.setVerticalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notification, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );

        infoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new java.awt.Dimension(388, 347));
        infoPanel.setSize(new java.awt.Dimension(388, 347));

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(notificationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(notificationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void assignedTablesMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_assignedTablesMouseClicked
    {//GEN-HEADEREND:event_assignedTablesMouseClicked
       navigator.goToAssignedTables();
    }//GEN-LAST:event_assignedTablesMouseClicked

    private void alertsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_alertsMouseClicked
    {//GEN-HEADEREND:event_alertsMouseClicked
        navigator.goToAlerts();
    }//GEN-LAST:event_alertsMouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alerts;
    private javax.swing.JLabel assignedTables;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel notification;
    private javax.swing.JPanel notificationPanel;
    private javax.swing.JLabel paymentOptions;
    // End of variables declaration//GEN-END:variables
}
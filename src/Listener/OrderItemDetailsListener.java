/*
 * CSCE 4444
 * 
 * 
 */
package Listener;

import Food.Food;

public interface OrderItemDetailsListener
{
    public void modifyItem(Food item);
    
    public void recallItem();
}

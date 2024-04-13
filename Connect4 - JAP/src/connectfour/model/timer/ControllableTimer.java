/******************************************
* Controllable Timer class
* Written by Daniel Cormier,
* for use in CST8221
*
* See attached documentation for usage notes.
*********************************************/

package connectfour.model.timer;
import java.awt.EventQueue;

/**
ControllableTimer:  A timer that can be started and stopped.
@author Daniel Cormier
@version 1.1
@since 1.8_301
modified by: Paul Squires, Cole Brito
date modified: March 24, 2024
*/

public class ControllableTimer extends Thread
{
    /** Resumes operation. */
	public static final int START = 1;
    
    /** Pauses operation */
    public static final int STOP = 2;
    
    /** Resets timer value. */
    public static final int RESET = 3;
    
    /** For terminating this thread for a clean shutdown */
    public static final int TERMINATE=4;
    
    /**Current timer status*/
    private int status = START;
    /**time elapsed*/
    private int elapsed=0;
    
    /**Listener which responds to changes in time*/
	private ControllableTimerChangeListener listener;
    
    /**
    This method permits external control of the timer.
    Send ControllableTimer.START as the parameter to resume timing.
    ControllableTimer.STOP to pause timing.
    ControllableTimer.RESET to reset the timer to 0.
    @param cmd The control parameter, with values indicated above.
    */

    /**
    Constructor requires reference to a View class.
    You may need to edit it.  This is a reference to your class that displays the timer.
    @param listener The new listener to respond to changes in time
    */
    public ControllableTimer(ControllableTimerChangeListener listener) {
        this.listener = listener;
    }
    
    /**
     * Set the listener to respond to time changes
     * @param listener The new listener
     */
    public void setListener(ControllableTimerChangeListener listener) {
    	this.listener = listener;
    }

    /**
    Method through which the timer is controlled.  Timer starts on by default.
    @param cmd \nSTART: Starts timer.\nSTOP: Pauses timer.\nRESET: Sets timer back to 0.\nTERMINATE: Kills this thread for a clean shutdown.
    */

    public synchronized void setStatus(int cmd)
    {
        switch (cmd)
        {
            case START:
                status=START;
                notify();
                break;
            case STOP:
                status=STOP;
                break;
            case RESET:
                elapsed=0;
                break;
            case TERMINATE:
                status=TERMINATE;
        }
    }
    
    /**
    Permits checking the status of the timer. Mostly intended for internal use.
    @return status of the program in operation (START, STOP, TERMINATE)
    */
    
    public synchronized int getStatus()
    {
        if (status==STOP)
        {
            try
            {
                wait(); //wait can only be called from a synchronized method.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return status;
    }
    
    /**
     * Gets time elapsed
     * @return time elapsed
     */
    public synchronized int getTime()
    {
        return elapsed;
    }
    
    /**
    The timer's main loop.
    */
    
    public void run()
    {
        while (getStatus()!=TERMINATE)
        {
            try
            {
                sleep(1000); //Waits for 1000 milliseconds
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            
            //Threadsafe code to modify your UI.  Injects operations into the UI thread.
            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                { 	//setTime method is part of your code.  Either modify this, or add an appropriate convenience method.
                	++elapsed;
                	if (listener != null) {
                		listener.setTime(elapsed);
                	}
                }
            }); //End EventQueue

        }
    }
}
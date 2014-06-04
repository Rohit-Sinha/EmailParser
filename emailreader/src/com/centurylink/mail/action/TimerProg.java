package com.centurylink.mail.action;



import java.util.Timer;
import java.util.TimerTask;

public class TimerProg {
	
	//constructor
    public TimerProg() {
    	new Timer().schedule(new RemindTask(),	0,	30000);	//
    }

    //class to write task
    class RemindTask extends TimerTask {
    	
        //method to write task
        public void run() {
           try {
			MailParser.mailing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//prints current time
        }
    }
    
    
    
   //main method to start execution 
    public static void main(String[]args){
    	new TimerProg();
    }
    
    
    
    
    
}

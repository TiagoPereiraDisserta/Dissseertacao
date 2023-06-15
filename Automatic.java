import java.time.Instant;
import java.util.*; 

public class Automatic {
	
 Random random = new Random(Instant.now().getEpochSecond());
  static long  acumulativo=0;
  int acumulstiveInt=0;
    Automatic(){
          Thread threadX= new Thread();
    	acumulstiveInt=0 ;   }
    
 public static void main(String args[]) {
	 String Buffer="";
	 
	 while(true) {
		 try {
		    Thread thread = new SimulationThread();
		    thread.run();
		    Thread.sleep(acumulativo);
		 }catch(Exception e) {
			 Buffer=Buffer+" "+Instant.now().getEpochSecond()+";"+e.getMessage() +";"+e.getLocalizedMessage()+";"+e.getClass()+";"+e.toString();
			e.notifyAll(); 
			System.out.println(Buffer);
		 }
		 
		 acumulativo=(acumulativo+1)%100000;
		 
	 }
	 
 }
 
 




}

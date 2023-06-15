
public class TypeBThread extends Thread{
  TypeBThread(int i){
	  System.out.print(i);
	  
  }
  public void  run(){
	   String[] a={"."," "};
	   scenario_typeB.main(a);
	   for(int i=0;i<10;i++) {
		   Scenary_TypeG.main(a);

		   Thread thread= new TypeGThread(i);
		   thread.run();
		   try {
			  thread.join();
			  thread = new TypeBThread(i+1);
			  thread.run();
		   }catch(Exception e) {
			   
		   }
		   
		   scenario_typeB.main(a);

		   
	   }
   }
}

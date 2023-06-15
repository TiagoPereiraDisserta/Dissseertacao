public class TypeGThread extends Thread{
  TypeGThread(int i){
	  System.out.print(i);
	  
  }
  public void  run(){
	   String[] a={"."," "};
	   Scenary_TypeG.main(a);
	   for(int i=0;i<10;i++) {
		   scenario_typeB.main(a);

		   Thread thread= new TypeBThread(i);
		   thread.run();
		   Scenary_TypeG.main(a);
		   try {
		   thread.join();
		   thread=new TypeGThread(i+1);
		   thread.run();
		   }catch(Exception e) {
			   
		   }
		   

	   }
   }
}
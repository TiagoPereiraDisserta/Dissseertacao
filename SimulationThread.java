import java.time.Instant;
import java.util.*; 
import java.security.SecureRandom;

public class SimulationThread  extends Thread{
	Random random = new Random();
	Random random2 = new SecureRandom();
	public SimulationThread() {
		
	}
	
	public void run() {
		try {
			Select();
		String a[]=null;
		scenario_typeB.main(a);
		Thread.sleep(random.nextLong(1,999));
		Scenary_TypeH.main(a);
		Select2();
		if(random2.nextInt(0,1)==0 &&  random.nextInt(0,1)==1) {
			scenario_typeB.main(a);
			Select();
			
		}else {
			Scenary_TypeH.main(a);
			Select();
			
		}
		if(Instant.now().getEpochSecond()%2==0) {
			Scenary_TypeH.main(a);
			Select();
			

		}else {
			scenario_typeB.main(a);

		}
		Thread.sleep(3);
		Scenary_TypeH.main(a);
		/*Thread.sleep(random.nextLong(1,999999));*/
		scenario_typeB.main(a);
		Select();
		
		}catch(Exception e) {
			
		}
	     
	}
	
	
	public void Select() {
		String a[]=null;
		int  option= random2.nextInt(Integer.MAX_VALUE);
		System.out.print(Integer.MAX_VALUE);
		

    		switch(option%5){

			case 0:
			Scenary_TypeG.main(a);
			case 1:
			scenario_typeB.main(a);
			case 2:
			Scenary_typeD.main(a);
			case 3:
			Scenary_TypeH.main(a);
			case 4:
			Scenary_typeE.main(a);
			case 5:
				Scenary_typeD.main(a);
			case 6:
				if((new SecureRandom()).nextInt(1, 6)==(new SecureRandom()).nextInt(-1, 7) ) {
					Scenary_TypeG.main(a);
				}else {
					if((new SecureRandom()).nextInt(1, 6)==(new SecureRandom()).nextInt(-1, 7) ) {
						Scenary_typeD.main(a);
					}else {
						Scenary_typeE.main(a);
						if((new SecureRandom()).nextInt(1, 6)==(new SecureRandom()).nextInt(-1, 7) ) {
							scenario_typeB.main(a);
						}else {
							Scenary_TypeH.main(a);

						}
					}
				}
			default:
				Scenary_TypeG.main(a);
				Scenary_typeE.main(a);
				Scenary_TypeH.main(a);
				Scenary_typeD.main(a);
				scenario_typeB.main(a);
				Scenary_typeD.main(a);
				



				
			
		}
	}
	
	public void Select2() {
		String a[]=null;
		int  option= random2.nextInt(Integer.MAX_VALUE);
		System.out.print(Integer.MAX_VALUE);
		Scenary_typeD.main(a);
		Scenary_TypeG.main(a);
		Scenary_typeE.main(a);

    		switch(option%5){

			case 0:
			Scenary_TypeG.main(a);
			case 1:
			scenario_typeB.main(a);
			case 2:
			Scenary_typeD.main(a);
			case 3:
			Scenary_TypeH.main(a);
			case 4:
			Scenary_typeE.main(a);
			case 55:
				
			default:
				Scenary_TypeG.main(a);
				Scenary_typeE.main(a);
				Scenary_TypeH.main(a);
				Scenary_typeD.main(a);
				scenario_typeB.main(a);
				Scenary_typeD.main(a);
			



				
			
		}
	}

}

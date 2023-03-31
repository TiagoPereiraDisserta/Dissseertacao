
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import java.security.MessageDigest;

import java.util.*; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;


public class Resumo {
	

	
	private long PosixDate;
	private static Random random= new Random();
	
	private static String atons[]= {"なし","あ","しな","な","し",
			"くそ","そ","そく","任","天","堂","堂任",
			"子犬","犬","子","あ","と","へ",
			"も","よ","ろ","を","ふ","","ら","	や",
			"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
			"犬","子","あ","と","へ",
			"犬子あとへ","犬子","あと","へ",
			"犬","子あ","とへ",
			"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
			"牙","酒","з","画"};
	
	
	
	public static void main(String[] args) {
		Resumo resumo=new Resumo();
		
		resumo.Exportar();
		System.out.println("Id:\t"+resumo.Id+"\nIDKey:\t"+resumo.IdKey);
		System.out.println(".!.");
	}
	
	private int Id;
	private String IdKey;
	private Type type;
	private double HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION;
	private double HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION;
	private int  SCHEDULING_INTERVAL;
	private double CLOUDLET_INITIAL_CPU_PERCENTAGE;
	private double CLOUDLET_CPU_INCREMENT_PER_SECOND;
	private int HOST_SEARCH_RETRY_DELAY;
	private int[][] DC_HOST_PES= {{0,1,2},{2,1,0},{1},{1,2,3,4,5,6,7,8,9}};
	private int numbesOFHostsPES=0;
	private int numberOfHost=0;
	private int[][] VM_PES={{0,1,2},{1,2,3,4,5,6,7,8,9},{1},{2,1,0}};
	private int numberOfVMPES=0;
	private int numberOfVM=0;
	private int numberOfMigration;
	private int createdVms;
	private  int  numberOfRows=0;
	private int numberOfColunns=0;
	private int extraPES=0;
	private  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");  
	private LocalDateTime now =null;  
    private long ut ;
    private LocalDateTime now2 =null;
    private long finalPosixDate=-12;
 

	
	
	Resumo(){
		now =LocalDateTime.now();
		Id=random.hashCode();

	}
	
	Resumo(Type type){
		Id=random.hashCode();
		now =LocalDateTime.now();
		this.type=type;
	}
	
    /* metodo que vai exportar para CSV */ 
	public  void Exportar() {

		String buffer="";
		String buffer2="";
		
		/*Parametros*/
		buffer=buffer+this.getIDKey()+";";
		buffer2=buffer2+this.getIDKey()+";";
		 now =LocalDateTime.now();  
		 
		buffer=buffer+"'["+dtf.format(now)+"]';";
		now =LocalDateTime.now();  

		buffer2=buffer2+dtf.format(now)+";";
        ut=Instant.now().getEpochSecond();
		buffer=buffer+ut+";";
		ut=Instant.now().getEpochSecond();
		buffer2=buffer2+ut+";";
		buffer=buffer+this.type+";";
		buffer2=buffer2+this.type+";";
       
		buffer=buffer+this.CLOUDLET_INITIAL_CPU_PERCENTAGE+";";
		buffer2=buffer2+this.CLOUDLET_INITIAL_CPU_PERCENTAGE+";";

		buffer=buffer+this.CLOUDLET_CPU_INCREMENT_PER_SECOND+";";
		buffer2=buffer2+this.CLOUDLET_CPU_INCREMENT_PER_SECOND+";";

		buffer=buffer+this.HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+";";
		buffer2=buffer2+this.HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+";";

		buffer=buffer+this.HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+";";
		buffer2=buffer2+this.HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+";";
		
		buffer=buffer+this.getDC_HOST_PES()+";";

		buffer2=buffer2+this.getDC_HOST_PES()+";";
		buffer=buffer+this.getDC_HOST_PESValue()+";";
		
		buffer2=buffer2+this.getDC_HOST_PESValue()+";";
		buffer=buffer+this.numbesOFHostsPES+";";
		buffer2=buffer2+this.numbesOFHostsPES+";";
		buffer=buffer+this.getVM_PES()+";";

		buffer2=buffer2+this.getVM_PES()+";";
		buffer=buffer+this.getVM_PES_MatrixValue()+";";
		buffer2=buffer2+this.getVM_PES_MatrixValue()+";";
		buffer=buffer+this.numberOfVMPES+";" ;
		buffer2=buffer2+this.numberOfVMPES+";" ;
		
		if( this.PosixDate >0) {
			buffer=buffer+this.finalPosixDate+";";
			buffer2=buffer2+this.finalPosixDate+";";
		}else {
			now2 =LocalDateTime.now();
			this.finalPosixDate=Instant.now().getEpochSecond();
			buffer2=buffer2+this.finalPosixDate+";";
		}

		
		/*metricas*/
		
		buffer=buffer+this.createdVms+";";
		buffer=buffer+this.numberOfMigration;
		System.out.println(buffer);
		System.out.println("Tentar exportar");
		try {
		File myObj = new File(this.IdKey+".csv");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	        System.out.println("File'"+myObj.getName()+"' already exists.");
	      }
	      try {
	    	  FileWriter myFileWriter= new FileWriter(this.IdKey+".csv");
	    	  try {
		    	  FileWriter myFileWriter2= new FileWriter("table.csv",true);
		    	  myFileWriter2.write(buffer+"\n");
		    	 
		    	  myFileWriter2.close();
		    	  

		    	  
		      }catch(Exception e) {
					System.out.println(e.getCause().toString()+"\t. . .\t"+e.getMessage());

		      }
	    	  myFileWriter.write(buffer2);
	    	  myFileWriter.close();
	    	  System.out.println("Terminou exportar");
	    	  
	      }catch(Exception e) {
	    	  
				System.out.println(e.getCause().toString()+"\t. . .\t"+e.getMessage());

	      }
	      
	     
		}catch(Exception e) {
			System.out.println("\t. . .\t"+e.getMessage());
		}
	}
	
	
	public void setIdKey() {
		this.IdKey="a"+Id+"b"+atons[Math.abs((random.nextInt()%(atons.length-1)))]+Math.abs(random.nextInt())+this.type;
       this.IdKey=getSha256(this.IdKey)+this.type;
       this.IdKey=getSha256(this.IdKey)+this.type;
       this.IdKey=getSha256(this.IdKey+this.PosixDate)+this.now;
       this.IdKey=getSha256(this.IdKey+dtf.format(now))+dtf.format(now);
       System.out.println(this.IdKey);
       this.IdKey=getSha256(this.IdKey+this.IdKey+this.type);
       this.IdKey=getSha256(this.IdKey);
       this.IdKey=(this.IdKey)+this.type;
	}
	
	public void setType(Type type) {
		this.type=type;
	}
public void setHOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION(double HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION) {
	this.HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION=HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION;
}

public void setSCHEDULING_INTERVAL(int SCHEDULING_INTERVAL) {
	this.SCHEDULING_INTERVAL=SCHEDULING_INTERVAL;
}

public void setCLOUDLET_INITIAL_CPU_PERCENTAGE(double CLOUDLET_INITIAL_CPU_PERCENTAGE) {
	this.CLOUDLET_INITIAL_CPU_PERCENTAGE=CLOUDLET_INITIAL_CPU_PERCENTAGE;
}

public void setDC_HOST_PES(int[][] DC_HOST_PES){
	this.DC_HOST_PES=DC_HOST_PES;
	
	
}

public void setVM_PES(int [][] VM_PES) {
	this.VM_PES=VM_PES;
}
 
public void setCLOUDLET_CPU_INCREMENT_PER_SECOND( double CLOUDLET_CPU_INCREMENT_PER_SECOND) {
	this.CLOUDLET_CPU_INCREMENT_PER_SECOND= CLOUDLET_CPU_INCREMENT_PER_SECOND;;
	
}
public void setNumberOfMigration(int migrationsNumber) {
	this.numberOfMigration=migrationsNumber;
}


public void setcreatedVms(int createdVms) {
	this.createdVms=createdVms;
}
private int getId() {
	return this.Id;
}


private String getIDKey() {
	return this.IdKey;
}


private  double getCLOUDLET_CPU_INCREMENT_PER_SECOND() {
	return this.CLOUDLET_CPU_INCREMENT_PER_SECOND;
}

private   String getDC_HOST_PES() {
	String answer ="'{";
	for(int i=0;i<this.DC_HOST_PES.length-1;i++) {
		answer= answer+"{";
		for(int j=0;j<this.DC_HOST_PES[i].length-1;j++) {
			answer=answer+this.DC_HOST_PES[i][j]+",";
			
		}
		answer=answer+this.DC_HOST_PES[i][DC_HOST_PES[i].length-1];
		answer=answer+"},";
		
	}
	   answer=answer+"{";
	   for(int j=0;j<this.DC_HOST_PES[DC_HOST_PES.length-1].length-1;j++) {
			answer=answer+this.DC_HOST_PES[DC_HOST_PES.length-1][j]+",";
			
		}
		answer=answer+this.DC_HOST_PES[DC_HOST_PES.length-1][DC_HOST_PES[DC_HOST_PES.length-1].length-1];
		answer=answer+"}";
		
	return answer+"}'";
}

private  String getVM_PES() {
	
	String answer="'{";
	
	for(int i=0;i<this.VM_PES.length-1;i++) {
		answer= answer+"{";
		for(int j=0;j<this.VM_PES[i].length-1;j++) {
			answer=answer+this.VM_PES[i][j]+",";
			
		}
		answer=answer+this.VM_PES[i][VM_PES[i].length-1];
		answer=answer+"},";
		
	}
	   answer=answer+"{";
	   for(int j=0;j<this.VM_PES[VM_PES.length-1].length-1;j++) {
			answer=answer+this.VM_PES[VM_PES.length-1][j]+",";
			
		}
		answer=answer+this.VM_PES[VM_PES.length-1][VM_PES[VM_PES.length-1].length-1];
		answer=answer+"}";
	
	return answer+"}'";
}

public void setTime(LocalDateTime now) {
	this.now=now;
}

public  int getDC_HOST_PESValue() {
	
	int answer=0;
	for(int i=0;i<this.DC_HOST_PES.length;i++){
		for(int j=0;j<this.DC_HOST_PES[i].length;j++) {
			answer=answer+(this.DC_HOST_PES[i][j])*i*j;
		}		
	}
	
	
    return answer;
}


public int getVM_PES_MatrixValue() {
	
	int answer=0;
	for(int i=0;i<this.VM_PES.length;i++){
		for(int j=0;j<this.VM_PES[i].length;j++) {
			answer=answer+(this.VM_PES[i][j])*i*j;
		}		
	}
	
	
    return answer;
}

public String getIdkey() {
	return this.IdKey;
}

	 public static String getSha256(String value) {
	    try{
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(value.getBytes());
	        return bytesToHex(md.digest());
	    } catch(Exception ex){
	        throw new RuntimeException(ex);
	    }
	 }
	 private static String bytesToHex(byte[] bytes) {
	    StringBuffer result = new StringBuffer();
	    for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	    return result.toString();
	 }


public  void setnumberOfVMPES(int numberOfVMPES) {
	this.numberOfVMPES=numberOfVMPES;
	
}

public void setnumberOfVMPES() {
	int answer=0;
	for(int i=0;i<VM_PES.length;i++) {
		for(int j=0;j<VM_PES[i].length;j++){
			answer=answer+this.VM_PES[i][j];
		}
		
	}
	this.numberOfVMPES=answer;
}

public void setNumberOfHOSPES() {
	int answer=0;
	for(int i=0;i<DC_HOST_PES.length;i++) {
		for(int j=0;j<DC_HOST_PES[i].length;j++){
			answer=answer+this.DC_HOST_PES[i][j];
		}
		
	}
	this.numbesOFHostsPES =answer;
}

public void setNumberofVM() {
	int answer=0;
	for(int i=0;i<VM_PES.length;i++) {
		for(int j=0;j<VM_PES[i].length;j++){
			answer++;
		}
		
	}
	this.numberOfVM=answer;
}

public void setNumberofHost() {
	int answer=0;
	for(int i=0;i<DC_HOST_PES.length;i++) {
		for(int j=0;j<DC_HOST_PES[i].length;j++){
			answer++;
		}
		
	}
	this.numberOfVM=answer;
}

public void setExtaPES(int ExtraPES) {
	this.extraPES=ExtraPES;

}

public void setFinalPOSIXDate() {
	now2 =LocalDateTime.now();
	this.finalPosixDate=Instant.now().getEpochSecond();
}

public void setInicialDate(LocalDateTime now) {
	this.now=now;
	
	
}
}

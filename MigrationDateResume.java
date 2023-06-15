import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import java.security.MessageDigest;

import java.util.*; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class MigrationDateResume {

	String simulationIdkey ="";
	
	private String IdKey="";
	private long beginTime =-1234; 
	private long finishTime=-123456789;
	Type simulationType =Type.なし;
	private long duration =-1234;
	private short index=0;
	private int VMid=0;
	
	MigrationDateResume(String simulationIdKey, int index,Type type){
		
		this.simulationIdkey=simulationIdKey;
		this.beginTime =Instant.now().getEpochSecond();
		this.simulationType=type;
	}
	
	
	public void setBeginTime() {
		this.beginTime =Instant.now().getEpochSecond();
		System.out.println("\n\t # migration nº"+this.index+":\t"+this.beginTime);
		
	}
	
	public void recordMigrationEnd() {
		this.finishTime=Instant.now().getEpochSecond();
	}
	
	public void setDurationOfMigration() {
		long answer =-1;
		if(this.finishTime<0||this.beginTime<0) {
			answer=this.finishTime-this.beginTime;
		}
		this.duration=answer;
		System.out.println(this.duration);
	}
	
	
	public void setDuration(long Duration) {
		this.duration=Duration;
		
	}
	public  void Exportar() {
		if(this.IdKey!=null || this.IdKey.equals("") || this.IdKey.equals(" ")) {
			
		

		String buffer="";
		String buffer2="";
		
	buffer=buffer+this.IdKey+';';
	buffer2=buffer2+this.IdKey+';';
	
	buffer=buffer+this.index+";";
	buffer2=buffer2+this.index+";";

		
		

		
		
		System.out.println(buffer);
		System.out.println("Tentar exportar");
		try {
		File myObj = new File("# migração nº"+this.index+":\t"+this.beginTime+"||\t "+this.simulationIdkey+"||\t"+this.simulationType+"||\t\t\t"+this.IdKey+".csv");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	        System.out.println("File'"+myObj.getName()+"' already exists.");
	      }
	      try {
	    	  FileWriter myFileWriter= new FileWriter("# migração nº"+this.index+":\t"+this.beginTime+"||\t "+this.simulationIdkey+"||\t"+this.simulationType+"||\t\t\t"+this.IdKey+".csv");
	    	  try {
	    		  
	    		  try {
	    			  File MigrationsFileObject = new File("MigrationsTable.csv");
	    			  
	    			  if(MigrationsFileObject.createNewFile()) {
	    				  try {
	    			    	  FileWriter myFileWriter2= new FileWriter("# migração nº"+this.index+":\t"+this.beginTime+"||\t "+this.simulationIdkey+"||\t"+this.simulationType+"||\t\t\t"+this.IdKey+".csv");
                             myFileWriter2.write(buffer2);
	    			    	  myFileWriter2.close();
	    				  }catch(Exception e) {
	    					  System.out.println(e.getMessage()+"\t\n\t"+e.getLocalizedMessage());
	    				  }
	    			  }else{
	    			        System.out.println("File'"+myObj.getName()+"' already exists.");
 
	    			  }
	    			  
	    		  }catch(Exception e) {
	    			  System.out.println(e.getMessage());
	    		  }
	    		  
	    		  
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
		}else {
			System.out.println("\n\tIdKeyy can't be null or empty!\n");
		}
	}
	
	public void setIdKey() {
		this.IdKey=this.getSha256(this.IdKey+this.simulationIdkey+this.index+this.simulationType+this.beginTime+this.VMid);
		this.IdKey=this.getSha256(this.IdKey)+this.index;
		System.out.println("\n\t # migração nº"+this.index+":\t"+this.beginTime+"||\t "+this.simulationIdkey+"||\t"+this.simulationType+"||\t\t\t"+this.IdKey);
	}

	
	public String getId() {
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
}

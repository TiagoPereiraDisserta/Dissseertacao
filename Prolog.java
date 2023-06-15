import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.security.*;

public class Prolog {
	
private String buffer ="";
private ArrayList<MigrationProlog> set= new ArrayList<MigrationProlog>();
private String IdKey=" ";
private Random random=null;
private Long TimeStamp;
private Type type=Type.なし;
private boolean migration=false;


private boolean close=false;
private int[][] VM_PES;
private int[][] DC_HOST_PES;
private int numberOfVMPES;
private LocalDateTime now;
private long finalPosixDate;
private int extraPES;
private int numberOfVM;
private int numbesOFHostsPES;
private int migrationsnumber=-1;
private double CLOUDLET_CPU_INCREMENT_PER_SECOND;
private long duration =Integer.MIN_VALUE;
private Duration durationD=null;
double CLOUDLET_INITIAL_CPU_PERCENTAGE=0.0;
 
private static String atons[]= {"なし","あ","しな","な","し",
		"くそ","そ","そく","任","天","堂","堂任",
		"子犬","犬","子","あ","と","へ",
		"も","よ","ろ","を","ふ","","ら","	や",
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"も","よ","ろ","を","ふ","","ら","	や",
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"ج","ي","س","ص","ق","ر","ش	","ت","","を","ふ","","ら","	や",
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"も","よ","ろ","を","ふ","","ら","	や","ก", "ข","ก ไก่","ฃ ขวด","ฅ คน","จ","ญ","	ญ หญิง","ฎ","ฎ ชฎา","ฐ","ฑ","มณโฑ",
		
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"も","よ","ろ","を","ふ","","ら","	や",
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"ج","ي","س","ص","ق","ر","ش	","ت","","を","ふ","","ら","	や",
		"り", "に","か","た","ち","つ","れ","","a","ま","み","め","む",
		"犬","子","あ","と","へ",
		"犬子あとへ","犬子","あと","へ","▒","♣","✑","✒","✓","✔","✇",
		"犬","子あ","とへ",
		"犬へ","c","d","r","くそ","そ","そく","任","天","くそそ","そく任","天","葡萄牙","葡","萄牙","萄牙",
		"牙","酒","з","画","なし","א","ב","ג","ו","ז","י","\"י\"","צ","ס‎	","ל","שׁ",
		"も","よ","ろ","を","ふ","","ら","	や","ก", "ข","ก ไก่","ฃ ขวด","ฅ คน","จ","ญ","	ญ หญิง","ฎ","ฎ ชฎา","ฐ","ฑ","มณโฑ"};

public static void main(String[] args) {
	
}


   Prolog(){
	   System.out.println("Começar criar ficheiro.");
	   random=new SecureRandom();
	   this.IdKey="."+Instant.now().getEpochSecond();
	   
	   
   }
   public boolean init() {
	   TimeStamp=Instant.now().getEpochSecond();
	   this.IdKey=this.IdKey+random.nextLong()+atons[random.nextInt(0, atons.length-1)];
	   System.out.println(atons[random.nextInt(0, atons.length-1)]);
	   return (close=false);
   }
   
   public boolean terminet() {
	   for(int i =0;i<set.size();i++) {
		   set.get(i).terminet();
		   
	   }
	  
	   return (close=true);
   }
   
   public boolean isClose() {
	   
	   return close;
   }
   
   public void SetType(Type t) {
	   this.type=t;
   }
public boolean Export() {
	buffer="simulation(";
	buffer=buffer+"\""+this.getIdkey()+"\",";
	buffer=buffer+"\""+type+"\",";
	
		
	
	buffer=buffer+this.TimeStamp+",";
	buffer=buffer+this.CLOUDLET_INITIAL_CPU_PERCENTAGE+",";
	CLOUDLET_INITIAL_CPU_PERCENTAGE objT=null;
	objT= new CLOUDLET_INITIAL_CPU_PERCENTAGE(this.IdKey,this.CLOUDLET_INITIAL_CPU_PERCENTAGE);
	
	buffer=buffer+this.numberOfVM+",";
	buffer=buffer+this.numberOfVMPES+",";
	//buffer=buffer+"\""+this.getVM_PES()+"\",";
	
	buffer=buffer+this.numbesOFHostsPES+",";
	//buffer=buffer+"\""+this.getDC_HOST_PES()+"\"";
	buffer=buffer+this.migrationsnumber+",";
	buffer=buffer+this.finalPosixDate+",";
	buffer=buffer+this.duration+",";
	boolean ctrl=false;
	
		durationD=new Duration(this.IdKey,this.duration);
		
	
	
	
	
	
	if(migration) {
		buffer=buffer+"1";
	     MigrationsNumber migrationNumberMN=null;

		migrationNumberMN.Export();
	}else{
		buffer=buffer+"0";
	}
	buffer=buffer+").";
	
	try{
		if(isClose()) {
			
			File myObj = new File("simulations.pl");
			if (myObj.createNewFile()) {
				//funçoes mais constante e definições
		        System.out.println("File created: " + myObj.getName());
		        FileWriter myFileWriter= new FileWriter("simulations.pl",true);
				myFileWriter.write(":-style_check(-discontiguous).\n");
		    	 
		    	  myFileWriter.close();
		        
		      } else {
		        System.out.println("File'"+myObj.getName()+"' already exists.");
		      }
			//Base de dados
			FileWriter myFileWriter= new FileWriter("simulations.pl",true);
			myFileWriter.write(buffer+"\n");
	    	 
	    	  myFileWriter.close();
			
		}else {
			System.out.println("Não está terminado e logo não para terminar. Termine primeiro.");
			
		}
		if(this.isClose()&&this.terminet()) {
		for(int i=0;i<set.size();i++) {
			   set.get(i).Export();
			   
		   }
		
			durationD.Export();
		
		
			
			objT.Export();
		
		
		}else {
			return false;
		}
		return true;
	}catch(Exception e) {
		return false;
	}
	
	 
}

public void setMigration() {
	this.migration=true;
	this.set.add(new MigrationProlog(this.IdKey));
}

public void setIdKey() {
	this.IdKey=getSha256(this.IdKey);
}

public void setIdKey(String IdKey) {
	this.IdKey=IdKey;
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

		public void setNumberOfHOSTPES() {
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
			LocalDateTime now2 = LocalDateTime.now();
			this.finalPosixDate=Instant.now().getEpochSecond();
		}

		public void setFinalPOSIXDate(long  finalPosixDate) {
			
			this.finalPosixDate=finalPosixDate;
		}
		public void setInicialDate(LocalDateTime now) {
			this.now=now;
			
			
		}
		
		public void setCLOUDLET_INITIAL_CPU_PERCENTAGE(double CLOUDLET_INITIAL_CPU_PERCENTAGE) {
			this.CLOUDLET_INITIAL_CPU_PERCENTAGE=CLOUDLET_INITIAL_CPU_PERCENTAGE;
			
		}

		public void setNumberofVMPES(int nVMPES) {
			this.numberOfVMPES=nVMPES;
		}
		public void setHostPESMatrix(int Matrix[][]) {
			this.DC_HOST_PES=Matrix;
		}
		public void setVMPESMatrxi(int Matrix[][]) {
			this.VM_PES=Matrix;
		}

		

		public String getMigration() {
			if(migration==true) {
				return "sim;true";
			}else {
				return "não;false";
			}
		}
		private   String getDC_HOST_PES() {
			String answer ="'{";
			for(int i=0;i<this.DC_HOST_PES.length-1;i++) {
				answer= answer+"{";
				for(int j=0;j<this.DC_HOST_PES[i].length-1;j++) {
					answer=answer+this.DC_HOST_PES[i][j]+";";
					
				}
				answer=answer+this.DC_HOST_PES[i][DC_HOST_PES[i].length-1];
				answer=answer+"};";
				
			}
			   answer=answer+"{";
			   for(int j=0;j<this.DC_HOST_PES[DC_HOST_PES.length-1].length-1;j++) {
					answer=answer+this.DC_HOST_PES[DC_HOST_PES.length-1][j]+";";
					
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
					answer=answer+this.VM_PES[i][j]+";";
					
				}
				answer=answer+this.VM_PES[i][VM_PES[i].length-1];
				answer=answer+"};";
				
			}
			   answer=answer+"{";
			   for(int j=0;j<this.VM_PES[VM_PES.length-1].length-1;j++) {
					answer=answer+this.VM_PES[VM_PES.length-1][j]+";";
					
				}
				answer=answer+this.VM_PES[VM_PES.length-1][VM_PES[VM_PES.length-1].length-1];
				answer=answer+"}";
		
			return answer+"}'";
		}
		
		public  void setDuration(long duration) {
			this.duration=duration;
		}
		
		private class  MigrationProlog{
			
			String IdKey="";
			String SimulationIdKey=null;
			String salt=null;
			long MigrationTimeStand=0;
			boolean termine=false;
			
			MigrationProlog(String simulationIdKey){
				this.SimulationIdKey=simulationIdKey;
				this.salt=atons[random.nextInt(0, atons.length-1)];
				this.MigrationTimeStand=Instant.now().getEpochSecond();
				System.out.println(this.salt);
				System.out.println("houve uma migração com o salt:\t"+salt+"no momento:\t"+this.MigrationTimeStand);
			}
			
			public void terminet() {
				this.IdKey=getSha256(this.SimulationIdKey+this.MigrationTimeStand+this.salt);
				this.termine=true;
			}
			  public void Export() {
				  if(true) {
				  
				  String buffer="migration(";
				  buffer=buffer+"\""+this.IdKey+"\",\""+this.SimulationIdKey+"\","+this.MigrationTimeStand+",\""+this.salt+"\"";
				  buffer=buffer+").";
				  try {
					//Base de dados
						FileWriter myFileWriter= new FileWriter("simulations.pl",true);
						myFileWriter.write(buffer+"\n");
				    	 System.out.println(buffer+"\n");
				    	  myFileWriter.close();
				  }catch(Exception e) {
					  
				  }
				  
				  buffer=buffer+").";
				  }else {
					  System.out.println("Tem terminar primeiro");
				  }
			  }
			
		}
		public  void setMigrationsNumber(int migrationsnumber) {
			this.migrationsnumber=migrationsnumber;
		}
		
		public void setStartTime(Long startTime) {
			this.TimeStamp=startTime;
		}
		
		public void setDuration() {
			this.duration=this.finalPosixDate-this.TimeStamp;
		}
		
		
		private class Duration{
			long duration= -1*Integer.MAX_VALUE;
			String SimulationIDKey =null;
			boolean terminet=false;
			
			
			Duration(String IdKey,long Duration){
				this.duration=Duration;
				this.SimulationIDKey=IdKey;
			}
			public void setDuration(long Duration) {
				this.duration=Duration;
			}
			
			public boolean  Export() {
				boolean answer=false;
				if(true) {
					String buffer="duration(";
					buffer=buffer+"\""+this.SimulationIDKey+"\","+this.duration;
					 buffer=buffer+").";
					try {
						FileWriter myFileWriter= new FileWriter("simulations.pl",true);
						myFileWriter.write(buffer+"\n");
				    	 System.out.println(buffer);
				    	  myFileWriter.close();
						
						answer=true;
					}catch(Exception e) {
						answer=false;
					}
					
					
					
				}else {
					
					answer=false;
					
				}
				
				
				return true;
			}
		}
		
		
		class MigrationsNumber{
			
			String SimulationIDKey ="";
			int MigrationsNumber=-1*Integer.MAX_VALUE;
			boolean terminet=false;
			
			MigrationsNumber(String SimulationIDKey,int number){
				this.SimulationIDKey=SimulationIDKey;
				MigrationsNumber=number;
				
			}
			
			
			public boolean Export() {
				boolean answer=false;
				String buffer="migrationsNumber(";
				buffer=buffer+").";
				if(true) {
					try {
						FileWriter myFileWriter= new FileWriter("simulations.pl",true);
						myFileWriter.write(buffer+"\n");
				    	 System.out.println(buffer);
				    	  myFileWriter.close();
						
						
						answer=true;
						
					}catch(Exception e) {
						answer=true;
					}
				}else {
					answer=false;
				}
				
				return answer;
				
			}
		}
		
		
		private   class CLOUDLET_INITIAL_CPU_PERCENTAGE{
			String SimulationIDKey ="";

			
			private double CLOUDLET_INITIAL_CPU_PERCENTAGE=0;
			
			CLOUDLET_INITIAL_CPU_PERCENTAGE(String id, double CLOUDLET_INITIAL_CPU_PERCENTAGE){
				this.CLOUDLET_INITIAL_CPU_PERCENTAGE=CLOUDLET_INITIAL_CPU_PERCENTAGE;
				this.SimulationIDKey=id;
			}
			
			private void Export() {
				/*CLOUDLET_INITIAL_CPU_PERCENTAGE*/
				String buffer = "cloudlet_initial_cpu_percentage(";
				buffer=buffer+"\""+this.SimulationIDKey+"\","+this.CLOUDLET_INITIAL_CPU_PERCENTAGE;
				buffer=buffer+").";
				
				try {
					FileWriter myFileWriter= new FileWriter("simulations.pl",true);
					myFileWriter.write(buffer+"\n");
			    	 System.out.println(buffer);
			    	  myFileWriter.close();
				}catch(Exception e) {
					
				}
			}
		}
		public void setCLOUDLET_CPU_INCREMENT_PER_SECOND(double CLOUDLET_CPU_INCREMENT_PER_SECOND) {
			this.CLOUDLET_CPU_INCREMENT_PER_SECOND=CLOUDLET_CPU_INCREMENT_PER_SECOND;
		}
		private class CLOUDLET_CPU_INCREMENT_PER_SECOND{
			String SimulationIDKey ="";

			
			private int CLOUDLET_CPU_INCREMENT_PER_SECOND=0;		
			CLOUDLET_CPU_INCREMENT_PER_SECOND(String SimulationIDKey, int CLOUDLET_CPU_INCREMENT_PER_SECOND){
				this.SimulationIDKey=SimulationIDKey;
				this.CLOUDLET_CPU_INCREMENT_PER_SECOND=CLOUDLET_CPU_INCREMENT_PER_SECOND;
			}
			
			public void Export() {
				try {
					FileWriter myFileWriter= new FileWriter("simulations.pl",true);
					myFileWriter.write(buffer+"\n");
			    	 System.out.println(buffer);
			    	  myFileWriter.close();
				}catch(Exception e) {
					
					
				}
			}
		}
		
		
		private  class Matrix{
			Row rows[]= new Row[10];			
			
			
			private class Row{
				
				
			}
 
}
	
}


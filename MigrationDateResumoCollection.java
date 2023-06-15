import java.security.MessageDigest;
import java.util.ArrayList;
import java.time.Instant;
import java.time.format.DateTimeFormatter;


public class MigrationDateResumoCollection {
	private ArrayList<MigrationDateResume> content = new ArrayList<MigrationDateResume>();
	private int size=1;
	private String IdKey =".";
	MigrationDateResumoCollection(){
		
	}
	
	public void init() {
		size=0;
		this.setIdKey();
		System.out.println("\n\t init \t\n");
		
	}
	
	public void addAndInit(String simulationIdKey,Type type, int index ) {
		MigrationDateResume atarashi = new MigrationDateResume(simulationIdKey,index,type);
		content.get(this.size).setBeginTime();
		size++;
		

	}
	
	public int getSize() {
		return size;
	}
	
	public MigrationDateResume getById(String IdKey) {
		
		MigrationDateResume answer=null;
		for(int i=0;i<size;i++) {
			MigrationDateResume tmp=content.get(i);
			if(tmp.getId().equals(IdKey)) {
				answer=tmp;
				break;
			}else {
				answer=null;
			}
		}
		
		return answer;
	}
	
public MigrationDateResume getByIndex(int i) {
		
		MigrationDateResume answer=null;
		answer=content.get(i);
		
		return answer;
	}

public boolean isEmpty() {
	if(size<=0) {
		return true;
	}else {
	return false;
	}
}


public int getIndexbyMigrationDataResumo(MigrationDateResume migrationDateResume) {
	int answer=-1;
	for(int i=0;i<this.size;i++) {
		MigrationDateResume tmp= content.get(i);
		if(tmp.equals(migrationDateResume)) {
			answer=i;
		
	}
	}
	if(answer<0) {
		System.out.println("Não existe seguinte objecto.");
	}else {
		
	}
	
	return  answer;
	
}


public void ExportCollection() {
	if((size==0 || size==-1) && this.isEmpty()) {
	System.out.println("Collection is empty !!");
	}else {
		for(int i=0;i<size;i++) {
			content.get(i).Exportar();
		}		
	}
	
}

 private  void setIdKey() {
	 for(int i=0;i<6;i++) {
	this.IdKey=this.getSha256(this.IdKey+"."+Instant.now().getEpochSecond());
	 }
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

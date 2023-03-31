import java.util.*;



import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.CloudletSimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimEntity;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder; 


public class sample {
  public static void main(String args[]) {
	  
  CloudSim simulation = new CloudSim();
  
  var broker0 = new DatacenterBrokerSimple(simulation);
  
  /* criação de hospede com seguintes carateristas: 
   *     - 2 GB Ram
   *     - 30 GB de Armazenamento
   *     - 5GB de largura de banda*/
  
  
  long ram0 = 20000; //in Megabytes
  long storage0 = 300000; //in Megabytes
  long bw0 = 5000; //in Megabits/s
  
  
 
  var host0 = new HostSimple(ram0, bw0, storage0, List.of(new PeSimple(20000)));
  /* criação de hospede com seguintes carateristas: 
   *     - 1 GB Ram
   *     - 30 GB de Armazenamento
   *     - 1GB de largura de banda*/
  
  long ram1 = 20000; //in Megabytes
  long storage1 = 300000; //in Megabytes
  long bw1 = 5000; //in Megabits/s
  var host1 = new HostSimple(ram1, bw1, storage1, List.of(new PeSimple(20000)));
  
  var vm0 = new VmSimple(1000, 1);
  vm0.setRam(4005).setBw(4000).setSize(5000);
  
  var dc0 = new DatacenterSimple(simulation, List.of(host0));
  var dc1= new DatacenterSimple(simulation,List.of(host1));
  
  var utilizationModel = new UtilizationModelDynamic(0.4);
  var cloudlet0 = new CloudletSimple(10000, 1, utilizationModel);
  System.console();
  System.out.println("\t"+System.currentTimeMillis());
  
  var cloudlet1 = new CloudletSimple(10000, 1, utilizationModel);
  var cloudlet2 = new CloudletSimple(10000, 1, utilizationModel);
  var cloudlet3 = new CloudletSimple(10000, 1, utilizationModel);

  var cloudletList = List.of(cloudlet0, cloudlet1,cloudlet2,cloudlet3);
  
  
  broker0.submitCloudletList(cloudletList);
  
	
  broker0.submitVmList(List.of(vm0));

	simulation.start();
	
	new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();

	
	  
  }
}

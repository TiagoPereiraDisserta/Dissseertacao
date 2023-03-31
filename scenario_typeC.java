import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicy;
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyAbstract;
import org.cloudbus.cloudsim.allocationpolicies.migration.VmAllocationPolicyMigration;
import org.cloudbus.cloudsim.allocationpolicies.migration.VmAllocationPolicyMigrationFirstFitStaticThreshold;
import org.cloudbus.cloudsim.brokers.DatacenterBroker;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.cloudlets.CloudletSimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.resources.Pe;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.selectionpolicies.VmSelectionPolicy;
import org.cloudbus.cloudsim.selectionpolicies.VmSelectionPolicyMinimumUtilization;
import org.cloudbus.cloudsim.util.TimeUtil;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModel;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.builders.tables.HostHistoryTableBuilder;
import org.cloudsimplus.listeners.DatacenterBrokerEventInfo;
import org.cloudsimplus.listeners.VmHostEventInfo;
import org.cloudsimplus.util.Log;

import ch.qos.logback.classic.Level;



public class scenario_typeC {
	
  public static String description() {
	  return " Simples  Maquina Virtual habitar uma host num DataCneter.";
	  
  }
  public static void main(String args[]) {
	  
	  
  
	  
  CloudSim simulation = new CloudSim();
  
  Random random = new Random();
  
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
  
  long ram1 = 1000; //in Megabytes
  long storage1 = 300000; //in Megabytes
  long bw1 = 5000; //in Megabits/s
  var host1 = new HostSimple(ram1, bw1, storage1, List.of(new PeSimple(20000)));
  var host2 = new HostSimple(ram1, bw1, storage1, List.of(new PeSimple(20000)));

  
  var vm0 = new VmSimple(1000, 1);
  vm0.setRam(500).setBw(1000).setSize(4500);
  
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



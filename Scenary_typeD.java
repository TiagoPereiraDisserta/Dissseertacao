
/* Selecionar aleatóriamente  o host para VM */

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

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;





public  final class Scenary_typeD {
	
	
 private Resumo resumo= new Resumo();
 private static Prolog prolog = new Prolog();
 private int numberOfVMPES=0;
 private int numbesOFHostsPES=0;
 private static Random random = new Random();
 private  CloudSim simulation= null;
 private int numberOfColumns = random.nextInt(2,6);
 private int numberOfLines = random.nextInt(2,6);
 private static final int  SCHEDULING_INTERVAL = random.nextInt(0,4);
 private static  int[][] DC_HOST_PES = {{4, 5}, {8, 8, 8}};
 private static double HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION = random.nextDouble((0.78-(0.78*0.10)),(0.78+(0.78*0.10)));
 private static final double HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION = random.nextDouble((0.1-(0.1*0.10)),(0.2+(0.1*0.50)));

 
 public static String description() {
	  return " Cenario onde distribuição de pes no host e DC é aletório, carateristas da VM é aletorio\n, SCHEDULING_INTERVAL é aletório , carateristas do host é aletorio\n, caracteristas do DC é aletorio, Pes necessarios para VM é aletorio mas semelhrante ao VM, HOST_SEARCH_RETRY_DELAY é aletorio \n "
	  		+ "CLOUDLET_INITIAL_CPU_PERCENTAGE é aletório, HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION é aleatório \n  " 
			 + "";
	  
	  
 }
 
 
 private static final int    VM_MIPS = random.nextInt(50,150);
 private static final int   VM_SIZE = random.nextInt(500,1500);
 private static final int    VM_RAM  = random.nextInt(5000, 15000);
 private static final long   VM_BW   = random.nextInt(1000,3000);
 
 
 private static  int HOST_SEARCH_RETRY_DELAY = random.nextInt(5, 15);
 private static final int   HOST_BW = random.nextInt(8000,32000);
 private static final int    HOST_MIPS = random.nextInt(VM_MIPS,VM_MIPS+1000);
 private static  int[] HOST_RAM = {random.nextInt(VM_RAM,VM_RAM+50000), random.nextInt(VM_RAM,VM_RAM+50000), random.nextInt(VM_RAM,VM_RAM+50000)}; 
 private static final int   HOST_STORAGE = random.nextInt(VM_SIZE,1000000+VM_SIZE);

 
 
 private static final int[][] VM_PES = {{3, 2, 2}, {4, 4, 4}};

 
 private List<DatacenterBrokerSimple> brokerList=null;

 private static final long   CLOUDLET_LENGTH =20_000;
 private static final long   CLOUDLET_FILESIZE = 300;
 private static final long   CLOUDLET_OUTPUT_SIZE = 300;
 
 
 private static final double CLOUDLET_INITIAL_CPU_PERCENTAGE = random.nextDouble(0.5-(0.5*0.25),0.5+(0.5*0.25));

 private static final double CLOUDLET_CPU_INCREMENT_PER_SECOND = random.nextDouble(0.05-(0.05*0.0010),0.05+(0.05*0.0010));

 private  List<Datacenter> datacenterList;

 private int migrationsNumber;
 private int createdVms;
 private int createdCloudlets;
 private int createdHosts;
 


 public static void main(String[] args) {
	 System.out.println("Starting ... ");
	 prolog.init();
	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD/MM/YYYY HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now();  
	 
	System.out.print("Date:"+dtf.format(now));
	 System.out.println("HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION : "+HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+"%");
	 System.out.println("CLOUDLET_INITIAL_CPU_PERCENTAGE : "+CLOUDLET_INITIAL_CPU_PERCENTAGE+"%");
	 System.out.println("CLOUDLET_CPU_INCREMENT_PER_SECOND :"+  CLOUDLET_CPU_INCREMENT_PER_SECOND );
     System.out.println("HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION: "+HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION+"%");
     
     System.out.println();
     System.out.println();
     System.out.println("Host ::");
     System.out.println("\tHOST_MIPS:\t"+HOST_MIPS);
     System.out.println("\tHOST_BW: \t"+HOST_BW );
     System.out.println("\tHOST_STORAGE:\t"+HOST_STORAGE);
     System.out.println();
     System.out.println("Cloudlet\t::");
     System.out.println("\tCLOUDLET_LENGTH:\t"+CLOUDLET_LENGTH);
     System.out.println("\tCLOUDLET_FILESIZE:\t"+CLOUDLET_FILESIZE );
     System.out.println("\tCLOUDLET_OUTPUT_SIZE:\t"+CLOUDLET_OUTPUT_SIZE);
  
     System.out.println();
     System.out.println();
	 new Scenary_typeD();
	 System.out.println("Finish...");
	  dtf = DateTimeFormatter.ofPattern("DD/MM/YYYY HH:mm:ss");  
	  now = LocalDateTime.now();  
	 
	System.out.print("Date:"+dtf.format(now));
	prolog.Export();
	 
	 
 }
 
 public Scenary_typeD(){
	 Log.setLevel(Level.INFO);
	 resumo.setType(Type.D);
	
 	resumo.setCLOUDLET_CPU_INCREMENT_PER_SECOND(CLOUDLET_CPU_INCREMENT_PER_SECOND);
 	resumo.setHOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION(HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION);
 	resumo.setCLOUDLET_INITIAL_CPU_PERCENTAGE(CLOUDLET_INITIAL_CPU_PERCENTAGE);
 	resumo.setSCHEDULING_INTERVAL(SCHEDULING_INTERVAL);
 	resumo.setDC_HOST_PES(DC_HOST_PES);
 	resumo.setSCHEDULING_INTERVAL(SCHEDULING_INTERVAL);
 	resumo.setNumberofVM();
	resumo.setnumberOfVMPES();
	resumo.setNumberofHost();
	resumo.setNumberOfHOSPES();
	resumo.setIdKey();
	prolog.setIdKey(resumo.getIdkey());
	
	 
	 System.out.println("Number of VM PES:"+numberOfVMPES+ "||\t Number of Hosts PES:"+numbesOFHostsPES);
	 System.out.println("Extra PES: "+(this.numbesOFHostsPES-this.numberOfVMPES));

	 System.out.println("Starting " + getClass().getSimpleName());
	 HOST_RAM=configHostsRam();
	 
	 
     
    resumo.setInitialPosixDate();
   
    simulation = new CloudSim();
    resumo.setFinalPOSIXDate();
    resumo.setDuration();
    prolog.setFinalPOSIXDate(resumo.getTime());
    prolog.setFinalPOSIXDate(resumo.getEndTime());
    prolog.setDuration(resumo.getDuration());
    this.datacenterList = createDatacenters();
    this.brokerList = createBrokers();
    createVmsAndCloudlets();
    

    simulation.start();
    printResults();
    
    System.out.println(getClass().getSimpleName() + " finished!");
    resumo.setcreatedVms(createdVms);
    resumo.setNumberOfMigration(migrationsNumber);
    resumo.setNumberOfMigration(migrationsNumber);
    prolog.setMigrationsNumber(migrationsNumber);
    
    resumo.Exportar();
    prolog.terminet();
     
	 
	 
 }
 private void validateConfiguration() {
     final int dcId = 1;
     for (final int[] hostPes : DC_HOST_PES) {
         final int hostsNumber = hostPes.length;
         if(HOST_RAM.length < hostsNumber){
             final var msg = String.format(
                 "The length of array HOST_RAM must be at least equal to the number of Hosts for Datacenter %d.", dcId);
             throw new IllegalStateException(msg);
         }
     }
 
 }
 
 

 
 private List<Datacenter> createDatacenters() {
     final int datacentersNumber = DC_HOST_PES.length;
     return IntStream.range(0, datacentersNumber).mapToObj(this::createDatacenter).toList();
 }
 
 private Datacenter createDatacenter(final int index) {
     final var hostList = createHosts(DC_HOST_PES[index]);
     final VmAllocationPolicy allocationPolicy = createVmAllocationPolicy();
     final Datacenter dc = new DatacenterSimple(simulation, hostList, allocationPolicy);
     dc.setSchedulingInterval(SCHEDULING_INTERVAL)
       .setHostSearchRetryDelay(HOST_SEARCH_RETRY_DELAY);

     final String hostsStr =
         hostList.stream()
                 .map(host -> String.format("Host %d w/ %d PEs", host.getId(), host.getNumberOfPes()))
                 .collect(joining(", "));
     System.out.printf("%s: %s%n", dc, hostsStr);
     return dc;
 }
 
 
 private List<Host> createHosts(final int[] pesNumberArray) {
     final List<Host> list = new ArrayList<>(DC_HOST_PES.length);
     for (int i = 0; i < pesNumberArray.length; i++) {
         final long ram = HOST_RAM[i];
         list.add(createHost(pesNumberArray[i], ram));
     }

     return list;
 }
 
 public Host createHost(final int pesNumber, final long ram) {
     final var peList = createPeList(pesNumber);
     final var host = new HostSimple(ram, HOST_BW, HOST_STORAGE, peList);
     host.setId(createdHosts++);
     host.setVmScheduler(new VmSchedulerTimeShared());
     host.enableStateHistory();
     return host;
 }
 
 public List<Pe> createPeList(final int pesNumber) {
     final var peList = new ArrayList<Pe>(pesNumber);
     for(int i = 0; i < pesNumber; i++) {
         peList.add(new PeSimple(HOST_MIPS));
     }

     return peList;
 }
 
 private VmAllocationPolicyMigration createVmAllocationPolicy() {
     final var policy = new VmAllocationPolicyMigrationFirstFitStaticThreshold(
         new VmSelectionPolicyMinimumUtilization(), 0.9);

     policy.setUnderUtilizationThreshold(HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION);
     return policy;
 }
 
 private int[] configHostsRam() {
	 int[] answer = new int[numberOfLines];
	 for(int i=0;i<numberOfLines;i++) {
		 answer[i]=random.nextInt(VM_RAM,VM_RAM+100000);
	 }
	 return answer;
 }
 private List<DatacenterBrokerSimple> createBrokers() {
     return datacenterList.stream().map(dc -> new DatacenterBrokerSimple(simulation)).toList();
 }
 
 private double getCpuUsageIncrement(final UtilizationModelDynamic um){
     return um.getUtilization() + um.getTimeSpan()*CLOUDLET_CPU_INCREMENT_PER_SECOND;
  }
 private UtilizationModelDynamic createCpuUtilizationModel(double initialCpuUsagePercent, double maxCpuUsagePercentage) {
     if(maxCpuUsagePercentage < initialCpuUsagePercent){
         throw new IllegalArgumentException("Max CPU usage must be equal or greater than the initial CPU usage.");
     }

     initialCpuUsagePercent = Math.min(initialCpuUsagePercent, 1);
     maxCpuUsagePercentage = Math.min(maxCpuUsagePercentage, 1);
     final UtilizationModelDynamic um;
     if (initialCpuUsagePercent < maxCpuUsagePercentage) {
         um = new UtilizationModelDynamic(initialCpuUsagePercent)
             .setUtilizationUpdateFunction(this::getCpuUsageIncrement);
     }
     else um = new UtilizationModelDynamic(initialCpuUsagePercent);

     um.setMaxResourceUtilization(maxCpuUsagePercentage);
     return um;
 }
 
 private void createVmsAndCloudlets() {
     int i = 0;
     for (final var broker : brokerList) {
         createAndSubmitVms(broker, VM_PES[i++]);
         createAndSubmitCloudlets(broker);
         broker.addOnVmsCreatedListener(this::onVmsCreatedListener);
     }
 }
 public void createAndSubmitVms(final DatacenterBroker broker, final int[] vmPesArray) {
     final List<Vm> list = Arrays.stream(vmPesArray).mapToObj(this::createVm).toList();
     broker.submitVmList(list);
     list.forEach(vm -> vm.addOnMigrationStartListener(this::startMigration));
 }
 
 public void createAndSubmitCloudlets(final DatacenterBroker broker) {
     final var cloudletList = new ArrayList<Cloudlet>(VM_PES.length);
     final UtilizationModelDynamic um = createCpuUtilizationModel(CLOUDLET_INITIAL_CPU_PERCENTAGE, 1);
     for(final var vm: broker.getVmWaitingList()){
         final var cloudlet = createCloudlet(vm, um);
         cloudletList.add(cloudlet);
     }

     broker.submitCloudletList(cloudletList);
 }
 
 public Vm createVm(final int pes) {
     return new VmSimple(createdVms++, VM_MIPS, pes)
                 .setRam(VM_RAM)
                 .setBw(VM_BW)
                 .setSize(VM_SIZE);
 }
 
 public Cloudlet createCloudlet(final Vm vm, final UtilizationModel cpuUtilizationModel) {
     final var utilizationModelFull = new UtilizationModelFull();

     final var broker = vm.getBroker();
     final Cloudlet cloudlet =
         new CloudletSimple(createdCloudlets++, CLOUDLET_LENGTH, vm.getNumberOfPes())
             .setFileSize(CLOUDLET_FILESIZE)
             .setOutputSize(CLOUDLET_OUTPUT_SIZE)
             .setUtilizationModelRam(utilizationModelFull)
             .setUtilizationModelBw(utilizationModelFull)
             .setUtilizationModelCpu(cpuUtilizationModel);
     broker.bindCloudletToVm(cloudlet, vm);

     return cloudlet;
 }
 
 private void onVmsCreatedListener(final DatacenterBrokerEventInfo info) {
     final var broker = info.getDatacenterBroker();
     System.out.printf("# All %d VMs submitted to %s have been created.%n", broker.getVmCreatedList().size(), broker);
     datacenterList.stream()
                   .map(dc -> (VmAllocationPolicyMigrationFirstFitStaticThreshold)dc.getVmAllocationPolicy())
                   .forEach(policy -> policy.setOverUtilizationThreshold(HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION));
     broker.removeOnVmsCreatedListener(info.getListener());
 }
 private void startMigration(final VmHostEventInfo info) {
	 prolog.setMigration();
     migrationsNumber++;
 }
 private void printResults() {
     for (final var broker : brokerList) {
         final var cloudletFinishedList = broker.getCloudletFinishedList();
         final Comparator<Cloudlet> cloudletComparator =
             comparingLong((Cloudlet c) -> c.getVm().getHost().getId())
                 .thenComparingLong(c -> c.getVm().getId());
         cloudletFinishedList.sort(cloudletComparator);
         new CloudletsTableBuilder(cloudletFinishedList).setTitle(broker.toString()).build();
         System.out.printf("Number of VM migrations: %d%n", migrationsNumber);
     }

     printHostStateHistory();
 }
 private void printHostStateHistory() {
     System.out.printf(
         "%nHosts CPU usage History (when the allocated MIPS is lower than the requested, it is due to VM migration overhead)%n");
     datacenterList.stream()
                   .map(Datacenter::getHostList).flatMap(List::stream)
                   .filter(h -> !h.getStateHistory().isEmpty())
                   .forEach(this::printHostStateHistory);
 }
 
 private void printHostStateHistory(final Host host) {
     new HostHistoryTableBuilder(host).setTitle(host.toString()).build();
 }


 
}

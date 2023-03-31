



import ch.qos.logback.classic.Level;
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicy;
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
import org.cloudbus.cloudsim.selectionpolicies.VmSelectionPolicyMinimumUtilization;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModel;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.builders.tables.HostHistoryTableBuilder;
import org.cloudsimplus.listeners.DatacenterBrokerEventInfo;
import org.cloudsimplus.listeners.EventListener;
import org.cloudsimplus.listeners.VmHostEventInfo;
import org.cloudsimplus.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.joining;

import java.time.LocalDateTime;



public final class Scenary_TypeH {
    /**
     * @see Datacenter#getSchedulingInterval()
     */
	
	
	
    private static final int  SCHEDULING_INTERVAL = 2;

    /**
     * A matrix where each row defines the PEs capacity for hosts in one Datacenter.
     * Each item is the number of PEs for a Host.
     * The number of rows (length of the matrix) define the number of Datacenters to create.
     * The number of cols (length of each row) define the number of Hosts for each Datacenter.
     *
     * The total number of items define the number of Hosts to create.
     */
    private static final int[][] DC_HOST_PES = {{4, 5}, {8, 8, 8}};
    Resumo resumo= new Resumo();
    ArrayList<MigrationDateResume> migrationList = new ArrayList<MigrationDateResume>();
    /**
     * The percentage of host CPU usage that trigger VM migration
     * due to under utilization (in scale from 0 to 1, where 1 is 100%).
     */
    private static final double HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION = 0.1;

    /**
     * The percentage of host CPU usage that trigger VM migration
     * due to over utilization (in scale from 0 to 1, where 1 is 100%).
     */
    private static double HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION = 0.78;

    /** @see Datacenter#setHostSearchRetryDelay(double) */
    private static  int HOST_SEARCH_RETRY_DELAY = 10;

    /**
     * The time spent during VM migration depend on the
     * bandwidth of the target Host.
     * By default, a {@link Datacenter}
     * uses only 50% of the BW to migrate VMs, while the
     * remaining capacity is used for VM communication.
     * This can be changed by calling
     * {@link DatacenterSimple#setBandwidthPercentForMigration(double)}.
     *
     * <p>The 16000 Mb/s is the same as 2000 MB/s. Since just half of this capacity
     * is used for VM migration, only 1000 MB/s will be available for this process.
     * The time that takes to migrate a Vm depend on the VM RAM capacity.
     * Since VMs in this example are created with 2000 MB of RAM, any migration
     * will take 2 seconds to finish, as can be seen in the logs.
     */
    private static final long   HOST_BW = 16_000L; //Mbps

    private static final int    HOST_MIPS = 1000; //for each PE

    /**
     * RAM capacity for created Hosts.
     * The length of this array must be the length of the largest row on {@link #DC_HOST_PES} matrix.
     */
    private static final long[] HOST_RAM = {50_000, 50_000, 50_000}; //host memory (MB)

    private static final long   HOST_STORAGE = 1_000_000; //host storage (MB)

    /**
     * A matrix where each row defines the number of PEs required by each VM in one Datacenter.
     * The total number of items define the number of VMs to create.
     *
     * The length of this matrix (number of rows) must be equal to the number of datacenters,
     * defined by the length of {@link #DC_HOST_PES}.
     */
    private static final int[][] VM_PES = {{3, 2, 2}, {4, 4, 4}};

    private static final int    VM_MIPS = 100; //for each PE
    private static final long   VM_SIZE = 1000; //image size (MB)
    private static final int    VM_RAM  = 10_000; //VM memory (MB)
    private static final long   VM_BW   = 2000; //Mbps

    private static final long   CLOUDLET_LENGTH = 20_000;
    private static final long   CLOUDLET_FILESIZE = 300;
    private static final long   CLOUDLET_OUTPUT_SIZE = 300;

    /**
     * The percentage of CPU that a cloudlet will use when
     * it starts executing (in scale from 0 to 1, where 1 is 100%).
     * For each cloudlet create, this value is used
     * as a base to define CPU usage.
     * @see #createAndSubmitCloudlets(DatacenterBroker)
     */
    private static final double CLOUDLET_INITIAL_CPU_PERCENTAGE = 0.5;

    /**
     * Defines the speed (in percentage) that CPU usage of a cloudlet
     * will increase during the simulation execution.
     * (in scale from 0 to 1, where 1 is 100%).
     * @see #createCpuUtilizationModel(double, double)
     */
    private static final double CLOUDLET_CPU_INCREMENT_PER_SECOND = 0.05;

    private final List<DatacenterBrokerSimple> brokerList;

    private final CloudSim simulation;
    private final List<Datacenter> datacenterList;

    private int migrationsNumber;
    private int createdVms;
    private int createdCloudlets;
    private int createdHosts;
    
public void setHOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION(int  HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION) {
	 this.HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION= HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION;
	 
	}

    public static void main(final String[] args) {
        new Scenary_TypeH();
        
    }

    private Scenary_TypeH(){
    	
    	resumo.setType(Type.H);
        resumo.setTime(LocalDateTime.now());
    	resumo.setIdKey();
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
    	resumo.setVM_PES(VM_PES);
    	
        Log.setLevel(Level.INFO);
        validateConfiguration();

        System.out.println("Starting " + getClass().getSimpleName());
        simulation = new CloudSim();

        this.datacenterList = createDatacenters();
        this.brokerList = createBrokers();
        createVmsAndCloudlets(); 

        simulation.start();
        resumo.setCLOUDLET_INITIAL_CPU_PERCENTAGE(CLOUDLET_INITIAL_CPU_PERCENTAGE);
        

        printResults();
        System.out.println(getClass().getSimpleName() + " finished!");
        resumo.setcreatedVms(createdVms);
        resumo.setNumberOfMigration(migrationsNumber);
        
        
        resumo.Exportar();
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

        final int datacentersNumber = DC_HOST_PES.length;
        if(VM_PES.length < datacentersNumber) {
            throw new IllegalStateException("" +
                "The number of rows on matrix VM_PES must be at least equal to the number of datacenters, indicating PEs for VMs in each datacenter..");
        }
    }

    /**
     * Creates an VmAllocationPolicy and sets an upper utilization threshold higher than the
     * {@link #HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION}
     * to enable placing VMs which will use more CPU than
     * defined by the value in the mentioned constant.
     * After VMs are all submitted to Hosts, the threshold is changed
     * to the value of the constant.
     * This is used to  place VMs into a Host which will
     * become overloaded in order to trigger the migration.
     */
    private VmAllocationPolicyMigration createVmAllocationPolicy() {
        final var policy = new VmAllocationPolicyMigrationFirstFitStaticThreshold(
            new VmSelectionPolicyMinimumUtilization(), 0.9);

        policy.setUnderUtilizationThreshold(HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION);
        return policy;
    }

    /**
     * Creates one {@link DatacenterBroker} for each Datacenter.
     * @return
     */
    private List<DatacenterBrokerSimple> createBrokers() {
        return datacenterList.stream().map(dc -> new DatacenterBrokerSimple(simulation)).toList();
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

    /**
     * Creates VMs and Cloudlets for each broker.
     */
    private void createVmsAndCloudlets() {
        int i = 0;
        for (final var broker : brokerList) {
            createAndSubmitVms(broker, VM_PES[i++]);
            createAndSubmitCloudlets(broker);
            broker.addOnVmsCreatedListener(this::onVmsCreatedListener);
        }
    }

    /**
     * A listener that is called after all VMs from a broker are created,
     * setting the allocation policy to the default value
     * so that some Hosts will be overloaded with the placed VMs and migration will be fired.
     *
     * The listener is removed after finishing, so that it's called just once,
     * even if new VMs are submitted and created latter on.
     */
    private void onVmsCreatedListener(final DatacenterBrokerEventInfo info) {
        final var broker = info.getDatacenterBroker();
        System.out.printf("# All %d VMs submitted to %s have been created.%n", broker.getVmCreatedList().size(), broker);
        datacenterList.stream()
                      .map(dc -> (VmAllocationPolicyMigrationFirstFitStaticThreshold)dc.getVmAllocationPolicy())
                      .forEach(policy -> policy.setOverUtilizationThreshold(HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION));
        broker.removeOnVmsCreatedListener(info.getListener());
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

    /**
     * Creates a Cloudlet.
     *
     * @param vm the VM that will run the Cloudlets
     * @param cpuUtilizationModel the CPU UtilizationModel for the Cloudlet
     * @return the created Cloudlets
     */
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

    public void createAndSubmitVms(final DatacenterBroker broker, final int[] vmPesArray) {
        final List<Vm> list = Arrays.stream(vmPesArray).mapToObj(this::createVm).toList();
        broker.submitVmList(list);
        list.forEach(vm -> vm.addOnMigrationStartListener(this::startMigration));
    }

    /**
     * A listener method that is called when a VM migration starts.
     * @param info information about the happened event
     *
     * @see Vm#addOnMigrationFinishListener(EventListener)
     */
    private void startMigration(final VmHostEventInfo info) {
        migrationsNumber++;
    	migrationList.add(new MigrationDateResume(resumo.getIdkey(),migrationsNumber,Type.H));

    }

    public Vm createVm(final int pes) {
        return new VmSimple(createdVms++, VM_MIPS, pes)
                    .setRam(VM_RAM)
                    .setBw(VM_BW)
                    .setSize(VM_SIZE);
    }

    /**
     * Creates a CPU UtilizationModel for a Cloudlet.
     * If the initial usage is lower than the max usage, the usage will
     * be dynamically incremented along the time, according to the
     * {@link #getCpuUsageIncrement(UtilizationModelDynamic)}
     * function. Otherwise, the CPU usage will be static, according to the
     * defined initial usage.
     *
     * @param initialCpuUsagePercent the percentage of CPU utilization
     * that created Cloudlets will use when they start to execute.
     * If this value is greater than 1 (100%), it will be changed to 1.
     * @param maxCpuUsagePercentage the maximum percentage of
     * CPU utilization that created Cloudlets are allowed to use.
     * If this value is greater than 1 (100%), it will be changed to 1.
     * It must be equal or greater than the initial CPU usage.
     * @return
     */
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

    /**
     * Increments the CPU resource utilization, that is defined in percentage values.
     * @return the new resource utilization after the increment
     */
    private double getCpuUsageIncrement(final UtilizationModelDynamic um){
       return um.getUtilization() + um.getTimeSpan()*CLOUDLET_CPU_INCREMENT_PER_SECOND;
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
    
    
    
}
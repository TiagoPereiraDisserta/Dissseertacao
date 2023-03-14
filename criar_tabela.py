import csv


header=["Id","Scenary_type","HOST_OVER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION"]
data=[1,"F",0.2]

f=open('table.csv','w')
try:

    writer = csv.writer(f)
    print("criou um ficheiro \n");
    print(header)
    writer.writerow(header)
    writer.writerow(data)
    
except:
    print("ops   .... não consegui escrever ")
 
f.close()


f2=open('table.csv','r');
try:
    reader=csv.reader(f2)
    print("consegui abrir pasta \n")
    rows=[]
    for row in reader:
        rows.append(row)
    print(rows)    
    
except:
    print("ops ... não conesegui abrir")
    
f2.close()

 
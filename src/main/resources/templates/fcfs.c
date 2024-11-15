#include<stdio.h>

typedef struct{
    int pid;
    float at,bt,wt,tat,ct;
} Process;

void swap(Process *a,Process *b){
    Process t=*a;
    *a=*b;
    *b=t;
}

void sort(Process *arr,int n){
    for(int i=0;i<n;i++){
        for(int j=0;j<n-1-i;j++){
            if(arr[j].at>arr[j+1].at){
                swap(&arr[j],&arr[j+1]);
            }
        }
    }
}

int main(){
    int n;
    int pid[100];
    float bt[100],wt[100],tat[100],at[100];
    Process processes[100];
    float avgwt=0,avgtat=0;
    printf("Enter number of process: ");
    scanf("%d",&n);
    printf("Enter Process Id | Arrival Time | Burst Time\n");

    for(int i=0;i<n;i++){
        scanf("%d %f %f",&processes[i].pid,&processes[i].at,&processes[i].bt);
    }

    sort(processes,n);

    for(int i=0;i<n;i++){
        if(i==0){
            processes[i].ct=processes[i].at+processes[i].bt;
            processes[i].wt=0;
            processes[i].tat=processes[i].ct-processes[i].at;
        }
        else {
            processes[i].ct = processes[i-1].ct + processes[i].bt;
            processes[i].tat = processes[i].ct - processes[i].at;
            processes[i].wt = processes[i].tat - processes[i].bt;
        }

        avgwt+=processes[i].wt;
        avgtat+=processes[i].tat;
    }

    printf("\nProcess Id | Arrival Time | Burst Time | Completion Time | Tat | Waiting Time \n");
    for(int i=0;i<n;i++){
        printf("%d %.2f %.2f %.2f %.2f %.2f\n",processes[i].pid,processes[i].at,processes[i].bt,processes[i].ct,processes[i].tat,processes[i].wt);
    }
    
    printf("\nAvg waiting time is %f\n",(float)avgwt/n);
    
    printf("\nAvg TAT is %f\n",(float)avgtat/n);
    return 0;
}
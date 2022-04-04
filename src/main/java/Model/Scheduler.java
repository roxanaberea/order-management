package Model;

import View.GUI;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxServers;
    private int maxClientsPerServer;
    private Strategy strategy;
    private GUI frame;

    public Scheduler(int maxServers, GUI frame) {
        servers = new ArrayList<Server>(maxServers);
        this.frame = frame;
        for(int i = 0; i < maxServers; i++) {
            Server serv = new Server(frame);
            servers.add(serv);
        }
        for (Server q:servers){
            Thread t;
            t = new Thread();
            t.start();
        }
        this.maxServers = maxServers;
    }

    public void setStrategy() {
            this.strategy = new ConcreteStrategyTime();
    }

    public void takeClient(Client c)  {
        strategy.addTask(this.servers, c,frame);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public boolean areServersEmpty(){
        for (Server q: servers){
            if(q.isEmpty()==false)
                return false;
        }
        return true;
    }

    public String toString() {
        String s = "";
        int i = 1;
        for(Server sv : servers) {
            s += "Coada "+i + " ";
            s += sv.toString() + "\n";
            i++;
        }
        return s;
    }

}

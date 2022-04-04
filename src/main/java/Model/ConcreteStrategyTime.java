package Model;

import View.GUI;

import java.util.Iterator;
import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    public void addTask(List<Server> servers, Client c, GUI frame) {
        int time= servers.get(0).getWaitingTime();
        int dest = 0;
        int i = 0;
        for(Iterator<Server> it = servers.iterator(); it.hasNext();){
            Server serv = it.next();
            if(serv.getWaitingTime() < time) {
                time = serv.getWaitingTime();
                dest = i;
            }
            i++;
        }
        servers.get(dest).addClient(c);
        System.out.println("Clientul cu id-ul : "+ c.getId() +" pus in coada "+ (dest+1));
        frame.log("Clientul cu id-ul : "+ c.getId() +" pus in coada "+ (dest+1));
        frame.log("\n");
    }


}


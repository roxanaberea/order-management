package Model;

import View.GUI;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable {
    private int waitingTime;
    private int servingTime;
    private GUI frame;
    private AtomicBoolean running = new AtomicBoolean(false);
    private BlockingQueue<Client> client;
    private volatile boolean empty;

    public Server(GUI frame) {
        this.waitingTime = 0;
        this.servingTime = 0;
        this.client = new LinkedBlockingQueue<Client>(100);
        this.frame = frame;
    }

    public void addClient(Client cl){
            client.add(cl);
            cl.setFinishTime(cl.getFinishTime() + waitingTime);
            waitingTime += cl.getServTime();
            servingTime = cl.getServTime();
            empty = false;
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        Client c = null;
        Scheduler sch=null;
        running.set(true);
        while (running.get()==true) {
            if (!client.isEmpty() ) {
                try {
                    Thread.sleep(1000 * c.getServTime());
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
                c = client.peek();
                if (c != null) {
                    waitingTime -= c.getServTime();
                    if (c.getServTime() - 1 == 0) {
                        sch.takeClient(c);
                        if (client.isEmpty())
                            empty = true;
                    } else
                        c.setServTime(c.getServTime() - 1);
                }

            }

        }
    }

    public int serverLength() {
        int length = 0;

        for(Iterator<Client> it = client.iterator(); it.hasNext();){
            length++;
            it.next();
        }

        return length;
    }


    public BlockingQueue<Client> getClient() {
        return client;
    }

    public int getNumberOfClients() {
        return client.size();
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setServingTime(int servingTime){
        this.servingTime = servingTime;
    }

    public int getServingTime() {
        return servingTime;
    }

    public boolean isEmpty(){
            return client.isEmpty();
        }

    @Override
    public String toString() {
        String s = "";
        if(client.isEmpty()) {
           s+="inchis";
        }
        for(Client c : client) {
            s += "C" + c.getId() + " ";
        }
        return s;
    }
}


package Controller;

import Model.Client;
import Model.Scheduler;
import Model.Server;
import View.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class SimulationManager implements Runnable {
    public static int timeLimit = 100;
    public static int maxServingTime = 20;
    public static int minServingTime = 0;
    public static int nrServers = 3;
    public static int nrClients = 5;
    public static int maxArrivalTime = 20;
    public static int minArrivalTime = 0;


    private Scheduler scheduler; //for queue management and client ditribution
    private ArrayList<Client> generatedClients;
    private GUI frame;
    private static String pathOut;

    public SimulationManager() {
        frame = new GUI();
        generatedClients = new ArrayList<Client>();
        frame.addButtonListener(new ButtonListener());
    }
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.setStarted(true);
            frame.repaint();
            frame.revalidate();
        }
    }

    public int getDataFromUI() {
        int i = 1;
        try {
            this.timeLimit = Integer.parseInt(frame.getSimulationTime());
            this.maxServingTime = Integer.parseInt(frame.getMaxServingTime());
            this.minServingTime = Integer.parseInt(frame.getMinServingTime());
            this.nrServers = Integer.parseInt(frame.getNrServers());
            this.nrClients = Integer.parseInt(frame.getNrClients());
            this.maxArrivalTime = Integer.parseInt(frame.getMaxArrivalTime());
            this.minArrivalTime = Integer.parseInt(frame.getMinArrivalTime());

            if(timeLimit <= 0 || maxServingTime < minServingTime || nrServers <= 0 || nrServers > 20 || nrClients <= 0 || maxArrivalTime < minArrivalTime || minServingTime <= 0 || minArrivalTime < 0) {
                frame.showMessage("Simulation settings are not correctly entered");
            }
        }catch(NumberFormatException e) {
            frame.showMessage("Try introducing another input");
            i = 0;
        }

        return i;
    }

    private void generateRandomClients(int nrOfClients) {
        Random rand = new Random();
        for(int i= 0; i < nrOfClients; i++) {
            int arrivalTime = (int) ((Math.random()*100)/maxArrivalTime);
            int processingTime = (int) ((Math.random()*100)/maxServingTime);
            arrivalTime += minArrivalTime;
            processingTime += minServingTime;

            Client c = new Client(arrivalTime,processingTime,i);
            generatedClients.add(c);

        }
        Collections.sort(generatedClients);
    }

    public void updateState() {
        frame.getLogg().setText("");
        frame.qLog(this.scheduler.toString());
    }

    public String emptyTime(int []empty) {
        String s = "";
        for(int i = 0; i < empty.length; i++) {
            if(empty[i] < 0) {
                empty[i] = 0;
            }
            s += "Queue "+ (i+1) + " empty time:"+ empty[i] + "\n";
        }
        return s;
    }

    public void run() {
        int peakHour = 0; //moment of maximum waiting clients
        int max = 0;
        float averageWaitingTime = 0;
        float averageServingTime = 0;
        boolean start;
        while(true) {
            start = frame.isStarted();
            System.out.println(start);
            if(start == true)
                break;
        }
        int confirm = getDataFromUI();
        int [] emptyTime = new int[nrServers];
        if(confirm == 1) {
            generateRandomClients(nrClients);
            this.scheduler = new Scheduler(nrServers,frame);
            this.scheduler.setStrategy();
            int currentTime = 0;
            Client currentClient;
            while(currentTime < timeLimit) {
                int aux = 0;
                frame.log("T= " + currentTime + "\n");
                if(!generatedClients.isEmpty()) {
                    for(Iterator<Client> it = generatedClients.iterator(); it.hasNext();) {
                        Client c = it.next();
                        if(c.getArrTime() == currentTime) {
                            frame.log("Clientul cu id-ul: "+c.getId()+", timp preluare: "+ currentTime + ", timp procesare: " +c.getServTime()+ ", timp finalizare: " + c.getFinishTime() + "\n");
                            scheduler.takeClient(c);
                            System.out.println(c.toString()+" a fost preluat la timpul: "+currentTime);
                        }
                    }
                }
                System.out.println(currentTime);
                int i = 0;
                for(Server s : scheduler.getServers()) {
                    aux += s.getNumberOfClients();
                    averageWaitingTime += s.getWaitingTime();
                    averageServingTime += s.getServingTime();
                    if(s.getClient().peek() != null) {

                    }else {
                        emptyTime[i] += 1;
                    }
                    i++;
                }
                if(aux > max) {
                    peakHour = currentTime;
                    max = aux;
                }
                updateState();
                for(Server s : scheduler.getServers()) {
                    if(s.getWaitingTime() <= 0) {
                        s.setWaitingTime(0);
                    } else {
                        s.setWaitingTime(s.getWaitingTime()-1);
                    }
                }
                try {
                    Thread.sleep(950);
                } catch (InterruptedException e) {
                    System.out.println("exp");
                }
                currentTime++;
            }
            averageWaitingTime = averageWaitingTime  / (nrServers *timeLimit);
            averageServingTime = averageServingTime / (nrServers *timeLimit);
            frame.showStats("Peak hour: " + peakHour + "\n" + " number of clients: "+max+ "\n" +
                    "Average waiting time:" + averageWaitingTime + "\n" + " Average serving time:" + averageServingTime + "\n" +
                    emptyTime(emptyTime));
        }
    }

//    public static  void setValues( String path, String path2) {
//        FileInputStream f = null;
//        try {
//            f = new FileInputStream(path);
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//            e.printStackTrace();	}
//        InputStreamReader fchar=new InputStreamReader(f);
//        BufferedReader buf=new BufferedReader(fchar);
//        String line = null, line2 = null, line3 = null, line4 = null, line5 = null;
//        try {
//            line=buf.readLine();
//            line2=buf.readLine();
//            line3=buf.readLine();
//            line4=buf.readLine();
//            line5=buf.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int v[]=new int[7];
//        nrOfClients = v[0] = Integer.parseInt(line);
//        nrOfServers = v[1] = Integer.parseInt(line2);
//        timeLimit = v[2] = Integer.parseInt(line3);
//        int i = 3;
//        for(String val: line4.split(",")) {
//            v[i++] = Integer.parseInt(val);
//        }
//        for(String val: line5.split(",")) {
//            v[i++] = Integer.parseInt(val);
//        }
//        try {
//            fchar.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        maxArrivalTime = v[4];
//        minArrivalTime = v[3];
//        maxProcessingTime =v[6];
//        minProcessingTime = v[5];
//        pathOut = path2;
//    }

//    public static void scriereFisier (String path){
//        FileOutputStream outputStream = null;
//        try {
//            outputStream = new FileOutputStream(path);
//        } catch (FileNotFoundException e2) {
//            e2.printStackTrace();
//        }
//        byte[] strToBytes = frameBody();
//        try {
//            outputStream.write(strToBytes);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        //setValues(args[0], args[1]);
        SimulationManager manager = new SimulationManager();
        Thread t = new Thread(manager);
        t.start();
    }

}

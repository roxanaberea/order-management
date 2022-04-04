package Model;

public class Client implements Comparable {
    private int arrTime;
    private int servTime;
    private int finishTime;
    private int id;

    public Client(int arrival, int serving,int id) {
        this.arrTime = arrival;
        this.servTime = serving;
        this.finishTime = arrival + serving;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrTime() {
        return arrTime;
    }

    public void setArrTime(int arrTime) {
        this.arrTime = arrTime;
    }

    public int getServTime() {
        return servTime;
    }

    public void setServTime(int servTime) {
        this.servTime = servTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Clientul cu id-ul: " + id + ", arrivalTime: " + arrTime + ", servingTime: " + servTime + ", finishTime= "
                + finishTime + "\n";
    }

    public int compareTo(Object o) {
        Client c = (Client)o;
        return Integer.compare(this.arrTime, c.getArrTime());
    }

}


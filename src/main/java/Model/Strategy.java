package Model;

import View.GUI;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Client c, GUI frame);
}


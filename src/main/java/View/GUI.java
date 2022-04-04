package View;

import Model.Scheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private JTextField minServingTime = new JTextField(10);
    private JTextField maxServingTime = new JTextField(10);
    private JTextField maxArrivalTime = new JTextField(10);
    private JTextField minArrivalTime = new JTextField(10);
    private JTextField noOfServers = new JTextField(10);
    private JTextField noOfClients = new JTextField(10);
    private JTextField simulationTime = new JTextField(10);
    private JCheckBox strategyTime = new JCheckBox("Time strategy");
    private JTextArea queueLog = new JTextArea();
    private JTextArea logg = new JTextArea();
    private JFrame mainFrame = new JFrame();
    private JPanel setupFrame = new JPanel();
    private JButton start = new JButton("Start");
    private boolean started = false;


    private JPanel tracker = new JPanel();
    private JPanel logger = new JPanel();
    private Scheduler scheduler;

    public GUI() {
        setupFrame.setLayout(new GridLayout(7,2));

        JPanel op1 = new JPanel();
        JLabel l1 = new JLabel("Datele simularii:");
        op1.add(l1);
        op1.setVisible(true);
        setupFrame.add(op1);
        setupFrame.add(new JPanel());

        JPanel op2 = new JPanel();
        JLabel l2 = new JLabel("N:");
        op2.add(l2);
        op2.add(noOfClients);
        setupFrame.add(op2);

        JPanel op3 = new JPanel();
        JLabel l3 = new JLabel("Q:");
        op3.add(l3);
        op3.add(this.noOfServers);
        setupFrame.add(op3);

        JPanel op4 = new JPanel();
        JLabel l4 = new JLabel("timp de simulare:");
        op4.add(l4);
        op4.add(simulationTime);
        setupFrame.add(op4);

        JPanel op = new JPanel();
        JLabel l = new JLabel("MIN arr:");
        op.add(l);
        op.add(minArrivalTime);
        setupFrame.add(op);

        JPanel op5 = new JPanel();
        JLabel l5 = new JLabel("MAX arr:");
        op5.add(l5);
        op5.add(maxArrivalTime);
        setupFrame.add(op5);

        JPanel op6 = new JPanel();
        JLabel l6 = new JLabel("MIN serving:");
        op6.add(l6);
        op6.add(minServingTime);
        setupFrame.add(op6);

        JPanel op7 = new JPanel();
        JLabel l7 = new JLabel("MAX serving:");
        op7.add(l7);
        op7.add(maxServingTime);
        setupFrame.add(op7);

        JPanel op8 = new JPanel();
        op8.setLayout(new FlowLayout());
        op8.add(strategyTime);
        setupFrame.add(op8);

        JPanel op9 = new JPanel();
        op9.add(start);
        setupFrame.add(op9);

        tracker.setLayout(new BoxLayout(tracker,BoxLayout.PAGE_AXIS));
        logg.setEditable(false);
        JScrollPane scrollPanTr = new JScrollPane(logg);
        scrollPanTr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanTr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JLabel lab = new JLabel("Queues");
        tracker.add(lab);
        tracker.add(scrollPanTr);
        tracker.setVisible(true);

        logger.setVisible(true);
        logger.setLayout(new BoxLayout(logger,BoxLayout.Y_AXIS));
        queueLog.setEditable(false);

        JScrollPane scrollPan = new JScrollPane(queueLog);
        scrollPan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPan.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel log = new JLabel("Log of events");
        logger.add(log);
        logger.add(scrollPan);


        mainFrame.add(setupFrame);
        mainFrame.setSize(1270,500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1,3));
        mainFrame.add(tracker);
        mainFrame.add(logger);
        mainFrame.setVisible(true);

    }

    public void showStats(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JTextArea getLogg() {
        return logg;
    }

    public void addButtonListener(ActionListener btn) {
        start.addActionListener(btn);
    }

    public void qLog(String s) {
        logg.append(s);
    }

    public void log(String s) {
        queueLog.append(s);
    }

    public String getMinServingTime() {
        return minServingTime.getText();
    }

    public String getMaxServingTime() {
        return maxServingTime.getText();
    }

    public String getMaxArrivalTime() {
        return maxArrivalTime.getText();
    }

    public String getNrServers() {
        return noOfServers.getText();
    }

    public String getNrClients() {
        return noOfClients.getText();
    }

    public String getSimulationTime() {
        return simulationTime.getText();
    }

    public boolean getStrategyTime() {
        return strategyTime.isSelected();
    }


    public String getMinArrivalTime() {
        return minArrivalTime.getText();
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}


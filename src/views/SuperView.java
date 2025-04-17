package views;

import controllers.AppController;

import javax.swing.*;

public class SuperView extends JFrame {
    protected AppController controller;

    public SuperView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public String getTitle() {
        return "Java Cafe";
    }

}

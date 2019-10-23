package ru.inkrot.kit.doceditor;

import javax.swing.*;

public class Main {

    public Main() {
        Screen screen = new Screen();
        screen.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Main();
    }
}

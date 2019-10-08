package ru.inkrot.kit.texteditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.rmi.RemoteException;

public class Screen extends JFrame implements ActionListener {

    public final String ACTION_NEW = "ACTION_NEW";
    public final String ACTION_OPEN = "ACTION_OPEN";
    public final String ACTION_SAVE = "ACTION_SAVE";

    public static File currentFile = null;

    private JTextArea textArea;

    public Screen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        setLocationRelativeTo(null);
        setTitle("Новый файл *");
        initGui();
    }

    private void initGui() {
        Font font = new Font("Arial", 0, 20);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);

        JMenuItem newBtn = new JMenuItem("Новый");
        newBtn.setFont(font);
        newBtn.addActionListener(this);
        newBtn.setActionCommand(ACTION_NEW);
        fileMenu.add(newBtn);
        //////////
        JMenuItem openBtn = new JMenuItem("Открыть");
        openBtn.setFont(font);
        openBtn.addActionListener(this);
        openBtn.setActionCommand(ACTION_OPEN);
        fileMenu.add(openBtn);
        //////////
        JMenuItem saveBtn = new JMenuItem("Сохранить");
        saveBtn.setFont(font);
        saveBtn.addActionListener(this);
        saveBtn.setActionCommand(ACTION_SAVE);
        fileMenu.add(saveBtn);

        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                textArea.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textArea.setBounds(0, 0, 700, 400);

        add(textArea, BorderLayout.CENTER);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(ACTION_NEW)) {
            currentFile = null;
            setTitle("Новый файл *");
            textArea.setText("");
        } else if (action.equals(ACTION_OPEN)) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Выберите текстовый файл", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                setTitle(chooser.getSelectedFile().getAbsolutePath());
                try {
                    BufferedReader reader = new BufferedReader(
                            new FileReader(currentFile));
                    textArea.setText(reader.readLine());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (action.equals(ACTION_SAVE)) {
            if (currentFile != null) {
                writeFile(currentFile, textArea.getText());
            } else {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                int r = chooser.showOpenDialog(this);
                if (r != JFileChooser.APPROVE_OPTION) return;
                currentFile = chooser.getSelectedFile();
                setTitle(chooser.getSelectedFile().getAbsolutePath());
                writeFile(currentFile, textArea.getText());
            }
        }
    }

    public void writeFile(File file, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));
            writer.write(data);
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

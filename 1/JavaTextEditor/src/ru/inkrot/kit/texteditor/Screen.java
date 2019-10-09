package ru.inkrot.kit.texteditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.rmi.RemoteException;

public class Screen extends JFrame implements ActionListener {

    public final String ACTION_NEW = "ACTION_NEW";
    public final String ACTION_OPEN = "ACTION_OPEN";
    public final String ACTION_SAVE = "ACTION_SAVE";

    public String loadedFileText = null;
    public boolean edited = false;
    public static File currentFile = null;

    private JTextArea textArea;

    public Screen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setTitle("Новый файл *");
        setTitle("Текстовый редактор");
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
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (loadedFileText != null) {
                    if (! loadedFileText.equals(textArea.getText())) {
                        edited = true;
                        Screen.this.setTitle(currentFile.getAbsolutePath() + " *");
                    } else {
                        edited = false;
                        Screen.this.setTitle(currentFile.getAbsolutePath());
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
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
            chooser.setDialogTitle("Открыть");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовый файл", "txt", "text");
            chooser.addChoosableFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (! chooser.getSelectedFile().exists()) {
                    JOptionPane.showMessageDialog(null, "Файл не найден");
                    return;
                }
                currentFile = chooser.getSelectedFile();
                setTitle(chooser.getSelectedFile().getAbsolutePath());
                try {
                    BufferedReader reader = new BufferedReader(
                            new FileReader(currentFile));
                    String text = reader.readLine();
                    textArea.setText(text);
                    loadedFileText = text;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (action.equals(ACTION_SAVE)) {
            if (currentFile != null) {
                writeFile(currentFile, textArea.getText());
            } else {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранить файл");
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    currentFile = fileToSave;
                    setTitle(fileChooser.getSelectedFile().getAbsolutePath());
                    writeFile(currentFile, textArea.getText());
                }
            }
        }
    }

    public void writeFile(File file, String data) {
        try {
            if (! file.exists()) file.createNewFile();
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));
            writer.write(data);
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

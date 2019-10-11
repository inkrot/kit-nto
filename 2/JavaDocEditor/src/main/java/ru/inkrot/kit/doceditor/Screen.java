package ru.inkrot.kit.doceditor;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class Screen extends JFrame implements ActionListener, ItemListener {

    public final String TITLE = "Редактор документов";
    public final String ACTION_NEW = "ACTION_NEW";
    public final String ACTION_OPEN = "ACTION_OPEN";
    public final String ACTION_SAVE = "ACTION_SAVE";
    public final String ACTION_CLOSE = "ACTION_CLOSE";

    public String loadedFileText = null;
    public String loadedFileTextFont = null;
    public int loadedFileTextSize = 0;
    public Color loadedFileTextColor = null;

    //public String currentFileTextFont = null;     textArea.getFont().getFamily()
    //public int currentFileTextSize = 0;           textArea.getFont().getSize()
    //public Color currentFileTextColor = null;     textArea.getForeground()

    public boolean edited = false;

    public static File currentFile = null;

    private JTextArea textArea;

    private JMenuItem saveBtn;

    private JComboBox fontCombo, sizeCombo, colorCombo;

    public Screen() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(725, 422);
        setLocationRelativeTo(null);
        initGui();
        closeEdit();
    }

    private void initGui() {
        UIManager.put("OptionPane.cancelButtonText", "Отменить");
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (checkIsSaved()) System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        Font font = new Font("Arial", 0, 17);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);
        menuBar.add(fileMenu);

        JMenuItem newBtn = new JMenuItem("Новый");
        newBtn.setFont(font);
        newBtn.addActionListener(this);
        newBtn.setActionCommand(ACTION_NEW);
        fileMenu.add(newBtn);

        JMenuItem openBtn = new JMenuItem("Открыть");
        openBtn.setFont(font);
        openBtn.addActionListener(this);
        openBtn.setActionCommand(ACTION_OPEN);
        fileMenu.add(openBtn);

        saveBtn = new JMenuItem("Сохранить");
        saveBtn.setFont(font);
        saveBtn.addActionListener(this);
        saveBtn.setActionCommand(ACTION_SAVE);
        fileMenu.add(saveBtn);

        JMenuItem closeBtn = new JMenuItem("Закрыть");
        closeBtn.setFont(font);
        closeBtn.addActionListener(this);
        closeBtn.setActionCommand(ACTION_CLOSE);
        fileMenu.add(closeBtn);

        menuBar.add(fontCombo = new JComboBox(new String[]{"Шрифт: Arial", "Шрифт: Calibri", "Шрифт: Comic Sans MS", "Шрифт: Impact", "Шрифт: Times New Roman"}));
        menuBar.add(sizeCombo = new JComboBox(new String[]{"Размер: 8", "Размер: 12", "Размер: 16", "Размер: 20", "Размер: 24"}));
        menuBar.add(colorCombo = new JComboBox(new String[]{"Цвет: черный", "Цвет: синий", "Цвет: желтый", "Цвет: зеленый", "Цвет: красный"}));

        fontCombo.addItemListener(this);
        sizeCombo.addItemListener(this);
        colorCombo.addItemListener(this);

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

            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (loadedFileText != null) {
                    if (! loadedFileText.equals(textArea.getText())) setEdited(true);
                    else setEdited(false);
                }
            }
        });
        add(textArea, BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED) return;
        JComboBox c = (JComboBox) e.getSource();
        String option = (String) c.getSelectedItem();
        if (c.equals(fontCombo)) {
            String value = option.substring(7);
            textArea.setFont(new Font(value, 0, textArea.getFont().getSize()));
        } else if (c.equals(sizeCombo)) {
            String value = option.substring(8);
            textArea.setFont(new Font(textArea.getFont().getFamily(), 0, Integer.parseInt(value)));
        } else if (c.equals(colorCombo)) {
            String value = option.substring(6);
            textArea.setForeground(strToColor(value));
        }
        setEdited(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(ACTION_NEW)) {
            if (! checkIsSaved()) return;
            currentFile = null;
            setTitle("Новый файл");
            textArea.setText("");
            edited = false;
            loadedFileText = "";
            openEdit();
        } else if (action.equals(ACTION_OPEN)) {
            if (! checkIsSaved()) return;

            loadWord();
            openEdit();

            /*
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Открыть");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Документ", "doc", "docx");
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
                    BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) sb.append(line).append('\n');
                    String text = sb.toString().substring(0, sb.length() - 1);
                    textArea.setText(text);
                    loadedFileText = text;
                    openEdit();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }*/
        } else if (action.equals(ACTION_SAVE)) {
            saveCurrentFile();
        } else if (action.equals(ACTION_CLOSE)) {
            if (! checkIsSaved()) return;
            textArea.setText("");
            setEdited(false);
            closeEdit();
        }
    }

    public void setEdited(boolean edited) {
        if (edited) {
            if (currentFile != null) Screen.this.setTitle(currentFile.getAbsolutePath() + " *");
            else Screen.this.setTitle("Новый файл *");
        } else {
            if (currentFile != null) Screen.this.setTitle(currentFile.getAbsolutePath());
            else Screen.this.setTitle("Новый файл");
        }
        this.edited = edited;
    }

    public void closeEdit() {
        setTitle(TITLE);
        fontCombo.setVisible(false);
        sizeCombo.setVisible(false);
        colorCombo.setVisible(false);
        saveBtn.setEnabled(false);
        textArea.setEnabled(false);
    }

    public void openEdit() {
        fontCombo.setVisible(true);
        sizeCombo.setVisible(true);
        colorCombo.setVisible(true);
        saveBtn.setEnabled(true);
        textArea.setEnabled(true);
    }

    public void writeWord() {

    }

    public void loadWord() {
        File file = new File("C:/Users/Islam/Desktop/kit-nto\\2\\JavaDocEditor\\src\\main\\resources\\document.docx");
        try {
            currentFile = file;
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            XWPFParagraph paragraph = paragraphs.get(0);
            XWPFRun run = paragraph.getRuns().get(0);

            loadedFileTextFont = run.getFontFamily();
            loadedFileTextColor = Color.decode("#" + run.getColor());
            loadedFileTextSize = run.getFontSize();
            loadedFileText = paragraph.getText();

            textArea.setFont(new Font(loadedFileTextFont, 0, loadedFileTextSize));
            textArea.setForeground(loadedFileTextColor);
            textArea.setText(loadedFileText);

            fontCombo.setSelectedItem("Шрифт: " + loadedFileTextFont);
            sizeCombo.setSelectedItem("Размер: " + loadedFileTextSize);
            colorCombo.setSelectedItem("Цвет: " + colorToStr(loadedFileTextColor));

            setEdited(false);

            fis.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private Color strToColor(String str) {
        if (str.equals("синий")) return Color.BLUE;
        if (str.equals("желтый")) return Color.YELLOW;
        if (str.equals("зеленый")) return Color.GREEN;
        if (str.equals("красный")) return Color.RED;
        return Color.BLACK;
    }

    private String colorToStr(Color color) {
        if (color.equals(Color.BLUE)) return "синий";
        if (color.equals(Color.YELLOW)) return "желтый";
        if (color.equals(Color.GREEN)) return "зеленый";
        if (color.equals(Color.RED)) return "красный";
        return "черный";
    }

    // return: true - продолжить; false - отмена
    private boolean checkIsSaved()
    {
        if (!edited) return true;
        String what = currentFile == null ? "новый файл" : "\"" + currentFile.getAbsolutePath() + "\"";
        int result = JOptionPane.showConfirmDialog(null, "Сохранить " + what + "?", TITLE, JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            saveCurrentFile();
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return true;
        } else return false;
    }

    public void saveCurrentFile() {
        if (currentFile != null) {
            writeFile(currentFile, textArea.getText());
            setTitle(currentFile.getAbsolutePath());
            edited = false;
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить файл");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Документ (*.docx)", "docx", "doc");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getPath();
                currentFile = new File(path.endsWith(".txt") ? path : path + ".txt");
                writeFile(currentFile, textArea.getText());
                setTitle(currentFile.getAbsolutePath());
                edited = false;
            }
        }
    }

    public void writeFile(File file, String text) {
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

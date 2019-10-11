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

public class Screen extends JFrame implements ActionListener {

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

        JMenu fontMenu = new JMenu("Форматирование");
        fontMenu.setFont(font);
        menuBar.add(fontMenu);

        fontMenu.add(fontCombo = new JComboBox(new String[]{"Шрифт: Arial", "Шрифт: Calibri", "Шрифт: Comic Sans MS", "Шрифт: Impact", "Шрифт: Times New Roman"}));
        fontMenu.add(sizeCombo = new JComboBox(new String[]{"Размер: 8", "Размер: 10", "Размер: 12", "Размер: 14", "Размер: 16"}));
        fontMenu.add(colorCombo = new JComboBox(new String[]{"Цвет: черный", "Цвет: синий", "Цвет: желтый", "Цвет: зеленый", "Цвет: красный"}));

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
                    if (! loadedFileText.equals(textArea.getText())) {
                        edited = true;
                        if (currentFile != null) Screen.this.setTitle(currentFile.getAbsolutePath() + " *");
                        else Screen.this.setTitle("Новый файл *");
                    } else {
                        edited = false;
                        if (currentFile != null) Screen.this.setTitle(currentFile.getAbsolutePath());
                        else Screen.this.setTitle("Новый файл");
                    }
                }
            }
        });
        add(textArea, BorderLayout.CENTER);

        setJMenuBar(menuBar);
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
            edited = false;
            closeEdit();
        }
    }

    public void closeEdit() {
        setTitle(TITLE);
        saveBtn.setEnabled(false);
        textArea.setEnabled(false);
    }

    public void openEdit() {
        saveBtn.setEnabled(true);
        textArea.setEnabled(true);
    }

    public void writeWord() {

    }

    public void loadWord() {
        File file = new File("C:/Users/Admin/Desktop/kit-nto/2/JavaDocEditor/src/main/resources/document.docx");
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            XWPFParagraph paragraph = paragraphs.get(0);
            XWPFRun run = paragraph.getRuns().get(0);
            System.out.println(Color.decode("#" + run.getColor()));
            System.out.println(run.getFontSize());
            System.out.println(run.getFontFamily());
            System.out.println(paragraph.getText());

            textArea.setText(loadedFileText = paragraph.getText());
            loadedFileTextFont = run.getFontFamily();
            loadedFileTextColor = Color.decode("#" + run.getColor());
            loadedFileTextSize = run.getFontSize();
            textArea.setFont(new Font(loadedFileTextFont, 0, loadedFileTextSize));
            textArea.setForeground(loadedFileTextColor);

            fis.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
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

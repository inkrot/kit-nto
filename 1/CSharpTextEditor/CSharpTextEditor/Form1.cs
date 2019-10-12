using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CSharpTextEditor
{
    public partial class Form1 : Form
    {
        string currentFilePath = null;
        string loadedFileText = null;
        bool edited = false;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.Resize += new EventHandler(Form1_OnResize);
            this.FormClosing += Form1_FormClosing;
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!checkIsSaved()) e.Cancel = true;
        }

        private void Form1_OnResize(object sender, System.EventArgs e)
        {
            textBox.Size = new Size(this.Size.Width - 16, this.Size.Height - 65);
        }

        private void newMenuItem_Click(object sender, EventArgs e)
        {
            if (!checkIsSaved()) return;
            currentFilePath = null;
            loadedFileText = "";
            textBox.Text = "";
            this.Text = "Новый файл";
            edited = false;
            textBox.Enabled = true;
            saveMenuItem.Enabled = true;
        }

        private void openMenuItem_Click(object sender, EventArgs e)
        {
            if (!checkIsSaved()) return;
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.InitialDirectory = ".";
            openFileDialog.Filter = "Текстовые файлы (*.txt)|*.txt";
            openFileDialog.FilterIndex = 0;
            openFileDialog.RestoreDirectory = true;

            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                string selectedFileName = openFileDialog.FileName;
                string readText = File.ReadAllText(selectedFileName);
                currentFilePath = selectedFileName;
                this.Text = currentFilePath;
                loadedFileText = readText;
                textBox.Text = readText;
                textBox.Enabled = true;
                saveMenuItem.Enabled = true;
            }
        }

        private void saveMenuItem_Click(object sender, EventArgs e)
        {
            saveCurrentFile(true);
        }

        // return: true - продолжить; false - отмена
        private bool checkIsSaved()
        {
            if (! edited) return true;
            string what = currentFilePath == null ? "новый файл" : "\"" + currentFilePath + "\"";
            DialogResult dialogResult = MessageBox.Show("Сохранить " + what + "?", "Текстовый редактор", MessageBoxButtons.YesNoCancel);
            if (dialogResult == DialogResult.Yes)
            {
                saveCurrentFile(false);
                return true;
            }
            else if (dialogResult == DialogResult.No)
                return true;
            else
                return false;
        }

        private void saveCurrentFile(bool andOpen)
        {
            string text = textBox.Text;
            if (currentFilePath != null)
            {
                System.IO.File.WriteAllText(currentFilePath, text);
                loadedFileText = text;
                this.Text = currentFilePath;
            }
            else
            {
                SaveFileDialog saveFileDialog = new SaveFileDialog();
                saveFileDialog.InitialDirectory = ".";
                saveFileDialog.Filter = "Текстовый файл (*.txt)|*.txt";
                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                {
                    string filename = saveFileDialog.FileName;
                    System.IO.File.WriteAllText(filename, text);
                    if (andOpen)
                    {
                        currentFilePath = filename;
                        this.Text = currentFilePath;
                        loadedFileText = text;
                    }
                }
            }
            edited = false;
        }

        private void textBox_TextChanged(object sender, EventArgs e)
        {
            if (loadedFileText != null)
            {
                if (!loadedFileText.Equals(textBox.Text))
                {
                    if (currentFilePath == null)
                        this.Text = "Новый файл *";
                    else
                        this.Text = currentFilePath + " *";
                    edited = true;
                }
                else if (currentFilePath == null)
                {
                    this.Text = "Новый файл";
                    edited = false;
                }
                else
                {
                    this.Text = currentFilePath;
                    edited = false;
                }
            }
        }

        private void closeMenuItem_Click(object sender, EventArgs e)
        {
            if (!checkIsSaved()) return;
            this.Text = "Текстовый редактор";
            currentFilePath = null;
            loadedFileText = null;
            textBox.Text = "";
            textBox.Enabled = false;
            saveMenuItem.Enabled = false;
            edited = false;
        }
    }
}

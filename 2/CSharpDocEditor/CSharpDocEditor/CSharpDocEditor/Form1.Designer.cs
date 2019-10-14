namespace CSharpDocEditor
{
    partial class Form1
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.файлToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.closeMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.sizeMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.size_8_item = new System.Windows.Forms.ToolStripMenuItem();
            this.size_12_item = new System.Windows.Forms.ToolStripMenuItem();
            this.size_16_item = new System.Windows.Forms.ToolStripMenuItem();
            this.size_20_item = new System.Windows.Forms.ToolStripMenuItem();
            this.size_24_item = new System.Windows.Forms.ToolStripMenuItem();
            this.size_26_item = new System.Windows.Forms.ToolStripMenuItem();
            this.textBox = new System.Windows.Forms.RichTextBox();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.файлToolStripMenuItem,
            this.sizeMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(709, 26);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // файлToolStripMenuItem
            // 
            this.файлToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newMenuItem,
            this.openMenuItem,
            this.saveMenuItem,
            this.closeMenuItem});
            this.файлToolStripMenuItem.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.файлToolStripMenuItem.Name = "файлToolStripMenuItem";
            this.файлToolStripMenuItem.Size = new System.Drawing.Size(56, 22);
            this.файлToolStripMenuItem.Text = "Файл";
            // 
            // newMenuItem
            // 
            this.newMenuItem.Name = "newMenuItem";
            this.newMenuItem.Size = new System.Drawing.Size(154, 22);
            this.newMenuItem.Text = "Новый";
            this.newMenuItem.Click += new System.EventHandler(this.newMenuItem_Click);
            // 
            // openMenuItem
            // 
            this.openMenuItem.Name = "openMenuItem";
            this.openMenuItem.Size = new System.Drawing.Size(154, 22);
            this.openMenuItem.Text = "Открыть";
            this.openMenuItem.Click += new System.EventHandler(this.openMenuItem_Click);
            // 
            // saveMenuItem
            // 
            this.saveMenuItem.Enabled = false;
            this.saveMenuItem.Name = "saveMenuItem";
            this.saveMenuItem.Size = new System.Drawing.Size(154, 22);
            this.saveMenuItem.Text = "Сохранить";
            this.saveMenuItem.Click += new System.EventHandler(this.saveMenuItem_Click);
            // 
            // closeMenuItem
            // 
            this.closeMenuItem.Name = "closeMenuItem";
            this.closeMenuItem.Size = new System.Drawing.Size(154, 22);
            this.closeMenuItem.Text = "Закрыть";
            this.closeMenuItem.Click += new System.EventHandler(this.closeMenuItem_Click);
            // 
            // sizeMenuItem
            // 
            this.sizeMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.size_8_item,
            this.size_12_item,
            this.size_16_item,
            this.size_20_item,
            this.size_24_item,
            this.size_26_item});
            this.sizeMenuItem.Enabled = false;
            this.sizeMenuItem.Name = "sizeMenuItem";
            this.sizeMenuItem.Size = new System.Drawing.Size(125, 22);
            this.sizeMenuItem.Text = "Размер шрифта: 20";
            // 
            // size_8_item
            // 
            this.size_8_item.Name = "size_8_item";
            this.size_8_item.Size = new System.Drawing.Size(152, 22);
            this.size_8_item.Text = "8";
            this.size_8_item.Click += new System.EventHandler(this.size_8_item_Click);
            // 
            // size_12_item
            // 
            this.size_12_item.Name = "size_12_item";
            this.size_12_item.Size = new System.Drawing.Size(152, 22);
            this.size_12_item.Text = "12";
            this.size_12_item.Click += new System.EventHandler(this.size_12_item_Click);
            // 
            // size_16_item
            // 
            this.size_16_item.Name = "size_16_item";
            this.size_16_item.Size = new System.Drawing.Size(152, 22);
            this.size_16_item.Text = "16";
            this.size_16_item.Click += new System.EventHandler(this.size_16_item_Click);
            // 
            // size_20_item
            // 
            this.size_20_item.Name = "size_20_item";
            this.size_20_item.Size = new System.Drawing.Size(152, 22);
            this.size_20_item.Text = "20";
            this.size_20_item.Click += new System.EventHandler(this.size_20_item_Click);
            // 
            // size_24_item
            // 
            this.size_24_item.Name = "size_24_item";
            this.size_24_item.Size = new System.Drawing.Size(152, 22);
            this.size_24_item.Text = "24";
            this.size_24_item.Click += new System.EventHandler(this.size_24_item_Click);
            // 
            // size_26_item
            // 
            this.size_26_item.Name = "size_26_item";
            this.size_26_item.Size = new System.Drawing.Size(152, 22);
            this.size_26_item.Text = "26";
            this.size_26_item.Click += new System.EventHandler(this.size_26_item_Click);
            // 
            // textBox
            // 
            this.textBox.Enabled = false;
            this.textBox.Font = new System.Drawing.Font("Calibri", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.textBox.Location = new System.Drawing.Point(0, 27);
            this.textBox.Name = "textBox";
            this.textBox.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
            this.textBox.Size = new System.Drawing.Size(709, 357);
            this.textBox.TabIndex = 1;
            this.textBox.Text = "";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(709, 384);
            this.Controls.Add(this.textBox);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Form1";
            this.Text = "Редактор документов";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ToolStripMenuItem файлToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveMenuItem;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.RichTextBox textBox;
        private System.Windows.Forms.ToolStripMenuItem closeMenuItem;
        private System.Windows.Forms.ToolStripMenuItem sizeMenuItem;
        private System.Windows.Forms.ToolStripMenuItem size_8_item;
        private System.Windows.Forms.ToolStripMenuItem size_12_item;
        private System.Windows.Forms.ToolStripMenuItem size_16_item;
        private System.Windows.Forms.ToolStripMenuItem size_20_item;
        private System.Windows.Forms.ToolStripMenuItem size_24_item;
        private System.Windows.Forms.ToolStripMenuItem size_26_item;
    }
}


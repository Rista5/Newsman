namespace DataLayerLib
{
    partial class CreatePictureForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.txtName = new System.Windows.Forms.TextBox();
            this.txtDescription = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.openFileDialog = new System.Windows.Forms.OpenFileDialog();
            this.pictureBox = new System.Windows.Forms.PictureBox();
            this.btnOk = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.lblPath = new System.Windows.Forms.Label();
            this.btnEditPath = new System.Windows.Forms.Button();
            this.dgvNews = new System.Windows.Forms.DataGridView();
            this.lblNews = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvNews)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(73, 56);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(49, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Name:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(39, 82);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(83, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "Description:";
            // 
            // txtName
            // 
            this.txtName.Location = new System.Drawing.Point(128, 51);
            this.txtName.Name = "txtName";
            this.txtName.Size = new System.Drawing.Size(195, 22);
            this.txtName.TabIndex = 2;
            // 
            // txtDescription
            // 
            this.txtDescription.Location = new System.Drawing.Point(128, 79);
            this.txtDescription.Multiline = true;
            this.txtDescription.Name = "txtDescription";
            this.txtDescription.Size = new System.Drawing.Size(195, 66);
            this.txtDescription.TabIndex = 3;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(81, 177);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 17);
            this.label3.TabIndex = 4;
            this.label3.Text = "Path:";
            // 
            // openFileDialog
            // 
            this.openFileDialog.FileName = "openFileDialog";
            // 
            // pictureBox
            // 
            this.pictureBox.Location = new System.Drawing.Point(410, 41);
            this.pictureBox.Name = "pictureBox";
            this.pictureBox.Size = new System.Drawing.Size(293, 153);
            this.pictureBox.TabIndex = 6;
            this.pictureBox.TabStop = false;
            // 
            // btnOk
            // 
            this.btnOk.Location = new System.Drawing.Point(380, 302);
            this.btnOk.Name = "btnOk";
            this.btnOk.Size = new System.Drawing.Size(75, 23);
            this.btnOk.TabIndex = 7;
            this.btnOk.Text = "OK";
            this.btnOk.UseVisualStyleBackColor = true;
            this.btnOk.Click += new System.EventHandler(this.btnOk_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.Location = new System.Drawing.Point(474, 302);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(75, 23);
            this.btnCancel.TabIndex = 8;
            this.btnCancel.Text = "Cancel";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // lblPath
            // 
            this.lblPath.AutoSize = true;
            this.lblPath.Location = new System.Drawing.Point(125, 177);
            this.lblPath.Name = "lblPath";
            this.lblPath.Size = new System.Drawing.Size(36, 17);
            this.lblPath.TabIndex = 9;
            this.lblPath.Text = "path";
            // 
            // btnEditPath
            // 
            this.btnEditPath.Location = new System.Drawing.Point(84, 208);
            this.btnEditPath.Name = "btnEditPath";
            this.btnEditPath.Size = new System.Drawing.Size(118, 28);
            this.btnEditPath.TabIndex = 10;
            this.btnEditPath.Text = "Select picture";
            this.btnEditPath.UseVisualStyleBackColor = true;
            this.btnEditPath.Click += new System.EventHandler(this.btnEditPath_Click);
            // 
            // dgvNews
            // 
            this.dgvNews.AllowUserToAddRows = false;
            this.dgvNews.AllowUserToDeleteRows = false;
            this.dgvNews.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvNews.Location = new System.Drawing.Point(94, 261);
            this.dgvNews.Name = "dgvNews";
            this.dgvNews.ReadOnly = true;
            this.dgvNews.RowTemplate.Height = 24;
            this.dgvNews.Size = new System.Drawing.Size(240, 150);
            this.dgvNews.TabIndex = 11;
            // 
            // lblNews
            // 
            this.lblNews.AutoSize = true;
            this.lblNews.Location = new System.Drawing.Point(39, 261);
            this.lblNews.Name = "lblNews";
            this.lblNews.Size = new System.Drawing.Size(46, 17);
            this.lblNews.TabIndex = 12;
            this.lblNews.Text = "News:";
            // 
            // CreatePictureForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.lblNews);
            this.Controls.Add(this.dgvNews);
            this.Controls.Add(this.btnEditPath);
            this.Controls.Add(this.lblPath);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnOk);
            this.Controls.Add(this.pictureBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.txtDescription);
            this.Controls.Add(this.txtName);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "CreatePictureForm";
            this.Text = "CreatePictureForm";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvNews)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtName;
        private System.Windows.Forms.TextBox txtDescription;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.OpenFileDialog openFileDialog;
        private System.Windows.Forms.PictureBox pictureBox;
        private System.Windows.Forms.Button btnOk;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Label lblPath;
        private System.Windows.Forms.Button btnEditPath;
        private System.Windows.Forms.DataGridView dgvNews;
        private System.Windows.Forms.Label lblNews;
    }
}
namespace DataLayerLib
{
    partial class MainForm
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
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.button5 = new System.Windows.Forms.Button();
            this.pictureBox = new System.Windows.Forms.PictureBox();
            this.button6 = new System.Windows.Forms.Button();
            this.btnGetAllNews = new System.Windows.Forms.Button();
            this.btnGetAllUsers = new System.Windows.Forms.Button();
            this.btnGetAllComments = new System.Windows.Forms.Button();
            this.btnGetAllPicctures = new System.Windows.Forms.Button();
            this.btnGetAllAudio = new System.Windows.Forms.Button();
            this.btnUsersWhoModifiedNews = new System.Windows.Forms.Button();
            this.txtUsersWhoModifiedNews = new System.Windows.Forms.TextBox();
            this.btnNewsModifiedByUser = new System.Windows.Forms.Button();
            this.txtNewsModifiedByUser = new System.Windows.Forms.TextBox();
            this.btnGetCommentsForNews = new System.Windows.Forms.Button();
            this.txtGetCommentsForNews = new System.Windows.Forms.TextBox();
            this.btnGetPicturesForNews = new System.Windows.Forms.Button();
            this.txtGetPicturesForNews = new System.Windows.Forms.TextBox();
            this.btnGetAudiosForNews = new System.Windows.Forms.Button();
            this.txtGetAudiosForNews = new System.Windows.Forms.TextBox();
            this.btnCreateNews = new System.Windows.Forms.Button();
            this.btnCreateUser = new System.Windows.Forms.Button();
            this.btnCreateComment = new System.Windows.Forms.Button();
            this.btnCreatePicture = new System.Windows.Forms.Button();
            this.btnCreateAudio = new System.Windows.Forms.Button();
            this.btnUpdateNews = new System.Windows.Forms.Button();
            this.btnUpdateUser = new System.Windows.Forms.Button();
            this.btnUpdateComment = new System.Windows.Forms.Button();
            this.btnUpdatePicture = new System.Windows.Forms.Button();
            this.btnUpdateAudio = new System.Windows.Forms.Button();
            this.btnDeleteNews = new System.Windows.Forms.Button();
            this.btnDeleteUser = new System.Windows.Forms.Button();
            this.btnDeleteComment = new System.Windows.Forms.Button();
            this.btnDeletePicture = new System.Windows.Forms.Button();
            this.btnDeleteAudio = new System.Windows.Forms.Button();
            this.txtUpdateNews = new System.Windows.Forms.TextBox();
            this.txtUpdateUser = new System.Windows.Forms.TextBox();
            this.txtUpdateComment = new System.Windows.Forms.TextBox();
            this.txtUpdatePicture = new System.Windows.Forms.TextBox();
            this.txtUpdateAudio = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(661, 12);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(127, 23);
            this.button1.TabIndex = 0;
            this.button1.Text = "Ucitaj vesti";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(661, 41);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(127, 23);
            this.button2.TabIndex = 1;
            this.button2.Text = "Ucitaj korisnike";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button_ucitaj_korisnike);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(661, 70);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(127, 23);
            this.button3.TabIndex = 2;
            this.button3.Text = "Ucitaj komentare";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button_ucitaj_komentare);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(661, 99);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(127, 23);
            this.button4.TabIndex = 3;
            this.button4.Text = "Ucitaj slike";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button_ucitaj_slike);
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(661, 128);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(127, 23);
            this.button5.TabIndex = 4;
            this.button5.Text = "Ucitaj audio";
            this.button5.UseVisualStyleBackColor = true;
            this.button5.Click += new System.EventHandler(this.button_ucitaj_audio);
            // 
            // pictureBox
            // 
            this.pictureBox.Location = new System.Drawing.Point(683, 186);
            this.pictureBox.Name = "pictureBox";
            this.pictureBox.Size = new System.Drawing.Size(105, 93);
            this.pictureBox.TabIndex = 5;
            this.pictureBox.TabStop = false;
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(661, 157);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(127, 23);
            this.button6.TabIndex = 6;
            this.button6.Text = "Prikazi sliku";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button_prikazi_sliku);
            // 
            // btnGetAllNews
            // 
            this.btnGetAllNews.Location = new System.Drawing.Point(12, 12);
            this.btnGetAllNews.Name = "btnGetAllNews";
            this.btnGetAllNews.Size = new System.Drawing.Size(150, 23);
            this.btnGetAllNews.TabIndex = 7;
            this.btnGetAllNews.Text = "GetAllNews";
            this.btnGetAllNews.UseVisualStyleBackColor = true;
            this.btnGetAllNews.Click += new System.EventHandler(this.btnGetAllNews_Click);
            // 
            // btnGetAllUsers
            // 
            this.btnGetAllUsers.Location = new System.Drawing.Point(12, 41);
            this.btnGetAllUsers.Name = "btnGetAllUsers";
            this.btnGetAllUsers.Size = new System.Drawing.Size(150, 23);
            this.btnGetAllUsers.TabIndex = 8;
            this.btnGetAllUsers.Text = "GetAllUsers";
            this.btnGetAllUsers.UseVisualStyleBackColor = true;
            this.btnGetAllUsers.Click += new System.EventHandler(this.btnGetAllUsers_Click);
            // 
            // btnGetAllComments
            // 
            this.btnGetAllComments.Location = new System.Drawing.Point(12, 70);
            this.btnGetAllComments.Name = "btnGetAllComments";
            this.btnGetAllComments.Size = new System.Drawing.Size(150, 23);
            this.btnGetAllComments.TabIndex = 9;
            this.btnGetAllComments.Text = "GetAllComments";
            this.btnGetAllComments.UseVisualStyleBackColor = true;
            this.btnGetAllComments.Click += new System.EventHandler(this.btnGetAllComments_Click);
            // 
            // btnGetAllPicctures
            // 
            this.btnGetAllPicctures.Location = new System.Drawing.Point(12, 99);
            this.btnGetAllPicctures.Name = "btnGetAllPicctures";
            this.btnGetAllPicctures.Size = new System.Drawing.Size(150, 23);
            this.btnGetAllPicctures.TabIndex = 10;
            this.btnGetAllPicctures.Text = "GetAllPictures";
            this.btnGetAllPicctures.UseVisualStyleBackColor = true;
            this.btnGetAllPicctures.Click += new System.EventHandler(this.btnGetAllPicctures_Click);
            // 
            // btnGetAllAudio
            // 
            this.btnGetAllAudio.Location = new System.Drawing.Point(12, 128);
            this.btnGetAllAudio.Name = "btnGetAllAudio";
            this.btnGetAllAudio.Size = new System.Drawing.Size(150, 23);
            this.btnGetAllAudio.TabIndex = 11;
            this.btnGetAllAudio.Text = "GetAllAudio";
            this.btnGetAllAudio.UseVisualStyleBackColor = true;
            this.btnGetAllAudio.Click += new System.EventHandler(this.btnGetAllAudio_Click);
            // 
            // btnUsersWhoModifiedNews
            // 
            this.btnUsersWhoModifiedNews.Location = new System.Drawing.Point(168, 12);
            this.btnUsersWhoModifiedNews.Name = "btnUsersWhoModifiedNews";
            this.btnUsersWhoModifiedNews.Size = new System.Drawing.Size(214, 23);
            this.btnUsersWhoModifiedNews.TabIndex = 12;
            this.btnUsersWhoModifiedNews.Text = "UsersWhoModifedNews";
            this.btnUsersWhoModifiedNews.UseVisualStyleBackColor = true;
            this.btnUsersWhoModifiedNews.Click += new System.EventHandler(this.btnUsersWhoModifiedNews_Click);
            // 
            // txtUsersWhoModifiedNews
            // 
            this.txtUsersWhoModifiedNews.Location = new System.Drawing.Point(388, 12);
            this.txtUsersWhoModifiedNews.Name = "txtUsersWhoModifiedNews";
            this.txtUsersWhoModifiedNews.Size = new System.Drawing.Size(85, 22);
            this.txtUsersWhoModifiedNews.TabIndex = 13;
            // 
            // btnNewsModifiedByUser
            // 
            this.btnNewsModifiedByUser.Location = new System.Drawing.Point(168, 41);
            this.btnNewsModifiedByUser.Name = "btnNewsModifiedByUser";
            this.btnNewsModifiedByUser.Size = new System.Drawing.Size(214, 23);
            this.btnNewsModifiedByUser.TabIndex = 14;
            this.btnNewsModifiedByUser.Text = "NewsModiefiedByUser";
            this.btnNewsModifiedByUser.UseVisualStyleBackColor = true;
            this.btnNewsModifiedByUser.Click += new System.EventHandler(this.btnNewsModifiedByUser_Click);
            // 
            // txtNewsModifiedByUser
            // 
            this.txtNewsModifiedByUser.Location = new System.Drawing.Point(388, 42);
            this.txtNewsModifiedByUser.Name = "txtNewsModifiedByUser";
            this.txtNewsModifiedByUser.Size = new System.Drawing.Size(85, 22);
            this.txtNewsModifiedByUser.TabIndex = 15;
            // 
            // btnGetCommentsForNews
            // 
            this.btnGetCommentsForNews.Location = new System.Drawing.Point(168, 70);
            this.btnGetCommentsForNews.Name = "btnGetCommentsForNews";
            this.btnGetCommentsForNews.Size = new System.Drawing.Size(214, 23);
            this.btnGetCommentsForNews.TabIndex = 16;
            this.btnGetCommentsForNews.Text = "GetCommentsForNews";
            this.btnGetCommentsForNews.UseVisualStyleBackColor = true;
            this.btnGetCommentsForNews.Click += new System.EventHandler(this.btnGetCommentsForNews_Click);
            // 
            // txtGetCommentsForNews
            // 
            this.txtGetCommentsForNews.Location = new System.Drawing.Point(388, 71);
            this.txtGetCommentsForNews.Name = "txtGetCommentsForNews";
            this.txtGetCommentsForNews.Size = new System.Drawing.Size(85, 22);
            this.txtGetCommentsForNews.TabIndex = 17;
            // 
            // btnGetPicturesForNews
            // 
            this.btnGetPicturesForNews.Location = new System.Drawing.Point(168, 99);
            this.btnGetPicturesForNews.Name = "btnGetPicturesForNews";
            this.btnGetPicturesForNews.Size = new System.Drawing.Size(214, 23);
            this.btnGetPicturesForNews.TabIndex = 18;
            this.btnGetPicturesForNews.Text = "GetPicturesForNews";
            this.btnGetPicturesForNews.UseVisualStyleBackColor = true;
            this.btnGetPicturesForNews.Click += new System.EventHandler(this.btnGetPicturesForNews_Click);
            // 
            // txtGetPicturesForNews
            // 
            this.txtGetPicturesForNews.Location = new System.Drawing.Point(388, 100);
            this.txtGetPicturesForNews.Name = "txtGetPicturesForNews";
            this.txtGetPicturesForNews.Size = new System.Drawing.Size(85, 22);
            this.txtGetPicturesForNews.TabIndex = 19;
            // 
            // btnGetAudiosForNews
            // 
            this.btnGetAudiosForNews.Location = new System.Drawing.Point(168, 128);
            this.btnGetAudiosForNews.Name = "btnGetAudiosForNews";
            this.btnGetAudiosForNews.Size = new System.Drawing.Size(214, 23);
            this.btnGetAudiosForNews.TabIndex = 20;
            this.btnGetAudiosForNews.Text = "GetAudiosForNews";
            this.btnGetAudiosForNews.UseVisualStyleBackColor = true;
            this.btnGetAudiosForNews.Click += new System.EventHandler(this.btnGetAudiosForNews_Click);
            // 
            // txtGetAudiosForNews
            // 
            this.txtGetAudiosForNews.Location = new System.Drawing.Point(388, 129);
            this.txtGetAudiosForNews.Name = "txtGetAudiosForNews";
            this.txtGetAudiosForNews.Size = new System.Drawing.Size(85, 22);
            this.txtGetAudiosForNews.TabIndex = 21;
            // 
            // btnCreateNews
            // 
            this.btnCreateNews.Location = new System.Drawing.Point(12, 227);
            this.btnCreateNews.Name = "btnCreateNews";
            this.btnCreateNews.Size = new System.Drawing.Size(150, 23);
            this.btnCreateNews.TabIndex = 22;
            this.btnCreateNews.Text = "CreateNews";
            this.btnCreateNews.UseVisualStyleBackColor = true;
            this.btnCreateNews.Click += new System.EventHandler(this.btnCreateNews_Click);
            // 
            // btnCreateUser
            // 
            this.btnCreateUser.Location = new System.Drawing.Point(12, 256);
            this.btnCreateUser.Name = "btnCreateUser";
            this.btnCreateUser.Size = new System.Drawing.Size(150, 23);
            this.btnCreateUser.TabIndex = 23;
            this.btnCreateUser.Text = "CreateUser";
            this.btnCreateUser.UseVisualStyleBackColor = true;
            this.btnCreateUser.Click += new System.EventHandler(this.btnCreateUser_Click);
            // 
            // btnCreateComment
            // 
            this.btnCreateComment.Location = new System.Drawing.Point(12, 285);
            this.btnCreateComment.Name = "btnCreateComment";
            this.btnCreateComment.Size = new System.Drawing.Size(150, 23);
            this.btnCreateComment.TabIndex = 24;
            this.btnCreateComment.Text = "CreateComment";
            this.btnCreateComment.UseVisualStyleBackColor = true;
            this.btnCreateComment.Click += new System.EventHandler(this.btnCreateComment_Click);
            // 
            // btnCreatePicture
            // 
            this.btnCreatePicture.Location = new System.Drawing.Point(12, 314);
            this.btnCreatePicture.Name = "btnCreatePicture";
            this.btnCreatePicture.Size = new System.Drawing.Size(150, 23);
            this.btnCreatePicture.TabIndex = 25;
            this.btnCreatePicture.Text = "CreatePicture";
            this.btnCreatePicture.UseVisualStyleBackColor = true;
            this.btnCreatePicture.Click += new System.EventHandler(this.btnCreatePicture_Click);
            // 
            // btnCreateAudio
            // 
            this.btnCreateAudio.Location = new System.Drawing.Point(12, 343);
            this.btnCreateAudio.Name = "btnCreateAudio";
            this.btnCreateAudio.Size = new System.Drawing.Size(150, 23);
            this.btnCreateAudio.TabIndex = 26;
            this.btnCreateAudio.Text = "CreateAudio";
            this.btnCreateAudio.UseVisualStyleBackColor = true;
            // 
            // btnUpdateNews
            // 
            this.btnUpdateNews.Location = new System.Drawing.Point(168, 227);
            this.btnUpdateNews.Name = "btnUpdateNews";
            this.btnUpdateNews.Size = new System.Drawing.Size(150, 23);
            this.btnUpdateNews.TabIndex = 27;
            this.btnUpdateNews.Text = "UpdateNews";
            this.btnUpdateNews.UseVisualStyleBackColor = true;
            this.btnUpdateNews.Click += new System.EventHandler(this.btnUpdateNews_Click);
            // 
            // btnUpdateUser
            // 
            this.btnUpdateUser.Location = new System.Drawing.Point(168, 256);
            this.btnUpdateUser.Name = "btnUpdateUser";
            this.btnUpdateUser.Size = new System.Drawing.Size(150, 23);
            this.btnUpdateUser.TabIndex = 28;
            this.btnUpdateUser.Text = "UpdateUser";
            this.btnUpdateUser.UseVisualStyleBackColor = true;
            this.btnUpdateUser.Click += new System.EventHandler(this.btnUpdateUser_Click);
            // 
            // btnUpdateComment
            // 
            this.btnUpdateComment.Location = new System.Drawing.Point(168, 285);
            this.btnUpdateComment.Name = "btnUpdateComment";
            this.btnUpdateComment.Size = new System.Drawing.Size(150, 23);
            this.btnUpdateComment.TabIndex = 29;
            this.btnUpdateComment.Text = "UpdateComment";
            this.btnUpdateComment.UseVisualStyleBackColor = true;
            // 
            // btnUpdatePicture
            // 
            this.btnUpdatePicture.Location = new System.Drawing.Point(168, 314);
            this.btnUpdatePicture.Name = "btnUpdatePicture";
            this.btnUpdatePicture.Size = new System.Drawing.Size(150, 27);
            this.btnUpdatePicture.TabIndex = 30;
            this.btnUpdatePicture.Text = "UpdatePicture";
            this.btnUpdatePicture.UseVisualStyleBackColor = true;
            this.btnUpdatePicture.Click += new System.EventHandler(this.btnUpdatePicture_Click);
            // 
            // btnUpdateAudio
            // 
            this.btnUpdateAudio.Location = new System.Drawing.Point(168, 343);
            this.btnUpdateAudio.Name = "btnUpdateAudio";
            this.btnUpdateAudio.Size = new System.Drawing.Size(150, 23);
            this.btnUpdateAudio.TabIndex = 31;
            this.btnUpdateAudio.Text = "UpdateAudio";
            this.btnUpdateAudio.UseVisualStyleBackColor = true;
            // 
            // btnDeleteNews
            // 
            this.btnDeleteNews.Location = new System.Drawing.Point(415, 227);
            this.btnDeleteNews.Name = "btnDeleteNews";
            this.btnDeleteNews.Size = new System.Drawing.Size(150, 23);
            this.btnDeleteNews.TabIndex = 32;
            this.btnDeleteNews.Text = "DeleteNews";
            this.btnDeleteNews.UseVisualStyleBackColor = true;
            this.btnDeleteNews.Click += new System.EventHandler(this.btnDeleteNews_Click);
            // 
            // btnDeleteUser
            // 
            this.btnDeleteUser.Location = new System.Drawing.Point(415, 256);
            this.btnDeleteUser.Name = "btnDeleteUser";
            this.btnDeleteUser.Size = new System.Drawing.Size(150, 23);
            this.btnDeleteUser.TabIndex = 33;
            this.btnDeleteUser.Text = "DeleteUser";
            this.btnDeleteUser.UseVisualStyleBackColor = true;
            this.btnDeleteUser.Click += new System.EventHandler(this.btnDeleteUser_Click);
            // 
            // btnDeleteComment
            // 
            this.btnDeleteComment.Location = new System.Drawing.Point(415, 285);
            this.btnDeleteComment.Name = "btnDeleteComment";
            this.btnDeleteComment.Size = new System.Drawing.Size(150, 23);
            this.btnDeleteComment.TabIndex = 34;
            this.btnDeleteComment.Text = "DeleteComment";
            this.btnDeleteComment.UseVisualStyleBackColor = true;
            this.btnDeleteComment.Click += new System.EventHandler(this.btnDeleteComment_Click);
            // 
            // btnDeletePicture
            // 
            this.btnDeletePicture.Location = new System.Drawing.Point(415, 314);
            this.btnDeletePicture.Name = "btnDeletePicture";
            this.btnDeletePicture.Size = new System.Drawing.Size(150, 23);
            this.btnDeletePicture.TabIndex = 35;
            this.btnDeletePicture.Text = "DeletePicture";
            this.btnDeletePicture.UseVisualStyleBackColor = true;
            this.btnDeletePicture.Click += new System.EventHandler(this.btnDeletePicture_Click);
            // 
            // btnDeleteAudio
            // 
            this.btnDeleteAudio.Location = new System.Drawing.Point(415, 343);
            this.btnDeleteAudio.Name = "btnDeleteAudio";
            this.btnDeleteAudio.Size = new System.Drawing.Size(150, 23);
            this.btnDeleteAudio.TabIndex = 36;
            this.btnDeleteAudio.Text = "DeleteAudio";
            this.btnDeleteAudio.UseVisualStyleBackColor = true;
            // 
            // txtUpdateNews
            // 
            this.txtUpdateNews.Location = new System.Drawing.Point(324, 228);
            this.txtUpdateNews.Name = "txtUpdateNews";
            this.txtUpdateNews.Size = new System.Drawing.Size(85, 22);
            this.txtUpdateNews.TabIndex = 37;
            // 
            // txtUpdateUser
            // 
            this.txtUpdateUser.Location = new System.Drawing.Point(324, 257);
            this.txtUpdateUser.Name = "txtUpdateUser";
            this.txtUpdateUser.Size = new System.Drawing.Size(85, 22);
            this.txtUpdateUser.TabIndex = 38;
            // 
            // txtUpdateComment
            // 
            this.txtUpdateComment.Location = new System.Drawing.Point(324, 286);
            this.txtUpdateComment.Name = "txtUpdateComment";
            this.txtUpdateComment.Size = new System.Drawing.Size(85, 22);
            this.txtUpdateComment.TabIndex = 39;
            // 
            // txtUpdatePicture
            // 
            this.txtUpdatePicture.Location = new System.Drawing.Point(324, 314);
            this.txtUpdatePicture.Name = "txtUpdatePicture";
            this.txtUpdatePicture.Size = new System.Drawing.Size(85, 22);
            this.txtUpdatePicture.TabIndex = 40;
            // 
            // txtUpdateAudio
            // 
            this.txtUpdateAudio.Location = new System.Drawing.Point(324, 343);
            this.txtUpdateAudio.Name = "txtUpdateAudio";
            this.txtUpdateAudio.Size = new System.Drawing.Size(85, 22);
            this.txtUpdateAudio.TabIndex = 41;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.txtUpdateAudio);
            this.Controls.Add(this.txtUpdatePicture);
            this.Controls.Add(this.txtUpdateComment);
            this.Controls.Add(this.txtUpdateUser);
            this.Controls.Add(this.txtUpdateNews);
            this.Controls.Add(this.btnDeleteAudio);
            this.Controls.Add(this.btnDeletePicture);
            this.Controls.Add(this.btnDeleteComment);
            this.Controls.Add(this.btnDeleteUser);
            this.Controls.Add(this.btnDeleteNews);
            this.Controls.Add(this.btnUpdateAudio);
            this.Controls.Add(this.btnUpdatePicture);
            this.Controls.Add(this.btnUpdateComment);
            this.Controls.Add(this.btnUpdateUser);
            this.Controls.Add(this.btnUpdateNews);
            this.Controls.Add(this.btnCreateAudio);
            this.Controls.Add(this.btnCreatePicture);
            this.Controls.Add(this.btnCreateComment);
            this.Controls.Add(this.btnCreateUser);
            this.Controls.Add(this.btnCreateNews);
            this.Controls.Add(this.txtGetAudiosForNews);
            this.Controls.Add(this.btnGetAudiosForNews);
            this.Controls.Add(this.txtGetPicturesForNews);
            this.Controls.Add(this.btnGetPicturesForNews);
            this.Controls.Add(this.txtGetCommentsForNews);
            this.Controls.Add(this.btnGetCommentsForNews);
            this.Controls.Add(this.txtNewsModifiedByUser);
            this.Controls.Add(this.btnNewsModifiedByUser);
            this.Controls.Add(this.txtUsersWhoModifiedNews);
            this.Controls.Add(this.btnUsersWhoModifiedNews);
            this.Controls.Add(this.btnGetAllAudio);
            this.Controls.Add(this.btnGetAllPicctures);
            this.Controls.Add(this.btnGetAllComments);
            this.Controls.Add(this.btnGetAllUsers);
            this.Controls.Add(this.btnGetAllNews);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.pictureBox);
            this.Controls.Add(this.button5);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Name = "MainForm";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.PictureBox pictureBox;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.Button btnGetAllNews;
        private System.Windows.Forms.Button btnGetAllUsers;
        private System.Windows.Forms.Button btnGetAllComments;
        private System.Windows.Forms.Button btnGetAllPicctures;
        private System.Windows.Forms.Button btnGetAllAudio;
        private System.Windows.Forms.Button btnUsersWhoModifiedNews;
        private System.Windows.Forms.TextBox txtUsersWhoModifiedNews;
        private System.Windows.Forms.Button btnNewsModifiedByUser;
        private System.Windows.Forms.TextBox txtNewsModifiedByUser;
        private System.Windows.Forms.Button btnGetCommentsForNews;
        private System.Windows.Forms.TextBox txtGetCommentsForNews;
        private System.Windows.Forms.Button btnGetPicturesForNews;
        private System.Windows.Forms.TextBox txtGetPicturesForNews;
        private System.Windows.Forms.Button btnGetAudiosForNews;
        private System.Windows.Forms.TextBox txtGetAudiosForNews;
        private System.Windows.Forms.Button btnCreateNews;
        private System.Windows.Forms.Button btnCreateUser;
        private System.Windows.Forms.Button btnCreateComment;
        private System.Windows.Forms.Button btnCreatePicture;
        private System.Windows.Forms.Button btnCreateAudio;
        private System.Windows.Forms.Button btnUpdateNews;
        private System.Windows.Forms.Button btnUpdateUser;
        private System.Windows.Forms.Button btnUpdateComment;
        private System.Windows.Forms.Button btnUpdatePicture;
        private System.Windows.Forms.Button btnUpdateAudio;
        private System.Windows.Forms.Button btnDeleteNews;
        private System.Windows.Forms.Button btnDeleteUser;
        private System.Windows.Forms.Button btnDeleteComment;
        private System.Windows.Forms.Button btnDeletePicture;
        private System.Windows.Forms.Button btnDeleteAudio;
        private System.Windows.Forms.TextBox txtUpdateNews;
        private System.Windows.Forms.TextBox txtUpdateUser;
        private System.Windows.Forms.TextBox txtUpdateComment;
        private System.Windows.Forms.TextBox txtUpdatePicture;
        private System.Windows.Forms.TextBox txtUpdateAudio;
    }
}


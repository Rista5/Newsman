﻿using DataLayerLib.DTOs;
using DataLayerLib.Entities;
using NHibernate;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DataLayerLib
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            pictureBox.SizeMode = PictureBoxSizeMode.StretchImage;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();

            IList<News> news = session.QueryOver<News>()
                .Where(x => x.Id >= 1).List<News>();

            foreach(News n in news)
            {
                MessageBox.Show("id: " + n.Id + " title " + n.Title + " content " + n.Content);
            }

            session.Close();
        }

        private void button_ucitaj_korisnike(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();

            IList<User> users = session.QueryOver<User>()
                .Where(x => x.Id >= 1).List<User>();

            foreach (User u in users)
            {
                MessageBox.Show("id: " + u.Id + "\nUsername: "+u.Username+ "\nPassword: "+ u.Password);
            }

            session.Close();
        }

        private void button_ucitaj_komentare(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();

            IList<Comment> comments = session.QueryOver<Comment>()
                .Where(x => x.Id >= 1).List<Comment>();

            foreach (Comment c in comments)
            {
                MessageBox.Show("id_komentara: "+c.Id + "\n Sadrzaj: "+c.Content+ "\n Kreirao user: "
                    + c.CreatedBy.Username + "\n Komentar na vest: "+c.BelongsTo.Title
                    +"\n Datum postovanja: "+c.PostDate);
            }

            session.Close();
        }

        private void button_ucitaj_slike(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();

            IList<Picture> pictures = session.QueryOver<Picture>()
                .Where(x => x.Id >= 1).List<Picture>();

            foreach (Picture p in pictures)
            {
                MessageBox.Show("id_slike: " + p.Id + "\n Naziv: " + p.Name + "\n Opis: "+ p.Description
                    +"\n Pripada vesti: " + p.BelongsTo.Title);
            }

            session.Close();
        }

        private void button_ucitaj_audio(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();

            IList<Audio> audios = session.QueryOver<Audio>()
                .Where(x => x.Id >= 1).List<Audio>();

            foreach (Audio a in audios)
            {
                MessageBox.Show("id_zvuka: " + a.Id + "\n Naziv: " + a.Name + "\n Opis: " + a.Description
                    + "\n Pripada vesti: " + a.BelongsTo.Title);
            }

            session.Close();
        }

        private void button_prikazi_sliku(object sender, EventArgs e)
        {
            ISession session = DataLayer.GetSession();
            Picture picture = session.Get<Picture>(1);
            session.Close();

            MultimediaLoader.IMultimediaLoader loader = new MultimediaLoader.FileSystemLoader();
            byte[] pictureData = loader.GetMedia(picture.Id, picture.Name);
            System.IO.MemoryStream ms = new System.IO.MemoryStream(pictureData);
            Image image = Image.FromStream(ms);
            
            pictureBox.Image = image;
        }

        private void btnGetAllNews_Click(object sender, EventArgs e)
        {
            List<NewsDTO> newsDTO = DTOManager.GetAllNews();
            foreach (NewsDTO dto in newsDTO)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllUsers_Click(object sender, EventArgs e)
        {
            List<UserDTO> userDTOs = DTOManager.GetALlUsers();
            foreach (UserDTO dto in userDTOs)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllComments_Click(object sender, EventArgs e)
        {
            List<CommentDTO> commentDTOs = DTOManager.GetAllComments();
            foreach (CommentDTO comment in commentDTOs)
                MessageBox.Show(comment.ToString());
        }

        private void btnGetAllPicctures_Click(object sender, EventArgs e)
        {
            List<PictureDTO> dtos = DTOManager.GetAllPictures();
            foreach (PictureDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllAudio_Click(object sender, EventArgs e)
        {
            List<AudioDTO> dtos = DTOManager.GetAllAudio();
            foreach (AudioDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnUsersWhoModifiedNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtUsersWhoModifiedNews.Text);
            List<UserDTO> dtos = DTOManager.GetUsersWhoModifiedThisNews(id);
            foreach (UserDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnNewsModifiedByUser_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtNewsModifiedByUser.Text);
            List<NewsDTO> dtos = DTOManager.GetNewsModifiedByUser(id);
            foreach (NewsDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetCommentsForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetCommentsForNews.Text);
            List<CommentDTO> dtos = DTOManager.GetCommentsForNews(id);
            foreach (CommentDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetPicturesForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetPicturesForNews.Text);
            List<PictureDTO> dtos = DTOManager.GetPicturesForNews(id);
            foreach (PictureDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAudiosForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetAudiosForNews.Text);
            List<AudioDTO> dtos = DTOManager.GetAudiosForNews(id);
            foreach (AudioDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnCreateUser_Click(object sender, EventArgs e)
        {
            CreateUserForm form = new CreateUserForm();
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManager.CreateUser(form.Username, form.Password))
                {
                    MessageBox.Show("Kreiran user: " + form.Username);
                }
                else
                    MessageBox.Show("Greska!");
            }
        }

        private void btnCreateComment_Click(object sender, EventArgs e)
        {
            List<UserDTO> users = DTOManager.GetALlUsers();
            List<NewsDTO> news = DTOManager.GetAllNews();
            CreateCommentForm form = new CreateCommentForm(users, news);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManager.CreateComment(form.User_Id, form.News_Id, form.Content))
                    MessageBox.Show("Uspesno dodat komentar");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnCreatePicture_Click(object sender, EventArgs e)
        {
            List<NewsDTO> news = DTOManager.GetAllNews();
            CreatePictureForm form = new CreatePictureForm(news);
            if(form.ShowDialog()==DialogResult.OK)
            {
                if (DTOManager.CreatePicture(form.News_ID, form.PictureName,
                    form.Description, form.PictureData))
                    MessageBox.Show("Uspesno dodata slika");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdateUser_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtUpdateUser.Text);
            UserDTO user = DTOManager.GetUser(id);
            CreateUserForm form = new CreateUserForm(true, user.Username);
            if(form.ShowDialog() == DialogResult.OK)
            {
                
                if (DTOManager.UpdateUser(user.Id, form.Password, form.Username, form.NewPassword))
                    MessageBox.Show("Uspesno azuriran user");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnCreateNews_Click(object sender, EventArgs e)
        {
            List<UserDTO> users = DTOManager.GetALlUsers();
            CreateNewsForm form = new CreateNewsForm(users);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if(DTOManager.CreateNews(form.Title,form.Content,form.User_ID))
                    MessageBox.Show("Uspesno kreirana vest");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdateNews_Click(object sender, EventArgs e)
        {
            int idNews = int.Parse(txtUpdateNews.Text);
            NewsDTO news = DTOManager.GetNews(idNews);
            List<UserDTO> users = DTOManager.GetALlUsers();
            CreateNewsForm form = new CreateNewsForm(users, news.Title, news.Content);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if(DTOManager.UpdateNews(form.User_ID,news.Id,form.Title, form.Content))
                    MessageBox.Show("Uspesno azurirana vest");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdatePicture_Click(object sender, EventArgs e)
        {
            int pictureId = int.Parse(txtUpdatePicture.Text);
            PictureDTO picture = DTOManager.GetPicture(pictureId);
            CreatePictureForm form = new CreatePictureForm(picture.Name, picture.Description, picture.PictureData);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManager.UpdatePicture(picture.Id, form.PictureName, form.Description, form.PictureData))
                    MessageBox.Show("Uspesno azurirana slika");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnDeleteNews_Click(object sender, EventArgs e)
        {
            int newsId = int.Parse(txtUpdateNews.Text);
            if (DTOManager.DeleteNews(newsId))
                MessageBox.Show("Vest je obrisana");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeleteUser_Click(object sender, EventArgs e)
        {
            int userId = int.Parse(txtUpdateUser.Text);
            if (DTOManager.DeleteUser(userId))
                MessageBox.Show("User je obrisan");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeleteComment_Click(object sender, EventArgs e)
        {
            int commentId = int.Parse(txtUpdateComment.Text);
            if (DTOManager.DeleteComment(commentId))
                MessageBox.Show("Komentar je obrisan");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeletePicture_Click(object sender, EventArgs e)
        {
            int pictureId = int.Parse(txtUpdatePicture.Text);
            if (DTOManager.DeletePicture(pictureId))
                MessageBox.Show("Slika je obrisana");
            else
                MessageBox.Show("Greska");
        }
    }
}

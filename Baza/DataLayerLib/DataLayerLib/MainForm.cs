using DataLayerLib.DTOs;
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
            byte[] pictureData = loader.GetMedia(picture.Id,picture.BelongsTo.Id, picture.Name);
            System.IO.MemoryStream ms = new System.IO.MemoryStream(pictureData);
            Image image = Image.FromStream(ms);

        }

        private void btnGetAllNews_Click(object sender, EventArgs e)
        {
            List<NewsDTO> newsDTO = DTOManagers.NewsDTOManager.GetAllNews();
            foreach (NewsDTO dto in newsDTO)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllUsers_Click(object sender, EventArgs e)
        {
            List<UserDTO> userDTOs = DTOManagers.UserDTOManager.GetALlUsers();
            foreach (UserDTO dto in userDTOs)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllComments_Click(object sender, EventArgs e)
        {
            List<CommentDTO> commentDTOs = DTOManagers.CommentDTOManager.GetAllComments();
            foreach (CommentDTO comment in commentDTOs)
                MessageBox.Show(comment.ToString());
        }

        private void btnGetAllPicctures_Click(object sender, EventArgs e)
        {
            List<PictureDTO> dtos = DTOManagers.PictureDTOManager.GetAllPictures();
            foreach (PictureDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAllAudio_Click(object sender, EventArgs e)
        {
            List<AudioDTO> dtos = DTOManagers.AudioDTOManager.GetAllAudio();
            foreach (AudioDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnUsersWhoModifiedNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtUsersWhoModifiedNews.Text);
            List<UserDTO> dtos = DTOManagers.UserDTOManager.GetUsersWhoModifiedThisNews(id);
            foreach (UserDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnNewsModifiedByUser_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtNewsModifiedByUser.Text);
            List<NewsDTO> dtos = DTOManagers.NewsDTOManager.GetNewsModifiedByUser(id);
            foreach (NewsDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetCommentsForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetCommentsForNews.Text);
            List<CommentDTO> dtos = DTOManagers.CommentDTOManager.GetCommentsForNews(id);
            foreach (CommentDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetPicturesForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetPicturesForNews.Text);
            List<PictureDTO> dtos = DTOManagers.PictureDTOManager.GetPicturesForNews(id);
            foreach (PictureDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnGetAudiosForNews_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtGetAudiosForNews.Text);
            List<AudioDTO> dtos = DTOManagers.AudioDTOManager.GetAudiosForNews(id);
            foreach (AudioDTO dto in dtos)
                MessageBox.Show(dto.ToString());
        }

        private void btnCreateUser_Click(object sender, EventArgs e)
        {
            CreateUserForm form = new CreateUserForm();
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManagers.UserDTOManager.CreateUser(form.Username, form.Password))
                {
                    MessageBox.Show("Kreiran user: " + form.Username);
                }
                else
                    MessageBox.Show("Greska!");
            }
        }

        private void btnCreateComment_Click(object sender, EventArgs e)
        {
            List<UserDTO> users = DTOManagers.UserDTOManager.GetALlUsers();
            List<NewsDTO> news = DTOManagers.NewsDTOManager.GetAllNews();
            CreateCommentForm form = new CreateCommentForm(users, news);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManagers.CommentDTOManager.CreateComment(form.User_Id, form.News_Id, form.Content))
                    MessageBox.Show("Uspesno dodat komentar");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnCreatePicture_Click(object sender, EventArgs e)
        {
            List<NewsDTO> news = DTOManagers.NewsDTOManager.GetAllNews();
            CreatePictureForm form = new CreatePictureForm(news);
            if(form.ShowDialog()==DialogResult.OK)
            {
                if (DTOManagers.PictureDTOManager.CreatePicture(form.News_ID, form.PictureName,
                    form.Description, form.PictureData))
                    MessageBox.Show("Uspesno dodata slika");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdateUser_Click(object sender, EventArgs e)
        {
            int id = int.Parse(txtUpdateUser.Text);
            UserDTO user = DTOManagers.UserDTOManager.GetUser(id);
            CreateUserForm form = new CreateUserForm(true, user.Username);
            if(form.ShowDialog() == DialogResult.OK)
            {
                
                if (DTOManagers.UserDTOManager.UpdateUser(user.Id, form.Password, form.Username, form.NewPassword))
                    MessageBox.Show("Uspesno azuriran user");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnCreateNews_Click(object sender, EventArgs e)
        {
            List<UserDTO> users = DTOManagers.UserDTOManager.GetALlUsers();
            CreateNewsForm form = new CreateNewsForm(users);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if(DTOManagers.NewsDTOManager.CreateNews(form.Title,form.Content,form.User_ID))
                    MessageBox.Show("Uspesno kreirana vest");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdateNews_Click(object sender, EventArgs e)
        {
            int idNews = int.Parse(txtUpdateNews.Text);
            NewsDTO news = DTOManagers.NewsDTOManager.GetNews(idNews);
            List<UserDTO> users = DTOManagers.UserDTOManager.GetALlUsers();
            CreateNewsForm form = new CreateNewsForm(users, news.Title, news.Content);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if(DTOManagers.NewsDTOManager.UpdateNews(form.User_ID,news.Id,form.Title, form.Content))
                    MessageBox.Show("Uspesno azurirana vest");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnUpdatePicture_Click(object sender, EventArgs e)
        {
            int pictureId = int.Parse(txtUpdatePicture.Text);
            PictureDTO picture = DTOManagers.PictureDTOManager.GetPicture(pictureId);
            CreatePictureForm form = new CreatePictureForm(picture.Name, picture.Description, picture.PictureData);
            if(form.ShowDialog() == DialogResult.OK)
            {
                if (DTOManagers.PictureDTOManager.UpdatePicture(picture.Id, form.PictureName, form.Description, form.PictureData))
                    MessageBox.Show("Uspesno azurirana slika");
                else
                    MessageBox.Show("Greska");
            }
        }

        private void btnDeleteNews_Click(object sender, EventArgs e)
        {
            int newsId = int.Parse(txtUpdateNews.Text);
            if (DTOManagers.NewsDTOManager.DeleteNews(newsId))
                MessageBox.Show("Vest je obrisana");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeleteUser_Click(object sender, EventArgs e)
        {
            int userId = int.Parse(txtUpdateUser.Text);
            if (DTOManagers.UserDTOManager.DeleteUser(userId))
                MessageBox.Show("User je obrisan");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeleteComment_Click(object sender, EventArgs e)
        {
            int commentId = int.Parse(txtUpdateComment.Text);
            if (DTOManagers.CommentDTOManager.DeleteComment(commentId))
                MessageBox.Show("Komentar je obrisan");
            else
                MessageBox.Show("Greska");
        }

        private void btnDeletePicture_Click(object sender, EventArgs e)
        {
            int pictureId = int.Parse(txtUpdatePicture.Text);
            if (DTOManagers.PictureDTOManager.DeletePicture(pictureId))
                MessageBox.Show("Slika je obrisana");
            else
                MessageBox.Show("Greska");
        }

        private void btnUpdateComment_Click(object sender, EventArgs e)
        {
            int commentId = int.Parse(txtUpdateComment.Text);
            CommentDTO dto = DTOManagers.CommentDTOManager.GetComment(commentId);
            CreateCommentForm form = new CreateCommentForm(dto);
            if(form.ShowDialog() == DialogResult.OK)
            {
                dto.Content = form.Content;
                if (DTOManagers.CommentDTOManager.UpdateComment(dto))
                    MessageBox.Show("Azuriran komentar");
                else
                    MessageBox.Show("Greska");
            }
        }
    }
}

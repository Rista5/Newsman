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
    public partial class Form1 : Form
    {
        public Form1()
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

            MultimediaLoader.IPictureLoader loader = new MultimediaLoader.FileSystemPictureLoader();
            byte[] pictureData = loader.GetPicture(picture.Id, picture.Name);
            System.IO.MemoryStream ms = new System.IO.MemoryStream(pictureData);
            Image image = Image.FromStream(ms);
            
            pictureBox.Image = image;
        }
    }
}

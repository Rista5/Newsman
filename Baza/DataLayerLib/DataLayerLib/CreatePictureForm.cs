using DataLayerLib.DTOs;
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

namespace DataLayerLib
{
    public partial class CreatePictureForm : Form
    {
        public string Path { get; set; }
        public string PictureName { get; set; }
        public string Description { get; set; }
        public int News_ID { get; set; }
        public byte[] PictureData { get; set; }
        public bool UpdatePicture { get; set; }
        private CreatePictureForm()
        {
            InitializeComponent();
        }

        public CreatePictureForm(List<NewsDTO> newsDTOs):this()
        {
            dgvNews.DataSource = newsDTOs;
            UpdatePicture = false;
        }

        public CreatePictureForm(string pictureName, string description,byte[] pictureData):this()
        {
            UpdatePicture = true;
            dgvNews.Hide();
            lblNews.Hide();
            PictureName = pictureName;
            Description = description;
            txtDescription.Text = description;
            txtName.Text = pictureName;
            PictureData = pictureData;
            if(pictureData!=null)
            {
                MemoryStream ms = new MemoryStream(pictureData);
                Image image = Image.FromStream(ms);
                pictureBox.Image = image;
            }
        }

        private void btnEditPath_Click(object sender, EventArgs e)
        {
            if(openFileDialog.ShowDialog() == DialogResult.OK)
            {
                Path = openFileDialog.FileName;
                lblPath.Text = openFileDialog.FileName;
                FileInfo fileInfo = new FileInfo(Path);
                using (BinaryReader br = new BinaryReader(new FileStream(Path, FileMode.Open)))
                {
                    PictureData = br.ReadBytes((int)fileInfo.Length);
                }
                MemoryStream ms = new MemoryStream(PictureData);
                Image image = Image.FromStream(ms);
                pictureBox.Image = image;
            }
        }

        private void btnOk_Click(object sender, EventArgs e)
        {
            if(dgvNews.SelectedRows!=null && txtName.Text!= null 
                && txtDescription.Text!=null)
            {
                if (!UpdatePicture)
                    News_ID = (int)dgvNews.SelectedCells[0].Value;
                else News_ID = 0;
                PictureName = txtName.Text;
                Description = txtDescription.Text;
                FileInfo fileInfo = new FileInfo(Path);
                using (BinaryReader br = new BinaryReader(new FileStream(Path,FileMode.Open)))
                {
                    PictureData = br.ReadBytes((int)fileInfo.Length);
                }
                DialogResult = DialogResult.OK;
                Close();
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}

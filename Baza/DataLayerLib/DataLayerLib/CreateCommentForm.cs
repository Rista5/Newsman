using ObjectModel.DTOs;
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
    public partial class CreateCommentForm : Form
    {
        public string Content { get; set; }
        public int User_Id { get; set; }
        public int News_Id { get; set; }
        private bool updateComment;
        private CreateCommentForm()
        {
            InitializeComponent();
        }

        public CreateCommentForm(List<UserDTO> users, List<NewsDTO> news):this()
        {
            dgvUser.DataSource = users;
            dgvNews.DataSource = news;
            updateComment = false;
        }

        public CreateCommentForm(CommentDTO comment):this()
        {
            dgvUser.Hide();
            dgvNews.Hide();
            label3.Hide();
            label2.Hide();
            User_Id = comment.CreatedBy.Id;
            News_Id = comment.BelongsToNewsId;
            Content = comment.Content;
            txtContent.Text = comment.Content;
            updateComment = true;
        }

        private void btnOk_Click(object sender, EventArgs e)
        {
            if (dgvUser.SelectedRows != null && dgvNews.SelectedRows != null)
            {
                if(!updateComment)
                {
                    User_Id = (int)dgvUser.SelectedCells[0].Value;
                    News_Id = (int)dgvNews.SelectedCells[0].Value;
                }
                Content = txtContent.Text;
                if (Content != null)
                {
                    DialogResult = DialogResult.OK;
                    Close();
                }
                else
                    MessageBox.Show("Niste uneli sadrzaj");

            }
            else
                MessageBox.Show("Niste selektovali usera ili news");
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}

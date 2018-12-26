using DataLayerLib.DTOs;
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
    public partial class CreateNewsForm : Form
    {
        public string Title { get; set; }
        public string Content { get; set; }
        public int User_ID { get; set; }
        private CreateNewsForm()
        {
            InitializeComponent();
        }
        public CreateNewsForm(List<UserDTO> users):this()
        {
            dgvUser.DataSource = users;
        }
        public CreateNewsForm(List<UserDTO> users,string title, string content):this()
        {
            Title = title;
            Content = content;
            txtTitle.Text = title;
            txtContent.Text = content;
            dgvUser.DataSource = users;
        }

        private void btnOk_Click(object sender, EventArgs e)
        {
            if(txtTitle.Text.Length>0 && txtContent.Text.Length> 0
                && dgvUser.SelectedRows!=null)
            {
                int userId = (int)dgvUser.SelectedCells[0].Value;
                Content = txtContent.Text;
                Title = txtTitle.Text;
                User_ID = userId;
                DialogResult = DialogResult.OK;
                Close();
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}

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
    public partial class CreateUserForm : Form
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public bool UpdateForm { get; set; }
        public string NewPassword { get; set; }
        public CreateUserForm()
        {
            InitializeComponent();
            lblNewPassword.Hide();
            txtNewPassoword.Hide();
        }
        public CreateUserForm(string username, string password):this()
        {
            Username = username;
            Password = password;
            txtPassword.Text = password;
            txtUsername.Text = username;
        }

        public CreateUserForm(bool update, string username):this()
        {
            if(update)
            {
                UpdateForm = update;
                lblNewPassword.Show();
                txtNewPassoword.Show();
            }
            txtUsername.Text = username;
        }
        private void btnOk_Click(object sender, EventArgs e)
        {
            bool filledIn = txtUsername.Text != null && txtPassword.Text != null;
            if (UpdateForm && txtNewPassoword!=null && filledIn)
            {
                NewPassword = txtNewPassoword.Text;
                Username = txtUsername.Text;
                Password = txtPassword.Text;
                DialogResult = DialogResult.OK;
                this.Close();
            }
            else if(filledIn)
            {
                Username = txtUsername.Text;
                Password = txtPassword.Text;
                DialogResult = DialogResult.OK;
                this.Close();
            }
            else
            {
                MessageBox.Show("Niste popunili sva polja");
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}

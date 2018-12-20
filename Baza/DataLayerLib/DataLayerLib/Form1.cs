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
        }
    }
}

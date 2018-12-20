using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.Entities
{
    class News
    {
        public virtual int Id { get; protected set; }
        public virtual String Title { get; set; }
        public virtual String Content { get; set; }
        public virtual DateTime LastModified { get; set; }
        public virtual IList<NewsModified> Modifications { get; set; }
        public virtual IList<Picture> Pictures { get; set; }
        public virtual IList<Audio> AudioRecordings { get; set; }
        public virtual IList<Comment> Comments { get; set; }
        public News()
        {
            Modifications = new List<NewsModified>();
            Pictures = new List<Picture>();
            AudioRecordings = new List<Audio>();
            Comments = new List<Comment>();
        }
    }
}

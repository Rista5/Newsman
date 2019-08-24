using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.Entities
{
    public class News
    {
        public virtual int Id { get; protected set; }
        public virtual string Title { get; set; }
        public virtual string Content { get; set; }
        public virtual DateTime LastModified { get; set; }
        public virtual Picture BackgroundPicture { get; set; }
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

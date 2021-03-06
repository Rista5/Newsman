﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ObjectModel.Entities
{
    public class Comment
    {
        public virtual int Id { get; protected set; }
        public virtual string Content { get;  set; }
        public virtual News BelongsTo { get; set; }
        public virtual User CreatedBy { get; set; }
        public virtual DateTime PostDate { get; set; }

        public Comment() { }
    }
}

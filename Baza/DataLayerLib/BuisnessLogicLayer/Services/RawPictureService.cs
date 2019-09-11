using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class RawPictureService
    {
        private IMultimediaLoader loader;

        private RawPictureService() { }
        public RawPictureService(IMultimediaLoader loader)
        {
            this.loader = loader;
        }

        public byte[] GetPicture(int pictureId, int newsId)
        {
            return loader.GetMedia(pictureId, newsId);
        }

        public bool PutPicture(int pictureId,int newsId, byte[] pictureData)
        {
            bool res = loader.SaveMedia(pictureId, newsId, pictureData);
            if (res)
            {
                MessageQueueManager manager = MessageQueueManager.Instance;
                manager.PublishMessage(newsId, pictureId, new PictureUpdateObject(pictureId, newsId), MessageOperation.Update);
            }
            return res;
        }
        
        class PictureUpdateObject
        {
            public PictureUpdateObject(int pictureId, int newsId)
            {
                PictureId = pictureId;
                NewsId = newsId;
            }

            public int PictureId { get; set; }
            public int NewsId { get; set; }
        }
    }
}

using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
using DataLayerLib.MultimediaLoader;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace API.Models
{
    public class ServiceProvider
    {
        private AudioService audioService;
        private CommentService commentService;
        private NewsService newsService;
        private PictureService pictureService;
        private UserService userService;
        private RawPictureService rawPictureService;

        public ServiceProvider()
        { 
            audioService = new AudioService(new AudioDTOManager(), new FileSystemLoaderWav());
            commentService = new CommentService(new CommentDTOManager());
            newsService = new NewsService(new NewsDTOManager(), new FileSystemLoader());
            pictureService = new PictureService(new PictureDTOManager(), new FileSystemLoader());
            userService = new UserService(new UserDTOManager());
            rawPictureService = new RawPictureService(new FileSystemLoader());
        }

        public UserService UserService { get => userService; }
        public PictureService PictureService { get => pictureService; }
        public NewsService NewsService { get => newsService; }
        public CommentService CommentService { get => commentService; }
        public AudioService AudioService { get => audioService; }
        public RawPictureService RawPictureService { get => rawPictureService; }
    }
}
using BuisnessLogicLayer.Services;
using DataLayerLib.DTOManagers;
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

        public ServiceProvider()
        { 
            audioService = new AudioService(new AudioDTOManager());
            commentService = new CommentService(new CommentDTOManager());
            newsService = new NewsService(new NewsDTOManager());
            pictureService = new PictureService(new PictureDTOManager());
            userService = new UserService(new UserDTOManager());
        }

        public UserService UserService { get => userService; }
        public PictureService PictureService { get => pictureService; }
        public NewsService NewsService { get => newsService; }
        public CommentService CommentService { get => commentService; }
        public AudioService AudioService { get => audioService; }
    }
}
﻿using BuisnessLogicLayer;
using DataLayerLib.MultimediaLoader.Generator;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayerLib.MultimediaLoader
{
    public class FileSystemLoaderWav: IMultimediaLoader
    {
        public static string LocationFolder { get; private set; }
        public IPathGenerator pathgen { get; set; }

        private static string Name(int pictureId)
        {
            return pictureId + ".wav";
        }

        public FileSystemLoaderWav()
        {
            //LocationFolder = @"C:\Users\Uros\Downloads\IV godina\VII semestar\Arhitektura i projektovanje softvera\Projekat\Newsman\Baza\Slike\";
            pathgen = new IIExpresPath();
            string path = Directory.GetCurrentDirectory();
            for (int i = 0; i < 4; i++)
                path = Path.GetDirectoryName(path);
            path += "\\Slike\\";
            LocationFolder = path;
        }

        public byte[] GetMedia(int id, int newsId)
        {
            string name = Name(id);
            string path = pathgen.GeneratePath(id, newsId, name);
            path += name;

            byte[] pictureData = null;
            try
            {
                string s = Directory.GetCurrentDirectory();
                if (File.Exists(path))
                {
                    FileInfo info = new FileInfo(path);
                    long size = info.Length;
                    FileStream fs = new FileStream(path, FileMode.Open);
                    BinaryReader br = new BinaryReader(fs);
                    pictureData = br.ReadBytes((int)size);

                    br.Close();
                    fs.Close();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return pictureData;
        }

        public bool SaveMedia(int id, int newsId, byte[] data)
        {
            //string pictureName = name + id.ToString();
            //string path = LocationFolder + pictureName + ".jpg";
            string name = Name(id);
            string path = pathgen.GeneratePath(id, newsId, name);

            try
            {
                if (!File.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }
                FileStream fs = new FileStream(path + name, FileMode.Create);
                BinaryWriter bw = new BinaryWriter(fs);
                bw.Write(data);

                bw.Close();
                fs.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return true;
        }

        public bool DeleteMedia(int id, int newsId)
        {
            //string pictureName = name + id.ToString();
            //string path = LocationFolder + pictureName + ".jpg";
            string name = Name(id);
            string path = pathgen.GeneratePath(id, newsId, name);
            path += id + ".jpg";
            try
            {
                if (File.Exists(path))
                {
                    File.Delete(path);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return true;
        }
    }
}

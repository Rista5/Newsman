using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using DataLayerLib.MultimediaLoader.Generator;

namespace DataLayerLib.MultimediaLoader
{
    class FileSystemLoader : IMultimediaLoader
    {
        public static string LocationFolder { get; private set; }
        public IPathGenerator pathgen { get; set; }

        public FileSystemLoader()
        {
            //LocationFolder = @"C:\Users\Uros\Downloads\IV godina\VII semestar\Arhitektura i projektovanje softvera\Projekat\Newsman\Baza\Slike\";
            pathgen = new ClassicPath();
            string path = Directory.GetCurrentDirectory();
            for(int i =0;i<4;i++)
                path = Path.GetDirectoryName(path);
            path += "\\Slike\\";
            LocationFolder = path;
        }

        public byte[] GetMedia(int id,int newsId, string name)
        {
            string path = pathgen.GeneratePath(id, newsId, name);
            path += name;

            byte[] pictureData = null;
            try
            {
                if(File.Exists(path))
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
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return pictureData;
        }

        public bool SaveMedia(int id,int newsId, string name, byte[] data)
        {
            //string pictureName = name + id.ToString();
            //string path = LocationFolder + pictureName + ".jpg";

            string path = pathgen.GeneratePath(id, newsId, name);
            
            try
            {
                if(!File.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }
                FileStream fs = new FileStream(path+name, FileMode.Create);
                BinaryWriter bw = new BinaryWriter(fs);
                bw.Write(data);

                bw.Close();
                fs.Close();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

            return true;
        }

        public bool DeleteMedia(int id, int newsId, string name)
        {
            //string pictureName = name + id.ToString();
            //string path = LocationFolder + pictureName + ".jpg";

            string path = pathgen.GeneratePath(id, newsId, name);
            path+= name;
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

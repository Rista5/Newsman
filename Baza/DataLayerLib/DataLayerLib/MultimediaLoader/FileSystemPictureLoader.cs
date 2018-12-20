using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace DataLayerLib.MultimediaLoader
{
    class FileSystemPictureLoader : IPictureLoader
    {
        public static string LocationFolder { get; private set; }

        public FileSystemPictureLoader()
        {
            LocationFolder = @"C:\Users\Uros\Downloads\IV godina\VII semestar\Arhitektura i projektovanje softvera\Projekat\Newsman\Baza\Slike\";
        }

        public byte[] GetPicture(int id, string name)
        {
            string pictureName = name + id.ToString();
            string path = LocationFolder + pictureName + ".jpg";
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

        public bool SavePicture(int id, string name, byte[] data)
        {
            throw new NotImplementedException();
        }
    }
}

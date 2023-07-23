using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using ImageWeb.Events;

namespace ImageWeb.Models
{
    public class PhotosModel
    {
        public event EventHandler<PhotoEventArgs> numPhotos;
        private readonly string[] filterTypes = { ".jpg", ".png", ".gif", ".bmp", ".jpeg" };
        /// <summary>
        /// PhotosModel
        /// constructor
        /// </summary>
        public PhotosModel()
        {
            Photos = new List<PhotoInfo>();
            UpdatePhotoList();
        }

        public List<PhotoInfo> Photos { get; }
        public string getPhotoPath { get; set; }

        /// <summary>
        /// UpdatePhotoList
        /// updates photos list
        /// </summary>
        public void UpdatePhotoList()
        {
            if (Directory.Exists(getPhotoPath))
            {
                Photos.Clear();
                string[] paths = getPaths();
                try
                {
                    List<string> ThumbnailsPaths = new List<string>();
                    foreach (string filter in this.filterTypes)
                    {
                       string[]  temp_ThumbnailsPaths = Directory.GetFiles(getPhotoPath + "Thumbnails", "*"+filter, SearchOption.AllDirectories);
                        foreach (string full_photo_path in temp_ThumbnailsPaths)
                        {
                            ThumbnailsPaths.Add(ReplaceSlash(full_photo_path));
                        }
                    }

                    //create tuple of image path and it's thumbnail path
                    List<Tuple<string, string>> pathsTuple = new List<Tuple<string, string>>();
                    foreach (string path in paths)
                    {
                        foreach (string thumbPath in ThumbnailsPaths)
                        {
                            if (Path.GetFileName(path).Equals(Path.GetFileName(thumbPath)))
                            {
                                Tuple<string, string> pair = new Tuple<string, string>(path, thumbPath);
                                pathsTuple.Add(pair);
                                PhotoInfo photo = new PhotoInfo(pair.Item1, pair.Item2, ReplaceSlash(getPhotoPath));
                                Photos.Add(photo);
                                break;
                            }
                        }
                    }
                }
                catch
                {

                }

                // set number of images i output directory
                PhotoEventArgs photoEvent = new PhotoEventArgs(Photos.Count);
                this.numPhotos?.Invoke(this, photoEvent);
            }
        }
        /// <summary>
        /// getPaths
        /// returns all paths of images in directory
        /// </summary>
        /// <returns name = "paths">array of paths</returns>
        private string ReplaceSlash(string path)
        {
            return path.Replace("\\", "/");
        }
        private string[] getPaths()
        {
            List<string> paths = new List<string>();
            string[] directories = Directory.GetDirectories(this.getPhotoPath);
            foreach (string path in directories)
            {
                if (!Path.GetFileName(path).Equals("Thumbnails"))
                {
                    foreach (string filter in this.filterTypes)
                    {
                        paths.AddRange(Directory.GetFiles(path, "*"+ filter, SearchOption.AllDirectories));
                    }
                }
            }
            List<string> fixed_paths = new List<string>();
            foreach (string full_photo_path in paths)
            {
                fixed_paths.Add(ReplaceSlash(full_photo_path));
            }
            return fixed_paths.ToArray();
        }

        /// <summary>
        /// Remove
        /// removes photo from list and from file
        /// </summary>
        /// <param name="OriginPath">a photo to remove</param>
        public void Remove(PhotoInfo OriginPath)
        {
            try
            {
                foreach (PhotoInfo ph in Photos)
                {
                    if (ph.ImgPath.Equals(OriginPath.ImgPath))
                    {
                        Photos.Remove(OriginPath);
                        string fullpath = OriginPath.ImgPath;
                        string FullthumbPath = OriginPath.ThumbPath;
                        File.Delete(fullpath);
                        File.Delete(FullthumbPath);
                        break;
                    }
                }
                // update the num of pictures in the main page
                PhotoEventArgs photoEvent = new PhotoEventArgs(Photos.Count);
                this.numPhotos?.Invoke(this, photoEvent);
            }
            catch
            {
                throw new Exception("Could not remove Photo (model)");
            }
        }

        /// <summary>
        /// updatePath
        /// update image path
        /// </summary>
        /// <param name="sender">sender</param>
        /// <param name="args">event</param>
        public void updatePath(object sender, PhotoEvent args)
        {
            this.getPhotoPath = args.Path;
        }

    }
}
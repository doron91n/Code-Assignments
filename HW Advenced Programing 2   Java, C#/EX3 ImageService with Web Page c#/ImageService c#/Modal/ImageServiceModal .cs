

using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
/// <summary>
/// ImageServiceModal
/// class that handles file system operations
/// </summary>
namespace ImageService.Modal
{
    public class ImageServiceModal : IImageServiceModal
    {
        #region Members
        private static Regex r = new Regex(":");
        private string OutputDirectory;   // The Output Folder for the service
        private int thumbNailSize;    // The Size Of The Thumbnail pic Size
        #endregion

        /// <summary>
        /// ImageServiceModal
        /// constructor
        /// </summary>
        /// <param name="path">path to the file</param>
        /// <param name="size">size of thumbnail image</param>
        public ImageServiceModal(string path, int size)
        {
            this.OutputDirectory = path;
            this.thumbNailSize = size;
        }

        /// <summary>
        /// AddiFile
        /// creates folders and move file into them
        /// </summary>
        /// <param name="originalPath"> the path the file came from </param>
        /// <param name="result"> true if succeeded,false otherwise </param>
        /// <returns></returns>
        public string AddFile(string originalPath, out bool result)
        {
            result = false;
            string return_msg = "";
            // checks the file at given path exists
            if(File.Exists(originalPath)) {
                string outPutNewPath = "";
                string thumbNewPath = "";
                // extract the name of the image
                string fileName = originalPath.Substring(originalPath.LastIndexOf("\\"));
                try
                {
                 // create all directories needed if they don't already exist
                this.CreateDirectories(originalPath, out outPutNewPath, out thumbNewPath); 
                // addes the file name to the needed paths.
                outPutNewPath += fileName;
                thumbNewPath += fileName;
                } catch(Exception e)
                {
                    return_msg= "Error: Failed creating directories requierd for the new file. Error thrown: " + e.Message;
                    return return_msg;
                }
                // create and save images in their destenation folders
                this.createImages(originalPath, outPutNewPath, thumbNewPath, out return_msg);
                // check if both the image and the thumbnail were created succsefully
                bool outImage = File.Exists(outPutNewPath);
                bool thumbImage = File.Exists(thumbNewPath);
                if (thumbImage && outImage) {
                     result = true;
                }
                return return_msg;
            }
            // sends error if file wasn`t found
            result = false;
            return_msg= "Error: adding file failed , file not found.";
            return return_msg;
        }

        /// <summary>
        /// CreateDirectories
        /// creates all needed directories in the given paths
        /// </summary>
        /// <param name="path">original file path</param>
        /// <param name="outNewPath">new path of the file</param>
        /// <param name="thumbNewPath">path of the thumb file</param>
        private void CreateDirectories(string path, out string outNewPath, out string thumbNewPath)
        {
            DateTime imageTime = new DateTime();
              // extract the image creation time and date
            try {
                imageTime = this.GetDateFromImage(path);
                }
            catch (Exception failedGetDate) {
                try {
                // create directory for images without dates.
                createNoDateDirectories( path, out  outNewPath, out  thumbNewPath);
                } catch (Exception failedCreateNoDateFolders)
                {
                    throw  new Exception("Error: failed creating folders for image without dates. Exception thrown: " 
                        + failedCreateNoDateFolders.Message);
                }
            }
            int imageYear = imageTime.Year;
            int imageMonth = imageTime.Month;
            string dateDirectory = "\\" + imageYear.ToString() + "\\" + imageMonth.ToString();
            try {
                // create outPut Directory if doesn't exist already
                DirectoryInfo dirInfo = Directory.CreateDirectory(this.OutputDirectory);
                // make outPut Directory a hidden directory
                dirInfo.Attributes = FileAttributes.Directory | FileAttributes.Hidden;
                // create new path for outPut Directory
                outNewPath = this.OutputDirectory+ dateDirectory;
                // create new path for ThumbNail Directory
                thumbNewPath = this.OutputDirectory+"\\Thumbnails"+ dateDirectory;
                // create the needed directories for images and thumbnails
                Directory.CreateDirectory(outNewPath);
                Directory.CreateDirectory(thumbNewPath);
            } catch (Exception e) 
            {
                Exception newException = new Exception("Error: failed to create image directories. Exception thrown: "
                    + e.Message);
                throw newException;
            }
        }

        /// <summary>
        /// createNoDateDirectories
        /// creates directories for files without a date
        /// </summary>
        /// <param name="path">original file path</param>
        /// <param name="outNewPath">new path of the file</param>
        /// <param name="thumbNewPath">path of the thumb file</param>
        private void createNoDateDirectories(string path, out string outNewPath, out string thumbNewPath)
        {
            Directory.CreateDirectory(path + "\\No-Date");
            Directory.CreateDirectory(path + "\\Thumbnails-No-Date");
            thumbNewPath = this.OutputDirectory + "\\Thumbnails-No-Date";
            outNewPath = this.OutputDirectory + "\\No-Date";
        }

        /// <summary>
        /// createImage
        /// creates an image in the given path
        /// </summary>
        /// <param name="path">original file path</param>
        /// <param name="outNewPath">new path of the file</param>
        /// <param name="thumbNewPath">path of the thumb file</param>
        /// <param name="message">if succeeded create returns file's new path,error otherwise </param>
        private void createImages(string path, string outNewPath, string thumbNewPath, out string message)
        {

            try
            {
                Thread.Sleep(10);
               int fileCounter = 0;
                // the new file path after checking for multiply occurrences of the same file
                outNewPath = repeatingFilesPath(outNewPath, out fileCounter);
                message = "Error: failed to move image to: "+outNewPath;
                // move wanted file to its new location
                File.Move(path, outNewPath);
                // create the TumbNail image
                createThumbImage( outNewPath,  thumbNewPath,  fileCounter, out message);
            } catch (Exception e) {
                // return when the message is the error message
                message = "Error: failed creating images at needed folders";
                return ;
            }
            // save the new created file path as a message
            message = outNewPath;       
        }

        /// <summary>
        /// repeatingFilesPath
        /// checks if there is more than one file with the same name , if so - concat to the name the number of the copy
        /// </summary>
        /// <param name="outNewPath">the new path of the file</param>
        /// <param name="fileCounter">number of copies of the same name</param>
        /// <returns></returns>
        private string repeatingFilesPath(string outNewPath,out int fileCounter)
        {
             fileCounter = 0;
            // extract the image file extention
            string fileExtension = Path.GetExtension(outNewPath);
            // get the files path without its extention
            string tempPath = outNewPath.Substring(0, outNewPath.Length - fileExtension.Length);
            string finalPath = tempPath;
            // check how many occurrences of the same file exist by the file counter ,if such files exists.
            while (File.Exists(tempPath + (fileCounter > 0 ? "(" + fileCounter.ToString() + ")"
                + fileExtension : fileExtension)))
            {
                fileCounter++;
                finalPath = tempPath + (fileCounter > 0 ? ("(" + fileCounter.ToString() + ")") : "");
            }
            // returns the final full path 
            return finalPath + fileExtension;
        }

        /// <summary>
        /// createThumbImage
        /// creates a thumbnail image
        /// </summary>
        /// <param name="outNewPath">the new path of the image</param>
        /// <param name="thumbNewPath">the new path of the thumbnail image</param>
        /// <param name="fileCounter">number of occurences of the same name</param>
        /// <param name="message">if succeeded create returns file's new path,error otherwise </param>
        private void createThumbImage(string outNewPath, string thumbNewPath, int fileCounter ,out string message)
        {
            message = "Error: failed to extract thumbnail from image";
            // extract the image file extention
            string fileExtension = Path.GetExtension(outNewPath);
            // extract a thumbnail from the original image
            Image image = Image.FromFile(outNewPath),
            thumb = image.GetThumbnailImage(thumbNailSize, thumbNailSize, () => false, IntPtr.Zero);
            // change the thumb file path acoording to the original image
            thumbNewPath = thumbNewPath.Substring(0, thumbNewPath.Length - fileExtension.Length);
            thumbNewPath += (fileCounter > 0 ? ("(" + fileCounter.ToString() + ")")
                + fileExtension : fileExtension);
            message = "Error: failed to save thumbnail";
            // save the thumbnail image
            thumb.Save(thumbNewPath);
            // close connection to thumb image
            thumb.Dispose();
            // close connection to image
            image.Dispose();
        }


        /// <summary>
        /// GetDateFromImage
        /// extract the date the image was taken
        /// </summary>
        /// <param name="path">path of the image</param>
        /// <returns> date the image was taken </returns>
        private DateTime GetDateFromImage(string path)
        {
            try {
                // try extracting the date which the image was taken
                using (Image myImage = Image.FromFile(path))
                {
                    PropertyItem propItem = myImage.GetPropertyItem(36867);
                    string dateTaken = r.Replace(Encoding.UTF8.GetString(propItem.Value), "-", 2);
                    myImage.Dispose();
                    return DateTime.Parse(dateTaken);
                } }catch(Exception e)
            {
                // if failed to get the date which the image was taken returns the date it was created.
                DateTime timeNow = DateTime.Now;
                TimeSpan localOffset = timeNow - timeNow.ToUniversalTime();
                return File.GetLastWriteTimeUtc(path) + localOffset;
            }
        }
    }
 }
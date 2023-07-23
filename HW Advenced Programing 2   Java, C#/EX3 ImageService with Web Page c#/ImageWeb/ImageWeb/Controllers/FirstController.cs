using Commands.Infrastructure;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ImageWeb.Models;

namespace ImageWeb.Controllers
{
    public class FirstController : Controller
    {

        private static ImageServiceInfoModel service_info_model = new ImageServiceInfoModel();
        private static LogsModel log_model = new LogsModel();
        private static ConfigModel config = new ConfigModel();
        private static PhotosModel photos = new PhotosModel();
        private static PhotoInfo photoToView = null;
        private  ConnectionModel Connection_model = ConnectionModel.CreateConnection();
        private ConnectionModel connection = ConnectionModel.CreateConnection();
        private string handler_to_delete;
        private bool got_delete_confirm = false;
        /// <summary>
        /// FirstController
        /// constructor
        /// </summary>
        public FirstController()
        {
            Debug.WriteLine("00000000000000000000000000000000000000000  ImageServiceController  Consturctor  ");
            photos.numPhotos += service_info_model.SetPicNum;
            config.sendPath += photos.updatePath;
            config.DeletedHandler += GotHandlerDeleteConfirm;
            while (config.InfoReceived == false)
            {
                System.Threading.Thread.Sleep(10);
            }
            photos.getPhotoPath = config.OutputDir.Replace("\\","/");
            photos.UpdatePhotoList();
        }
        /// <summary>
        /// ImageWebView
        /// ImageWebView viewer
        /// </summary>
        [HttpGet]
        public ActionResult ImageWebView()
        {
            return View(service_info_model);
        }
        /// <summary>
        /// LogsView
        /// LogsView viewer
        /// </summary>
        [HttpGet]
        public ActionResult LogsView()
        {
            return View(log_model);
        }
        /// <summary>
        /// ConfigView
        /// configView viewer
        /// </summary>
        public ActionResult ConfigView()
        {
            return View(config);
        }
        /// <summary>
        /// GotHandlerDeleteConfirm
        /// raised when the config page got from server confirmation for deleting handler, sets got_delete_confirm to true/false
        /// </summary>
        /// <param name="e">the close command recived from server after deleting handler </param>
        public void GotHandlerDeleteConfirm(object sender, CommandRecievedEventArgs e)
        {
            if (!string.IsNullOrEmpty(handler_to_delete)) {
                if (e.RequestDirPath.Contains(this.handler_to_delete))
                {
                    this.got_delete_confirm = true;
                }
                else {
                    this.got_delete_confirm = false;
                }
            }
        }
        /// <summary>
        /// DeleteHandler
        /// DeleteHandler viewer
        /// </summary>
        public ActionResult DeleteHandler(string path)
        {
            Handlers handler = new Handlers(path);
            return View(handler);
        }
        /// <summary>
        /// DeleteHandOk
        /// goes over handlers to delete and send CloseCommand to server 
        /// </summary>
        /// <param name="handler">handler to delete</param>
        public ActionResult DeleteHandOk(string handler)
        {
            handler_to_delete = handler;
            got_delete_confirm = false;
            foreach (Models.DirectoryModel dir in config.HandlersList)
            {
                if (dir.DirectoryPath.Equals(handler))
                {
                    config.SendCloseHandlerCmd(handler);
                }
            }
            Debug.WriteLine("111111111111111111111111111111111  controller 1  got_delete_confirm:" + got_delete_confirm);
            while (!got_delete_confirm) { System.Threading.Thread.Sleep(10);}
            Debug.WriteLine("111111111111111111111111111111111  controller 2  got_delete_confirm:" + got_delete_confirm);
            if (got_delete_confirm) { 
            return RedirectToAction("ConfigView");
            }
            else {return RedirectToAction("Error"); }
      }
        /// <summary>
        /// PhotosView
        /// opens PhotosView
        /// </summary>
        /// <param name="path">path to the file</param>
        /// <param name="size">size of thumbnail image</param>
        public ActionResult PhotosView()
        {
            photos.getPhotoPath = config.OutputDir.Replace("\\", "/");
            photos.UpdatePhotoList();
            return View(photos);
        }
        /// <summary>
        /// Error
        /// Error viewer
        /// </summary>
        public ActionResult Error()
        {
            return View();
        }

        /// <summary>
        /// PhotoRealSize
        /// display selected photo ih real size
        /// </summary>
        /// <param name="path">path to the photo</param>
        public ActionResult PhotoRealSize(string path)
        {
            foreach (PhotoInfo photos in photos.Photos)
            {
                if (photos.ImgPath.Equals(path))
                {
                    photoToView = photos;
                    return View(photoToView);
                }
            }
            return RedirectToAction("Error");
        }


        /// <summary>
        /// DeletePhoto
        /// finds photo and delete it
        /// </summary>
        [HttpGet]
        public ActionResult DeletePhoto(string path)
        {
            PhotoInfo photo = FindPhoto(path);
            return View(photo);
        }
        /// <summary>
        /// FindPhoto
        /// returns the photo picked
        /// </summary>
        /// <param name="path">path to photo</param>
        private PhotoInfo FindPhoto(string path)
        {
            List<PhotoInfo> tempList = photos.Photos;
            foreach (PhotoInfo pic in tempList)
            {
                if (pic.ImgPath.Equals(path))
                {
                    return pic;
                }
            }
            return null;
        }
        /// <summary>
        /// DeleteAndWait
        /// deletes photo and returns to PhotosView
        /// </summary>
        public ActionResult DeleteAndWait(string path)
        {
            foreach (PhotoInfo p in photos.Photos)
            {
                if (p.ImgPath.Equals(path))
                {
                    photos.Remove(p);
                    return RedirectToAction("PhotosView");
                }
            }
            return RedirectToAction("PhotosView");
        }
    }
}


using System.Collections.Generic;
using System.Configuration;
using System.Linq;

namespace ImageService.Modal
{
   public class ConfigReader
    {
        #region Members
        private List<string> handlers_list;
        public int TumbNailSize { get; }
        public string OutputDir { get; }
        public string LogName { get; }
        public string SourceName { get; }
        public List<string> HandlersList { get { return this.handlers_list; } set { this.handlers_list = value; } }
        private static ConfigReader configReader;
        #endregion
        /// <summary>
        /// Function Name: ConfigReader() ,singleton
        /// Description: class constructor, responsable for handling the service config .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ConfigReader(){
            this.handlers_list = new List<string>();
            this.SourceName = ConfigurationManager.AppSettings["SourceName"];
            this.OutputDir = ConfigurationManager.AppSettings["OutputDir"];
            this.LogName = ConfigurationManager.AppSettings["LogName"];
            this.TumbNailSize = int.Parse(ConfigurationManager.AppSettings["ThumbnailSize"]);
            this.HandlersList = ConfigurationManager.AppSettings["Handler"].Split(';').ToList();
        }
        /// <summary>
        /// Function Name: CreateConfigReader() , static.
        /// Description: class static singleton "constructor", returns a instance of this class.
        /// Arguments: null
        /// </summary>
        /// <returns> configReader: a instance of this class. </returns>
        public static ConfigReader CreateConfigReader()
        {
            if (configReader == null)
            {
                configReader = new ConfigReader();
            }
            return configReader;
        }
        /// <summary>
        /// Function Name: RemoveHandler() .
        /// Description: removes given handler from the list.
        /// Arguments: handler_path: the handler to remove.
        /// </summary>
        /// <returns> null </returns>
        public void RemoveHandler(string handler_path)
        {
            List<string> temp_list = new List<string>(this.HandlersList);
            temp_list.Remove(handler_path);
            this.HandlersList = temp_list;
        }
    }
}

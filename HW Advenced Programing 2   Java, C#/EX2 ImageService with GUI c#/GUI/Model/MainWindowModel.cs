

namespace GUI.Model
{
   public class MainWindowModel
    {
        private ConnectionModel connection;
        /// <summary>
        /// Function Name: MainWindowModel()
        /// Description: class constructor, holding the settings and log windows .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public MainWindowModel() {
            this.connection = ConnectionModel.CreateConnection();
        }
        /// <summary>
        /// Function Name: BackgroundFromConnectionStatus()
        /// Description: returns a color (string) based on the client connection status , connected=white,disconnected= dark grey.
        /// Arguments: null
        /// </summary>
        /// <returns> string with the background color   </returns>
        public string BackgroundFromConnectionStatus()
        {
          switch (this.connection.IsClientConnected())
            {
                case true:
                    return "White";
                case false:
                    return "DarkGray";
                default:
                    return "Transparent";
            }
        }
    }
}

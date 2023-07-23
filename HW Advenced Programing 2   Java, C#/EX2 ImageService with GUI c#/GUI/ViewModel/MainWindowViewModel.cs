using GUI.Model;
using Microsoft.Practices.Prism.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace GUI.ViewModel
{
    class MainWindowViewModel
    {
        private MainWindowModel MainWindowModel;
        public ICommand CloseCommand { get; private set; }
        /// <summary>
        /// Function Name: MainWindowViewModel
        /// Description: class constructor, holding the logs and settings windows .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public MainWindowViewModel()
        {
            this.MainWindowModel = new MainWindowModel();
            this.CloseCommand = new DelegateCommand<object>(this.OnWindowClosing);
        }
        /// <summary>
        /// Function Name: BackgroundFromConnectionStatus()
        /// Description: returns a color (string) based on the client connection status , connected=white,disconnected= dark grey.
        /// Arguments: null
        /// </summary>
        /// <returns> string with the background color   </returns>
        public string BackgroundFromConnectionStatus
        {
            get { return this.MainWindowModel.BackgroundFromConnectionStatus(); }
        }
        /// <summary>
        /// Function Name: OnWindowClosing()
        /// Description: closes the client connection before the gui closes.
        /// Arguments: obj - the closing object
        /// </summary>
        /// <returns> null </returns>
        private void OnWindowClosing(object obj)
        {
            ConnectionModel connection = ConnectionModel.CreateConnection();
            connection.CloseConnection();
        }
    }
}

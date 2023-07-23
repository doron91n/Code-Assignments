

using Commands.Infrastructure;
using GUI.Model;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace GUI.ViewModel
{
    class LogViewModel
    {
        #region Members
         private LogModel log_model;
        #endregion
        /// <summary>
        /// Function Name: LogViewModel()
        /// Description: class constructor, responsable for handling the logs page.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public LogViewModel()
        {
            this.log_model = new LogModel();
        }
        /// <summary>
        /// Function Name: getLogsList
        /// Description: a getter and setter for this class logs list .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ObservableCollection<MessageRecievedEventArgs> getLogsList
        {
            get { return this.log_model.getLogsList; }
            set
            {
                this.log_model.getLogsList = value;
            }
        }

    }
}
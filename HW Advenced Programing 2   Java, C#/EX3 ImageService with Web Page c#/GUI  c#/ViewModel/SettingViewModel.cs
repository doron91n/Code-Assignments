using GUI.Model;
using Microsoft.Practices.Prism.Commands;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;

namespace GUI.ViewModel

{
    class SettingViewModel : viewModel
    {
        private SettingModel setting_model;
        /// <summary>
        /// Function Name:  SettingViewModel()
        /// Description:  class constructor, responsable for handling the service settings(config) .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public SettingViewModel()
        {
            setting_model = new SettingModel();
            this.setting_model.PropertyChanged +=
                delegate (Object sender, PropertyChangedEventArgs e)
                {
                    NotifyPropertyChanged("SettingViewModel: "+e.PropertyName);
                };
            this.RemoveCommand = new DelegateCommand<object>(this.OnRemove, this.CanRemove);
             PropertyChanged += OnPropertyChanged;
        }
        /// <summary>
        /// Function Name: OnPropertyChanged()
        /// Description: activates event for the class members upon change detected .
        /// Arguments: sender: the property name.
        /// </summary>
        /// <returns> null </returns>
        private void OnPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            var command = this.RemoveCommand as DelegateCommand<object>;
            command.RaiseCanExecuteChanged();
        }
        /// <summary>
        /// Function Name: RemoveCommand
        /// Description: the RemoveCommand command getter and setter .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ICommand RemoveCommand { get; private set; }
        /// <summary>
        /// Function Name:  OnRemove()
        /// Description: invoked when client press the remove selected handler button,send the close command to server.
        /// Arguments: obj - the removed object
        /// </summary>
        /// <returns> null </returns>
        private void OnRemove(object obj)
        {
            // remove the selected handler
            setting_model.SendCloseHandlerCmd(this.SelectedHandler);
        }
        /// <summary>
        /// Function Name: CanRemove ()
        /// Description: returns true/false if a handler was selected from the list.
        /// Arguments: obj - the removed object
        /// </summary>
        /// <returns> null </returns>
        private bool CanRemove(object obj)
        {
            if (string.IsNullOrEmpty(this.SelectedHandler))
            {
                return false;
            }
            return true;
        }
        #region Getters & Setters
        /// <summary>
        /// Function Name: OutputDirectory
        /// Description: a getter and setter for OutputDirectory.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string OutputDirectory
        {
            get { return this.setting_model.OutputDirectory; }
            set
            {
                this.setting_model.OutputDirectory = value;
            }
        }
        /// <summary>
        /// Function Name: SourceName
        /// Description: a getter and setter for SourceName.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string SourceName
        {
            get { return this.setting_model.SourceName; }
            set
            {
                this.setting_model.SourceName = value;
            }
        }
        /// <summary>
        /// Function Name: LogName
        /// Description: a getter and setter for LogName .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string LogName
        {
            get { return this.setting_model.LogName; }
            set
            {
                this.setting_model.LogName = value;
            }
        }
        /// <summary>
        /// Function Name: ThumbSize
        /// Description: a getter and setter for ThumbSize .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string ThumbSize
        {
            get { return this.setting_model.ThumbSize; }
            set
            {
                this.setting_model.ThumbSize = value;
            }
        }
        /// <summary>
        /// Function Name: SelectedHandler
        /// Description: a getter and setter for this class selected handler to remove .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string SelectedHandler
        {
            get { return this.setting_model.SelectedHandler; }
            set
            {
                this.setting_model.SelectedHandler = value;

            }
        }
        /// <summary>
        /// Function Name: HandlersList
        /// Description: a getter and setter for this class Handlers list .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ObservableCollection<string> HandlersList
        {
            get { return this.setting_model.HandlersList; }
            set
            {
                this.setting_model.HandlersList = value;
            }
        }
        #endregion
    }
}
using System.ComponentModel;

namespace GUI.ViewModel

{
    abstract class viewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        /// <summary>
        /// Function Name: NotifyPropertyChanged()
        /// Description: invoked upon a changed detected at one of the Properties .
        /// Arguments: propName: the Property Changed name.
        /// </summary>
        /// <returns> null </returns>
        public void NotifyPropertyChanged(string propName)
        {
            if (PropertyChanged != null)
            {
                this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propName));
            }
        }
    }
}
using GUI.ViewModel;
using System.Windows.Controls;

namespace GUI.View

{
    /// <summary>
    /// Interaction logic for SettingView.xaml
    /// </summary>
    public partial class SettingView : UserControl
    {
        /// <summary>
        /// Function Name: LogView()
        /// Description: class constructor, responsable for handling the settings page.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public SettingView()
        {
            InitializeComponent();
            this.DataContext = new SettingViewModel();
        }
    }
}

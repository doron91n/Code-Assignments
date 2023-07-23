
using System.Windows;
using GUI.ViewModel;

namespace GUI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private MainWindowViewModel MainWindowViewModel;
        /// <summary>
        /// Function Name: MainWindow()
        /// Description: class constructor, responsable for holding the settings and logs pages.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public MainWindow()
        {
            InitializeComponent();
            MainWindowViewModel = new MainWindowViewModel();
            this.DataContext = MainWindowViewModel;
        }
    }
}

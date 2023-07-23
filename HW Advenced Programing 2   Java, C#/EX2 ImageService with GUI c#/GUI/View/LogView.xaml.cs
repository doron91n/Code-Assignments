using GUI.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace GUI.View
{
    /// <summary>
    /// Interaction logic for LogControl.xaml
    /// </summary>
    public partial class LogView : UserControl
    {
        /// <summary>
        /// Function Name: LogView()
        /// Description: class constructor, responsable for handling the logs page.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public LogView()
        {
            InitializeComponent();
            this.DataContext = new LogViewModel();
        }
    }
}
using Commands.Infrastructure;
using System;
using System.Windows.Data;
using System.Globalization;

namespace GUI.ViewModel
{
    class Colors : IValueConverter
    {
        /// <summary>
        /// Function Name: Convert()
        /// Description:based on given message type returns a color name (INFO=Green /FAIL=Red / WARNING=Yellow).
        /// Arguments: null
        /// </summary>
        /// <returns> string color name(INFO=Green /FAIL=Red / WARNING=Yellow) </returns>
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            // we return the proper color according to the type off the message: red-for fail; yellow-for warning; yellow green- for info.
            MessageTypeEnum type = (MessageTypeEnum)value;
            switch (type)
            {
                case MessageTypeEnum.INFO:
                    return "Green";
                case MessageTypeEnum.FAIL:
                    return "Red";
                case MessageTypeEnum.WARNING:
                    return "Yellow";
                default:
                    return "White";
            }
        }
        /// <summary>
        /// Function Name: ConvertBack()
        /// Description: Not Implemented .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}

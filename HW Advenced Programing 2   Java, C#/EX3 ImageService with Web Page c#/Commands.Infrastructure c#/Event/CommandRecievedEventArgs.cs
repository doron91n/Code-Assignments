
using System;

namespace Commands.Infrastructure
{
    public class CommandRecievedEventArgs : EventArgs
    {
        public CommandEnum CommandID { get; set; }      // The Command ID
        public string CommandName { get
            {
                string name = "";
                switch (CommandID)
                {
                    case CommandEnum.NewFileCommand:
                        name = "NewFileCommand";
                        break;
                    case CommandEnum.GetConfigCommand:
                        name = "GetConfigCommand";
                        break;
                    case CommandEnum.LogCommand:
                        name = "LogCommand";
                        break;
                    case CommandEnum.CloseCommand:
                        name = "CloseCommand";
                        break;
                     default:
                        name = "NoCommand";
                        break;
                }
                return name;
            } }                  // the command name

        public string[] Args { get; set; }          // the provided arguments
        public string RequestDirPath { get; set; }  // The Request Directory

        /// <summary>
        /// Function Name: CommandRecievedEventArgs()
        /// Description: constructor.
        /// Arguments: id: this command id enum.
        ///            args: provided string arguments for this command.
        ///            path: string the command path if needed.
        /// </summary>
        /// <returns> null. </returns>
        public CommandRecievedEventArgs(CommandEnum id, string[] args, string path)
        {
            CommandID = id;
            Args = args;
            RequestDirPath = path;
        }
        /// <summary>
        /// Function Name: ToString()  OverRides Object.TOString()
        /// Description: returns a string representing this class.
        /// Arguments: null
        /// </summary>
        /// <returns> string represnting this class. </returns>
        public override string  ToString () {

            return "Command: " + CommandName + " Args: " + ArgsToString() + " Path: " + RequestDirPath;
        }
        /// <summary>
        /// Function Name: ArgsToString()
        /// Description: returns a string representing this class members.
        /// Arguments: null
        /// </summary>
        /// <returns> string filled with this class members content </returns>
        private string ArgsToString()
        {
            string s = "";
            int i = 0;
            foreach (string arg in Args)
            {
                s += " [" + i + "]= " + arg;
                i++;
            }
            return s;
        }
    }
}

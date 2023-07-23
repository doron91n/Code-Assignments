using ImageService.Modal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Commands.Infrastructure;
using ImageService.Logging;

/// <summary>
/// GetConfigCommand
/// class that operate the GetConfigCommand 
/// </summary>
namespace ImageService.Commands
{
    public class GetConfigCommand : ICommand
    {
        private ConfigReader config_reader;
        /// <summary>
        /// Function Name: GetConfigCommand()
        /// Description: class constructor, preforms the get config command .
        /// Arguments: null.
        /// </summary>
        /// <returns> null </returns>
        public GetConfigCommand()
        {
            this.config_reader = ConfigReader.CreateConfigReader();
        }
        /// <summary>
        /// Function Name: Execute()
        /// Description: Executes the get config command .
        /// Arguments: string[] args: needed arguments .
        ///            result: command excution status, true=succsess false=fail.
        /// </summary>
        /// <returns> string representing command excution status </returns>
        public string Execute(string[] args, out bool result)
        {
            // Arg[0] = OutputDir ,  Arg[1] = SourceName , Arg[2] = LogName , Arg[3] = ThumbnailSize ,Arg[4 -] = HandlersList
            string handlers = String.Join(",", this.config_reader.HandlersList);
            string[] config = (this.config_reader.OutputDir + "," + this.config_reader.SourceName + "," + this.config_reader.LogName + "," + this.config_reader.TumbNailSize + "," + handlers).Split(',');
            CommandRecievedEventArgs cmd = new CommandRecievedEventArgs(CommandEnum.GetConfigCommand, config, "NO_PATH");
            result = true;
            return JsonConvert.SerializeObject(cmd);
        }
    }
}






  

using System;
using ImageService.Modal;
using Commands.Infrastructure;
using Newtonsoft.Json;
using ImageService.Modal.Event;
using ImageService.Logging;
/// <summary>
/// CloseCommand
/// class that operate the close handler command 
/// </summary>
namespace ImageService.Commands
{
    public class CloseCommand : ICommand
    {
        public event EventHandler<DirectoryCloseEventArgs> CloseH;

        /// <summary>
        /// Function Name: Execute()
        /// Description: Executes the close handler command .
        /// Arguments: string[] args: needed arguments .
        ///            result: command excution status, true=succsess false=fail.
        /// </summary>
        /// <returns> string representing command excution status </returns>
        public string Execute(string[] args, out bool result)
        {
            ConfigReader config_reader = ConfigReader.CreateConfigReader();
            result = false;
             //remove handler from lists
            string handler = args[0];
            DirectoryCloseEventArgs e = new DirectoryCloseEventArgs(handler, "close handler");
            // invoke the event so the handler will be removed
            CloseH?.Invoke(this, e);
            //delete handler from config reader
            config_reader.RemoveHandler(handler);
            result = true;
            CommandRecievedEventArgs cmd = new CommandRecievedEventArgs(CommandEnum.CloseCommand, new string[]{ handler }, handler);
            result = true;
            return JsonConvert.SerializeObject(cmd);
        }
    }



}

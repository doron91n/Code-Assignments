

using Commands.Infrastructure;
using ImageService.Logging;
using Newtonsoft.Json;
using System.Collections.Generic;
/// <summary>
/// NewFileCommand
/// class that operate the LogCommand 
/// </summary>
namespace ImageService.Commands
{
    public class LogCommand : ICommand
    {

        /// <summary>
        /// Function Name: Execute()
        /// Description: Executes the Log command .
        /// Arguments: string[] args: needed arguments .
        ///            result: command excution status, true=succsess false=fail.
        /// </summary>
        /// <returns> string representing command excution status </returns>
        public string Execute(string[] args, out bool result)
        {
            LogHandler log_handler = LogHandler.CreateLogHandler();
            result = true;
            List<MessageRecievedEventArgs> logMsgList = log_handler.LogMsgList;
            List<string> logs=new List<string>();
            // logs_to_client[i] = msg Status (info|warning|Fail) &  msg string
            foreach (MessageRecievedEventArgs msg_args in logMsgList)
            {
                logs.Add(msg_args.Status.ToString()+" & "+ msg_args.Message);
            }
            CommandRecievedEventArgs cmd_args = new CommandRecievedEventArgs(CommandEnum.LogCommand, logs.ToArray(), "NO_PATH");
            return JsonConvert.SerializeObject(cmd_args);
        }
    }
};
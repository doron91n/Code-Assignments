

using ImageService.Logging;

namespace ImageService.Commands
{
    public interface ICommand
    {
        /// <summary>
        /// Function Name: Execute()
        /// Description: Executes the command .
        /// Arguments: string[] args: needed arguments .
        ///            result: command excution status, true=succsess false=fail.
        /// </summary>
        /// <returns> string representing command excution status </returns>
        string Execute(string[] args, out bool result); 
    }
}

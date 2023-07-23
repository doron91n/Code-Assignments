

using Commands.Infrastructure;

namespace ImageService.Controller
{
    public interface IImageController
    {
        string ExecuteCommand(CommandEnum commandID, string[] args, out bool result);          // Executing the Command Requset
    }
}

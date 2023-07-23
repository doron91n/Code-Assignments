

using System;
namespace Commands.Infrastructure
{
    public class MessageRecievedEventArgs : EventArgs
    {
        public MessageTypeEnum Status { get; set; }
        public string StatusName { get
            {
                string name = "";
                switch (Status)
                {
                    case MessageTypeEnum.INFO:
                        name = "INFO";
                        break;
                    case MessageTypeEnum.FAIL:
                        name = "FAIL";
                        break;
                    case MessageTypeEnum.WARNING:
                        name = "WARNING";
                        break;
                    default:
                        name = "No Type";
                        break;
                }
                return name;
            }
        }
        public string Message { get; set; }
    }
}

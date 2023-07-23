


using Commands.Infrastructure;
using ImageService.Controller;
using ImageService.Logging;
using ImageService.Modal;
using ImageService.Server;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.ServiceProcess;

namespace ImageService
{
    #region Enum ServiceState
    public enum ServiceState
    {
        SERVICE_STOPPED = 0x00000001,
        SERVICE_START_PENDING = 0x00000002,
        SERVICE_STOP_PENDING = 0x00000003,
        SERVICE_RUNNING = 0x00000004,
        SERVICE_CONTINUE_PENDING = 0x00000005,
        SERVICE_PAUSE_PENDING = 0x00000006,
        SERVICE_PAUSED = 0x00000007,
    }
    #endregion
    #region struct ServiceStatus
    [StructLayout(LayoutKind.Sequential)]
    public struct ServiceStatus
    {
        public int dwServiceType;
        public ServiceState dwCurrentState;
        public int dwControlsAccepted;
        public int dwWin32ExitCode;
        public int dwServiceSpecificExitCode;
        public int dwCheckPoint;
        public int dwWaitHint;
    };
    #endregion
	
    public partial class ImageService : ServiceBase
    {
        #region members
        private ImageServer image_server;
        private ServerTcp server_Tcp;
        private IImageServiceModal modal;
		private IImageController Controller;
        private ILoggingService logging;
        private EventLog eventLog;
        private List<string> handlersPathList;
        private ConfigReader config_reader;
        private int eventID = 0;
        private LogHandler log_handler;
        #endregion

        /// <summary>
        /// ImageService
        /// constructor
        /// </summary>
        /// <param name="args">arguments</param>
        public ImageService(string[] args)
        {
            InitializeComponent();
            this.config_reader =ConfigReader.CreateConfigReader();
            string eventSourceName = this.config_reader.SourceName;
            string logName = this.config_reader.LogName;
            eventLog = new EventLog();
            if (!EventLog.SourceExists(eventSourceName))
            {
                EventLog.CreateEventSource(eventSourceName, logName);
            }
            eventLog.Source = eventSourceName;
            eventLog.Log = logName;
        }
		
        private void InitializeComponent()
        {
            this.eventLog = new System.Diagnostics.EventLog();
            ((System.ComponentModel.ISupportInitialize)(this.eventLog)).BeginInit();
            // ImageService
            this.ServiceName = "ImageService";
            ((System.ComponentModel.ISupportInitialize)(this.eventLog)).EndInit();

        }
		
        [DllImport("advapi32.dll", SetLastError = true)]
        private static extern bool SetServiceStatus(IntPtr handle, ref ServiceStatus serviceStatus);


        public void OnTimer(object sender, System.Timers.ElapsedEventArgs args)
        {
            //  monitoring activities .  
            logging.Log("Monitoring the System", MessageTypeEnum.INFO);
        }

		/// <summary>
        /// OnStart
        /// initiallize the related classes and start running service
        /// </summary>
        /// <param name="args">arguments</param>
        protected override void OnStart(string[] args)
        {
            //initiating members of LoggingService,ImageController,ImageServiceModal,(image+tcp)servers.
            handlersPathList = this.config_reader.HandlersList;
            modal = new ImageServiceModal(this.config_reader.OutputDir, this.config_reader.TumbNailSize);
            logging = LoggingService.CreateLogger();
             this.log_handler =  LogHandler.CreateLogHandler();
            ImageController controller = new ImageController(modal);
            this.Controller = controller;
            image_server = new ImageServer(controller, logging, handlersPathList);
            server_Tcp = new ServerTcp(logging, controller);
            //add function to event
            server_Tcp.Start();
            controller.HandlerClosedEvent += image_server.CloseHandler;
            server_Tcp.CloseCommandRecieved += image_server.CloseCommand;
            logging.MessageRecieved += onMsg;
            logging.MessageRecieved += log_handler.AddEntry;
            logging.MessageRecieved += server_Tcp.NewLogEntry;
            image_server.NotifyClients += server_Tcp.NotifyClients;
            // Update the service state to Start Pending. 
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.dwCurrentState = ServiceState.SERVICE_START_PENDING;
            serviceStatus.dwWaitHint = 100000;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);
            // Set up a timer to trigger every minute.  
            System.Timers.Timer timer = new System.Timers.Timer();
            timer.Interval = 60000; // 60 seconds  
            timer.Elapsed += new System.Timers.ElapsedEventHandler(this.OnTimer);
            timer.Start();
            logging.Log("ImageService Started", MessageTypeEnum.INFO);
            // Update the service state to Running.  
            serviceStatus.dwCurrentState = ServiceState.SERVICE_RUNNING;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);
        }

        /// <summary>
        /// OnStop
        /// stops running the service and closes everything
        /// </summary>
        protected override void OnStop()
        {
            LogHandler log_h =LogHandler.CreateLogHandler();
            // close the image server
            this.image_server.CloseServer();
            // close the tcp server
            this.server_Tcp.Stop();
            logging.MessageRecieved -= onMsg;
            logging.NotifyServerClients -= server_Tcp.NotifyClients;
            logging.MessageRecieved -= this.log_handler.AddEntry;
            logging.MessageRecieved -= log_h.AddEntry;
            logging.MessageRecieved += server_Tcp.NewLogEntry;
            image_server.NotifyClients -= server_Tcp.NotifyClients;
            server_Tcp.CloseCommandRecieved += image_server.CloseCommand;
            log_handler.ClearLog();
            log_h.ClearLog();
            logging.Log("Service OnStop", MessageTypeEnum.INFO);

        }

        /// <summary>
        /// OnMsg
        /// gets the message to display and check the type of the message
        /// </summary>
        /// <param name="sender">the sender of the event</param>
        /// <param name="e">message contains message and type</param>
        public void onMsg(object sender, MessageRecievedEventArgs e)
        {
            EventLogEntryType eventType;
            switch (e.Status)
            {
                case MessageTypeEnum.INFO:
                    eventType = EventLogEntryType.Information;
                    break;
                case MessageTypeEnum.WARNING:
                    eventType = EventLogEntryType.Warning;
                    break;
                case MessageTypeEnum.FAIL:
                    eventType = EventLogEntryType.FailureAudit;
                    break;
                default:
                    eventType = EventLogEntryType.Information;
                    break;
            }
            eventLog.WriteEntry(e.Message, eventType,eventID++);
        }
    }
}

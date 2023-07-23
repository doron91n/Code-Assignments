﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using ImageService.Server;
using ImageService.Controller;
using ImageService.Modal;
using ImageService.Logging;
using ImageService.Logging.Modal;
using System.Configuration;
using ImageService.Infrastructure;
using ImageService.Controller.Handlers;


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
        private ImageServer m_imageServer;
		private IImageServiceModal modal;
		private IImageController controller;
        private ILoggingService logging;
        private EventLog eventLog;
        private List<string> handlersPathList;
        private ConfigReader config_reader;
        private int eventID = 1;
        private AndroidServer m_android_server;
        #endregion

        /// <summary>
        /// ImageService
        /// constructor
        /// </summary>
        /// <param name="args">arguments</param>
        public ImageService(string[] args)
        {
            InitializeComponent();
            string eventSourceName = ConfigurationManager.AppSettings["SourceName"];
            string logName = ConfigurationManager.AppSettings["LogName"];
            eventLog = new System.Diagnostics.EventLog();
            if (!System.Diagnostics.EventLog.SourceExists(eventSourceName))
            {
                System.Diagnostics.EventLog.CreateEventSource(eventSourceName, logName);
            }
            eventLog.Source = eventSourceName;
            eventLog.Log = logName;
        }
		
        private void InitializeComponent()
        {
            this.eventLog = new System.Diagnostics.EventLog();
            ((System.ComponentModel.ISupportInitialize)(this.eventLog)).BeginInit();
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
			//initiating members of LoggingService,ImageController,ImageServiceModal
			logging = LoggingService.CreateLogger();
            config_reader = ConfigReader.CreateConfigReader();
            modal = new ImageServiceModal(config_reader.OutputDir, config_reader.TumbNailSize);
			controller= new ImageController(modal);
			handlersPathList = config_reader.HandlersList;
			//add function to event
			logging.MessageRecieved += onMsg;
			//initiating ImageServer
			m_imageServer = new ImageServer(controller,logging,handlersPathList);
             m_android_server = new AndroidServer(logging, controller);
            // Set up a timer to trigger every minute.  
            System.Timers.Timer timer = new System.Timers.Timer();
            timer.Interval = 60000; // 60 seconds  
            timer.Elapsed += new System.Timers.ElapsedEventHandler(this.OnTimer);
            timer.Start();
            logging.Log("Start", MessageTypeEnum.INFO);
            // Update the service state to Start Pending. 
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.dwCurrentState = ServiceState.SERVICE_START_PENDING;
            logging.Log("Start Pending", MessageTypeEnum.INFO);
            m_android_server.Start();
            serviceStatus.dwWaitHint = 100000;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);
            // Update the service state to Running.  
            serviceStatus.dwCurrentState = ServiceState.SERVICE_RUNNING;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);
        }
		
        protected override void OnContinue()
        {
            logging.Log("Continue", MessageTypeEnum.INFO);
        }
		
        protected override void OnStop()
        {
			//send close command to server
            m_imageServer.sendCommand();
            m_android_server.Stop();
            logging.Log("Service OnStop", MessageTypeEnum.INFO);
        }
   
		/// <summary>
        /// OnMsg
        /// gets the message to display and check the type of the message
        /// </summary>
        /// <param name="sender">the sender of the event</param>
        /// <param name="e">message contains message and type</param>
        public void onMsg(object sender, Logging.Modal.MessageRecievedEventArgs e)
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

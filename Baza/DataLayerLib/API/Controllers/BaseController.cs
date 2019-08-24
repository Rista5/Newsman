using API.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    public class BaseController : ApiController
    {
        private static ServiceProvider _prototype = null;
        private static object _lock = new object();

        public ServiceProvider Service {
            get
            {
                if (_prototype == null)
                {
                    lock (_lock)
                    {
                        if(_prototype == null)
                            _prototype = new ServiceProvider();
                    }
                }
                return _prototype;
            }
        }
    }
}

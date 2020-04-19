From ./target folder

Start Web Server:
=============
java web.SimpleWebServer

Start App Server:
=============
java appserver.server.AppServer


Start Satellite
=================
java appserver.satellite.Satellite
OR
java appserver.satellite.Satellite ../../config/Satellite.Earth.properties ../../config/WebServer.properties ../../config/Server.properties
java appserver.satellite.Satellite ../../config/Satellite.Venus.properties ../../config/WebServer.properties ../../config/Server.properties
java appserver.satellite.Satellite ../../config/Satellite.Mercury.properties ../../config/WebServer.properties ../../config/Server.properties


Start Client:
=================
java appserver.client.PlusOneClient
OR
java appserver.client.FibonacciClient

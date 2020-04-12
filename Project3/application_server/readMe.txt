From ./target folder

Start Server:
=============
java web.SimpleWebServer


Start Satellite
=================
java appserver.satellite.Satellite
OR
java appserver.satellite.Satellite ../../config/Satellite.Earth.properties ../../config/WebServer.properties ../../config//Server.properties

Start Client:
=================
java appserver.client.PlusOneClient ../../config/Satellite.Earth.properties

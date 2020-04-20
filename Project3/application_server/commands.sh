#!/bin/bash

# Make sure these classes are in the right place
mv -f target/classes/appserver/job/impl/FibonacciAux.class target/classes/appserver/job/impl/Fibonacci.class target/classes/appserver/job/impl/PlusOneAux.class target/classes/appserver/job/impl/PlusOne.class  docRoot/appserver/job/impl 2>/dev/null
cd target/classes
command="$1"
if [[ $command -eq 1 ]]; then 
	echo java -classpath . web.SimpleWebServer ../../config/WebServer.properties
	java -classpath . web.SimpleWebServer ../../config/WebServer.properties
elif [[ $command -eq 2 ]]; then
	echo java -classpath . appserver.server.AppServer ../../config/Server.properties
	java -classpath . appserver.server.AppServer ../../config/Server.properties
elif [[ $command -eq 3 ]]; then
	echo java -classpath . appserver.satellite.Satellite ../../config/Satellite.Earth.properties ../../config/WebServer.properties ../../config/Server.properties
	java -classpath . appserver.satellite.Satellite ../../config/Satellite.Earth.properties ../../config/WebServer.properties ../../config/Server.properties
elif [[ $command -eq 4 ]]; then
	echo java -classpath . appserver.satellite.Satellite ../../config/Satellite.Mercury.properties ../../config/WebServer.properties ../../config/Server.properties
	java -classpath . appserver.satellite.Satellite ../../config/Satellite.Mercury.properties ../../config/WebServer.properties ../../config/Server.properties
elif [[ $command -eq 5 ]]; then
	echo java -classpath . appserver.satellite.Satellite ../../config/Satellite.Venus.properties ../../config/WebServer.properties ../../config/Server.properties
	java -classpath . appserver.satellite.Satellite ../../config/Satellite.Venus.properties ../../config/WebServer.properties ../../config/Server.properties
elif [[ $command -eq 6 ]]; then
	echo java -classpath . appserver.client.FibonacciClient ../../config/Server.properties
	java -classpath . appserver.client.FibonacciClient ../../config/Server.properties
fi 

Dependencies
============

This demo requires at least Java 8 is installed (Older java versions are currently unsupported).
You can test this by issuing "java -version" at a command prompt.
If java is not found, ensure that it is installed and that java is in your path.
See https://www.java.com/en/download/help/download_options.xml for installing java.
See https://www.java.com/en/download/help/path.xml for instructions on how to include java in your path.

Installation
============

Unzip the demo.zip anywhere - it is self contained.
Edit demo/skyve/javaee/skyve.json,
find the line = directory: "${SKYVE_CONTENT:/C:/demo/skyve/content/}", (line 28)
and change it to the absolute path to the skyve/content folder within the demo folder.
Ensure there is a trailing slash (slash can be used on ALL O/Ss)

On windows, double click on run.bat - to start WILDFLY (and the demo app).
On Mac/Linux, open a terminal, cd to demo directory and issue ./run.sh

Navigate to http://localhost:8080/skyve/ in a browser.
The credentials to use are:-

customer = demo
username = admin
password = admin

If you want demonstration data
==============================

As per the getting started videos at skyve.org - if you want to see example data, there's a job which will create a randomised data set. To schedule the job, do the following:
* Log in and expand the Admin module (accordion pane on left).
* Go to the Jobs menu item and switch to Jobs Schedule tab.
* Click the "Add" (green plus)
* Select the Job to load Demonstration Data and select "Run As" admin
* click the "Schedule Job For Now" button and then Cancel out of the form rather than OK to avoid the Job being permanently scheduled.
* Expand Whosin module or WhosinIntegrate to see the created data.

If you want to use your own google maps key
===========================================

Edit demo/skyve/javaee/skyve.json,
find the line = 		googleMapsV3Key: null, (line 101)
and change it to your google maps key - eg googleMapsV3Key: "<place_your_key_here>",

To use eclipse to perform development
=====================================

1) Open eclipse
2) Select [File -> New -> Java Project] menu item (or [File->New->Project->JavaProject] if you are already in the Java EE perspective)
3) Enter "skyve" as the [Project name:]
4) Switch off [Use default location] check box
5) Browse to the skyve folder within the demo folder
6) Click [Next] button
7) Click [Finish] button (if you are prompted to change perspective, click OK)
8) Select [Window -> Show View -> Ant] menu item
Drag "build.xml" file from the [Package Explorer] in the skyve project into the [Ant] view.
(Ensure you refresh the eclipse project after running any of the ANT targets)
    - compileReports - compiles any jrxml reports in your source path
    - deprecationReport - produces a report of deprecated attributes 
                          present in the skyve metadata
    - generateDefaultQueries - creates generatedQueries.xml, a set of default
                               queries for a module
    - generateDomain - validates all skyve metadata and generates the java domain classes
    - generateEditView - creates skyve/generateEdit.xml for a document 
    - touch - redeploy the app into WILDFLY

To change WILDFLY port
======================

Edit demo/wildfly/wildfly-10.1.0.Final\standalone\configuration\standalone.xml
Goto line 415 and change the socket binding for http from 8080.
Edit demo/skyve/javaee/skyve.json,
find the line 		server: "http://${SKYVE_HOSTNAME:localhost}:${SKYVE_PORT:8080}", (line 37) and change the port to match.
Restart the server (kill the command prompt running run.bat/run.sh and then restart run.bat/run.sh)
and hit http://localhost:XXXX/skyve/ in a browser where XXXX is the new port set.

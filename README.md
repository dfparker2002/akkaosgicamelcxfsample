akkaosgicamelcxfsample
======================

This sample application to showcase the integration of akka camel cxf for deployment in OSGI container such as apache karaf


Installation Instructions:

1) mvn clean install

2) start the karaf 2.3.2 console

3) Execute the following commands:
  a) features:chooseurl cxf 2.7.5
  b) features:install cxf 
  c) features:chooseurl camel 2.10.3 
  d) features:install camel 
  e) features:install camel-cxf

4) Deploy the generated kar artifact i.e BACCArchive-0.0.1-SNAPSHOT.kar in KARAF_INSTALL_HOME/deploy directory



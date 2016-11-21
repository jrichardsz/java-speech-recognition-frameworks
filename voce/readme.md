# Voce http://voce.sourceforge.net Tests and Proofs of Concept

Keywords : Java Speech recognition api java voce speech FreeTTS CMU Sphinx Speech Recognizer Text To Speech voce.sourceforge.net speech recognition using Java and Sphinx Pure Java speech recognition library java SpeechRecognition 

# Inspiration

I do this because I love artificial intelligence and I dream about the day when a true artificial intelligence can coexist with humans.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- [Download Maven](http://maven.apache.org/download.cgi)
- [Install Maven](http://maven.apache.org/install.html)

In order to compile and get your own library of voce jar, install this two offline jars in your local maven :

- clone and install this repo [https://github.com/jrichardsz/voce](https://github.com/jrichardsz/voce) using maven.

- open command line point at [offline-jars](https://github.com/jrichardsz/java-speech-recognition-apis/tree/master/voce/offline-jars) in a cloned folder.

- execute


```
mvn install:install-file -Dfile=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz-1.0.jar -DgroupId=edu.cmu.sphinx.model.acoustic.WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -DartifactId=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -Dversion=1.0 -Dpackaging=jar

```

### Compile

- go to cloned folder and execute

```
mvn clean package
```

...

### Install

- go to cloned folder and execute

```
mvn clean install
```

...


## Running the tests

### No Java IDE

If you dont like use an IDE to run a simple java code, try this:

- Move configuration files in any location of you operative system 

[config-files](https://github.com/jrichardsz/java-speech-recognition-apis/tree/master/voce/config-files)

- Open java class **org.jrichardsz.poc.voce.RecognitionExternalConfigTest** and edit with your external path configurations

```java
String voicePath = "\\app-config\\voce";
String grammarPath = "\\app-config\\voce\\gram";
```

- execute

```
mvn clean package exec:java -Dexec.mainClass="org.jrichardsz.poc.voce.RecognitionExternalConfigTest"
```

- If no errors, wait to the following text appear and start to say any number from 0 to 9

```

[INFO]
[INFO] ----------------------------------------
[INFO] Building poc-voce-recognition 1.0.0
[INFO] ----------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (defaul
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources
[WARNING] Using platform encoding (Cp1252 actua
[INFO] Copying 2 resources

.....


[Voce debug] Beginning initialization
[Voce] Initializing recognizer. This may take some time...
[Voce debug] Starting microphone...
[Voce debug] Microphone on
[Voce] Initialization complete
[Voce debug] Recognition thread starting
This is a speech recognition test. Speak digits from 0-9 into the microphone. Speak 'quit' to quit.
```

- If no errors, this will be the log after you say any number from 0 to 9

```
You said: six
[Voce debug] Finished recognizing
You said: seven
[Voce debug] Finished recognizing
You said: one
[Voce debug] Finished recognizing
You said: two oh
[Voce debug] Finished recognizing
You said: three
```


### And coding style tests

...


## Deployment

...

## Built With

* [Maven](https://www.apache.org/) - Apache Maven is a software project management and comprehension tool
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) - Java SE Development Kit 7 Downloads

## Contributing

....

## Versioning

Current version is 0.9.1

## Authors

* **Richard Osmar Leon Ingaruca** - *Migrate to maven* - [contact me](http://jrichardsz.weebly.com/contact.html)

## License

Voce is licensed under the BSD or LGPL Open Source licenses.  Also, be sure to read the licenses for [FreeTTS](http://freetts.sourceforge.net/docs/index.php) and [CMU Sphinx4](http://cmusphinx.sourceforge.net/wiki/tutorialsphinx4).
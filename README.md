# Setup

Download and install Google Chrome browser. Note the browser version.

Download and install Apache Maven. Follow the instructions at https://maven.apache.org/install.html

Clone the repository

Open hollard_insurance/testng.xml in a text editor. Replace value of following parameter in the file (i.e. "91.0.4472.114") with the version of Chrome browser
```
<parameter name="suite-chrome-version" value="91.0.4472.114" />
```

# Test
To run the tests in command line

`cd hollard_insurance`

`mvn clean test`

# Setup

Download and install Google Chrome browser. Note the browser version.

Download and install Apache Maven. Follow the instructions at https://maven.apache.org/install.html

Clone this repository `https://github.com/dimple-wanigatunga/hollard_insurance.git`

Open `hollard_insurance/testng.xml` in a text editor. Replace value of following parameter in the file with the version of Chrome browser i.e.
```
<parameter name="suite-chrome-version" value="91.0.4472.114" />
```

# Test
To run the tests in command line

`cd hollard_insurance`

`mvn clean test`

# Reports

Generated reports are inside `hollard_insurance/target/surefire-reports`

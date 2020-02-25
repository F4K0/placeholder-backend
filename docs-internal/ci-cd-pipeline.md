# CI/CD Pipeline
## Tools
- TravisCI
- SonarCloud
- JaCoCo

## Setup
#### TravisCI
Enable the integration with TravisCI. Start from https://travis-ci.com/ and link with GitHub.

Add a `.travis.yml` file to the project.
Config for java: https://docs.travis-ci.com/user/languages/java/

#### SonarCloud

TBD

#### JaCoCo
Add the JaCoCo gradle plugin to the `build.gradle` file.
Specify the plugin version, enable xml report generation for SonarCloud integration, and set the folder for the reports.

`jacoco {
     toolVersion = "0.8.5"
 }`
 
 `jacocoTestReport {
     reports {
         xml.enabled true
         xml.destination file("${buildDir}/reports/jacoco/jacoco.xml")
         html.destination file("${buildDir}/reports/jacoco/html")
     }
 }`
 
 For TravisCI integration, just add the `./gradlew jacocoTestReport` command to the script section in `.travis.yml`.
 
 `script:
    - ./gradlew jacocoTestReport`
 
 For SonarCloud integration, set the path of the xml report for the `sonar.coverage.jacoco.xmlReportPaths` in `build.gradle`.
 
 `sonarqube {
      properties {
          property "sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/jacoco.xml"
      }
  }`

## Usage
#### TravisCI
A build is triggered on every PR and commit.

#### SonarCloud
Every TravisCI build will run a sonarqube analysis. The result can be found on the SonarCloud site.

#### JaCoCO
Every TravisCI build will create a code coverage report with JaCoCo. Then the result is used by SonarCloud.
You can see the coverage data on the SonarCloud dashboard.

JaCoCo can be run locally as well. Run `gradlew jacocoTestReport` in the project folder.
The results of this can be found under `placeholder-service\build\reports\jacoco`.


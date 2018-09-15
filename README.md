# Canary lib Project

## Prerequisites
1. Jenkins 2.44
    1. Artifactory plugin (2.12.1). 
    1. Other plugins we have installed (and their versions) are shown in doc/screenshot2.png.
1. Artifactory 6.3.2
   1. Needs a "pubtest" user (same password) with the deploy permissions.
   1. Local repository "libs-snapshot-local". 
   1. Local repository "dev-snapshot-local" that allows for anonymous publishing.
1. Replace Artifactory URL in buildSrc/src/main/groovy/plugins/Artifactory.conventions.groovy on line 30 with your own corresponding Artifactory URL.
1. Configure new Jenkins job as shown in doc/screenshot1.png.

## Instructions
### Test #1
1. Run a build. This should produce the correct Repo Path in Artifactory.
### Test #2
1. Uncomment line 47 in buildSrc/src/main/groovy/plugins/Artifactory.conventions.groovy.
1. Run a build. This should produce the "No path found (externally resolved or deleted/overwritten)" Repo Path in Artifactory.
### Test #3
1. Remove Gradle-Artifactory Integration from Jenkins Job.
1. Run a build. This should produce the correct Repo Path in Artifactory.
### Test #4
1. Reset the Jenkins job configuration back to the way it was originally (re-enable Gradle-Artifactory Integration).
1. Change the version of build-info-extractor-gradle to 4.4.5 in buildSrc/build.gradle.
1. Change the Gradle version to 4.7 (or earlier) in gradle/wrapper/gradle-wrapper.properties.
1. Run a build. This should produce the correct Repo Path in Artifactory.
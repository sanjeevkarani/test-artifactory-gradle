package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

public class ArtifactoryConventions implements Plugin<Project> {
    public void apply(Project project) {
        project.apply(plugin: 'com.jfrog.artifactory')
        project.apply(plugin: 'maven-publish')

        def publishRepo

        project.with {
            if (project.hasProperty('ARTIFACTORY_REPO')) {
                publishRepo = ARTIFACTORY_REPO
            } else {
                publishRepo = "gradle-dev-local"
            }

            publishing {
                publications {
                    mavenJava(MavenPublication) {
                        pom.withXml { asNode().appendNode('description', description) }
                    }
                }
            }

            artifactory {
                contextUrl = "http://10.6.17.232/artifactory"
                publish {
                    repository {
                        repoKey = "gradle-dev-local"
                        maven = true
                        username = 'admin'
                        password = 'password'
                    }
                    defaults {
                        publications('mavenJava')
                    }
		    //resolve {
        		//repository {
            		//repoKey = 'gradle-release'
   	         	//username = "admin"
            		//password = "password"
            		//maven = true            
        		//}
   		    //}
                }

                afterEvaluate {
                    logger.info("Setting build number from environment")
                    def buildNumber = System.env["BUILD_TIMESTAMP"]
                    //if (buildNumber) {
                        clientConfig.info.setBuildNumber(buildNumber)
                    //}
                }
            }

            repositories {
                jcenter()
            }
        }
    }
}

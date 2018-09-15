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
                publishRepo = "dev-snapshot-local"
            }

            publishing {
                publications {
                    mavenJava(MavenPublication) {
                        pom.withXml { asNode().appendNode('description', description) }
                    }
                }
            }

            artifactory {
                contextUrl = "https://repocat.mutualofomaha.com/artifactory"
                publish {
                    repository {
                        repoKey = publishRepo
                        maven = true
                        username = 'pubtest'
                        password = 'pubtest'
                    }
                    defaults {
                        publications('mavenJava')
                    }
                }

                afterEvaluate {
                    logger.info("Setting build number from environment")
                    def buildNumber = System.env["BUILD_TIMESTAMP"]
                    if (buildNumber) {
//                        clientConfig.info.setBuildNumber(buildNumber)
                    }
                }
            }

            repositories {
                jcenter()
            }
        }
    }
}

package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

public class JarConventions implements Plugin<Project> {
    public void apply(Project project) {
        project.with {
            apply(plugin: 'java')
            apply(plugin: ArtifactoryConventions)

			publishing {
				publications {
                    mavenJava { from components.java }

                    def artifactFiles = [] as Set
                    mavenJava {
                        artifacts.each { artifact ->
                            artifactFiles << artifact.file
                        }
                        configurations.archives.artifacts.all { archiveArtifact ->
                            if (!artifactFiles.contains(archiveArtifact.file)) {
                                artifact archiveArtifact
                            }
                        }
                    }
				}
			}

            afterEvaluate {
                jar {
                    manifest {
                        attributes("Built-By": System.getenv("BUILD_TAG") ?: System.properties['user.name'],
                        "Implementation-Title": project.name,
                        "Implementation-Vendor": "Mutual of Omaha",
                        "Implementation-Vendor-Id": project.group,
                        "Implementation-Version": project.version)
                    }
                }
            }

            artifactoryPublish {
                properties = ['artifactory.licenses': 'Mutual-of-Omaha-Proprietary']
            }
        }
    }
}


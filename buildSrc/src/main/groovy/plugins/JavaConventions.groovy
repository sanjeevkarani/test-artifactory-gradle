package plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.javadoc.Javadoc

public class JavaConventions implements Plugin<Project> {
    static final String COMPATIBILITY_VERSION = '1.6'
    static final String JAVADOC_TASK_NAME = 'packageJavadoc'
    static final String SOURCES_TASK_NAME = 'packageJavaSource'

    public void apply(Project project) {
        project.plugins.apply('java')

        project.with {
            sourceCompatibility = COMPATIBILITY_VERSION
            targetCompatibility = COMPATIBILITY_VERSION

            javadoc {
                options.addStringOption("sourcepath", "")
            }

            Jar javadocTask = task(JAVADOC_TASK_NAME, type: Jar) {
                classifier = "javadoc"
                from javadoc
            }
            Jar sourcesTask = task(SOURCES_TASK_NAME, type: Jar) {
                classifier = "sources"
                from sourceSets.main.allJava
            }

            artifacts { archives javadocTask, sourcesTask }

            if (JavaVersion.current().isJava8Compatible()) {
                allprojects {
                    tasks.withType(Javadoc) {
                        options.addStringOption('Xdoclint:none', '-quiet')
                    }
                }
            }
        }
    }
}

//def server = Artifactory.server 'ob-arti'
//def rtMaven = Artifactory.newMavenBuild()
//def buildInfo

pipeline {
    agent {
        docker {
            image 'maven:3.6.3-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        ARTIFACTORY_SERVER_ID = 'ob-arti'
        ARTIFACTORY_DEPLOYER_RELEASE_REPO = 'onboard-repo-local'
        ARTIFACTORY_DEPLOYER_SNAPSHOT_REPO = 'onboard-repo-local'
        ARTIFACTORY_RESOLVER_RELEASE_REPO = 'onboard-repo-virt'
        ARTIFACTORY_RESOLVER_SNAPSHOT_REPO = 'onboard-repo-virt'
//        MAVEN_HOME = '/usr/bin/mvn'
    }
    stages {
        stage ('Artifactory configuration') {
            steps {
                rtMavenDeployer(
                        id: "MAVEN_DEPLOYER",
                        serverId: env.ARTIFACTORY_SERVER_ID,
                        releaseRepo: env.ARTIFACTORY_DEPLOYER_RELEASE_REPO,
                        snapshotRepo: env.ARTIFACTORY_DEPLOYER_SNAPSHOT_REPO
                )

                rtMavenResolver(
                        id: "MAVEN_RESOLVER",
                        serverId: env.ARTIFACTORY_SERVER_ID,
                        releaseRepo: env.ARTIFACTORY_RESOLVER_RELEASE_REPO,
                        snapshotRepo: env.ARTIFACTORY_RESOLVER_SNAPSHOT_REPO
                )
            }
        }
        stage('Build') {
            environment {
                // If your using the official maven image, these are probably where it puts it
                MAVEN_HOME = '/usr/share/maven'
//                JAVA_HOME= '/opt/java/openjdk'
            }
            steps {
                rtMavenRun (
//                        tool: "mvn-3.6.3", // Tool name from Jenkins configuration
                        pom: 'pom.xml',
                        goals: '-B -DskipTests clean package',
                        deployerId: "MAVEN_DEPLOYER",
                        resolverId: "MAVEN_RESOLVER"
                )
            }
        }
//        stage('Unit Test') {
//            steps {
//                sh 'mvn test'
//            }
//            post {
//                always {
//                    junit 'target/surefire-reports/*.xml'
//                }
//            }
//        }
    }
}
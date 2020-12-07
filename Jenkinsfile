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
    stages {
        stage ('Artifactory configuration') {
            steps {
                rtServer (
                        id: "ob-arti-server",
                        url: "http://35.242.242.155:8082/artifactory",
                        credentialsId: "talet-ob-artifactory"
                )

                rtMavenDeployer (
                        id: "MAVEN_DEPLOYER",
                        serverId: "ob-arti-server",
                        releaseRepo: "libs-release-local",
                        snapshotRepo: "libs-snapshot-local"
                )

                rtMavenResolver (
                        id: "MAVEN_RESOLVER",
                        serverId: "ob-arti-server",
                        releaseRepo: "libs-release",
                        snapshotRepo: "libs-snapshot"
                )
            }
        }
        stage('Build') {
            steps {
                rtMavenRun (
                        tool: "mvn-3.6.3", // Tool name from Jenkins configuration
                        pom: 'maven-example/pom.xml',
                        goals: '-B -DskipTests clean package',
                        deployerId: "MAVEN_DEPLOYER",
                        resolverId: "MAVEN_RESOLVER"
                )
//                sh 'mvn -B -DskipTests clean package'
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
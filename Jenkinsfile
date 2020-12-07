def server = Artifactory.server 'ob-arti'
def rtMaven = Artifactory.newMavenBuild()
def buildInfo

pipeline {
    agent {
        docker {
            image 'maven:3.6.3-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage ('Artifactory configuration') {
            rtMaven.tool = 'mvn-3.6.3' // Tool name from Jenkins configuration
            rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
            rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo:'libs-snapshot', server: server
            buildInfo = Artifactory.newBuildInfo()
        }
        stage('Build') {
            steps {
                rtMaven.run goals: '-B -DskipTests clean package', buildInfo: buildInfo
//                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
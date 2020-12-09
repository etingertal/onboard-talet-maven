pipeline {
    agent {
        docker {
            image 'maven:3.6.3-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker'
        }
    }
    environment {
        CRED_ID = 'talet-ob-artifactory'
        ARTIFACTORY_SERVER_ID = 'ob-arti'
        ARTIFACTORY_DEPLOYER_RELEASE_REPO = 'onboard-repo-local'
        ARTIFACTORY_DEPLOYER_SNAPSHOT_REPO = 'onboard-repo-local'
        ARTIFACTORY_RESOLVER_RELEASE_REPO = 'onboard-repo-virt'
        ARTIFACTORY_RESOLVER_SNAPSHOT_REPO = 'onboard-repo-virt'
        MAVEN_HOME = '/usr/share/maven' // Need to define maven home of the docker image
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
        stage ('Publish BuildInfo') {
            steps {
                rtBuildInfo (
                        captureEnv: true
                )
                rtPublishBuildInfo (
                        serverId: env.ARTIFACTORY_SERVER_ID
                )
            }
        }
        stage('Build') {
            steps {
                rtMavenRun (
                        pom: 'pom.xml',
                        goals: '-B -DskipTests clean package',
                        deployerId: "MAVEN_DEPLOYER",
                        resolverId: "MAVEN_RESOLVER"
                )
            }
        }
//        stage('Unit Test') {
//            steps {
//                rtMavenRun (
//                        pom: 'pom.xml',
//                        goals: 'test',
//                        deployerId: "MAVEN_DEPLOYER",
//                        resolverId: "MAVEN_RESOLVER"
//                )
//            }
//            post {
//                always {
//                    junit 'target/surefire-reports/*.xml'
//                }
//            }
//        }
        stage('Build Image') {
            steps {
                script {
                    sh 'sudo chmod +x /usr/bin/docker'
                    docker.build('35.242.242.155:8082/onboard-docker-repo-virt' + '/onboard-talet-maven')
                }
            }
        }
//        stage('Push Image') {
//
//        }
    }
}
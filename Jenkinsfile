pipeline {
     agent any
     tools {
             gradle 'Gradle'
         }
    stages {
        stage('Clone project') {
            steps {
                git branch: 'pipeline', credentialsId: 'GIT_CREDENTIAL' ,url: 'https://github.com/camilo1997/Serenity-Gradle.git'
            }
        }

          stage('Build') {
            steps {
//                 sh 'chmod +x gradlew'
                sh './gradlew clean build -x test'
            }
        }
        stage('Run tests') {
            steps {
                sh './gradlew clean test'
            }
        }
    }
    post {
        always {
            publishHTML(target: [
                reportName: 'Serenity',
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: false
            ])
            //sendSlackNotification(currentBuild.result);
        }
    }
}
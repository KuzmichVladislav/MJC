pipeline {
    agent any
    triggers {
        cron('H */8 * * *')
        pollSCM('* * * * *')
    }
    stages {
        stage('Clone the Git') {
            steps {
                git branch: 'module_6', credentialsId: '*****', url: 'https://github.com/KuzmichVladislav/MJC.git'
            }
        }
        stage('Build project') {
            steps {
                withGradle {
                    bat './gradlew clean build'
                }
            }
        }
        stage('SonarQube analysis') {
            steps {
                script {
                    def scannerHome = tool 'sonarqube';
                    withSonarQubeEnv(credentialsId: '****') {
                        bat "${scannerHome}/bin/sonar-scanner \
      -D sonar.projectName=SonarQubeMJCDemo \
      -D sonar.projectKey=SonarQubeMJCDemo \
      -D sonar.projectVersion=1.0 \
      -D sonar.sourceEncoding=UTF-8 \
      -D sonar.core.codeCoveragePlugin=jacoco \
      -D sonar.coverage.jacoco.xmlReportPaths=service/build/reports/jacoco.xml \
      -D sonar.host.url=http://localhost:9000/"
                    }
                }
            }
        }
        stage('JaCoCo Coverage') {
            steps {
                jacoco classPattern: 'service/build/classes/java/main/com/epam/esm/service/impl', execPattern: 'service/build/jacoco/**.exec', sourcePattern: 'service/src/main/java'
            }
        }
        stage('Deploy project') {
            steps {
                deploy adapters: [tomcat9(credentialsId: '*****',
                        path: '', url: 'http://localhost:5050')], contextPath: 'mjc', war: '**/*.war'
            }
        }
    }
}
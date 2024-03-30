pipeline {
    agent any

    tools {
        maven 'M3' // This name should match the one configured jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Run the Maven build
                sh 'mvn clean package'
            }
        }

        stage('Deploy Locally') {
            steps {
                // Assuming your JAR is named according to the standard Maven output
                sh 'java -jar target/Social-Network-0.0.1-SNAPSHOT.jar &'
            }
        }
    }
}

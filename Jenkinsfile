pipeline {
    agent any

    tools {
        // Use Maven, specifying the version if necessary
        maven 'Maven' // or the name of your configured Maven tool
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
                sh 'java -jar target/my-application-0.0.1-SNAPSHOT.jar &'
            }
        }
    }
}

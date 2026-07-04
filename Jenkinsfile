pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew clean test'
            }
            post {
                always {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'build/allure-results']]
                    ])
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
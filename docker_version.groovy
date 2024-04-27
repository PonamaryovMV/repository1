pipeline {
    agent any

    stages {
        stage('Docker version') {
            steps {
                sh "echo \$USER"
                sh 'docker version'
            }
        }
         stage('Docker version #2') {
            steps {
                sh "echo \$USER"
                sh 'docker version'
            }
        }
    }
}

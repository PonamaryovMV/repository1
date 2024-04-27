pipeline {
    agent any

    stages {
        stage('Docker version') {
            steps {
                sh "echo \$USER"
                sh 'docker version'
            }
        }
         stage('CHEKOUT') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/PonamaryovMV/repository1.git'
                sh "pwd"
                sh "ls -la"
                sh 'echo VIPOLNENO'                
            }
        }        
        stage('Number Building') {
            steps {
                script {
                    def buildNumber = env.BUILD_NUMBER
                    echo "Номер текущей сборки: ${buildNumber}"
                }
            }
        }
        stage('BUILD DOCKER IMAGE') {
            steps {
                script {
                    echo "СБОРКА ОБРАЗА"
                    sh "docker build -t myngix:0.0.2 ."
                }
            }
        }
        stage('Docker push to nexus') {
            steps {
                script {
                    echo "ПУШ В НЕКСУС"
                    sh "docker login -u admin -p 123  http://192.168.1.144:8123/repository/docker/"
                    sh "docker tag myngix:0.0.2 192.168.1.144:8123/new_nginx:0.0.2" 
                    sh "docker push 192.168.1.144:8123/new_nginx:0.0.2"
                }
            }
        }
        stage('DELETE LOCAL IMAGE') {
            steps {
                sh "docker image rm 192.168.1.144:8123/new_nginx:0.0.2"
            }
        }
        stage('DELETE WORKSPACE') {
            steps {
                echo 'Deletin workspace'
                deleteDir()
            }
        }
    }
}

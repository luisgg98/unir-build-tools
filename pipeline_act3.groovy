pipeline {
    agent {
       label 'agent01'
    }

    stages {
        stage('Source') {
            steps {
                git 'https://github.com/luisgg98/unir-test.git'
            }
        }
        stage('Log') {
            steps {
                sh 'ls -al'
                sh 'docker ps -a'
                sh 'echo ${PWD}'
                sh ' ls -a ${PWD}'
            }
        }
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/unit_result.xml'
                archiveArtifacts artifacts: 'results/coverage.xml'
            }
        }
        // New stage for API tests
        stage('API tests') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/api_result.xml'
            }
        }
        // New stage for E2E tests
        stage('E2E tests') {
            steps {
                sh 'docker images'
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'e2e-results/*.xml', allowEmptyArchive: true
            }
        }
    }
    post {
        always {
            recordCoverage qualityGates: [[criticality: 'FAILURE', integerThreshold: 85, metric: 'LINE', threshold: 85.0]], tools: [[parser: 'COVERAGE', pattern: 'results/coverage.xml']]
            junit 'results/*.xml'
            cleanWs()
            echo 'Removing all Docker images!'
            sh 'docker rmi $(docker images -q) -f || true'
            echo 'Removing all unused Docker objects!'
            sh 'docker system prune -f || true'
        }
        failure {
            // This step simulates sending an email on failure.
            // Replace `echo` with your email sending step.
            echo "Send an email: Job '${env.JOB_NAME}' build #${env.BUILD_NUMBER} failed."
            emailext (
                subject: "Failed Job: ${currentBuild.fullDisplayName}",
                body: """<p>Job Result: ${currentBuild.currentResult}</p>
                <p>Job Name: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>For more details, visit the URL: ${env.BUILD_URL}</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                to: 'luis.garcia215@comunidadunir.net'
            )
        }
    }
}
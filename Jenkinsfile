def kubernetes_config = """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: ci
    image: eclipseglsp/ci:latest
    tty: true
    resources:
      limits:
        memory: "4Gi"
        cpu: "2"
      requests:
        memory: "4Gi"
        cpu: "2"
    command:
    - cat
    volumeMounts:
    - mountPath: "/home/jenkins"
      name: "jenkins-home"
      readOnly: false
    - mountPath: "/.yarn"
      name: "yarn-global"
      readOnly: false
  volumes:
  - name: "jenkins-home"
    emptyDir: {}
  - name: "yarn-global"
    emptyDir: {}
"""
pipeline {
    agent {
        kubernetes {
            label 'emfcloud-agent-pod'
            yaml kubernetes_config
        }
    }
    options {
        buildDiscarder logRotator(numToKeepStr: '15')
    }
    environment {
        YARN_CACHE_FOLDER = "${env.WORKSPACE}/yarn-cache"
        SPAWN_WRAP_SHIM_ROOT = "${env.WORKSPACE}"
        CHROME_BIN="/usr/bin/google-chrome"
        EMAIL_TO = "ndoschek+eclipseci@eclipsesource.com, eneufeld+eclipseci@eclipsesource.com"
    }
    stages {
        stage('Build server') {
            steps {
                container('ci') {
                    timeout(30){
                        dir('backend/releng/org.eclipse.emfcloud.coffee.parent') {
                            sh 'mvn clean install -U --batch-mode -Dmaven.repo.local=/home/jenkins/.m2/repository'
                        }
                    }
                }
            }
        }
        stage('Build client') {
            steps {
                container('ci') {
                    withCredentials([string(credentialsId: "github-bot-token", variable: 'GITHUB_TOKEN')]) {
                        timeout(30){
                            dir('.') {
                                sh 'yarn build:client'
                            }
                        }
                    }
                }
            }
        }

        stage('Store artefacts') {
            when { branch 'master' }
            steps {
                container('ci') {
                    archiveArtifacts artifacts: 'backend/releng/org.eclipse.emfcloud.coffee.product/target/products/*.zip' , fingerprint: true
                    archiveArtifacts artifacts: 'backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server/target/org.eclipse.emfcloud.coffee.workflow.glsp.server-0.8.0-SNAPSHOT-glsp.jar' , fingerprint: true
                    archiveArtifacts artifacts: 'backend/plugins/org.eclipse.emfcloud.coffee.modelserver/target/org.eclipse.emfcloud.coffee.modelserver-0.8.0-SNAPSHOT-standalone.jar' , fingerprint: true
                    archiveArtifacts artifacts: 'client/browser-app/**', fingerprint: true
                }
            }
        }
    }

    post {
        always {
            // Record & publish checkstyle issues
            recordIssues  enabledForFailure: true, publishAllIssues: true,
            tool: checkStyle(reportEncoding: 'UTF-8'),
            qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]

            // Record maven,java warnings
            recordIssues enabledForFailure: true, skipPublishingChecks:true, tools: [mavenConsole(), java()]
        }
        failure {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build result FAILURE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) FAILURE', to: "${EMAIL_TO}"
                }
            }
        }
        unstable {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build result UNSTABLE: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: true,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} (#${BUILD_NUMBER}) UNSTABLE', to: "${EMAIL_TO}"
                }
            }
        }
        fixed {
            script {
                if (env.BRANCH_NAME == 'master') {
                    echo "Build back to normal: Send email notification to ${EMAIL_TO}"
                    emailext attachLog: false,
                    body: 'Job: ${JOB_NAME}<br>Build Number: ${BUILD_NUMBER}<br>Build URL: ${BUILD_URL}',
                    mimeType: 'text/html', subject: 'Build ${JOB_NAME} back to normal (#${BUILD_NUMBER})', to: "${EMAIL_TO}"
                }
            }
        }
    }
}

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
                    archiveArtifacts artifacts: 'backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server/target/org.eclipse.emfcloud.coffee.workflow.glsp.server-0.1.0-SNAPSHOT-glsp.jar' , fingerprint: true
                    archiveArtifacts artifacts: 'backend/plugins/org.eclipse.emfcloud.coffee.modelserver/target/org.eclipse.emfcloud.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar' , fingerprint: true
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
    }
}

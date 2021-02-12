def kubernetes_config = """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: ci
    image: eclipseglsp/ci:0.0.4
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
                            sh 'mvn clean install -Pfatjar -U --batch-mode -Dmaven.repo.local=/home/jenkins/.m2/repository'
                        }
                    }
                }
            }
        }
        stage('Copy server') {
            steps {
                container('ci') {
                    timeout(30){
                        dir('.') {
                            sh './run.sh -c'
                        }
                    }
                }
            }
        }
        stage('Build client') {
            steps {
                container('ci') {
                    timeout(30){
                        dir('.') {
                            sh './run.sh -f'
                        }
                    }
                }
            }
        }

        stage('Store artefacts') {
            // when { branch 'master' }
            steps {
                container('ci') {
                    archiveArtifacts artifacts: 'backend/releng/org.eclipse.emfcloud.coffee.product/target/products/*.zip' , fingerprint: true
                    archiveArtifacts artifacts: 'web/browser-app/**', fingerprint: true
                }
            }
        }
    }
}

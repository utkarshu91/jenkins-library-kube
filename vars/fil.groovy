#!/usr/bin/env groovy

def call(Map param) {
    def appName = param.appName
    def TESTVAR = param.TESTVAR
    def containerAgent = libraryResource 'agent.yaml'
    def GCR_DNS = "asia.gcr.io"
    def GCR_PROJECT_ID = "nonprod-utility-233414"
    def GCR_APP_PATH_PREFIX = param.appName
    def SERVICE_TYPE = param.service_type
    pipeline {
        agent {
            any {
                label "$appName"
                yaml  '''
apiVersion: v1
kind: Pod
metadata:
labels:
component: ci
spec:
  # this is agent.yaml file
  # Use service account that can deploy to all namespaces
 # cloud: kubernetes
  serviceAccountName: default
  containers:
  - name: maven
    image: gcr.io/cloud-builders/mvn:3.5.0-jdk-8
    command:
    - cat
    tty: true
    volumeMounts:
    - name: agent-data
      mountPath: /root/
  - name: git
    image: gcr.io/cloud-builders/git 
    command:
    - cat
    tty: true
    volumeMounts:
    - name: agent-data
      mountPath: /root/
  restartPolicy: Never
  volumes:
    - name: agent-data
      emptyDir: {}
'''

                defaultContainer 'jnlp'
                }
            }
        stages {
            stage("checkout"){
                steps {
                    container('git'){
                        sh "ls -l"
                        sh "echo $appName"
                        sh "echo $TESTVAR"
                        sh "echo $GCR_PROJECT_ID"
                        sh "echo $GCR_APP_PATH_PREFIX"
                        sh "echo $SERVICE_TYPE"
                        }
                    
                    }

                }
            stage("Maven Build") {
                steps {
                    container('maven') {
                    
                        sh "ls -l"
                        
                        }
                    }
                }   
        }
        
    }
}

#!/usr/bin/env groovy

def call(Map param) {
    pipeline {
        agent {
            any {
                label "uiui"
                defaultContainer 'jnlp'
                yamlFile '''
    apiVersion: v1
    kind: Pod
    metadata:
    labels:
    component: ci
    spec:
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
                }
            }
        stages {
            stage("checkout"){
                steps {
                    container('git'){
                        sh "ls -l"
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

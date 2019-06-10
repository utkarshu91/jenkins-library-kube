#!/usr/bin/env groovy

def call(Map param) {
    def appName = param.appName
    def containerAgent = libraryResource 'kubernetespod.yaml'
    def GCR_DNS = "asia.gcr.io"
    def GCR_PROJECT_ID = "nonprod-utility-233414"
    def GCR_APP_PATH_PREFIX = param.appName
    def SERVICE_TYPE = param.service_type
    pipeline {
        agent {
            any {
                label "$appName"
                defaultContainer 'jnlp'
                yaml "$containerAgent"
                }
            }
        stages {
            stage("checkout"){
                steps {
                    container('git'){
                        sh "ls -l"
                        sh "echo $appName"
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

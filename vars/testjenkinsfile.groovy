#!/usr/bin/env groovy

def call(Map param) {
    def appName = param.appName
    def containerAgent = libraryResource 'kubernetespod.yaml'
    pipeline {
        agent {
            any {
                label "$appName"
                defaultContainer 'jnlp'
                yamlFile "$containerAgent"
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

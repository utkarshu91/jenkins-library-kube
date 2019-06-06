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
                        sh "echo this_is_groovy_jenkinsfile" 
                        sh "echo $appName"
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

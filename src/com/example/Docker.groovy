package com.example

class Docker implements Serializable {
    def script

    Docker(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.echo "building the docker image..."
        script.withCredentials([script.usernamePassword(credentialsId: 'dockerhub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
            script.sh "docker build -t $imageName ."
            script.sh "echo $script.PASS | docker login -u $script.USER --password-stdin"
            script.sh "docker push $imageName"
        }
    }
}


////////// Another example of a groovy classes to drive in the point //////////
// package com.example

// class Utility implements Serializable {
//     def script

//     Utility(script) {
//         this.script = script
//     }

//     def printMessage(String message) {
//         script.echo "Message from Utility: $message"
//     }
// }

//// It is then called in a file vars/buildMessage.groovy like so:////
// import com.example.Utility

// def call(String message) {
//     def utility = new Utility(this)_________OR______return new Utility(this).printMessage(message)
//     utility.printMessage(message)___________|
// }

////// In the Jenkinsfile of your project repo, you would then call it like so: //////
// @Library('my-shared-library') _

// pipeline {
//     agent any
//     stages {
//         stage('Build') {
//             steps {
//                 buildMessage 'Building the application'
//             }
//         }
//     }
// }

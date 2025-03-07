def call() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-repo', passwoordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t mannyops/pipeline-demo-app:groovy1.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push mannyops/pipeline-demo-app:groovy1.0'
    }
}
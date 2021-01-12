stage('Do it') {
    parallel one: {
        node {
            docker.image('python:3.9.1').inside {
                stage('Python Versions') {
                    echo 'ONE BEFORE'
                    sleep 2
                    sh 'python --version'
                    sleep 2
                    echo 'ONE AFTER'
                }
            }
        }
    }, two: {
        node {
            docker.image('python:3.9.1').inside {
                stage('Python Versions') {
                    sleep 2
                    echo 'TWO BEFORE'
                    sleep 2
                    sh 'python --version'
                    echo 'TWO AFTER'
                }
            }
        }
    }
}

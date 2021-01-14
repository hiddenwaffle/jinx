node {
    stage('Setup') {
        sh 'ls -lrt'
        sh 'pwd'
    }
    def image = docker.build("tempdeleteme:47") // ${env.BUILD_ID}
    //docker.image('tempdeleteme:1').inside {
    image.inside {
        stage('Do it') {
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '''
            }
        }
    }
}

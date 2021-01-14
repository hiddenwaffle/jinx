node {
    git branch: 'main', url: 'https://github.com/hiddenwaffle/jinx.git'
    def inage = docker.build("tempdeleteme:${env.BUILD_ID}")
    //docker.image('tempdeleteme:1').inside {
    image.inside {
        stage('Do it') {
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '''
            }
        }
    }
}

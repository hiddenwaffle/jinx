node {
    stage('Setup') {
        checkout scm
        sh 'ls -lrt'
        sh 'pwd'
    }
    def image = docker.build("tempdeleteme:47") // ${env.BUILD_ID}
    //docker.image('tempdeleteme:1').inside {
    image.inside('--add-host=hepburn.local:192.168.65.2 --add-host=peck.local:192.168.65.2') {
        stage('Do it') {
            sh 'ls -lrt'
            sh 'pwd'
            sh 'cat /etc/hosts'
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '''
            }
        }
    }
}

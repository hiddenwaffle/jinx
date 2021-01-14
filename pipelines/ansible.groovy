node {
    stage('Setup') {
        checkout scm
        sh 'ls -lrt'
        sh 'pwd'
    }
    def image = docker.build("tempdeleteme:47") // ${env.BUILD_ID}
    //docker.image('tempdeleteme:1').inside {
    image.inside {
        stage('Do it') {
            sh 'ls -lrt'
            sh 'pwd'
            sh 'ping -c 1 hepburn.local'
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''poetry install'''
                sh '''poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '''
            }
        }
    }
}

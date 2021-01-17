node {
    stage('Setup') {
        checkout scm
    }
    def image = docker.build("tempdeleteme:latest")
    image.inside('--add-host=hepburn.local:192.168.65.2 --add-host=peck.local:192.168.65.2') {
        stage('Do it 1') {
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '''
            }
        }
        stage('Sleep') {
            sleep 60
        }
        stage('Do it 2') {
            withCredentials([sshUserPrivateKey(credentialsId: 'peck-pk', keyFileVariable: 'PECK_PK')]) {
                sh '''poetry run ansible -i inventory/hosts.yml roman -l peck.local -a 'free -h' --extra-vars "ansible_ssh_private_key_file=${PECK_PK}" '''
            }
        }
    }
}

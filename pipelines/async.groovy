doIt('free -h') { command ->
    stage('Do it 1') {
        sh 'poetry run ansible-playbook ' +
           '-i inventory/hosts.yml ' +
           '-l hepburn.local ' +
           '-e "ansible_ssh_private_key_file=${HEPBURN_PK}" ' +
           'playbooks/async.yml'
    }
}

//-------------------------------------------------------------------------//
// Utility functions
//-------------------------------------------------------------------------//

void doIt(command, fn) {
    node {
        stage('Setup') {
            checkout scm
        }
        image = docker.build('tempdeleteme:latest')
        image.inside('--add-host=hepburn.local:192.168.65.2 --add-host=peck.local:192.168.65.2') {
            withCredentials([
                sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')
            ]) {
                fn(command)
            }
        }
    }
}

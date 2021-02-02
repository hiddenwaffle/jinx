doIt('free -h') { command ->
    stage('Do it 1') {
        sh "poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a '${command}' " +
           '--extra-vars "ansible_ssh_private_key_file=${HEPBURN_PK}" '
    }
    stage('Do it 2') {
        sh "poetry run ansible -i inventory/hosts.yml roman -l peck.local -a '${command}' " +
           '--extra-vars "ansible_ssh_private_key_file=${PECK_PK}" '
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
                sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK'),
                sshUserPrivateKey(credentialsId: 'peck-pk', keyFileVariable: 'PECK_PK')
            ]) {
                fn(command)
            }
        }
    }
}

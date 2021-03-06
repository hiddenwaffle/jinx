// See lftp.md for more information
// This pipeline assumes that the information in the doc is still valid

doIt {
    stage('Do it') {
        sh """
            poetry run ansible-playbook playbooks/lftp.yml
        """
    }
}

//-------------------------------------------------------------------------//
// Utility functions
//-------------------------------------------------------------------------//

void doIt(fn) {
    node {
        stage('Setup') {
            checkout scm
        }
        image = docker.build('tempdeleteme:latest')
        image.inside('--add-host=sftp_server:192.168.65.2') {
            fn()
        }
    }
}

// See lftp/lftp.md for more information
// This pipeline assumes that the information in the doc is still valid

doIt {
    stage('Do it') {
        sh """
            #!/usr/bin/env bash
            date
            type lftp
            date
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
        image = docker.build('lftp_client:latest', '-f lftp/Dockerfile.lftp')
        image.inside('--add-host=sftp_server:192.168.65.2') {
            fn()
        }
    }
}

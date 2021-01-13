# jinx

## Jenkins

From the repository root:

```
docker run --user 0 --rm --privileged -p 8080:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v $PWD/jenkins_home:/var/jenkins_home jenkins/jenkins:lts
```

### Plugins

1. [Pipeline](https://github.com/jenkinsci/workflow-aggregator-plugin)
1. [Docker Pipeline](https://github.com/jenkinsci/docker-workflow-plugin)
1. [Git](https://github.com/jenkinsci/git-plugin)

### Credentials

* Go to the [global credentials store](http://localhost:8080/credentials/store/system/domain/_/)
* Add a secret text named `credential1`
* Add a private key named `hepburn-pk` using the contents of `.vagrant/machines/hepburn/virtualbox/private_key`
* Add a private key named `peck-pk` using the contents of `.vagrant/machines/peck/virtualbox/private_key`

## Ansible

### Manual steps

Add to /etc/hosts

```
127.0.0.1 hepburn.local
127.0.0.1 peck.local
```

### Example commands

Not all of these still work. A lot of the referenced files have been renamed or moved.

```
poetry run ansible -i hosts.yml roman -m ping
poetry run ansible -i hosts.yml roman -a 'free -h'
poetry run ansible -i hosts.yml roman -a 'hostname'
poetry run ansible -i hosts.yml roman -a 'hostname' -f 1
poetry run ansible -i hosts.yml roman -m setup
poetry run ansible -i hosts.yml roman -m stat -a 'path=/etc/environment'
poetry run ansible -i hosts.yml roman -m copy -a 'src=./README.md dest=/tmp'
poetry run ansible -i hosts.yml roman -m fetch -a 'src=/etc/hosts dest=/tmp'

poetry run ansible -i hosts.yml roman -B 100 -P 0 -a "sleep 5"
poetry run ansible -i hosts.yml peck.local -m async_status -a "jid=158428745974.24136"

poetry run ansible -i hosts.yml roman -b -a 'tail /var/log/messages'
poetry run ansible -i hosts.yml roman -b -m shell -a 'tail /var/log/messages | grep ansible-command | wc -l'

poetry run ansible-playbook playbooks/test_set_variable.yml

poetry run ansible -i 'localhost,' localhost --connection=local -m debug -a 'msg="Hello World"'

poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'who am i' --private-key=.vagrant/machines/hepburn/virtualbox/private_key
```

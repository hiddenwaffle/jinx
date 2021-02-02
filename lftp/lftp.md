# lftp

Start an SFTP server with one directory, `/upload` and username/password `foo/pass`

```
docker run --rm -p 22:22 atmoz/sftp foo:pass:::upload
```

Start another container and run LFTP in it

```
docker run -it --rm python:3.9.1 bash
```

In the LFTP container, point the domain name to host IP and install LFTP

```
echo '192.168.65.2 testserver' >> /etc/hosts
apt update
apt install lftp
```

Test the connection

```
lftp sftp://foo@testserver
```

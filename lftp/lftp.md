# lftp

Start an SFTP server with one directory, `/upload` and username/password `foo/pass`

```
docker run --rm -p 22:22 atmoz/sftp foo:pass:::upload
```

Build the LFTP image, if necessary

```
docker build -f Dockerfile.lftp -t lftp_client .
```

Start an LFTP container that can connect to the host

```
docker run -it --rm --add-host=sftp_server:192.168.65.2 lftp_client bash
```

Test the connection

```
mkdir ~/.ssh
chmod 700 ~/.ssh
ssh-keyscan -H sftp_server >> ~/.ssh/known_hosts
lftp sftp://foo@sftp_server
```

Example: Run a mirror command from the shell
* This copies the contents of /tmp/cheeseburgers into /upload/2021
* It creates the directory if it does not exist

```
lftp -c 'open -e "mirror --parallel=20 -R /tmp/cheeseburgers/ /upload/2021" sftp://foo@sftp_server'
```

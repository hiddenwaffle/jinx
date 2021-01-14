FROM python:3.9.1

WORKDIR /work

# Set up Poetry
RUN curl -sSL https://raw.githubusercontent.com/python-poetry/poetry/master/get-poetry.py | python -
ENV PATH "$PATH:/root/.poetry/bin"

# Fake networking to allow Ansible to SSH to the Vagrant instances
# WARNING: These values can/will probably change
RUN \
    echo "192.168.65.2 hepburn.local" >> /etc/hosts && \
    echo "192.168.65.2 peck.local" >> /etc/hosts && \
    mkdir ~/.ssh && chmod 700 ~/.ssh && \
    echo "[hepburn.local]:2222 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBO6WMqyabG5PbImHJTuuNaaVa2glHxrScWDBrFmROenvA4miDHPx5Q5ZqrSqjwJ4CtneDosKgqULVfm09S0hLGg=" >> ~/.ssh/known_hosts && \
    echo "[peck.local]:2200 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBKTORHuY2wx1VjenGYm8y6q3cyxz7IfFYDAPdZDnLJyV/9OJhMLZXd8jHg+XsrnNS25EoTg9mNWhDSFQCiFJdaA=" >> ~/.ssh/known_hosts

# Set up dependencies
COPY pyproject.toml poetry.lock ./
RUN \
    poetry config virtualenvs.create false && \
    poetry install

RUN cat /etc/hosts

FROM python:3.9.1

WORKDIR /work

# Set up Poetry
RUN curl -sSL https://raw.githubusercontent.com/python-poetry/poetry/master/get-poetry.py | python -
ENV PATH "$PATH:/root/.poetry/bin"

# Fake networking to allow Ansible to SSH to the Vagrant instances
# WARNING: These values can/will probably change
# TODO: Use ssh-keyscan to pull it dynamically?
RUN \
    mkdir ~/.ssh && chmod 700 ~/.ssh && \
    echo "[hepburn.local]:2222 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBGu8Qb0xqN80d/H/DN6rToJthkFQpt1IrtE2ZruzmjT4K4/vByHjocokSkTNr/+p6adUMem26lvl+wgzBNDAAcc=" >> ~/.ssh/known_hosts && \
    echo "[peck.local]:2200 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBLQb+dEowvaiha5dISUMoiRf7502TnOifwWxpxAdgJDlizy0VEJUUSzEEPmFKDRFDNS147cIHTEVTPui9eiNZkQ=" >> ~/.ssh/known_hosts

# Set up dependencies
COPY pyproject.toml poetry.lock ./
RUN \
    poetry config virtualenvs.create false && \
    poetry install

RUN cat /etc/hosts

FROM node:16-bullseye as build-stage
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && apt-get install -y git libxkbfile-dev libsecret-1-dev bash libsecret-1-0  maven openjdk-11-jdk

# Theia dependencies
RUN apt-get -y install --no-install-recommends \
    software-properties-common \
    libxkbfile-dev \
    libsecret-1-dev \
    build-essential libssl-dev


WORKDIR /coffee-editor

COPY ./backend ./backend
COPY ./client ./client

WORKDIR /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.parent/
RUN mvn clean verify

WORKDIR /coffee-editor/client

RUN yarn install &&\
    yarn copy:servers &&\
    yarn production &&\
    yarn autoclean --init && \
    echo *.ts >> .yarnclean && \
    echo *.ts.map >> .yarnclean && \
    echo *.spec.* >> .yarnclean && \
    yarn autoclean --force && \
    yarn cache clean

FROM node:16-bullseye-slim as production-stage
ENV DEBIAN_FRONTEND noninteractive


# Theia dependencies/Java
RUN apt-get update \
    && apt-get -y install --no-install-recommends \
    software-properties-common \
    libxkbfile-dev \
    libsecret-1-dev openjdk-11-jdk \
    build-essential libssl-dev  wget gnupg git

# C/C++ dependencies
RUN add-apt-repository 'deb http://apt.llvm.org/bullseye/ llvm-toolchain-bullseye-14 main'
RUN wget -O - https://apt.llvm.org/llvm-snapshot.gpg.key | apt-key add -
RUN apt-get update &&\
    apt-get -y install  clangd-14 &&\
    apt-get purge -y && \
    apt-get clean

RUN update-alternatives --install /usr/bin/clangd clangd /usr/bin/clangd-14 100


# Make readable for root only
RUN chmod -R 750 /var/run/

# Create a user to not run the container as root
RUN useradd -ms /bin/bash theia
WORKDIR /coffee-editor
COPY --chown=theia:theia --from=build-stage /coffee-editor/client ./client

WORKDIR /coffee-editor

# Copy favicon
RUN cp ./client/favicon.ico ./client/browser-app/lib
RUN sed -i 's/<\/head>/<link rel="icon" href="favicon.ico" \/><\/head>/g' client/browser-app/lib/index.html

USER theia

# Set up git repo in workspace for comparison
WORKDIR /coffee-editor/client/workspace/SuperBrewer3000
RUN git config --global user.name "Test User"
RUN git config --global user.email "test@example.com"
RUN git init
RUN git add *
RUN git commit -m "init"


WORKDIR /coffee-editor/client/browser-app/
EXPOSE 3000
ENTRYPOINT [ "node", "/coffee-editor/client/browser-app/src-gen/backend/main.js" ]
CMD [ "/coffee-editor/client/workspace/SuperBrewer3000", "--hostname=0.0.0.0","--port=3000","--plugins=local-dir:/coffee-editor/client/browser-app/plugins"]

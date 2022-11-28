# Setup dev environment
FROM node:16-bullseye as build

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && \
    apt-get install -y git bash maven openjdk-11-jdk

# Theia build dependencies
RUN  apt-get -y install --no-install-recommends \
    software-properties-common \
    libxkbfile-dev \
    libsecret-1-dev \
    build-essential libssl-dev


# Build the Java backend
FROM build AS backend

WORKDIR /coffee-editor

COPY ./backend ./backend

WORKDIR /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.parent/

RUN mvn clean verify

# Build frontend
FROM build AS frontend

WORKDIR /coffee-editor

COPY ./client ./client

WORKDIR /coffee-editor/client

RUN yarn install &&\
    yarn production &&\
    yarn autoclean --init && \
    echo *.ts >> .yarnclean && \
    echo *.ts.map >> .yarnclean && \
    echo *.spec.* >> .yarnclean && \
    yarn autoclean --force && \
    yarn cache clean

# Build production image
FROM node:16-bullseye-slim as production
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
COPY --chown=theia:theia  --from=frontend /coffee-editor/client ./client

# Copy backend jars
## ModelServer
COPY --from=backend /coffee-editor/backend/plugins/org.eclipse.emfcloud.coffee.modelserver/target/org.eclipse.emfcloud.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar \
    /coffee-editor/client/coffee-servers/servers/org.eclipse.emfcloud.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar
## ModelServer Log4JConfig
COPY --from=backend /coffee-editor/backend/plugins/org.eclipse.emfcloud.coffee.modelserver/log4j2-embedded.xml \
    /coffee-editor/client/coffee-servers/servers/model-server-log4j2-embedded.xml
## GLSP Server
COPY --from=backend /coffee-editor/backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server/target/org.eclipse.emfcloud.coffee.workflow.glsp.server-0.1.0-SNAPSHOT-glsp.jar \
    /coffee-editor/client/coffee-servers/servers/org.eclipse.emfcloud.coffee.workflow.glsp.server-0.1.0-SNAPSHOT-glsp.jar
## Workflow LSP
COPY --from=backend /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.product/target/products/org.eclipse.emfcloud.coffee.product.workflow.dsl/linux/gtk/x86_64 \
    /coffee-editor/client/coffee-servers/servers/wf-ls
## Workflow Analyzer
COPY --from=backend /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.product/target/products/org.eclipse.emfcloud.coffee.product.workflow.analyzer/linux/gtk/x86_64 \
    /coffee-editor/client/coffee-servers/servers/wf-analyzer
## Java Codegenerator
COPY --from=backend /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.product/target/products/org.eclipse.emfcloud.coffee.product.codegen/linux/gtk/x86_64 \
    /coffee-editor/client/coffee-servers/servers/java-codegen
## CPP Codegenerator
COPY --from=backend /coffee-editor/backend/releng/org.eclipse.emfcloud.coffee.product/target/products/org.eclipse.emfcloud.coffee.product.codegen.cpp/linux/gtk/x86_64 \
    /coffee-editor/client/coffee-servers/servers/cpp-codegen
## Model
COPY --from=backend /coffee-editor/backend/plugins/org.eclipse.emfcloud.coffee.model/target/org.eclipse.emfcloud.coffee.model-0.1.0-SNAPSHOT.jar \
    /coffee-editor/backend/plugins/org.eclipse.emfcloud.coffee.model/target/org.eclipse.emfcloud.coffee.model-0.1.0-SNAPSHOT.jar

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
